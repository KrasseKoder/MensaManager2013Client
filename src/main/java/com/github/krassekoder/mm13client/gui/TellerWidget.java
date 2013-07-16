package com.github.krassekoder.mm13client.gui;

import com.github.krassekoder.mm13client.network.Packet;
import com.github.krassekoder.mm13client.network.Packet1FoodList;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QHeaderView;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QResizeEvent;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TellerWidget extends QWidget {

    private static TellerWidget instance;

    private class TripleHeader extends QHeaderView {

        public TripleHeader(Qt.Orientation orientation, QWidget parent) {
            super(orientation, parent);
            setResizeMode(QHeaderView.ResizeMode.Fixed);
            setStretchLastSection(true);
        }

        @Override
        protected void resizeEvent(QResizeEvent qre) {
            super.resizeEvent(qre);
            adaptSections();
        }

        public void adaptSections() {
            int firstSize = sectionSizeHint(0);
            int halfSize = (width() - firstSize) / 2;
            resizeSection(0, firstSize);
            resizeSection(1, halfSize);
            resizeSection(2, halfSize);
        }
    }

    private class CustomItem extends QTableWidgetItem {
        public CustomItem(String text) {
            super(text);
            setFlags(Qt.ItemFlag.ItemIsEnabled);
        }
    }


    /*package*/ class ProductDisplay extends QLabel {

        private String id, name;
        private double price;
        private int count;

        public ProductDisplay(String id, String name, double price, int count, QWidget parent) {
            super(parent);
            this.id = id;
            this.name = name;
            this.price = price;
            this.count = count;
            updateText();
        }

        private void updateText() {
            setText(String.format(tr("<table><tr><td width=\"80%%\">"
                    + "<span style=\"font-weight: bold;\">%2$s</span>&nbsp;(%1$s)</td>"
                    + "<td style=\"width: 20%%; text-align: right;\">&middot;%4$d</td></tr><tr>"
                    + "<td style=\"padding-left: 20%%\">%3$.2f$&nbsp;&middot;&nbsp;%4$d&nbsp;=</td>"
                    + "<td style=\"text-align: end;\">%5$.2f$</td></table>"), id, name, price, count, price * count));
        }

        public String getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        public int getCount() {
            return count;
        }

        public void add() {
            count++;
            updateText();
        }

        public ProductDisplay clone(QWidget parent) {
            return new ProductDisplay(id, name, price, count, parent);
        }
    }

    private QTableWidget price, list;
    private QLineEdit product;
    private QPushButton pay;
    private QLabel ql, ql2;
    private QHBoxLayout qh1, qh2, qh3,qh4;
    private QVBoxLayout qv1, qv2;
    private QWidget qw;
    private QLabel pLabel;
    private double value = 0;

    /**
     * The TellerWidget is used to give up the order. It shows what the order
     * contains and how much it costs. This Widget contains a button to place
     * the order and pay it.
     */
    public TellerWidget(QWidget qw) {
        super(qw);
        instance = this;

        setupUi();
    }

    //This method sets up the User Interface including Layouts, Forms, Buttons, etc.
    private void setupUi() {
        setLayout(qv1 = new QVBoxLayout());
        qv1.addLayout(qh1 = new QHBoxLayout());
        qh1.addLayout(qv2 = new QVBoxLayout());
        qv2.addLayout(qh2 = new QHBoxLayout());
        qh2.addWidget(ql2 = new QLabel(tr("Product:"), this));
        qh2.addWidget(product = new QLineEdit(this));
        qv1.addWidget(pay = new QPushButton(tr("&Continue to Pay..."), this));
        pay.setIcon(new QIcon("classpath:com/github/krassekoder/go-next.png"));
        qv2.addLayout(qh4 = new QHBoxLayout());

        qh4.addWidget(list = new QTableWidget(0, 3));
        ArrayList<String> labels = new ArrayList<String>(3);
        labels.add(tr("ID"));
        labels.add(tr("Name"));
        labels.add(tr("Price"));
        list.verticalHeader().setVisible(false);
        list.verticalHeader().setResizeMode(QHeaderView.ResizeMode.ResizeToContents);
        list.setHorizontalHeader(new TripleHeader(Qt.Orientation.Horizontal, list));
        list.setHorizontalHeaderLabels(labels);

        qh1.addWidget(qw = new QWidget());

        qh4.addWidget(price = new QTableWidget(0, 1));
        price.verticalHeader().setVisible(false);
        price.verticalHeader().setResizeMode(QHeaderView.ResizeMode.ResizeToContents);
        price.horizontalHeader().setVisible(false);
        price.horizontalHeader().setStretchLastSection(true);

        qv2.addLayout(qh3 = new QHBoxLayout());
        qh3.addStretch();
        qh3.addWidget(pLabel = new QLabel(tr("0.00$")));

        pay.clicked.connect(this, "GoToPay()");
        product.textEdited.connect(this, "request()");
        product.returnPressed.connect(this, "moveFoodItem()");
        list.cellDoubleClicked.connect(this, "copyRow(int)");
    }

    //This method calls 'ChangeToPay()' in "MainWindow".
    private void GoToPay() {
        PayWidget w = PayWidget.instance;
        w.resetList();
        for(int i = 0; i < price.rowCount(); i++)
            w.insert((ProductDisplay)price.cellWidget(i, 0));
        MainWindow.instance.changeToPay();
    }

    public void request() {
        try {
            enterFoodList(Packet1FoodList.instance.request(product.text()));
        } catch(Packet.InvalidPacketException ex) {
            Logger.getLogger(TellerWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateView() {
        instance.request();
    }

    //Method for entering the FoodList
    private void enterFoodList(List<Packet1FoodList.FoodItem> items) {
        while(list.rowCount() > 0)
            list.removeRow(0);
        for(Packet1FoodList.FoodItem i : items) {
            int row = list.rowCount();
            list.insertRow(row);
            list.setItem(row, 0, new CustomItem(i.id));
            list.setItem(row, 1, new CustomItem(i.name));
            list.setItem(row, 2, new CustomItem(i.price));
        }

        ((TripleHeader)list.horizontalHeader()).adaptSections();
    }

    private void moveFoodItem() {
        if(list.rowCount() < 1)
            return;

        copyRow(0);
        product.selectAll();
    }

    private ProductDisplay findId(String id) {
        for(int i = 0; i < price.rowCount(); i++) {
            if(((ProductDisplay)price.cellWidget(i, 0)).getId().equals(id))
                return (ProductDisplay)price.cellWidget(i, 0);
        }
        return null;
    }

    private void copyRow(int row) {
        int r = price.rowCount();

        ProductDisplay display = findId(list.item(row, 0).text());
        if(display == null) {
           price.insertRow(r);
           price.setCellWidget(r, 0, new ProductDisplay(list.item(row, 0).text(), list.item(row, 1).text(), Double.parseDouble(list.item(row,2).text()), 1, price));
        } else {
            display.add();
        }

        value += Double.parseDouble(list.item(row,2).text());
        updateLabel();

    }

    //This method refreshes the 'moneylabel' and sets attribute value as text.
    private void updateLabel() {
        pLabel.setText(String.format(tr("%1$.2f$"), value));
    }

    //This method resets the value attribute.
    public void resetValue(){
        value = 0.00;
        updateLabel();
    }

    //This method returns the attribute value.
    public double giveValue()
    {
        value = Math.round( value * 100d ) / 100d;
        return value;
    }

    //This method calls every method necessary to accomplish a new purchase.
    public void newPurchase()
    {
       resetValue();
       product.clear();
       while(price.rowCount() > 0)
            price.removeRow(0);
       request();
    }
}
