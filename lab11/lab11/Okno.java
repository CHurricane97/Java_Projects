import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class Okno extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public Double[] a;
	public boolean order;
	public boolean Work = false;

	public Okno() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 91, 477, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Sortowanie Od Wi\u0119kszego do Mniejszego");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxNewCheckBox.setBounds(88, 140, 352, 52);
		contentPane.add(chckbxNewCheckBox);
		
		JButton btnNewButton = new JButton("Zapisz");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data = textField.getText().split(",");
				a = new Double[data.length];
				for(int i = 0; i < data.length; i++){
					a[i] = Double.valueOf(data[i]);
				}

				if(chckbxNewCheckBox.isSelected()){
					order = false;
				}else{
					order = true;
				}
				Work = true;
			}
		});
		btnNewButton.setBounds(122, 222, 254, 113);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Podaj Sk\u0142adowe Tablicy");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(159, 37, 179, 13);
		contentPane.add(lblNewLabel);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
	}
}
