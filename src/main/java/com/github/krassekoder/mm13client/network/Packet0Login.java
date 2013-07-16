package com.github.krassekoder.mm13client.network;

import static com.github.krassekoder.mm13client.network.Packet.socket;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;

public class Packet0Login extends Packet{

    public static Packet0Login instance;
    private byte rights;

    public static final int TELLER = 1, ADMIN = 2;

    public Packet0Login() {
        instance = this;
    }

    @Override
    public byte id() {
        return 0;
    }

    public byte login(String username, String password) throws InvalidPacketException {
        if(rights > 0)
            return rights;
        QByteArray data = new QByteArray();
        QByteArray uname = new QByteArray(username),
                pwd = new QByteArray(password);

        QDataStream s = new QDataStream(data, QIODevice.OpenModeFlag.WriteOnly);
        s.writeInt(uname.length());
        s.writeInt(pwd.length());

        data.append(uname);
        data.append(pwd);

        sendData(data);

        while(socket.bytesAvailable() < 2)
            socket.waitForReadyRead(10000);

        QByteArray res = socket.read(2);

        if(res.at(0) != 0)
            throw new InvalidPacketException();

        return rights = res.at(1);
    }

    @Override
    public void receiveData(QTcpSocket socket) throws InvalidPacketException {
        throw new InvalidPacketException();
    }

}
