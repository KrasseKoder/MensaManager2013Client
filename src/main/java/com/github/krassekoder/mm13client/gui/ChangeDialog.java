package com.github.krassekoder.mm13client.gui;
import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * The "ChangeDialog" shows you how much change you have to give back
 * and then bring you back to the "TellerWidget"if you press "Confirm" or 
 * to the "PayWidget" if you press "Back"
 */

public class ChangeDialog extends QDialog{

    private QLabel ch,bch;
    private QPushButton confirm,back;
    private QBoxLayout vLa1,vLa2, hLa1,hLa2;
    private PayWidget pay;

   
    public ChangeDialog(QWidget qw) {
        super(qw);
        setupUi();
    }
    /**
     * This method sets up the User Interface of the "ChangeDialog" 
     */
    private void setupUi() {
        setWindowTitle(tr("Change"));
        setFixedSize(160,110);
        
        setLayout(vLa1 = new QVBoxLayout(this));
        vLa1.addLayout(hLa1 = new QHBoxLayout(this));
        hLa1.addWidget(ch= new QLabel(tr("Change to give back:"),this));
        hLa1.addWidget(bch = new QLabel("",this));
        vLa1.addLayout(hLa2 = new QHBoxLayout(this));
        hLa2.addWidget(confirm = new QPushButton(tr("Confirm"), this));
        hLa2.addWidget(back = new QPushButton(tr("Back"), this));
        back.clicked.connect(this,"back()");
        confirm.clicked.connect(this,"confirm()");
        confirm.clicked.connect(this,"clearMoney()");
        
    }
    
    //Method to go back to the PayWidget
    private void back(){
    MainWindow.instance.disableChangeDialog();
    MainWindow.instance.resetMessage();
    }
    
    //Method to confirm the change
    private void confirm(){
    MainWindow.instance.disableChangeDialog();
    MainWindow.instance.ChangeToTeller();
    
    }
    
    //Method to set a new Change
    public void newChange(double newchange)
    {
        bch.setText(Double.toString(newchange));
    }
    
    public void clearMoney()
    {
    MainWindow.instance.clearMoney();
    }

   

    
}