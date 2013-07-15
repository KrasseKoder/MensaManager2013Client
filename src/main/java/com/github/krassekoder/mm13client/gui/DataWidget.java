package com.github.krassekoder.mm13client.gui;

import com.github.krassekoder.mm13client.network.Packet;
import com.github.krassekoder.mm13client.network.Packet1FoodList;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QHeaderView;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QResizeEvent;
import com.trolltech.qt.gui.QTableView;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class DataWidget extends QWidget {
    
    private class TripleHeader extends QHeaderView {
        public TripleHeader(Qt.Orientation orientation, QWidget parent) {
            super(orientation, parent);
            setResizeMode(QHeaderView.ResizeMode.Fixed);
            setStretchLastSection(true);
        }

        @Override
        protected void resizeEvent(QResizeEvent qre) {
            super.resizeEvent(qre);
            adaptSections();
        }

        public void adaptSections() {
            int firstSize = sectionSizeHint(0);
            int halfSize = (width() - firstSize) / 2;
            resizeSection(0, firstSize);
            resizeSection(1, halfSize);
            resizeSection(2, halfSize);
        }
    }

    private class CustomItem extends QTableWidgetItem {
        public CustomItem(String text) {
            super(text);
            setFlags(Qt.ItemFlag.ItemIsEnabled);
        }
    }
    
    private QHBoxLayout hLa1;
    private QVBoxLayout vLa1;
    private QPushButton button1,button2;
    private QTableView view;
    private QTableWidget list;
    private QLineEdit product;
    
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
        
        vLa1.addWidget(list = new QTableWidget(0, 3));
        ArrayList<String> labels = new ArrayList<String>(3);
        labels.add(tr("ID"));
        labels.add(tr("Name"));
        labels.add(tr("Price"));
        list.verticalHeader().setVisible(false);
        list.setHorizontalHeader(new DataWidget.TripleHeader(Qt.Orientation.Horizontal, list));
        list.setHorizontalHeaderLabels(labels);
        vLa1.addWidget(view = new QTableView());
        view.setVisible(false);
        vLa1.addLayout(hLa1= new QHBoxLayout());
        hLa1.addWidget(button1= new QPushButton(tr("Show Data"), this));
        button1.setIcon(new QIcon("classpath:com/github/krassekoder/data.png"));
        hLa1.addWidget(button2= new QPushButton(tr("Show Average"), this));
        button1.setIcon(new QIcon("classpath:com/github/krassekoder/data.png"));
        
        button1.pressed.connect(this,"showData()");
        button2.pressed.connect(this,"showAverage()");
        
        
        
    }
    
    private void showData(){
        list.setVisible(true);
        view.setVisible(false);
    }
    
    private void showAverage(){
        list.setVisible(false);
        view.setVisible(true);
        QMessageBox.information(this, tr("Buy MensaManagerPlus"), tr("Please "
                + "visit the krassekoder-githubpage wiki to purchase the unique "
                + "MensaManager2013Plus package including hilarious new features "
                + "to maximize your profit! The first ten visitors get a gratis"
                + "-key. So be fast!"));
        
    }
    
    public void request() {
        try {
            enterFoodList(Packet1FoodList.instance.request(""));
        } catch(Packet.InvalidPacketException ex) {
            Logger.getLogger(TellerWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void enterFoodList(List<Packet1FoodList.FoodItem> items) {
        while(list.rowCount() > 0)
            list.removeRow(0);
        for(Packet1FoodList.FoodItem i : items) {
            int row = list.rowCount();
            list.insertRow(row);
            list.setItem(row, 0, new DataWidget.CustomItem(i.id));
            list.setItem(row, 1, new DataWidget.CustomItem(i.name));
            list.setItem(row, 2, new DataWidget.CustomItem(i.price));
        }

        ((DataWidget.TripleHeader)list.horizontalHeader()).adaptSections();
    }
}
