package com.github.krassekoder.mm13client.network;

import static com.github.krassekoder.mm13client.network.Packet.socket;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;

public class Packet0Login extends Packet{

    public static Packet0Login instance;

    public Packet0Login() {
        instance = this;
    }

    @Override
    public byte id() {
        return 0;
    }

    public static boolean login(String username, String password) {
        QByteArray data = new QByteArray();
        QByteArray uname = new QByteArray(username),
                pwd = new QByteArray(password);

        QDataStream s = new QDataStream(data, QIODevice.OpenModeFlag.WriteOnly);
        s.writeInt(uname.length());
        s.writeInt(pwd.length());

        data.append(uname);
        data.append(pwd);

        while(socket.bytesAvailable() < 1)
            socket.waitForBytesWritten(10000);

        return socket.read(1).at(0) == 1;
    }

    @Override
    public void receiveData(QTcpSocket socket) throws InvalidPacketException {
        throw new InvalidPacketException();
    }

}
