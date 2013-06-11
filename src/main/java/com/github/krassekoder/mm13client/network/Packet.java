package com.github.krassekoder.mm13client.network;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.network.QTcpSocket;


public abstract class Packet {

    public static class InvalidPacketException extends Exception {}
    public static class TimeoutException extends Exception {}

    private static Packet[] packets = new Packet[128];

    public Packet() {
        packets[id()] = this;
    }

    public abstract byte id();
    public static Packet getById(byte id) {
        return packets[id];
    }

    public abstract QByteArray sendData();
    public abstract void receiveData(QTcpSocket socket) throws TimeoutException;

    public void send(QTcpSocket socket) {
        QByteArray packet = sendData();
        packet.prepend(id());
        socket.write(packet);
    }

    public static void receive(QTcpSocket socket) throws InvalidPacketException, TimeoutException {
        QByteArray id = socket.read(1);
        Packet packet = getById(id.at(0));
        if(packet == null)
            throw new InvalidPacketException();
        packet.receiveData(socket);
    }
}
