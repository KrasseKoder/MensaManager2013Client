package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.QtInfo;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * The "AboutDialog" is integrated in the help-menu and shows some minor 
 * information about the developers, Qt, and Qt Jambi.
 * It also indicates the build state... Currently Pre-Alpha.
 */
public final class AboutDialog extends QDialog {

    private QLabel logo, text;
    private QPushButton close;
    private QVBoxLayout vL;
    private QHBoxLayout hL1, hL2;

    public AboutDialog(QWidget qw) {
        super(qw);
        setupUi();
    }
    //This method sets up the User Interface including Layouts, Forms, Buttons, etc.
    private void setupUi() { 
        setWindowTitle(tr("About MensaManager"));

        vL = new QVBoxLayout(this);
        vL.addLayout(hL1 = new QHBoxLayout());
        vL.addLayout(hL2 = new QHBoxLayout());

        hL1.addWidget(logo = new QLabel());
        hL1.addWidget(text = new QLabel(generateHtml()));
        text.linkActivated.connect(this, "link(String)");
        hL2.addStretch();
        hL2.addWidget(close = new QPushButton(tr("Close"), this));

        close.clicked.connect(this, "close()");
    }

    private String generateHtml() {
        return String.format("<html><h2>%1$s</h2><h3>%2$s</h3><p>%3$s</p><p>%4$s</p><p><a href=\"1\">%5$s</a>, <a href=\"2\">%6$s</a></p></html>",
                "MensaManager 2013", String.format(tr("Version %1$s"), QApplication.applicationVersion()),
                String.format("Developed by %1$s", QApplication.organizationName()),
                String.format("Using QtJambi version  %1$s", QtInfo.versionString()),
                tr("About Qt"), tr("About QtJambi"));
    }

    private void link(String s) {
        switch (s.charAt(0)) {
            case '1':
                QApplication.aboutQt();
                break;
            case '2':
                QApplication.aboutQtJambi();
                break;
            default:
                System.err.println("Unknown link in About Dialog");
        }
    }
}
