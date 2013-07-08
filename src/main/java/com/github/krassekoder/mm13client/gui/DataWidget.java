package com.github.krassekoder.mm13client.gui;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTableView;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 *
 * 
 */
public class DataWidget extends QWidget {
    
    private QHBoxLayout hLa1;
    private QVBoxLayout vLa1;
    private QPushButton button1,button2;
    private QTableView view;
    
    public DataWidget(QWidget qw)
    {
        super(qw);
        setupUi();
    }
    /**
     * This method sets up the User Interface including Layouts, Forms, 
     * Buttons, etc.
     */
    private void setupUi() {
        setLayout(vLa1= new QVBoxLayout());
        vLa1.addLayout(hLa1= new QHBoxLayout());
        // to be named
        hLa1.addWidget(button1= new QPushButton(tr("Show Data"), this));
        // to be named
        hLa1.addWidget(button2= new QPushButton(tr("Show Average"), this));
        vLa1.addWidget(view= new QTableView(this));
    }
}
