package com.github.krassekoder.mm13client.gui;
import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
public class EscapeMessage extends QDialog{

    private QLabel em;
    private QPushButton ok,back;
    private QBoxLayout vLa1, hLa1;

    /**
     * Shows the EscapeMessage
     *
     */
    public EscapeMessage(QWidget qw) {
        super(qw);
        setupUi();
    }
    /**
     * This method sets up the User Interface of the "LoginDialog" including
     * 'Spinbox' and 'Checkbox'.
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
    
    private void back()
    {
    MainWindow.instance.disableEscapeMessage();
    }
    
    private void cancelOrder()
    {
    MainWindow.instance.disableEscapeMessage();
    MainWindow.instance.ChangeToTeller();
    }

    
}
