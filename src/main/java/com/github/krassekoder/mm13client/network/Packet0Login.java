package com.github.krassekoder.mm13client.network;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.network.QTcpSocket;
import javax.naming.TimeLimitExceededException;

public class Packet0Login extends Packet{

    private static Packet0Login instance;
    private String username, password;
    private boolean success;

    @Override
    public byte id() {
        return 0;
    }

    public static boolean login(String username, String password) {

        return false;
    }

    @Override
    public QByteArray sendData() {
        QByteArray result = new QByteArray();
        return result;
    }

    @Override
    public void receiveData(QTcpSocket socket) throws TimeoutException{
        while(socket.bytesAvailable() < 2)
            if(!socket.waitForReadyRead(10000))
                throw new TimeoutException();
        QByteArray hash = socket.read(2);
        success = hash.toInt() == username.hashCode();
    }

}
