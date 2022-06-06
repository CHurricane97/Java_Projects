package project;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Window extends JFrame {
    private JPanel panel;
    private JTable table1;
    private JTextField textField1;
    private JButton wykonajZadanieButton;
    private JList<String> list;
    private JButton zaladujKlaseButton;
    private JTextPane textPane1;
    private JTextArea textArea1;
    private JButton odswiezButton;
    private JButton wyladujButton;
    DefaultTableModel tableModel1;
    Path globalPath = Path.of(System.getProperty("user.dir"));

    ArrayList<Class<?>> classes = new ArrayList<>();


    Class<?> processorClass;
    Object processor;
    Method submitTaskMethod;
    Method getResultMethod;
    Method getInfoMethod;
    StatusListener st = new StatusListener();

    public void tableIni() {
        tableModel1 = new DefaultTableModel();
        tableModel1.addColumn("Zadanie Id");
        tableModel1.addColumn("Postep");
        tableModel1.insertRow(tableModel1.getRowCount(), new Object[]{tableModel1.getColumnName(0), tableModel1.getColumnName(1)});
        table1.setModel(tableModel1);
    }

    public void tableUpdater() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(
                () -> {
                    SwingUtilities.invokeLater(() -> {
                        tableModel1 = new DefaultTableModel();
                        tableModel1.addColumn("Zadanie Id");
                        tableModel1.addColumn("Postep");
                        tableModel1.insertRow(tableModel1.getRowCount(), new Object[]{tableModel1.getColumnName(0), tableModel1.getColumnName(1)});
                        for (HashMap.Entry<Integer, Integer> entry : st.statusTable.entrySet()) {
                            Integer key = entry.getKey();
                            Integer value = entry.getValue();
                            try {
                                tableModel1.setValueAt(value, key, 1);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                tableModel1.insertRow(tableModel1.getRowCount(), new Object[]{key, value});
                            }
                        }
                        table1.setModel(tableModel1);
                    });
                    if (getResultMethod != null) {
                        try {
                            textArea1.setText((String) getResultMethod.invoke(processor));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                },
                1, 10, TimeUnit.MILLISECONDS);
    }

    public Window() {
        tableIni();
        classes.clear();
        loadClassesF(globalPath);
        tableUpdater();

        wykonajZadanieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    submitTaskMethod.invoke(processor, textField1.getText(), st);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        });
        zaladujKlaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showOpenDialog(panel);
                globalPath = fc.getSelectedFile().toPath();

                classes.clear();
                loadClassesF(globalPath);
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                try {
                    st.statusTable.clear();
                    processorClass = classes.get(list.getSelectedIndex());
                    Constructor<?> cp = processorClass.getConstructor();
                    processor = cp.newInstance();
                    submitTaskMethod = processorClass.getDeclaredMethod("submitTask", String.class, processing.StatusListener.class);
                    getResultMethod = processorClass.getDeclaredMethod("getResult");
                    getInfoMethod = processorClass.getDeclaredMethod("getInfo");
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        list.addContainerListener(new ContainerAdapter() {
        });
        odswiezButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes.clear();
                loadClassesF(globalPath);
            }
        });
        wyladujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                classes = new ArrayList<>();
                processorClass = null;
                processor = null;
                submitTaskMethod = null;
                getResultMethod = null;
                getInfoMethod = null;
                list.setModel(new DefaultListModel<>());
                System.gc();
            }
        });
    }

    void loadClassesF(Path path) {
        LoaderClass l = new LoaderClass();
        Stream<Path> list1;
        try {
            list1 = Files.list(path);
            Path[] listOfFiles = list1.toArray(Path[]::new);
            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (Path file : listOfFiles) {
                String s = file.getFileName().toString();
                String[] tab = s.split("\\.");
                if (tab[tab.length - 1].equals("class")) {
                    listModel.addElement(file.getFileName().toString());
                    classes.add(l.findClass(file.toString()));
                }
            }
            list.setModel(listModel);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {

        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }


        new Window();
    }
}
