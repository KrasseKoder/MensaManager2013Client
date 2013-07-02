package com.github.krassekoder.mm13client.network;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.network.QTcpSocket;

public class Packet0Login extends Packet{

    private static Packet0Login instance;

    public Packet0Login() {
        instance = this;
    }

    @Override
    public byte id() {
        return 0;
    }

    public static boolean login(String username, String password) {
        QByteArray data = new QByteArray();
        data.append(QByteArray.number(username.length()));
        data.append(username);
        data.append(QByteArray.number(password.length()));
        data.append(password);
        return false;
    }

    @Override
    public void receiveData(QTcpSocket socket) throws InvalidPacketException {
        throw new InvalidPacketException();
    }

}
