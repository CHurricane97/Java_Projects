package Core;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Window extends JFrame {

	private JPanel contentPane;
	JList<File> list;
	JLabel lblNewLabel;
	JList<String> list_1;
	DataStructureOperator DSO=new DataStructureOperator();
	JLabel lblNewLabel_1;
	JScrollPane JP;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 709, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 list = new JList<>();
		 JP=new JScrollPane(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] rec=DSO.getRecord(list.getSelectedValue().getName());
				Image im=DSO.getImage(list.getSelectedValue().getName());
				lblNewLabel_1.setText("Wczytano z Pamieci");
			if(rec==null) {
				rec=DSO.getRecordFromFile(list.getSelectedValue().toString());
				DSO.addRecordData(list.getSelectedValue().getName(), rec);
				lblNewLabel_1.setText("Wczytano z Pliku");
			}
			if(im==null) {
				
				im=DSO.getImageFromFile(list.getSelectedValue().toString(), 420, 260);
				DSO.addImage(list.getSelectedValue().getName(), im);
				lblNewLabel_1.setText("Wczytano z Pliku");
			}
				
				
				 
				lblNewLabel.setIcon(new ImageIcon(im));
				DefaultListModel<String> listmodel= new DefaultListModel<>();
				for (int i=0;i<rec.length;i++) {
					listmodel.addElement(rec[i]);
				}
				list_1.setModel(listmodel);
			}
		});
		JP.setBounds(25, 23, 204, 408);
		contentPane.add(JP);
		
		list_1 = new JList<>();
		list_1.setBounds(292, 23, 349, 112);
		contentPane.add(list_1);
		
		 lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(265, 145, 420, 260);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Wczytaj");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadDirectoryList();
			}
		});
		btnNewButton.setBounds(25, 466, 616, 21);
		contentPane.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(278, 428, 130, 21);
		contentPane.add(lblNewLabel_1);
	}
	
	void LoadDirectoryList() {
		
		JFileChooser jfilechooser= new JFileChooser();
		jfilechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfilechooser.showOpenDialog(contentPane);
		DefaultListModel<File> listmodel= new DefaultListModel<>();
		File file=jfilechooser.getSelectedFile();
		File[] file2=file.listFiles();
		for(int i =0;i<file2.length;i++){
			
			listmodel.addElement(file2[i]);
		}
		list.setModel(listmodel);
	}
	
	
	
	
}
