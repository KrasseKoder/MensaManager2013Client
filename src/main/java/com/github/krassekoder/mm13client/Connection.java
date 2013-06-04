package com.github.krassekoder.mm13client;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.network.QHostAddress;
import com.trolltech.qt.network.QTcpSocket;


public class Connection extends QTcpSocket{
    public static Connection instance;

    public class PacketException extends Exception {}

    public Connection(QHostAddress a, int port) {
        connectToHost(a, port);
    }

    public boolean login(String uname, String pwd) throws PacketException {
        QByteArray username = new QByteArray(uname);
        QByteArray password = new QByteArray(pwd);
        QByteArray packet = new QByteArray();

        packet.append((byte)1); //Login packet Id
        packet.append((byte)(username.length() & 255));
        packet.append((byte)((username.length() & -256) >> 8)); //packet length
        packet.append(username);
        packet.append((byte)2); //Login packet Id
        packet.append((byte)(password.length() & 255));
        packet.append((byte)((password.length() & -256) >> 8)); //packet length
        packet.append(password);
        write(packet);

        while(bytesAvailable() < 4)
            waitForBytesWritten(10000);

        QByteArray res = read(4);
        if (res.at(0) != 3 || res.at(1) != 1 || res.at(2) != 0)
            throw new PacketException();

        if (res.at(3) == 1)
            return true;
        return false;
    }
}
