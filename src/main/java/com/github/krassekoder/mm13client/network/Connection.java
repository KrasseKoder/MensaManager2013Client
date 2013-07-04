package com.github.krassekoder.mm13client.network;

import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;


public class Connection{
    public static Connection instance;

    public class PacketException extends Exception {}

    public Connection() {
        instance  = this;
    }

    public boolean connect(String host, int port) {
        QTcpSocket s  = new QTcpSocket();
        s.connectToHost(host, port, QIODevice.OpenModeFlag.ReadWrite);
        if(!s.waitForConnected(10000))
            return false;
        Packet.socket = s;
        return true;
    }
}
