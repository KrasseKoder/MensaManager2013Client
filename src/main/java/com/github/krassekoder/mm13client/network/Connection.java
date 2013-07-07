package com.github.krassekoder.mm13client.network;

import com.trolltech.qt.network.QHostAddress;
import com.trolltech.qt.network.QTcpSocket;


public class Connection{
    public static Connection instance;

    public class PacketException extends Exception {}

    public Connection() {
        instance = this;
    }

    public boolean connect(String host, int port) {
        QTcpSocket socket = new QTcpSocket();
        System.out.println("Connecting to " + host + ":" + port);
        socket.connectToHost(new QHostAddress(host), port);
        if(!socket.waitForConnected()) {
            System.out.println(socket.errorString());
            return false;
        }
        Packet.init(socket);
        return true;
    }
}
