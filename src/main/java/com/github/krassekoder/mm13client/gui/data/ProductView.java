package com.github.krassekoder.mm13client.gui.data;

import com.github.krassekoder.mm13client.gui.TellerWidget;
import com.github.krassekoder.mm13client.network.Packet;
import com.github.krassekoder.mm13client.network.Packet1FoodList;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QHeaderView;
import com.trolltech.qt.gui.QResizeEvent;
import com.trolltech.qt.gui.QShowEvent;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductView extends QTableWidget{

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

    public ProductView(QWidget parent) {
        super(0, 3, parent);
        verticalHeader().setVisible(false);
        verticalHeader().setResizeMode(QHeaderView.ResizeMode.ResizeToContents);

        setHorizontalHeader(new TripleHeader(Qt.Orientation.Horizontal, this));
        ArrayList<String> labels = new ArrayList<String>(3);
        labels.add(tr("ID"));
        labels.add(tr("Name"));
        labels.add(tr("Price"));
        setHorizontalHeaderLabels(labels);
    }

    @Override
    protected void showEvent(QShowEvent qse) {
        super.showEvent(qse);

        List<Packet1FoodList.FoodItem> items = null;
        try {
            items = Packet1FoodList.instance.request("");
        } catch(Packet.InvalidPacketException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(items == null)
                items = new ArrayList<Packet1FoodList.FoodItem>();
        }

        while(rowCount() > 0)
            removeRow(0);
        for(Packet1FoodList.FoodItem i : items) {
            int row = rowCount();
            insertRow(row);
            setItem(row, 0, new CustomItem(i.id));
            setItem(row, 1, new CustomItem(i.name));
            setItem(row, 2, new CustomItem(i.price));
        }

        ((TripleHeader)horizontalHeader()).adaptSections();
    }


}
