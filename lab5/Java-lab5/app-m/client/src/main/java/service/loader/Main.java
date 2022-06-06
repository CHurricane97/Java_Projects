package service.loader;


import service.loader.api.AnalysisException;
import service.loader.api.AnalysisService;
import service.loader.api.DataSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTable table_1;
    DefaultTableModel tableModel1;
    DefaultTableModel tableModel2;
    DataSet dataSet;
    AnalysisService chosenService;
    ArrayList<AnalysisService> services = new ArrayList<>();

    public void tableIni() {
        tableModel1 = new DefaultTableModel();
        tableModel2 = new DefaultTableModel();
        int cloumns = Integer.parseInt(JOptionPane.showInputDialog("Podaj liczbe kolumn"));
        int rows = Integer.parseInt(JOptionPane.showInputDialog("Podaj liczbe wierszy"));
        tableModel1.addColumn("ID Wiersza");
        tableModel2.addColumn("Algorytm");
        for (int i = 0; i < cloumns; i++) {
            String name = JOptionPane.showInputDialog("Podaj nazwe kolumny");
            tableModel1.addColumn(name);
            tableModel2.addColumn(name);
        }
        for (int i = 0; i < rows; i++) {
            tableModel1.addRow(new Object[]{i});
        }
        table_1.setModel(tableModel2);
        table.setModel(tableModel1);
    }

    public void setResult() {
        new Thread(() -> {
            DataSet r = null;
            while (true) {
                try {
                    Thread.sleep(300);
                    r = chosenService.retrieve(true);
                } catch (AnalysisException | InterruptedException e) {
                    e.printStackTrace();
                }
                if (r != null) {
                    Object[] o = new Object[tableModel1.getColumnCount()];
                    for (int i = 0; i < tableModel1.getColumnCount(); i++) {
                        o[i] = r.getData()[i][0];
                    }
                    tableModel2.addRow(o);
                    table_1.setModel(tableModel2);
                    break;
                }
            }
        }).start();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Main() {
        JComboBox<AnalysisService> choice = new JComboBox<>();
        choice.setBounds(475, 225, 201, 20);
        ServiceLoader<AnalysisService> serviceLoader = ServiceLoader.load(AnalysisService.class);
        for (AnalysisService s : serviceLoader) {
            //services.add(s);
            choice.addItem(s);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 866, 520);
        contentPane = new JPanel();
        contentPane.add(choice);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 11, 772, 156);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(25, 306, 772, 139);
        contentPane.add(scrollPane_1);

        table_1 = new JTable();
        scrollPane_1.setViewportView(table_1);

        JButton btnNewButton = new JButton("Nowa tabela");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableIni();
            }
        });
        btnNewButton.setBounds(30, 222, 182, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Oblicz");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chosenService = (AnalysisService) choice.getSelectedItem();
                dataSet = new DataSet();
                tableModel1 = (DefaultTableModel) table.getModel();
                String[][] data = new String[tableModel1.getColumnCount()][];
                for (int i = 0; i < tableModel1.getColumnCount(); i++) {
                    data[i] = new String[tableModel1.getRowCount()];
                    for (int j = 0; j < tableModel1.getRowCount(); j++) {
                        data[i][j] = tableModel1.getValueAt(j, i).toString();
                    }
                }
                dataSet.setData(data);
                try {
                    chosenService.submit(dataSet);
                    setResult();
                } catch (AnalysisException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNewButton_1.setBounds(253, 222, 157, 23);
        contentPane.add(btnNewButton_1);

    }
}