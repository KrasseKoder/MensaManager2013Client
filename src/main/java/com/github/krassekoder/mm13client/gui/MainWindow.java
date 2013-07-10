package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QStatusBar;
import com.trolltech.qt.gui.QTabWidget;

public final class MainWindow extends QMainWindow {
    public static MainWindow instance;

    private QMenuBar menu;
    private QMenu toolsMenu, helpMenu;
    private QAction loginAction, logoutAction;
    private QAction aboutAction;
    private QStatusBar status;
    private QTabWidget tabs;
    private AboutDialog about;
    private LoginDialog login;
    private EscapeMessage esc;
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
        esc = new EscapeMessage(this);
        change = new ChangeDialog(this);
    }
    // This method sets up the User Interface of the main window including menu bar and tabs.
    private void setupUi() {
        setWindowTitle("MensaManager 2013");
        setMinimumSize(640,400);
        setMaximumWidth(640);

        setupMenus(); //sets up the menu bar as described below.
        setStatusBar(status = new QStatusBar(this));
        setCentralWidget(tabs = new QTabWidget(this));
        tabs.addTab(teller = new TellerWidget(tabs),tr("Teller"));
        tabs.addTab(new DataWidget(tabs), tr("Data"));
        pay = new PayWidget(tabs);
        pay.hide();
        admin = new AdminWidget(tabs);
        admin.hide();
        

    }

    //Switches from "TellerWidget" to "PayWidget"
    public void ChangeToPay()
    {
        tabs.insertTab(0, pay, tr("Pay"));
        tabs.setCurrentIndex(0);
        tabs.removeTab(1);
    }

    //Switches from "PayWidget" to "TellerWidget"
    public void ChangeToTeller()
    {
       teller.newPurchase();
       tabs.insertTab(0, teller, tr("Teller"));
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
    //Enables the EscapeMessage
    public void enableEscapeMessage(){
        esc.setVisible(true);
    }
    //Disables the EscapeMessage
    public void disableEscapeMessage(){
        esc.setVisible(false);
    }
    //Enables the ChangeDialog an sets a new Change
    public void enableChangeDialog(double newchange){
        change.newChange(newchange);
        change.setVisible(true);
        
    }
    //Disables the ChangeDialog
    public void disableChangeDialog(){
        change.setVisible(false);
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
    
    

    
/**
 *  This method sets up the menus 'Tools' and 'Help' as a menu bar on the
 *  upper side of the window.
 */
    private void setupMenus() {
        setMenuBar(menu = new QMenuBar(this));

        menu.addMenu(toolsMenu = new QMenu(tr("&Tools"), menu));
        toolsMenu.addAction(loginAction = new QAction(tr("&Login..."), toolsMenu));
        loginAction.triggered.connect(this, "openLoginDialog()");
        toolsMenu.addAction(logoutAction = new QAction(tr("Logout..."), toolsMenu));
        logoutAction.setVisible(false);
        logoutAction.triggered.connect(this, "clickLogout()");

        menu.addMenu(helpMenu = new QMenu(tr("&Help"), menu));
        helpMenu.addAction(aboutAction = new QAction(tr("&About..."), helpMenu));
        aboutAction.triggered.connect(this, "openAbout()");
    }

   
}
