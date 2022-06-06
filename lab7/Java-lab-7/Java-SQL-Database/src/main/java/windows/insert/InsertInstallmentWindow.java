package windows.insert;

import daoObjects.EventDAO;
import daoObjects.InstallmentDAO;
import tableObjects.Event;
import tableObjects.Installment;
import time.TimeMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class InsertInstallmentWindow extends JDialog{
    private JList<Event> list1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton anulujButton;

    private void getEvents(EventDAO eventDAO){
        ArrayList<Event> events = eventDAO.getAll();
        DefaultListModel<Event> eventDefaultListModel = new DefaultListModel<>();
        for (Event e : events) {
            eventDefaultListModel.addElement(e);
        }
        list1.setModel(eventDefaultListModel);
    }

    public InsertInstallmentWindow(Frame parent, EventDAO eventDAO, InstallmentDAO installmentDAO) {
        super(parent, true);
        getEvents(eventDAO);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate date = TimeMenu.checkDate(textField2.getText());
                if(date != null && textField1.getText().length() > 0 && textField2.getText().length() > 0)
                    installmentDAO.save(new Installment(list1.getSelectedValue().getEvent_Id(), installmentDAO.getMaxInstalmentNumberForEvent(list1.getSelectedValue().getEvent_Id()), Date.valueOf(date), textField1.getText()));
                else JOptionPane.showMessageDialog(null, "Nie wprowadzono poprawnych danych", "Blad", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        });
        anulujButton.addActionListener(e -> dispose());
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }
}
