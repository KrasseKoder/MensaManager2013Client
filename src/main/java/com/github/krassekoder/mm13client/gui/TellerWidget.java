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
import com.trolltech.qt.internal.QtJambiDebugTools;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TellerWidget extends QWidget {

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
        list.setHorizontalHeader(new TripleHeader(Qt.Orientation.Horizontal, list));
        list.setHorizontalHeaderLabels(labels);

        qh1.addWidget(qw = new QWidget());

        qh4.addWidget(price = new QTableWidget(0, 3));
        price.verticalHeader().setVisible(false);
        price.setHorizontalHeader(new TripleHeader(Qt.Orientation.Horizontal, price));
        price.setHorizontalHeaderLabels(labels);
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
        MainWindow.instance.ChangeToPay();
    }

    public void request() {
        try {
            enterFoodList(Packet1FoodList.instance.request(product.text()));
        } catch(Packet.InvalidPacketException ex) {
            Logger.getLogger(TellerWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

        value += Double.parseDouble(list.item(0,2).text());
        updateLabel();
        product.selectAll();
    }

    private void copyRow(int row) {
        int r = price.rowCount();
        price.insertRow(r);
        price.setItem(r, 0, new CustomItem(list.item(row, 0).text()));
        price.setItem(r, 1, new CustomItem(list.item(row, 1).text()));
        price.setItem(r, 2, new CustomItem(list.item(row, 2).text()));
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
