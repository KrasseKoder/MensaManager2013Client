package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;


public class AdminWidget extends QWidget
{
    private QGroupBox newUser;
    private QGroupBox newEntry;
    private QVBoxLayout qv1;
    
    
    public AdminWidget(QWidget qw)
 {
     super(qw);
     setupUi();
 }
    
  private void setupUi(){
      new QGroupBox(tr("New User"), this);
      new QGroupBox(tr("New Entry"), this);
          
     }  
}
