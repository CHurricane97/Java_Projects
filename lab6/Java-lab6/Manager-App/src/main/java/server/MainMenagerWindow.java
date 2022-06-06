package server;


import interfaces.IManager;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MainMenagerWindow extends JFrame{


    Registry registry;
    Manager managerImpl;
    IManager manager;
    private JList<Integer> list1;
    DefaultListModel<Integer> listModel;
    private JPanel panel1;

    private void runServer(){
        System.setProperty("javax.net.ssl.keyStore", "MenagerKeystore");
        System.setProperty("javax.net.ssl.keyStorePassword","manager");
        System.setProperty("javax.net.ssl.trustStore","MenagerKeystore");
        System.setProperty("javax.net.ssl.trustStorePassword","manager");
        System.setProperty("java.security.policy","p.policy");
        System.setSecurityManager(new SecurityManager());

        managerImpl = new Manager();
        try{
            manager = (IManager) UnicastRemoteObject.exportObject(managerImpl, 0);
            registry = LocateRegistry.createRegistry(50000, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory());
            registry.rebind(IManager.class.getName(), manager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(()->{
            while (true) {
                listModel = new DefaultListModel<>();

                for (Integer i : new ArrayList<>(managerImpl.bilboards.keySet())) {
                    listModel.addElement(i);
                }
                list1.setModel(listModel);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public MainMenagerWindow() {


        runServer();
        JFrame frame = new JFrame("Menager");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {


        new MainMenagerWindow();
    }
}
