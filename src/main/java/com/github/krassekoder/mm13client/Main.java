package com.github.krassekoder.mm13client;

import com.github.krassekoder.mm13client.gui.MainWindow;
import com.trolltech.qt.gui.QApplication;

/**
 * The main class. It calls "MainWindow" and "QApplication".
 */
public class Main {
    public static void main(String[] args) {
        QApplication a = new QApplication(args);
        MainWindow w = new MainWindow();

        QApplication.setApplicationName("MensaManager2013Client");
        QApplication.setApplicationVersion("Pre-Alpha");
        QApplication.setOrganizationName("KrasseKoder");
        QApplication.setOrganizationDomain("http://www.github.com/KrasseKoder/");

        w.show();
        QApplication.exec();
    }
}
