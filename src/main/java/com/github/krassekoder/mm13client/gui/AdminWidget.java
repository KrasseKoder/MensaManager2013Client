package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * The AdminWidget allows you to add new users and
 * products easily by writing the information into
 * the lines and confirming it by pressing "Enter".
 */
public class AdminWidget extends QWidget
{
    private QGroupBox newUser;
    private QGroupBox newEntry;
    private QHBoxLayout qh1,qh2,qh3,qh4;
    private QVBoxLayout qv1,qv2,qv3;
    private QLineEdit username, password,productname,price;
    private QLabel name1Label,name2Label, passwordLabel, priceLabel;
    private QPushButton enter1,enter2;
    private QFormLayout loginLayout;
    
    public AdminWidget(QWidget qw)
 {
     super(qw);
     setupUi();
 }
    
    /** 
     * This method sets up the User Interface of "AdminWidget" including 
     * 'QGroupBox' and 'QPushButton'.
     */
  private void setupUi(){
     
     setLayout(qv1=new QVBoxLayout());
     qv1.addWidget(newUser = new QGroupBox(tr("New User"), this));
     newUser.setLayout(qv2 = new QVBoxLayout(newUser));
     qv2.addLayout(qh1 = new QHBoxLayout());
     qh1.addWidget(name1Label = new QLabel(tr("Username:"), newUser));
     qh1.addWidget(username = new QLineEdit(newUser));
     qv2.addLayout(qh2 = new QHBoxLayout());
     qh2.addWidget(passwordLabel = new QLabel(tr("Password: "), newUser));
     qh2.addWidget(password = new QLineEdit(newUser));
     qv2.addWidget(enter1 = new QPushButton(tr("&Enter"), this));
     
     qv1.addWidget(newEntry = new QGroupBox(tr("New Entry"), this));
     newEntry.setLayout(qv3 = new QVBoxLayout(newEntry));
     qv3.addLayout(qh3 = new QHBoxLayout());
     qh3.addWidget(name2Label = new QLabel(tr("Name:"), newEntry));
     qh3.addWidget(productname = new QLineEdit(newEntry));
     qv3.addLayout(qh4 = new QHBoxLayout());
     qh4.addWidget(priceLabel = new QLabel(tr("Price: "), newEntry));
     qh4.addWidget(price = new QLineEdit(newEntry));
     qv3.addWidget(enter2 = new QPushButton(tr("&Enter"), this));
          
     }  
}

