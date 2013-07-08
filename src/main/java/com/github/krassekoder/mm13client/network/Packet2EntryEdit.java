package com.github.krassekoder.mm13client.network;

import static com.github.krassekoder.mm13client.network.Packet.socket;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;

public class Packet2EntryEdit extends Packet{

    public static Packet2EntryEdit instance;

    public Packet2EntryEdit() {
        instance = this;
    }

    @Override
    public byte id() {
        return 2;
    }

    public void edit(String pId, String pName, String pPrice) throws InvalidPacketException {
        QByteArray data = new QByteArray();
        QByteArray id = new QByteArray(pId), name = new QByteArray(pName), price = new QByteArray(pPrice);
        QDataStream s = new QDataStream(data, QIODevice.OpenModeFlag.WriteOnly);
        s.writeInt(id.length());
        s.writeInt(name.length());
        s.writeInt(price.length());
        data.append(id);
        data.append(name);
        data.append(price);

        sendData(data);

        while(socket.bytesAvailable() < 1)
            socket.waitForReadyRead(10000);

        if(socket.read(1).at(0) != 2)
            throw new InvalidPacketException();
    }

    @Override
    protected void receiveData(QTcpSocket socket) throws InvalidPacketException, TimeoutException {
        throw new InvalidPacketException();
    }

}
