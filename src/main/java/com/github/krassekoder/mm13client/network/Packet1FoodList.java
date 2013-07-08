package com.github.krassekoder.mm13client.network;

import static com.github.krassekoder.mm13client.network.Packet.socket;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.network.QTcpSocket;
import java.util.ArrayList;
import java.util.List;

public class Packet1FoodList  extends Packet{

    public class FoodItem {
        public String name, id, price;

        public FoodItem(String data) {
            String[] items = data.split("\n");
            id = items[0];
            name = items[1];
            price = items[2];
        }

    }

    public static Packet1FoodList instance;

    public Packet1FoodList() {
        instance = this;
    }

    @Override
    public byte id() {
        return 1;
    }

    public List<FoodItem> request(String req) throws InvalidPacketException {
        ArrayList<FoodItem> res = new ArrayList<FoodItem>();

        QByteArray re = new QByteArray(req);
        QByteArray packet = new QByteArray();
        QDataStream s = new QDataStream(packet, QIODevice.OpenModeFlag.WriteOnly);

        s.writeInt(re.length());
        packet.append(re);

        sendData(packet);

        while(socket.bytesAvailable() < 5)
            socket.waitForReadyRead(10000);

        if(socket.read(1).at(0) != 1)
            throw new InvalidPacketException();

        QByteArray len = socket.read(4);
        s = new QDataStream(len);

        int length = s.readInt();

        while(socket.bytesAvailable() < length)
            socket.waitForReadyRead(10000);

        String response = socket.read(length).toString();
        String[] meals = response.split("\n\n");
        ArrayList<FoodItem> result = new ArrayList<FoodItem>();
        for(int i = 0; i < meals.length; i++) {
            if(!meals[i].isEmpty())
            result.add(new FoodItem(meals[i]));
        }

        return result;
    }

    @Override
    protected void receiveData(QTcpSocket socket) throws InvalidPacketException, TimeoutException {
        throw new InvalidPacketException();
    }


}
