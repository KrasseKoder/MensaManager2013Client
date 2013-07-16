package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLineEdit;
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
    private QLineEdit value;
    private QPushButton button2,valueFive;
    private QHBoxLayout qh2;
    private QPushButton valueTen;
    
    
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
        qv1.addWidget(value= new QLineEdit(this));
        qv1.addLayout(qh1= new QHBoxLayout(this));
        qh1.addWidget(confirm= new QPushButton(tr("Create Voucher"),this));
        qh1.addWidget(button2 = new QPushButton(tr("button2"),this));
        qv1.addLayout(qh2= new QHBoxLayout(this));
        qh2.addWidget(valueFive= new QPushButton(new QIcon("classpath:com/github/krassekoder/bills.png"), tr("Add 5$"), this));
        qh2.addWidget(valueTen= new QPushButton(new QIcon("classpath:com/github/krassekoder/bills.png"), tr("Add 5$"), this));

    }
    
}
