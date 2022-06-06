import java.awt.EventQueue;

import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.awt.Font;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

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

	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(115, 35, 132, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Ilo\u015B\u0107 p\u00F3l (X)");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(115, 10, 132, 14);
		contentPane.add(lblNewLabel);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(115, 95, 132, 20);
		contentPane.add(textField_1);

		JLabel lblSizeY = new JLabel("Ilo\u015B\u0107 p\u00F3l (Y)");
		lblSizeY.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSizeY.setBounds(115, 70, 132, 14);
		contentPane.add(lblSizeY);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(115, 161, 132, 20);
		contentPane.add(textField_2);

		JLabel lblCount = new JLabel("Rozmiar kwadratu");
		lblCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCount.setBounds(115, 136, 132, 14);
		contentPane.add(lblCount);

		JLabel lblSpeed = new JLabel("Szybko\u015B\u0107 [ms]");
		lblSpeed.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSpeed.setBounds(115, 191, 132, 14);
		contentPane.add(lblSpeed);

		JButton btnLoadScript = new JButton("Start Mr\u00F3wka Langtona");
		btnLoadScript.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Okno(Integer.parseInt(
							textField.getText()),
							Integer.parseInt(textField_1.getText()),
							Integer.parseInt(textField_3.getText()),
							Integer.parseInt(textField_2.getText()),
							true
					);
				} catch (FileNotFoundException | ScriptException | NoSuchMethodException ex) {
					ex.printStackTrace();
				}

			}
		});
		btnLoadScript.setBounds(80, 262, 220, 70);
		contentPane.add(btnLoadScript);

		JButton btnLoadGolScript = new JButton("Start Gra w \u017Cycie");
		btnLoadGolScript.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadGolScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Okno(Integer.parseInt(
							textField.getText()),
							Integer.parseInt(textField_1.getText()),
							Integer.parseInt(textField_3.getText()),
							Integer.parseInt(textField_2.getText()),
							false
					);
				} catch (FileNotFoundException | ScriptException | NoSuchMethodException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnLoadGolScript.setBounds(80, 370, 220, 70);
		contentPane.add(btnLoadGolScript);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(115, 215, 132, 20);
		contentPane.add(textField_3);

	}





}
