package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * The "HelpDialog" is integrated in the help-menu and refers to the 
 * MensaManager2013 wiki at https://github.com/KrasseKoder/MensaManager2013Client/wiki.
 */
public final class HelpDialog extends QDialog {
    
    private QVBoxLayout vL;
    private QHBoxLayout hL1;
    private QPushButton close;
    private QLabel text;
    
    public HelpDialog(QWidget qw) {
        super(qw);
        setupUi();
    }
    
        private void setupUi() { 
        setWindowTitle(tr("Help"));
        vL = new QVBoxLayout(this);
        vL.addWidget(text = new QLabel());
        text.setText("Wieso? Weshalb? Warum? Wer nicht fragt bleibt dumm!\n\n\n\n\n Please visit the wiki!");
        vL.addLayout(hL1 = new QHBoxLayout());
        hL1.addWidget(close = new QPushButton(tr("Close"), this));
        close.clicked.connect(this, "close()");
        }
}
