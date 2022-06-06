package server;

import interfaces.IBillboard;
import interfaces.IManager;
import interfaces.Order;

import java.rmi.RemoteException;
import java.util.HashMap;

public class Manager implements IManager {

    HashMap<Integer, Order> orders;
    HashMap<Integer, IBillboard> bilboards;
    int orderCount = 0;
    int bilboardCount = 0;

    public Manager() {
        this.orders = new HashMap<>();
        bilboards = new HashMap<>();
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        bilboards.put(bilboardCount, billboard);
        return bilboardCount++;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        bilboards.remove(billboardId);
        return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        int orderID = orderCount++;
        order.client.setOrderId(orderID);
        orders.put(orderID, order);

        for(IBillboard bilb : bilboards.values()){
            if(bilb.getCapacity()[1] > 0){
                bilb.addAdvertisement(order.advertText, order.displayPeriod, orderID);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        for(IBillboard bilb : bilboards.values()){
            if(bilb.removeAdvertisement(orderId)) return true;
        }
        return false;
    }

    @Override
    public void increase() throws RemoteException {

    }

    @Override
    public int getOrderNumber() throws RemoteException {
        return orderCount;
    }
}
