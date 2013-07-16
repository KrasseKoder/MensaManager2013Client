package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/*
 * The "VoucherWidget" allows the teller to create a new voucher with different 
 * values.
 */
public class VoucherWidget extends QWidget{
    
    private QVBoxLayout qv1;
    private QHBoxLayout qh1;
    private QPushButton confirm;
    private QPushButton button2,valueFive;
    private QHBoxLayout qh2;
    private QPushButton valueTen;
    private QPushButton valueTwenty;
    private QPushButton valueFifty;
    private QHBoxLayout qh3;
    private QLabel nameLabel;
    private QLineEdit name;
    private QHBoxLayout qh4;
    private QPushButton print;
    private QLabel voucher;
    
    
    public VoucherWidget(QWidget qw)
    {
        super(qw);
        setupUi();
    }
    
   /* 
    * This method sets up the User Interface including Layouts, Forms,
    * Buttons, etc.
    */
    private void setupUi()
    {
        setLayout(qv1= new QVBoxLayout());
        qv1.addLayout(qh3 = new QHBoxLayout(this));
        qh3.addWidget(nameLabel= new QLabel(tr("Enter name:"),this));
        qh3.addWidget(name= new QLineEdit(this));
        qv1.addLayout(qh1= new QHBoxLayout(this));
        qv1.addLayout(qh2= new QHBoxLayout(this));
        qh2.addWidget(valueTwenty= new QPushButton(new QIcon("classpath:com/github/krassekoder/bills.png"), tr("Add 20$"), this));
        qh2.addWidget(valueFifty= new QPushButton(new QIcon("classpath:com/github/krassekoder/bills.png"), tr("Add 50"), this));
        qh1.addWidget(valueFive= new QPushButton(new QIcon("classpath:com/github/krassekoder/bills.png"), tr("Add 5$"), this));
        qh1.addWidget(valueTen= new QPushButton(new QIcon("classpath:com/github/krassekoder/bills.png"), tr("Add 10$"), this));
        qv1.addWidget(voucher= new QLabel(tr("This is the voucher dummy!")));
        qv1.addLayout(qh4= new QHBoxLayout(this));
        qh4.addWidget(print= new QPushButton(new QIcon("classpath:com/github/krassekoder/document-print.png"), tr("Print Voucher..."), this));
        qh4.addWidget(confirm= new QPushButton(new QIcon("classpath:com/github/krassekoder/go-next.png"), tr("Confirm Voucher..."), this));
        print.clicked.connect(this, "printVoucher()");
        confirm.clicked.connect(this, "confirm()");

    }
    
    private void printVoucher()
    {
        QMessageBox.information(this, tr("Connect printer"), tr("There is no printer connected!"));
    }
    
    private void confirm()
    {
        QMessageBox.information(this, tr("Voucher confirmed"), tr("The voucher is now ready to be used"));
        MainWindow.instance.showTeller();
    }
    
}
