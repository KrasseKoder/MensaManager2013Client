package com.github.krassekoder;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;

public class MainWindow extends QMainWindow{
    public static void main(String[] args) {
        QApplication a = new QApplication(args);
        MainWindow w = new MainWindow();

        w.show();
        QApplication.exec();
    }
}
