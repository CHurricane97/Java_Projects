

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Iterator;

import javax.crypto.SecretKey;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;



public class MainWindow extends JFrame {

	private JPanel contentPane;

	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		JOptionPane.showMessageDialog(null, "Java: 10");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void getKeys() {
		defaultList = new DefaultListModel<>();
		try {
	        for (Iterator<String> it = manager.ks.aliases().asIterator(); it.hasNext(); ) {
	            String s = it.next();
	            defaultList.addElement(s);
	        }
	        list.setModel(defaultList);
		}catch(Exception e) {
			
		}
	}
	/**
	 * Create the frame.
	 */
	
	KeystoreManager manager = new KeystoreManager();
	JList<String> list = new JList<>();
	DefaultListModel<String> defaultList;
	JScrollPane scrollPane = new JScrollPane();
	JComboBox<String> comboBox = new JComboBox<>();
	JFileChooser fc;
	
	
	
	public void comboInit() {
		comboBox.addItem("Algorytm RSA");
		comboBox.addItem("Algorytm AES");
	}
	
	public void choseAlghoritm(String alg, String message) {
		
	}
	

	
	public MainWindow() {
		
		comboInit();
	
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton encryptButton = new JButton("Zaszyfruj Dane");
		encryptButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				switch((String) comboBox.getSelectedItem()) {
				case "Algorytm RSA":
					try {
						RSAPublicKey pu = (RSAPublicKey) manager.ks.getCertificate(list.getSelectedValue()).getPublicKey();
						RSAPrivateKey pr = (RSAPrivateKey) manager.ks.getKey(list.getSelectedValue(), JOptionPane.showInputDialog("Enter password").toCharArray());
						
						RSAAlgorithm rsa = new RSAAlgorithm(pu.getModulus().bitLength());
						
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
						fc.showOpenDialog(contentPane);
						File file = fc.getSelectedFile();
						fc = null;
		
						
						FileInputStream fis = new FileInputStream(file.getAbsolutePath());
						byte[] b = fis.readAllBytes();
						fis.close();
						 
						String s = rsa.encryptFile(new String(b), pr);
						
						FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
						fos.write(s.getBytes());
						fos.close();
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				case "Algorytm AES":
					try {
						SecretKey sc = (SecretKey) manager.ks.getKey(list.getSelectedValue(),JOptionPane.showInputDialog("Enter password").toCharArray());
						
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
						fc.showOpenDialog(contentPane);
						File file = fc.getSelectedFile();
						fc = null;
						
						FileInputStream fis = new FileInputStream(file.getAbsolutePath());
						byte[] b = fis.readAllBytes();
						fis.close();
						
						AESAlgorithm aes = new AESAlgorithm();
						String a = aes.encryptFile(new String(b), sc);
						
						FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
						fos.write(a.getBytes());
						fos.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				}
			}
		});
		encryptButton.setBounds(330, 85, 200, 40);
		contentPane.add(encryptButton);
		
		JButton decryptButton = new JButton("Rozszyfruj Dane");
		decryptButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		decryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				switch((String) comboBox.getSelectedItem()) {
				case "Algorytm RSA":
					try {
						RSAPublicKey pu = (RSAPublicKey) manager.ks.getCertificate(list.getSelectedValue()).getPublicKey();
						RSAPrivateKey pr = (RSAPrivateKey) manager.ks.getKey(list.getSelectedValue(), JOptionPane.showInputDialog("Enter password").toCharArray());
						
						RSAAlgorithm rsa = new RSAAlgorithm(pu.getModulus().bitLength());
						
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
						fc.showOpenDialog(contentPane);
						File file = fc.getSelectedFile();
						fc = null;
						
						FileInputStream fis = new FileInputStream(file.getAbsolutePath());
						byte[] b = fis.readAllBytes();
						fis.close();
						
						String o = rsa.decryptFile(new String(b), pu);
						
						FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
						fos.write(o.getBytes());
						fos.close();
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				case "Algorytm AES":
					try {
						SecretKey sc = (SecretKey) manager.ks.getKey(list.getSelectedValue(),JOptionPane.showInputDialog("Enter password").toCharArray());
						
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
						fc.showOpenDialog(contentPane);
						File file = fc.getSelectedFile();
						fc = null;
						
						FileInputStream fis = new FileInputStream(file.getAbsolutePath());
						byte[] b = fis.readAllBytes();
						fis.close();
						
						AESAlgorithm aes = new AESAlgorithm();
						String a = aes.decryptFile(new String(b), sc);
						
						FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
						fos.write(a.getBytes());
						fos.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				}
			}
		});
		decryptButton.setBounds(330, 145, 200, 40);
		contentPane.add(decryptButton);
		
		JButton loadKeystoreButton = new JButton("Za\u0142aduj Keystore");
		loadKeystoreButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loadKeystoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				try {
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fc.setFileFilter(new FileNameExtensionFilter("JKS File", "jks"));
					fc.showOpenDialog(contentPane);
					File file = fc.getSelectedFile();
					fc = null;
					
					manager.loadKeystore(file.getAbsolutePath(), JOptionPane.showInputDialog("Enter password"));
					getKeys();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		loadKeystoreButton.setBounds(330, 205, 200, 40);
		contentPane.add(loadKeystoreButton);
		
		comboBox.setBounds(54, 352, 170, 22);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Wybierz Algorytm");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(54, 312, 170, 30);
		contentPane.add(lblNewLabel);
		
		scrollPane.setBounds(41, 70, 201, 232);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(list);
		
		JLabel lblWybierzKlucz = new JLabel("Wybierz Klucz");
		lblWybierzKlucz.setHorizontalAlignment(SwingConstants.CENTER);
		lblWybierzKlucz.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWybierzKlucz.setBounds(54, 30, 170, 30);
		contentPane.add(lblWybierzKlucz);
	}
}
