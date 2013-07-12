package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMessageBox;
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
    private QHBoxLayout hLa1,hLa2,hLa3,hLa4;
    private QPushButton print,esc,pay,creditcard,voucher,plasticcard, test;
    private QLineEdit money;
    private QVBoxLayout vLa1;
    private QLabel mLabel, pLabel;
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
        vLa1.addWidget(pLabel= new QLabel(tr("Choose a method of payment from the list:")));
        vLa1.addLayout(hLa3= new QHBoxLayout(this));
        hLa3.addWidget(creditcard= new QPushButton(tr("Creditcard")));
        hLa3.addWidget(voucher= new QPushButton(tr("Voucher")));
        vLa1.addLayout(hLa4= new QHBoxLayout(this));
        hLa4.addWidget(plasticcard= new QPushButton(tr("Plasticcard")));
        hLa4.addWidget(test= new QPushButton(tr("Testmethod")));
        vLa1.addWidget(view= new QTextBrowser(this));
        vLa1.addLayout(hLa1= new QHBoxLayout(this));
        hLa1.addWidget(mLabel= new QLabel(tr("&Money:"),this));
        hLa1.addWidget(money= new QLineEdit(this));
        mLabel.setBuddy(money);
        vLa1.addLayout(hLa2= new QHBoxLayout(this));
        hLa2.addWidget(print= new QPushButton(tr("P&rint && Pay"),this));
        print.setIcon(new QIcon("classpath:com/github/krassekoder/document-print.png"));
        hLa2.addWidget(pay= new QPushButton(tr("&Pay"),this));
        pay.setIcon(new QIcon("classpath:com/github/krassekoder/application-certificate.png"));
        hLa2.addWidget(esc= new QPushButton(tr("&Cancel"),this));
        esc.setIcon(new QIcon("classpath:com/github/krassekoder/process-stop.png"));
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
         if(money.hasAcceptableInput()&&money.isModified())
         {
             money.setModified(false);            
             getChange();
             MainWindow.instance.enableChangeDialog(change);
             resetChange();
         }
         
         else if(!money.isModified()&&!MainWindow.instance.ChangeIsVisible())
         {
             QMessageBox.information(this, tr("Enter money"), tr("Please type in the amount of money you recieved!"));
             money.setModified(false);
         }
         
        }

     //Enables the EscapeMessage
     private void EscapeMessage(){
       if(QMessageBox.question(this, tr("Cancel order"), tr("Do you really want to cancel the order?"),
                             new QMessageBox.StandardButtons(QMessageBox.StandardButton.Yes, QMessageBox.StandardButton.No))
                == QMessageBox.StandardButton.Yes) {
            MainWindow.instance.ChangeToTeller();
        }
     }

     //Saves the given Money in moneySafe
     public void getGivenMoney(){
         moneySafe= money.text();
     }

     //Resets the Change
     private void resetChange()
     {
         change=0;
     }
     
     //Calculates the Change
     private void getChange(){
        double speicher1;
        speicher1 = Double.parseDouble(moneySafe);
        change = speicher1-MainWindow.instance.giveValue();
        moneySafe=null;
    }

     //Returns the Charge
     public double giveChange()
     {
         getChange();
         return change;
     }

     //clears the money 'LineEdit'
     public void clearMoney()
     {
         money.clear();
     }
}