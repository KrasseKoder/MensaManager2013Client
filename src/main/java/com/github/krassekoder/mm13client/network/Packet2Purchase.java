package com.github.krassekoder.mm13client.network;

import static com.github.krassekoder.mm13client.network.Packet.socket;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;
import java.util.ArrayList;

public class Packet2Purchase extends Packet{
    public static Packet2Purchase instance;

    public Packet2Purchase() {
        instance = this;
    }

    @Override
    public byte id() {
        return 2;
    }

    /**
     * Represents a purchase to be sent to the database.
     * Use either cashPurchase, accountPurchase, or voucherPurchase to get a new object.
     * Use addItem to fill it and then submit it to the database.
     */
    public static class Purchase {
        private class Item { public int amount; public String id; }
        public enum Type { Cash, Account, Voucher }

        private ArrayList<Item> items;
        private Type type;
        private String data1, data2;

        private Purchase(Type type, String data1, String data2) {
            this.items = new ArrayList<Item>();
            this.type = type;
            this.data1 = data1;
            this.data2 = data2;
        }

        private String id() {
            switch(type) {
                case Cash: return "01";
                case Account: return "02";
                case Voucher: return "03";
            }
            return "00";
        }

        private String items() {
            StringBuilder res = new StringBuilder();

            for(Item i : items) {
                res.append("\n");
                res.append(i.amount);
                res.append("\t");
                res.append(i.id);
            }

            return res.toString();
        }

        public void addItem(int amount, String id) {
            Item i = new Item();
            i.amount = amount;
            i.id = id;
            items.add(i);
        }

        public double submit() throws InvalidPacketException {
            return Packet2Purchase.instance.purchase(this);
        }

        @Override
        public String toString() {
            return id() + (data1 == null ? "" : "\n" + data1) + (data2 == null ? "" : "\n" + data2) + "\n" + items();
        }

        public static Purchase cashPurchase(String money) {
            return new Purchase(Type.Cash, money, null);
        }

        public static Purchase accountPurchase(String username, String password) {
            return new Purchase(Type.Account, username, password);
        }

        public static Purchase voucherPurchase(String id) {
            return new Purchase(Type.Voucher, id, null);
        }
    }

    private double purchase(Purchase p) throws InvalidPacketException {

        QByteArray data = new QByteArray();
        QByteArray value = new QByteArray(p.toString());
        QDataStream s = new QDataStream(data, QIODevice.OpenModeFlag.WriteOnly);

        s.writeInt(value.length());
        data.append(value);

        sendData(data);

        while(socket.bytesAvailable() < 9)
            socket.waitForReadyRead(10000);

        if(socket.read(1).at(0) != 2)
            throw new InvalidPacketException();

        return (new QDataStream(socket)).readDouble();
    }

    @Override
    protected void receiveData(QTcpSocket socket) throws InvalidPacketException, TimeoutException {
        throw new InvalidPacketException();
    }

}
