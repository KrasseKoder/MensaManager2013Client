package com.github.krassekoder.mm13client.network;

import static com.github.krassekoder.mm13client.network.Packet.socket;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QDateTime;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Packet3Data extends Packet{

    public class Sale {
        double sum;
        QDateTime date;

        public Sale(String input) {
            String[] items = input.split("\t");
            date = QDateTime.fromString(items[0]);
            sum = Double.parseDouble(items[1]);
        }
    }

    public static final int SALES = 0;
    public static Packet3Data instance;

    public Packet3Data() {
        instance = this;
    }

    @Override
    public byte id() {
        return 3;
    }

    private String receiveString() throws InvalidPacketException {
        while(socket.bytesAvailable() < 5) //int
            socket.waitForReadyRead(10000);
        if(socket.read(1).at(0) != 3)
            throw new InvalidPacketException();

        int length = (new QDataStream(socket)).readInt();
        while(socket.bytesAvailable() < length)
            socket.waitForReadyRead(10000);

        return socket.read(length).toString();
    }

    private void requestData(int type) {
        QByteArray req = new QByteArray();
        QDataStream s = new QDataStream(req, QIODevice.OpenModeFlag.WriteOnly);
        s.writeInt(type);
        sendData(req);
    }

    private void insertSaleSorted(Sale s, List<Sale> list) {
        int t = s.date.toTime_t();

        ListIterator<Sale> i = list.listIterator();
        while(i.hasNext()) {
            if(i.next().date.toTime_t() < t)
                list.add(i.previousIndex(), s);
        }
    }

    public Sale[] getSales() throws InvalidPacketException {
        requestData(SALES);

        String s = receiveString();
        if(s.isEmpty())
            return new Sale[0];

        String[] sales = s.split("\n");
        LinkedList<Sale> result = new LinkedList<Sale>();
        for(int i = 0; i < sales.length; i++)
            insertSaleSorted(new Sale(sales[i]), result);

        return result.toArray(new Sale[result.size()]);
    }

    @Override
    protected void receiveData(QTcpSocket socket) throws InvalidPacketException, TimeoutException {
        throw new  InvalidPacketException();
    }

}
