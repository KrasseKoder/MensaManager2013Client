package com.github.krassekoder.mm13client.network;

import static com.github.krassekoder.mm13client.network.Packet.socket;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;

public class Packet4Admin extends Packet{

    public static Packet4Admin instance;

    public Packet4Admin() {
        instance = this;
    }

    @Override
    public byte id() {
        return 4;
    }

    public void editProduct(String pId, String pName, String pPrice) throws InvalidPacketException {
        QByteArray data = new QByteArray();
        QByteArray value = new QByteArray(pId + "\n" + pName + "\n" + pPrice);
        QDataStream s = new QDataStream(data, QIODevice.OpenModeFlag.WriteOnly);

        s.writeInt(value.length());
        data.append(value);
        data.prepend((byte)0);

        sendData(data);

        while(socket.bytesAvailable() < 1)
            socket.waitForReadyRead(10000);

        if(socket.read(1).at(0) != 4)
            throw new InvalidPacketException();
    }

    public void editUser(String name, String password, String rights) throws InvalidPacketException {
        QByteArray data = new QByteArray();
        QByteArray value = new QByteArray(name + "\n" + password + "\n" + rights);
        QDataStream s = new QDataStream(data, QIODevice.OpenModeFlag.WriteOnly);

        s.writeInt(value.length());
        data.append(value);
        data.prepend((byte)1);

        sendData(data);

        while(socket.bytesAvailable() < 1)
            socket.waitForReadyRead(10000);

        if(socket.read(1).at(0) != 4)
            throw new InvalidPacketException();
    }

    @Override
    protected void receiveData(QTcpSocket socket) throws InvalidPacketException, TimeoutException {
        throw new InvalidPacketException();
    }

}
