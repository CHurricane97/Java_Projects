package klient;

import interfaces.IClient;

import java.rmi.RemoteException;

public class Klient implements IClient {

    public int id;

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        id = orderId;
    }
}
