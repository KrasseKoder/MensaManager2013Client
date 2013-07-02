package com.github.krassekoder.mm13client.gui;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QListView;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTableView;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
public class TellerWidget extends QWidget{

 private QTableView price;
 private QListView qlv;
 private QLineEdit product;
 private QPushButton pay;
 private QLabel ql, ql2;
 private QHBoxLayout qh1,qh2,qh3;
 private QVBoxLayout qv1,qv2;
 private QWidget qw;
 private QLabel pLabel;
 
 
 public TellerWidget(QWidget qw)
 {
     super(qw);
     setupUi();
 }
 
 private void setupUi() {
     setLayout(qv1=new QVBoxLayout());
     qv1.addLayout(qh1 = new QHBoxLayout());
     qh1.addLayout(qv2 = new QVBoxLayout());
     qv2.addLayout(qh2 = new QHBoxLayout());
     qh2.addWidget(ql2 = new QLabel(tr("Id:"), this));
     qh2.addWidget(product = new QLineEdit(this));
     qv1.addWidget(pay = new QPushButton(tr("&pay..."),this));
     qh1.addWidget(qlv = new QListView());
     qh1.addWidget(qw = new QWidget());
     qv2.addWidget(price = new QTableView());
     qv2.addLayout(qh3 = new QHBoxLayout());
     qh3.addStretch();
     qh3.addWidget(pLabel = new QLabel(tr("0.00$")));
     
 }
 
    
    
    
    
    
}
