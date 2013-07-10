package com.github.krassekoder.mm13client.gui;

import com.github.krassekoder.mm13client.network.Packet;
import com.github.krassekoder.mm13client.network.Packet1FoodList;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TellerWidget extends QWidget {

    private QTableWidget price, list;
    private QLineEdit product;
    private QPushButton pay;
    private QLabel ql, ql2;
    private QHBoxLayout qh1, qh2, qh3,qh4;
    private QVBoxLayout qv1, qv2;
    private QWidget qw;
    private QLabel pLabel;
    private double value = 0;
    public String amount;

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
        qv2.addLayout(qh4 = new QHBoxLayout());
        qh4.addWidget(list = new QTableWidget(0, 3));
        ArrayList<String> labels = new ArrayList<String>(3);
        labels.add(tr("ID"));
        labels.add(tr("Name"));
        labels.add(tr("Prize"));
        list.setHorizontalHeaderLabels(labels);
        qh1.addWidget(qw = new QWidget());
        qh4.addWidget(price = new QTableWidget(0, 3));
        price.setHorizontalHeaderLabels(labels);
        qv2.addLayout(qh3 = new QHBoxLayout());
        qh3.addStretch();
        qh3.addWidget(pLabel = new QLabel(tr("0.00$")));

        pay.clicked.connect(this, "GoToPay()");
        product.textEdited.connect(this, "request()");
        product.returnPressed.connect(this, "moveFoodItem()");
    }

    private void GoToPay() {
        MainWindow.instance.ChangeToPay();
        saveAmount();
        newPurchase();
        
    }

    private void request() {
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
            list.setItem(row, 0, new QTableWidgetItem(i.id));
            list.setItem(row, 1, new QTableWidgetItem(i.name));
            list.setItem(row, 2, new QTableWidgetItem(i.price));
        }
    }

    private void moveFoodItem() {
        if(list.rowCount() < 1)
            return;
        int row = price.rowCount();
        price.insertRow(row);
        price.setItem(row, 0, new QTableWidgetItem(list.item(0, 0).text()));
        price.setItem(row, 1, new QTableWidgetItem(list.item(0, 1).text()));
        price.setItem(row, 2, new QTableWidgetItem(list.item(0, 2).text()));

        value += Double.parseDouble(list.item(0,2).text());
        updateLabel();
    }

    private void updateLabel() {
        pLabel.setText(String.format(tr("%1$.2f$"), value));
    }
    
    private void resetLabel() {
        value= 0.00;
        pLabel.setText("0.00");
    }
    
    private void saveAmount()
    {
        amount=pLabel.text();
    }
    
    private void newPurchase()
    {
       resetLabel();
       while(price.rowCount() > 0)
            price.removeRow(0); 
    }
}
