package bilboard;

import interfaces.IBillboard;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;

public class Bilboard implements IBillboard {

    ArrayList<Reklama> reklamy;
    int[] capacity;
    Duration duration;
    boolean onOff = true;

    public Bilboard(int maxPlacs) {
        this.reklamy = new ArrayList<>();
        capacity = new int[2];
        capacity[0] = maxPlacs;
        capacity[1] = capacity[0];
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        reklamy.add(new Reklama(orderId, advertText, displayPeriod));

        capacity[1] = capacity[0] - reklamy.size();
        return false;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        ArrayList<Reklama> c = new ArrayList<>(reklamy);
        for (Reklama ad : c){
            if(ad.id == orderId){
                reklamy.remove(ad);
                capacity[1] = capacity[0] - reklamy.size();
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return capacity;
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        duration = displayInterval;

    }

    @Override
    public boolean start() throws RemoteException {
        onOff = true;
        return false;
    }

    @Override
    public boolean stop() throws RemoteException {
        onOff = false;
        return false;
    }
}
