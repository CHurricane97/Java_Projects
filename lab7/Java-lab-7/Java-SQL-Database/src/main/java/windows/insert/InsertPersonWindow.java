package windows.insert;

import daoObjects.PersonDAO;
import tableObjects.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertPersonWindow extends JDialog{
    private JTextField textField1;
    private JPanel panel1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton anulujButton;

    public InsertPersonWindow(Frame parent, PersonDAO personDAO) {
        super(parent, true);
        OKButton.addActionListener(e -> {
            if(textField1.getText().length() > 0 && textField2.getText().length() > 0) {
                personDAO.save(new Person(textField1.getText(), textField2.getText()));
            }else{
                JOptionPane.showMessageDialog(null, "Nie wprowadzono danych", "Blad", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        anulujButton.addActionListener(e -> dispose());
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }


}
