package com.github.krassekoder.mm13client.network;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.network.QTcpSocket;

public class Packet0Login extends Packet{
    @Override
    public byte id() {
        return 0;
    }

    public static boolean login(String username, String password) {
        QByteArray data = new QByteArray();
        QByteArray uname = new QByteArray(username),
                pwd = new QByteArray(password);

        QDataStream s = new QDataStream(data);
        s.writeInt(uname.length());
        s.writeInt(pwd.length());

        data.append(uname);
        data.append(pwd);
        return false;
    }

    @Override
    public void receiveData(QTcpSocket socket) throws InvalidPacketException {
        throw new InvalidPacketException();
    }

}
