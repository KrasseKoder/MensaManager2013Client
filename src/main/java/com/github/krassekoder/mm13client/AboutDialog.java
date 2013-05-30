package com.github.krassekoder.mm13client;

import com.trolltech.qt.QtInfo;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public final class AboutDialog extends QDialog {

    private QLabel logo, text;
    private QPushButton close;
    private QVBoxLayout vL;
    private QHBoxLayout hL1, hL2;

    public AboutDialog(QWidget qw) {
        super(qw);
        setupUi();
    }

    private void setupUi() {
        setWindowTitle(tr("About %1").replace("%1", QApplication.applicationName()));

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
        return "<html><h2>MensaManager 2013</h2><h3>"
                + tr("Version %1").replace("%1", QApplication.applicationVersion())
                + "</h3><p>" + tr("Developed by %1").replace("%1", QApplication.organizationName())
                + "</p><p>" + tr("Using QtJambi version %1").replace("%1", QtInfo.versionString())
                + "</p><p><a href=\"1\">" + tr("About Qt")
                + "</a>, <a href=\"2\">" + tr("About QtJambi")
                + "</a></p></html>";
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
