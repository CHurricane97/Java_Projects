package windows;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import daoObjects.EventDAO;
import daoObjects.InstallmentDAO;
import daoObjects.PaymentDAO;
import daoObjects.PersonDAO;
import databaseConnection.ConnectionDataStorage;
import tableObjects.Event;
import tableObjects.Installment;
import tableObjects.Payment;
import tableObjects.Person;
import time.TimeMenu;
import windows.insert.InsertEventWindow;
import windows.insert.InsertInstallmentWindow;
import windows.insert.InsertPaymentWindow;
import windows.insert.InsertPersonWindow;
import windows.print.InstallmentPrintWindow;
import windows.print.MainPrintWindow;
import windows.print.PaymentPrintWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame {
    private JButton wyswietlDaneButton;
    private JPanel panel1;
    private JButton wczytajDaneZPlikuButton;
    private JButton wprowadzDaneRecznieButton;
    private JButton symulacjaUpływuCzasuButton;
    private JButton pobierzMonityButton;
    private JComboBox<String> comboBox1;
    private JLabel currDate;

    JDialog insertManual;
    JDialog print;

    PersonDAO personDAO;
    EventDAO eventDAO;
    PaymentDAO paymentDAO;
    InstallmentDAO installmentDAO;
    Connection connection;

    Date date;
    TimeMenu timeMenu;

    public void connectToDB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:" + ConnectionDataStorage.portNumber + "/" + ConnectionDataStorage.databaseName,
                            ConnectionDataStorage.loginName, ConnectionDataStorage.password);
            connection.setAutoCommit(false);
            personDAO = new PersonDAO(connection);
            eventDAO = new EventDAO(connection);
            paymentDAO = new PaymentDAO(connection);
            installmentDAO = new InstallmentDAO(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void setDate(LocalDate date) {
        this.date = Date.valueOf(date);
    }

    public MainWindow() {

        connectToDB();

        timeMenu = new TimeMenu();
        date = timeMenu.getDate();
        currDate.setText(date.toString());

        wyswietlDaneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseTable();
                print.setVisible(true);
            }
        });
        wczytajDaneZPlikuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertFileCSV();
            }
        });
        wprowadzDaneRecznieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (insertManual != null)
                    insertManual.setVisible(true);
            }
        });
        pobierzMonityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getMonits();
            }
        });
        symulacjaUpływuCzasuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate date = TimeMenu.checkDate(JOptionPane.showInputDialog("Podaj date w formacie \"RRRR-MM-DD\""));
                if (date != null) {
                    setDate(date);
                    currDate.setText(date.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Zla data!", "Blad", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                chooseTable();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    connection.close();
                    JOptionPane.showMessageDialog(null, "Polaczenie zakonczone");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        comboBoxInit();
        chooseTable();

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void comboBoxInit() {
        comboBox1.addItem("Osoba");
        comboBox1.addItem("Wplata");
        comboBox1.addItem("Rata");
        comboBox1.addItem("Wydarzenie");
    }

    private void chooseTable() {
        switch ((String) Objects.requireNonNull(comboBox1.getSelectedItem())) {
            case "Osoba":
                insertManual = new InsertPersonWindow(this, personDAO);
                print = new MainPrintWindow(this, "Osoba", personDAO, eventDAO);
                break;
            case "Wplata":
                insertManual = new InsertPaymentWindow(this, personDAO, eventDAO, installmentDAO, paymentDAO);
                print = new PaymentPrintWindow(this, personDAO, eventDAO, paymentDAO);
                break;
            case "Rata":
                insertManual = new InsertInstallmentWindow(this, eventDAO, installmentDAO);
                print = new InstallmentPrintWindow(this, personDAO, installmentDAO, eventDAO);
                break;
            case "Wydarzenie":
                insertManual = new InsertEventWindow(this, eventDAO);
                print = new MainPrintWindow(this, "Wydarzenie", personDAO, eventDAO);
                break;
        }
    }

    public void insertFileCSV() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
        jFileChooser.showOpenDialog(panel1);
        try (BufferedReader br = new BufferedReader(new FileReader(jFileChooser.getSelectedFile()))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                switch ((String) Objects.requireNonNull(comboBox1.getSelectedItem())) {
                    case "Osoba":
                        personDAO.save(new Person(values[0], values[1]));
                        if (first) {
                            JOptionPane.showMessageDialog(null, "Wczytano Dane", "Dane", JOptionPane.INFORMATION_MESSAGE);
                            first = false;
                        }

                        break;
                    case "Wydarzenie":
                        eventDAO.save(new Event(values[0], values[1], Date.valueOf(values[2])));
                        if (first) {
                            JOptionPane.showMessageDialog(null, "Wczytano Dane", "Dane", JOptionPane.INFORMATION_MESSAGE);
                            first = false;
                        }
                        break;
                    case "Rata":
                        installmentDAO.save(new Installment(Long.parseLong(values[0]), Long.parseLong(values[1]), Date.valueOf(values[2]), values[3]));
                        if (first) {
                            JOptionPane.showMessageDialog(null, "Wczytano Dane", "Dane", JOptionPane.INFORMATION_MESSAGE);
                            first = false;
                        }
                        break;
                    case "Wplata":
                        paymentDAO.save(new Payment(Date.valueOf(values[0]), values[1], Long.parseLong(values[2]), Long.parseLong(values[3]), Long.parseLong(values[4])));
                        if (first) {
                            JOptionPane.showMessageDialog(null, "Wczytano Dane", "Dane", JOptionPane.INFORMATION_MESSAGE);
                            first = false;
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Bledna opcja", "Blad", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException e) {

        }
    }

    private void getMonits() {
        try {
            FileWriter file = new FileWriter("monity.txt", true);
            FileWriter file1 = new FileWriter("monityEskalowane.txt", true);

            BufferedWriter out = new BufferedWriter(file);
            BufferedWriter out1 = new BufferedWriter(file1);

            out.write("------------------------[" + date.toString() + "]------------------------\n");
            out1.write("------------------------[" + date.toString() + "]------------------------\n");

            ArrayList<Person> persons = personDAO.getAll();
            ArrayList<Event> events = eventDAO.getAll();

            for (Person p : persons) {
                for (Event e : events) {
                    ArrayList<Installment> installments = installmentDAO.getAllForWhoNotPay(p, e);
                    for (Installment i : installments) {

                        long diffInMillies = i.getDue_Date().getTime() - date.getTime();
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                        if (diff < 7 && diff >= 0) {

                            out.write("Szanowna/y Pani/e " + p.getName() + " " + p.getSurname() + " Zostało " + diff + " dni do oplacenia raty nr: " + i.getInstallment_Number() + " wynoszacej " + i.getInstallment_Amount() + "zł dla wydarzenia " + e.getName() + "\n\n");

                        } else if (diff < 0) {

                            out1.write("Szanowna/y Pani/e " + p.getName() + " " + p.getSurname() + " Ma Pan/i zaległosc wynoszącą " + -1 * diff + " dni do oplacenia raty nr: " + i.getInstallment_Number() + " wynoszacej " + i.getInstallment_Amount() + "zł dla wydarzenia " + e.getName() + "\n\n");

                        }
//
                    }
                }
            }

            out.close();
            out1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }


        new MainWindow();
    }
}
