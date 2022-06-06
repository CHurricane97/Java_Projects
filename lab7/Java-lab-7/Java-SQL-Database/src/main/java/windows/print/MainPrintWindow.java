package windows.print;

import daoObjects.EventDAO;
import daoObjects.InstallmentDAO;
import daoObjects.PaymentDAO;
import daoObjects.PersonDAO;
import tableObjects.Event;
import tableObjects.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainPrintWindow extends JDialog{
    private JTable table1;
    private JPanel panel1;
    DefaultTableModel tableModel;

    private void personTableInit(PersonDAO personDAO){
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Imie");
        tableModel.addColumn("Nazwisko");
        ArrayList<Person> people = personDAO.getAll();
        for(Person p : people){
            tableModel.addRow(new Object[]{p.getName(), p.getSurname()});
        }
        table1.setModel(tableModel);
    }

    private void eventTableInit(EventDAO eventDAO){
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nazwa");
        tableModel.addColumn("Miejsce");
        tableModel.addColumn("Data");
        ArrayList<Event> events = eventDAO.getAll();
        for(Event e : events){
            tableModel.addRow(new Object[]{e.getName(), e.getPlace(), e.getDate()});
        }
        table1.setModel(tableModel);
    }

    private void chooseTable(String tableName, PersonDAO personDAO, EventDAO eventDAO){
        switch (tableName){
            case "Osoba":
                personTableInit(personDAO);
                break;
            case "Wydarzenie":
                eventTableInit(eventDAO);
                break;
        }
    }

    public MainPrintWindow(Frame parent, String tableName, PersonDAO personDAO, EventDAO eventDAO) {
        super(parent, true);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        chooseTable(tableName,personDAO, eventDAO);
    }
}
