package klient;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import interfaces.IClient;
import interfaces.IManager;
import interfaces.Order;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

public class MainClientWindow {

    Registry registry;
    IManager manager;
    String managerName = IManager.class.getName();

    Klient klientImpl;
    IClient klient;
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton makeOrderButton;
    private JList<Integer> list1;
    private JTextField textField1;
    private DefaultListModel<Integer> listModel;

    private void setClient(){
        klientImpl = new Klient();
        try{
        klient = (IClient) UnicastRemoteObject.exportObject(klientImpl, 0);
        registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostName(), 50000, new SslRMIClientSocketFactory());
        manager = (IManager) registry.lookup(managerName);
        } catch (RemoteException | NotBoundException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void makeOrder(String text){
        try {
            Order order = new Order();
            order.client = klient;
            order.advertText = text;
            order.displayPeriod = Duration.ofSeconds(Integer.parseInt(textField1.getText()));

            manager.placeOrder(order);
            listModel.addElement(klientImpl.id);
            list1.setModel(listModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MainClientWindow() {


        listModel = new DefaultListModel<>();
        setClient();
        makeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeOrder(textArea1.getText());
            }
        });
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if(!manager.withdrawOrder(list1.getSelectedValue())){
                        JOptionPane.showMessageDialog(null, "Zamówienie Nieaktywne");

                    } else {
                        JOptionPane.showMessageDialog(null, "Zamówienie Dezaktywowane");
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
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


        System.setProperty("javax.net.ssl.keyStore", "ClientKeystore");
        System.setProperty("javax.net.ssl.keyStorePassword","client");
        System.setProperty("javax.net.ssl.trustStore","ClientKeystore");
        System.setProperty("javax.net.ssl.trustStorePassword","client");
        JFrame frame = new JFrame("Klient");
        frame.setContentPane(new MainClientWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
