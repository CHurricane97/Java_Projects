package windows.insert;

import daoObjects.EventDAO;
import tableObjects.Event;
import time.TimeMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

public class InsertEventWindow extends JDialog{
    private JTextField textField1;
    private JPanel panel1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton anulujButton;
    private JTextField textField3;

    public InsertEventWindow(Frame parent, EventDAO eventDAO) {
        super(parent, true);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate date = TimeMenu.checkDate(textField3.getText());
                if(date != null && textField1.getText().length() > 0 && textField2.getText().length() > 0)
                    eventDAO.save(new Event(textField1.getText(), textField2.getText(), Date.valueOf(date)));
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
