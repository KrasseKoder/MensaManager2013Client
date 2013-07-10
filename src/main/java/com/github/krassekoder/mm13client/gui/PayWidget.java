package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * The "PayWidget" offers various non-cash payment options and also conventional 
 * cash payment. It is possible to enter the amount of money recieved and there 
 * is a button 'Pay' as well as a button 'Pay and Print'.
 */
public class PayWidget extends QWidget {
    
    private QTextBrowser view;
    private QHBoxLayout hLa1,hLa2;
    private QPushButton print,esc,pay;
    private QLineEdit money;
    private QVBoxLayout vLa1;
    private QLabel mLabel;
    public double change;
    public String moneySafe;
    
    public PayWidget(QWidget qw) {
        super(qw);
        setupUi();
    }
    /**
     * This method sets up the User Interface including Layouts, Forms, 
     * Buttons, etc.
     */
     private void setupUi()  {   
        setLayout(vLa1= new QVBoxLayout());
        vLa1.addWidget(view= new QTextBrowser(this));
        vLa1.addLayout(hLa1= new QHBoxLayout(this));
        hLa1.addWidget(mLabel= new QLabel(tr("&Money:"),this));
        hLa1.addWidget(money= new QLineEdit(this));
        mLabel.setBuddy(money);
        vLa1.addLayout(hLa2= new QHBoxLayout(this));
        hLa2.addWidget(print= new QPushButton(tr("P&rint && Pay"),this));
        hLa2.addWidget(pay= new QPushButton(tr("&Pay"),this));
        hLa2.addWidget(esc= new QPushButton(tr("&Cancel"),this));
        print.clicked.connect(this,"enableChangeDialog()");
        pay.clicked.connect(this,"enableChangeDialog())");
        esc.clicked.connect(this,"EscapeMessage()");
        money.editingFinished.connect(this,"getGivenMoney()");
    }  
     
     /**
      * This method calls 'ChangeToTeller()' in mm13client.gui.MainWindow in 
      * order to change from the Pay-tab to the Teller-tab when 'Pay' or 'Print' 
      * is pressed.
      */
     
     //Method to enable the ChangeDialog
     private void enableChangeDialog(){
        getChange();
        MainWindow.instance.enableChangeDialog(change);
        }
     
     //Enables the EscapeMessage
     private void EscapeMessage(){
       MainWindow.instance.enableEscapeMessage();
     }
     
     //Returns the GivenMoney
     public void getGivenMoney(){
         moneySafe= money.text();
     }
     //Calculates the Change
     private void getChange(){
        double speicher1;
        speicher1 = Double.parseDouble(moneySafe);
        change = speicher1-MainWindow.instance.giveValue();
    }
     
     //Returns the Charge
     public double giveChange()
     {
         getChange();
         return change;
     }
}