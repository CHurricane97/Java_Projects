package windows.insert;

import daoObjects.EventDAO;
import daoObjects.InstallmentDAO;
import daoObjects.PaymentDAO;
import daoObjects.PersonDAO;
import tableObjects.Event;
import tableObjects.Installment;
import tableObjects.Payment;
import tableObjects.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InsertPaymentWindow extends JDialog{

    private JList<Event> list1;
    private JPanel panel1;
    private JList<Installment> list2;
    private JList<Person> list3;
    private JButton OKButton;
    private JButton anulujButton;

    public InsertPaymentWindow(Frame parent, PersonDAO personDAO, EventDAO eventDAO, InstallmentDAO installmentDAO, PaymentDAO paymentDAO) {
        super(parent, true);
        getEvents(eventDAO);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paymentDAO.save(new Payment(null, list2.getSelectedValue().getInstallment_Amount(),
                            list3.getSelectedValue().getPerson_Id(), list2.getSelectedValue().getEvent_Id(),
                            list2.getSelectedValue().getInstallment_Number()));
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Nie wprowadzono poprawnych danych", "Blad", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });
        anulujButton.addActionListener(e -> dispose());

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getInstalments(installmentDAO);
            }
        });

        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getPersons(personDAO);
            }
        });
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void getEvents(EventDAO eventDAO){
        ArrayList<Event> events = eventDAO.getAll();
        DefaultListModel<Event> eventDefaultListModel = new DefaultListModel<>();
        for (Event e : events) {
            eventDefaultListModel.addElement(e);
        }
        list1.setModel(eventDefaultListModel);
    }

    private void getInstalments(InstallmentDAO installmentDAO){
        ArrayList<Installment> installments = installmentDAO.getAllForSpecifiedEvent(list1.getSelectedValue());
        DefaultListModel<Installment> installmentDefaultListModel = new DefaultListModel<>();
        for (Installment i : installments) {
            installmentDefaultListModel.addElement(i);
        }
        list2.setModel(installmentDefaultListModel);
    }

    private void getPersons(PersonDAO personDAO){
        ArrayList<Person> people = personDAO.getAllWhoNotPay(list2.getSelectedValue());
        DefaultListModel<Person> personDefaultListModel = new DefaultListModel<>();
        for (Person p : people) {
            personDefaultListModel.addElement(p);
        }
        list3.setModel(personDefaultListModel);
    }
}
