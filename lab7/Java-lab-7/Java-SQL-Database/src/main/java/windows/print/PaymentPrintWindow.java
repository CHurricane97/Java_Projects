package windows.print;

import daoObjects.EventDAO;
import daoObjects.InstallmentDAO;
import daoObjects.PaymentDAO;
import daoObjects.PersonDAO;
import tableObjects.Event;
import tableObjects.Installment;
import tableObjects.Payment;
import tableObjects.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PaymentPrintWindow extends JDialog{
    private JList<Event> list1;
    private JPanel panel1;
    private JList<Person> list2;
    private JTable table1;
    DefaultTableModel tableModel;

    public PaymentPrintWindow(Frame parent, PersonDAO personDAO, EventDAO eventDAO, PaymentDAO paymentDAO) {
        super(parent, true);
        getPersons(personDAO);
        getEvents(eventDAO);
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                paymentTableInit(paymentDAO);
            }
        });
        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                paymentTableInit(paymentDAO);
            }
        });
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void paymentTableInit(PaymentDAO paymentDAO){
        try {
            tableModel = new DefaultTableModel();
            tableModel.addColumn("Data Wpłaty");
            tableModel.addColumn("Kwota Wpłaty");
            tableModel.addColumn("Numer Raty");
            if(list2.getSelectedValue() !=null && list1.getSelectedValue()!=null)
                for (Payment p : paymentDAO.getAllForSpecifiedPerson(list2.getSelectedValue(), list1.getSelectedValue())) {
                    tableModel.addRow(new Object[]{p.getPayment_Date(), p.getPayment_Amount(), p.getInstallment_Number()});
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
