package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QStatusBar;
import com.trolltech.qt.gui.QTabWidget;

public final class MainWindow extends QMainWindow {

    private QMenuBar menu;
    private QMenu toolsMenu, helpMenu;
    private QAction loginAction;
    private QAction aboutAction;
    private QStatusBar status;
    private QTabWidget tabs;
    private AboutDialog about;
    private LoginDialog login;
    private PayWidget pay;
    private TellerWidget teller;

    public MainWindow() {
        super();
        setupUi();
    }

    private void setupUi() {
        setWindowTitle("MensaManager 2013");

        setupMenus();
        setStatusBar(status = new QStatusBar(this));
        setCentralWidget(tabs = new QTabWidget(this));
        tabs.addTab(new PayWidget(tabs),tr("Pay"));
        tabs.addTab(new TellerWidget(tabs),tr("Teller"));

    }

    private void openAbout() {
        if (about == null) {
            about = new AboutDialog(this);
        }
        about.show();
    }

    private void openLoginDialog() {
        if (login == null) {
            login = new LoginDialog(this);
        }
        login.exec();
    }

    private void setupMenus() {
        setMenuBar(menu = new QMenuBar(this));

        menu.addMenu(toolsMenu = new QMenu(tr("&Tools"), menu));
        toolsMenu.addAction(loginAction = new QAction(tr("&Login..."), toolsMenu));
        loginAction.triggered.connect(this, "openLoginDialog()");

        menu.addMenu(helpMenu = new QMenu(tr("&Help"), menu));
        helpMenu.addAction(aboutAction = new QAction(tr("&About..."), helpMenu));
        aboutAction.triggered.connect(this, "openAbout()");
    }
}
