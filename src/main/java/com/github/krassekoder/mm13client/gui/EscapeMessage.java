package com.github.krassekoder.mm13client.gui;
import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * The "EscapeMessage" appears if you press "Escape" in the "PayWidget"
 * and shows a message wheater you really want to cancel the order.
 * You can choose between "Yes" and "No".
 */

public class EscapeMessage extends QDialog{

    private QLabel em;
    private QPushButton ok,back;
    private QBoxLayout vLa1, hLa1;

    
    public EscapeMessage(QWidget qw) {
        super(qw);
        setupUi();
    }
    /**
     * This method sets up the User Interface of the "Escape Message"
     */
    private void setupUi() {
        setWindowTitle(tr("Cancel"));
        setFixedSize(140,70);
        
        setLayout(vLa1 = new QVBoxLayout(this));
        vLa1.addWidget(em= new QLabel(tr("Cancel the Order?"),this));
        vLa1.addLayout(hLa1 = new QHBoxLayout(this));
        hLa1.addWidget(ok = new QPushButton(tr("Yes"), this));
        hLa1.addWidget(back = new QPushButton(tr("No"), this));
        
        back.clicked.connect(this,"back()");
        ok.clicked.connect(this,"cancelOrder()");
    }
    //Method to go back to "PayWidget"
    private void back()
    {
    MainWindow.instance.disableEscapeMessage();
    }
    //Method to cancel the order and go to "Teller Widget"
    private void cancelOrder()
    {
    MainWindow.instance.disableEscapeMessage();
    MainWindow.instance.ChangeToTeller();
    }

    
}
