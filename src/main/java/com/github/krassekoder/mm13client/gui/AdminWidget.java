package com.github.krassekoder.mm13client.gui;

import com.github.krassekoder.mm13client.network.Packet;
import com.github.krassekoder.mm13client.network.Packet4Admin;
import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The AdminWidget allows you to add new users and
 * products easily by writing the information into
 * the lines and confirming it by pressing "Enter".
 */
public class AdminWidget extends QWidget
{
    private QGroupBox newUser;
    private QGroupBox newEntry;
    private QHBoxLayout qh1,qh2,qh3,qh4,qh5,qh6;
    private QVBoxLayout qv1,qv2,qv3;
    private QLineEdit username, password,productname,price,id,rights;
    private QLabel name1Label,name2Label,passwordLabel,priceLabel,IdLabel,rightsLabel;
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

     qv2.addLayout(qh6 = new QHBoxLayout());
     qh6.addWidget(rightsLabel = new QLabel(tr("Rights:      "), newEntry));
     qh6.addWidget(rights = new QLineEdit(newEntry));

     qv2.addWidget(enter1 = new QPushButton(tr("&Enter"), this));

     qv1.addWidget(newEntry = new QGroupBox(tr("New Entry"), this));
     newEntry.setLayout(qv3 = new QVBoxLayout(newEntry));
     qv3.addLayout(qh3 = new QHBoxLayout());
     qh3.addWidget(name2Label = new QLabel(tr("Name:"), newEntry));
     qh3.addWidget(productname = new QLineEdit(newEntry));
     qv3.addLayout(qh4 = new QHBoxLayout());
     qh4.addWidget(priceLabel = new QLabel(tr("Price: "), newEntry));
     qh4.addWidget(price = new QLineEdit(newEntry));
     qv3.addLayout(qh5 = new QHBoxLayout());
     qh5.addWidget(IdLabel = new QLabel(tr("Id:     "), newEntry));
     qh5.addWidget(id = new QLineEdit(newEntry));
     qv3.addWidget(enter2 = new QPushButton(tr("&Enter"), this));

     enter1.clicked.connect(this,"SaveNewUser()");
     enter2.clicked.connect(this,"SaveEntry()");
     }

  /**
   * Save NewEntry
   * Empty name will delete entry
   */
  public void SaveEntry()
  {
        try {
            Packet4Admin.instance.editProduct(id.text(), productname.text(), price.text());
        } catch(Packet.InvalidPacketException ex) {
            Logger.getLogger(AdminWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
      System.out.println("Product(" + id.text() + "): " + productname.text() + " : " + price.text());
      productname.clear();
      id.clear();
      price.clear();
      MainWindow.instance.showFoodList();
  }
  /**
   * Save NewUser saving to Library missing
   * rights = 0 will delete user
   */
  public void SaveNewUser()
  {
        try {
            Packet4Admin.instance.editUser(username.text(), password.text(), rights.text());
        } catch(Packet.InvalidPacketException ex) {
            Logger.getLogger(AdminWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
      System.out.println("User:" + username.text() + " " + password.text() + "(" + rights.text() + ")");
      username.clear();
      password.clear();
      rights.clear();
  }
}


