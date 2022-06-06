package gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import source.Initializer;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    JButton btnNewButton;
    JButton enButton;
    JButton plButton;
    JSlider slider;
    JLabel lblNewLabel;
    JLabel lblNewLabel_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame MainFrame = new MainFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */

    private void setLang(){
        btnNewButton.setText(Initializer.r.getString("MF_Button"));
        lblNewLabel_1.setText(Initializer.r.getString("MF_Question"));
    }


    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 497, 493);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        enButton = new JButton("EN");
        enButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Initializer.lang = "en";
                Initializer.country = "UK";
                Initializer.l=new Locale(Initializer.lang, Initializer.country);
                Initializer.r = ResourceBundle.getBundle("Bundle", Initializer.l);
                setLang();
                Initializer.pref.put("lang", Initializer.lang);
                Initializer.pref.put("country", Initializer.country);
            }
        });
        enButton.setBounds(272, 34, 89, 23);
        contentPane.add(enButton);

        plButton = new JButton("PL");
        plButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Initializer.lang = "pl";
                Initializer.country = "PL";
                Initializer.l=new Locale(Initializer.lang, Initializer.country);
                Initializer.r = ResourceBundle.getBundle("Bundle", Initializer.l);
                setLang();
                Initializer.pref.put("lang", Initializer.lang);
                Initializer.pref.put("country", Initializer.country);

            }
        });
        plButton.setBounds(371, 34, 89, 23);
        contentPane.add(plButton);

        slider = new JSlider();
        slider.setMaximum(20);
        slider.setValue(1);
        slider.setBounds(25, 254, 200, 26);
        contentPane.add(slider);

        btnNewButton = new JButton(Initializer.r.getString("MF_Button"));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new QuestionFrame(slider.getValue());
            }
        });
        btnNewButton.setBounds(314, 254, 130, 23);
        contentPane.add(btnNewButton);

        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(179, 228, 46, 14);
        contentPane.add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Wybierz ilo\u015B\u0107 pyta\u0144");
        lblNewLabel_1.setBounds(25, 181, 200, 14);
        contentPane.add(lblNewLabel_1);

        slider.addChangeListener(e -> lblNewLabel.setText(slider.getValue() + ""));
        slider.setMinimum(1);

        setLang();

        setVisible(true);
    }
}
