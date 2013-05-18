package com.github.krassekoder.mm13server;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QStatusBar;
import com.trolltech.qt.gui.QTabWidget;

public final class MainWindow extends QMainWindow {

    private QMenuBar menu;
    private QMenu helpMenu;
    private QAction aboutAction;
    private QStatusBar status;
    private QTabWidget tabs;
    private AboutDialog about;

    public MainWindow() {
        super();
        setupUi();
    }

    private void setupUi() {
        setWindowTitle("MensaManager 2013");

        setupMenus();
        setStatusBar(status = new QStatusBar(this));

        setCentralWidget(tabs = new QTabWidget(this));

    }

    private void openAbout() {
        if (about == null) {
            about = new AboutDialog(this);
        }
        about.show();
    }

    private void setupMenus() {
        setMenuBar(menu = new QMenuBar(this));

        menu.addMenu(helpMenu = new QMenu(tr("&Help"), menu));
        helpMenu.addAction(aboutAction = new QAction(tr("&About..."), helpMenu));
        aboutAction.triggered.connect(this, "openAbout()");
    }

    public static void main(String[] args) {
        QApplication a = new QApplication(args);
        MainWindow w = new MainWindow();

        w.show();
        QApplication.exec();
    }
}
