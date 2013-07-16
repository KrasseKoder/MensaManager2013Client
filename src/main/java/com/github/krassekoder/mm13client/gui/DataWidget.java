package com.github.krassekoder.mm13client.gui;

import com.github.krassekoder.mm13client.gui.data.ProductView;
import com.github.krassekoder.mm13client.gui.data.SalesView;
import com.trolltech.qt.gui.QAbstractItemView;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStackedLayout;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 *
 *
 */
public class DataWidget extends QWidget {

    private QStackedLayout la;
    private QVBoxLayout vLa1;
    private QHBoxLayout hLa1;
    private QPushButton productsButton, salesButton;
    private QAbstractItemView products, sales;

    public DataWidget(QWidget qw) {
        super(qw);
        setupUi();
    }

    /**
     * This method sets up the User Interface including Layouts, Forms,
     * Buttons, etc.
     */
    private void setupUi() {
        setLayout(vLa1 = new QVBoxLayout(this));
        vLa1.addLayout(la = new QStackedLayout(this));

        la.addStackedWidget(products = new ProductView(this));
        la.addStackedWidget(sales = new SalesView(this));
        la.setCurrentIndex(la.indexOf(products));

        vLa1.addLayout(hLa1= new QHBoxLayout());
        hLa1.addWidget(productsButton= new QPushButton(tr("Show Products"), this));
        productsButton.setIcon(new QIcon("classpath:com/github/krassekoder/data.png"));
        hLa1.addWidget(salesButton= new QPushButton(tr("Show Sales"), this));
        salesButton.setIcon(new QIcon("classpath:com/github/krassekoder/data.png"));

        productsButton.pressed.connect(this,"showProducts()");
        salesButton.pressed.connect(this,"showSales()");
    }

    private void showProducts() {
        la.setCurrentWidget(products);
    }

    private void showSales() {
        la.setCurrentWidget(sales);
        /*QMessageBox.information(this, tr("Buy MensaManagerPlus"), tr("Please "
                + "visit the krassekoder-githubpage wiki to purchase the unique "
                + "MensaManager2013Plus package including hilarious new features "
                + "to maximize your profit! The first ten visitors get a gratis"
                + "-key. So be fast!"));*/
    }
}
