package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QStatusBar;
import com.trolltech.qt.gui.QTabWidget;

public final class MainWindow extends QMainWindow {
    public static MainWindow instance;

    private QMenuBar menu;
    private QMenu toolsMenu, helpMenu;
    private QAction loginAction, logoutAction, quitAction, helpAction, aboutAction;
    private QStatusBar status;
    private QTabWidget tabs;
    private AboutDialog about;
    private HelpDialog help;
    private LoginDialog login;
    private PayWidget pay;
    private TellerWidget teller;
    private AdminWidget admin;
    private ChangeDialog change;

    /**
     * The "MainWindow" is the first window seen by the user and refers to all
     * other menus and widgets including the 'menubar' and the 'tabbar'.
     */
    public MainWindow() {
        super();
        instance = this;
        setupUi();

        login = new LoginDialog(this);
        change = new ChangeDialog(this);
        help = new HelpDialog(this);
    }
    // This method sets up the User Interface of the main window including menu bar and tabs.
    private void setupUi() {
        setWindowTitle("MensaManager 2013");
        setMinimumSize(640,400);

        setupMenus(); //sets up the menu bar as described below.
        setStatusBar(status = new QStatusBar(this));
        setCentralWidget(tabs = new QTabWidget(this));
        tabs.addTab(teller = new TellerWidget(tabs),tr("Teller"));
        tabs.addTab(new DataWidget(tabs), tr("Data"));
        pay = new PayWidget(tabs);
        pay.hide();
        admin = new AdminWidget(tabs);
        admin.hide();


        tabs.setTabIcon(0, new QIcon("classpath:com/github/krassekoder/accessories-calculator.png"));
        tabs.setTabIcon(1, new QIcon("classpath:com/github/krassekoder/system-search.png"));
    }

    //Switches from "TellerWidget" to "PayWidget"
    public void ChangeToPay()
    {
        tabs.insertTab(0, pay, tr("Pay"));
        tabs.setTabIcon(0, new QIcon("classpath:com/github/krassekoder/accessories-calculator.png"));
        tabs.setCurrentIndex(0);
        tabs.removeTab(1);
    }

    //Switches from "PayWidget" to "TellerWidget"
    public void ChangeToTeller()
    {
        teller.newPurchase();
        tabs.insertTab(0, teller, tr("Teller"));
        tabs.setTabIcon(0, new QIcon("classpath:com/github/krassekoder/accessories-calculator.png"));
        tabs.setCurrentIndex(0);
        tabs.removeTab(1);
    }
    //Gives the complete price of an order
    public double giveValue(){
        return teller.giveValue();
    }

    //Unlocks the "AdminWidget"
    public void unlockAdminWidget() {
        tabs.insertTab(2,admin,tr("Admin"));
        tabs.setTabIcon(2, new QIcon("classpath:com/github/krassekoder/document-properties.png"));
    }

    //Locks the "AdminWidget"
    public void lockAdminWidget() {
        tabs.removeTab(2);
    }

    //Enables the option to login
    public void enableLogin() {
        loginAction.setVisible(true);
    }

    //Disables the option to login
    public void disableLogin() {
        loginAction.setVisible(false);
    }

    //Enables the option to logout
    public void enableLogout() {
        logoutAction.setVisible(true);
    }

    //Disables the option to logout
    public void disableLogout() {
        logoutAction.setVisible(false);
    }

    //Enables the ChangeDialog and sets a new Change
    public void enableChangeDialog(double newchange){
        change.newChange(newchange);
        change.setVisible(true);

    }

    //Disables the ChangeDialog
    public void disableChangeDialog(){
        change.setVisible(false);
    }
    
    //returns whether the ChangeDialog is visible or not.
    public boolean ChangeIsVisible()
    {
        return change.isVisible();
    }

    //Opens the AboutDialog
    private void openAbout() {
        if (about == null) {
            about = new AboutDialog(this);
        }
        about.show();
    }

    //Opens the LoginDialog
    private void openLoginDialog() {
        login.show();
    }

    //Opens the HelpDialog
    private void openHelp() {
        help.show();
    }

    //The Method for loging out
    private void clickLogout() {
        login.logout();
        setWindowTitle("MensaManager 2013");
        disableLogout();
        enableLogin();
        lockAdminWidget();
        System.out.println("Logged out");
    }
    //Shows the "FoodList"
    public void showFoodList()
    {
        teller.request();
    }
    //Calls method 'clearMoney()' in "PayWidget" in order to clean the 'Line edit'
    public void clearMoney()
    {
        pay.clearMoney();
    }




/**
 *  This method sets up the menus 'Tools' and 'Help' as a menu bar on the
 *  upper side of the window.
 */
    private void setupMenus() {
        setMenuBar(menu = new QMenuBar(this));

        menu.addMenu(toolsMenu = new QMenu(tr("&Tools"), menu));
        toolsMenu.addAction(loginAction = new QAction(tr("&Login..."), toolsMenu));
        loginAction.setIcon(new QIcon("classpath:com/github/krassekoder/network-idle.png"));
        loginAction.triggered.connect(this, "openLoginDialog()");
        toolsMenu.addAction(logoutAction = new QAction(tr("Logout..."), toolsMenu));
        logoutAction.setIcon(new QIcon("classpath:com/github/krassekoder/network-offline.png"));
        logoutAction.setVisible(false);
        logoutAction.triggered.connect(this, "clickLogout()");
        toolsMenu.addAction(quitAction = new QAction(tr("Quit"), toolsMenu));
        quitAction.setIcon(new QIcon("classpath:com/github/krassekoder/system-log-out.png"));
        quitAction.triggered.connect(QApplication.instance(), "quit()");

        menu.addMenu(helpMenu = new QMenu(tr("&Help"), menu));
        helpMenu.addAction(helpAction = new QAction(tr("&Help..."), helpMenu));
        helpAction.setIcon(new QIcon("classpath:com/github/krassekoder/help-browser.png"));
        helpAction.triggered.connect(this, "openHelp()");
        helpMenu.addAction(aboutAction = new QAction(tr("&About..."), helpMenu));
        aboutAction.setIcon(new QIcon("classpath:com/github/krassekoder/text-html.png"));
        aboutAction.triggered.connect(this, "openAbout()");
    }
}
