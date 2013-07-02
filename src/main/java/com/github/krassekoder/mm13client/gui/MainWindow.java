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

    /**
     * The "MainWindow" is the first window seen by the user and refers to all
     * other menus and widgets including the 'menubar' and the 'tabbar'.
     */
    public MainWindow() {
        super();
        setupUi();
    }

    private void setupUi() { // This method sets up the User Interface of the main window including menu bar and tabs.
        setWindowTitle("MensaManager 2013");

        setupMenus(); //sets up the menu bar as descripted below.
        setStatusBar(status = new QStatusBar(this));
        setCentralWidget(tabs = new QTabWidget(this));
        tabs.addTab(new PayWidget(tabs),tr("Pay"));
        tabs.addTab(new TellerWidget(tabs),tr("Teller"));
        tabs.addTab(new DataWidget(tabs), tr("Data"));

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

    private void setupMenus() { //This function sets up the menus 'Tools' and 'Help' as a menu bar on the upper side of the window
        setMenuBar(menu = new QMenuBar(this));

        menu.addMenu(toolsMenu = new QMenu(tr("&Tools"), menu));
        toolsMenu.addAction(loginAction = new QAction(tr("&Login..."), toolsMenu));
        loginAction.triggered.connect(this, "openLoginDialog()");

        menu.addMenu(helpMenu = new QMenu(tr("&Help"), menu));
        helpMenu.addAction(aboutAction = new QAction(tr("&About..."), helpMenu));
        aboutAction.triggered.connect(this, "openAbout()");
    }
}
