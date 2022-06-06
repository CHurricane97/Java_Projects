package windows.print;

import daoObjects.EventDAO;
import daoObjects.InstallmentDAO;
import daoObjects.PersonDAO;
import tableObjects.Event;
import tableObjects.Installment;
import tableObjects.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InstallmentPrintWindow extends JDialog{
    private JTable table1;
    private JPanel panel1;
    private JList<Event> list1;
    private JList<Person> list2;
    DefaultTableModel tableModel;

    public InstallmentPrintWindow(Frame parent, PersonDAO personDAO, InstallmentDAO installmentDAO, EventDAO eventDAO) {
        super(parent, true);
        getPersons(personDAO);
        getEvents(eventDAO);
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                installmentTableEInit(installmentDAO);
            }
        });
        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                installmentTableInit(installmentDAO);
            }
        });
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void installmentTableInit(InstallmentDAO installmentDAO){
        try {
            tableModel = new DefaultTableModel();
            tableModel.addColumn("Numer Raty");
            tableModel.addColumn("Termin Raty");
            tableModel.addColumn("Kwota Raty");
            if(list2.getSelectedValue() !=null && list1.getSelectedValue()!=null)
            for (Installment i : installmentDAO.getAllForWhoNotPay(list2.getSelectedValue(), list1.getSelectedValue())) {
                tableModel.addRow(new Object[]{i.getInstallment_Number(), i.getDue_Date(), i.getInstallment_Amount()});
            }
            table1.setModel(tableModel);
        }catch (Exception e){

        }
    }

    private void installmentTableEInit(InstallmentDAO installmentDAO){
        try {
            tableModel = new DefaultTableModel();
            tableModel.addColumn("Numer Raty");
            tableModel.addColumn("Termin Raty");
            tableModel.addColumn("Kwota Raty");
            if(list1.getSelectedValue()!=null)
                for (Installment i : installmentDAO.getAllForSpecifiedEvent(list1.getSelectedValue())) {
                    tableModel.addRow(new Object[]{i.getInstallment_Number(), i.getDue_Date(), i.getInstallment_Amount()});
                }
            table1.setModel(tableModel);
        }catch (Exception e){

        }
    }

    private void getEvents(EventDAO eventDAO){
        ArrayList<Event> events = eventDAO.getAll();
        DefaultListModel<Event> eventDefaultListModel = new DefaultListModel<>();
        for (Event e : events) {
            eventDefaultListModel.addElement(e);
        }
        list1.setModel(eventDefaultListModel);
    }


    private void getPersons(PersonDAO personDAO){
        ArrayList<Person> people = personDAO.getAll();
        DefaultListModel<Person> personDefaultListModel = new DefaultListModel<>();
        for (Person p : people) {
            personDefaultListModel.addElement(p);
        }
        list2.setModel(personDefaultListModel);
    }
}
