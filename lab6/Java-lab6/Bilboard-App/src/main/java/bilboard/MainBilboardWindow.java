package bilboard;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import interfaces.IBillboard;
import interfaces.IManager;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class MainBilboardWindow extends JFrame{

    Registry registry;
    IManager manager;
    String managerName = IManager.class.getName();
    int id;
    int period = 10000;

    IBillboard bilboard;
    Bilboard bilboardImpl;
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton ONButton;
    private JButton OFFButton;
    private JTextField textField1;
    private JButton setPeriodButton;

    private void runBilboard(){
        System.setProperty("javax.net.ssl.keyStore", "BilboardKeystore");
        System.setProperty("javax.net.ssl.keyStorePassword","bilboard");
        System.setProperty("javax.net.ssl.trustStore","BilboardKeystore");
        System.setProperty("javax.net.ssl.trustStorePassword","bilboard");

        try {
            bilboardImpl = new Bilboard(2);
            bilboard = (IBillboard) UnicastRemoteObject.exportObject(bilboardImpl, 0);
            registry = LocateRegistry.getRegistry("localhost", 50000, new SslRMIClientSocketFactory());
            manager = (IManager) registry.lookup(managerName);

            id = manager.bindBillboard(bilboard);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public MainBilboardWindow() {
        textField1.setText(String.valueOf(10000));
        runBilboard();

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try {
                    manager.unbindBillboard(id);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<Reklama> copy = new ArrayList<>(bilboardImpl.reklamy);
                Collections.reverse(copy);
                if(copy.size() == 0) textArea1.setText("");
                for(Reklama ad : copy){
                    int seconds = period;
                    while(seconds > 0) {
                        if (ad.pozostaloSec.getSeconds() <= 0) {
                            try {
                                bilboardImpl.removeAdvertisement(ad.id);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!bilboardImpl.reklamy.contains(ad)) break;
                        while (!bilboardImpl.onOff){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        int second = 100;
                        try {
                            Thread.sleep(second);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        textArea1.setText(ad.tekstReklamy + " Pozostały czas wyświetlania[ms]: " + seconds + "\n Pozostały czas całkowity: " + ad.pozostaloSec);
                        ad.pozostaloSec = ad.pozostaloSec.minusMillis(second);
                        seconds -= second;
                    }
                }
            }
        }).start();

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        ONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bilboardImpl.start();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        OFFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bilboardImpl.stop();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setPeriodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                period = Integer.parseInt(textField1.getText());
            }
        });
    }

    public static void main(String[] args) {

        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }


        new MainBilboardWindow();
    }
}
