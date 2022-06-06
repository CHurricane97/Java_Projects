package gui;

import source.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ChoiceFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class QuestionFrame extends JDialog {

	private JPanel contentPane;
	private JTextField textField;
	private int number;
	private Quiz quiz;
	private Question[] questions;
	private int questionPointer;
	JLabel xxLabel;
	JLabel questionLabel;
	JLabel answerLabel;
	JButton btnNewButton;
	JButton btnNewButton_1;
	int points;

	private void setLang(){
		btnNewButton.setText(Initializer.r.getString("QF_button1"));
		btnNewButton_1.setText(Initializer.r.getString("QF_button2"));
	}

	public QuestionFrame(int number) {
		super();
		this.number = number;
		points = 0;
		quiz = new Quiz();
		questions = new Question[number];
		questionPointer = 0;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 688, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		xxLabel = new JLabel("");
		xxLabel.setBounds(32, 38, 46, 14);
		contentPane.add(xxLabel);

		questionLabel = new JLabel("");
		questionLabel.setBounds(10, 150, 652, 33);
		contentPane.add(questionLabel);

		textField = new JTextField();
		textField.setBounds(10, 242, 652, 33);
		contentPane.add(textField);
		textField.setColumns(10);

		answerLabel = new JLabel("");
		answerLabel.setBounds(10, 323, 652, 33);
		contentPane.add(answerLabel);

		this.repaint();

		btnNewButton = new JButton();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setEnabled(true);
				boolean answer = ServiceOperations.checkAnswer(questions[questionPointer - 1].getAnswer(), textField.getText());
				if(answer){
					answerLabel.setText(questions[questionPointer - 1].getAnswerGood());
					points++;
				}else{
					answerLabel.setText(questions[questionPointer - 1].getAnswerBad());
				}
			}
		});
		btnNewButton.setBounds(10, 441, 149, 23);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton();
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answerLabel.setText("");
				textField.setText("");
				if(questionPointer < number) {
					generateQuestion();
				}else{
					if(Initializer.lang =="pl"){
						JOptionPane.showMessageDialog(null, numbersPL(points));
					}
					else {
						JOptionPane.showMessageDialog(null, numbersEN(points));
					}
					dispose();
				}
			}
		});
		btnNewButton_1.setBounds(501, 441, 161, 23);
		contentPane.add(btnNewButton_1);

		generateQuestion();

		setLang();

		setVisible(true);
	}

	private void generateQuestion(){
		btnNewButton_1.setEnabled(false);
		xxLabel.setText((questionPointer + 1) + "/" + number);
		questions[questionPointer] = quiz.generateRandomQuestion();
		questionLabel.setText(questions[questionPointer].getContent());
		questionPointer++;

	}

	public static String numbersEN(int points){
		//System.out.println("eng");
		ChoiceFormat fmt = new ChoiceFormat("0#" + Initializer.r.getString("p2") + "| 1#" + Initializer.r.getString("p1") + "| 1<"+ Initializer.r.getString("p2"));
		return points + " " + fmt.format(points);
	}

	public static String numbersPL(int points){
		//System.out.println(points);

		if (points==0){
			return points +" " +Initializer.r.getString("p3");
		}
		String s = Integer.toString(points);
		char f = s.charAt(s.length() - 1);
		char g = '0';
		if(s.length() > 1)
			g = s.charAt(s.length() - 2);
		int j = Integer.parseInt(Character.toString(g));
		int u = Integer.parseInt(Character.toString(f));
		if(points > 19 && j!=1) {
			//ChoiceFormat fmt = new ChoiceFormat("0#punktów | 1#punktów | 1<punkty | 4<punktów");
			ChoiceFormat fmt = new ChoiceFormat("0#" + Initializer.r.getString("p3") + "| 1#" + Initializer.r.getString("p3") + "| 1<"+ Initializer.r.getString("p2")+ "| 4<"+ Initializer.r.getString("p3"));
			return points + " " + fmt.format(u);
		}
		else if(j == 1){
			//ChoiceFormat fmt = new ChoiceFormat("0#punktów");
			ChoiceFormat fmt = new ChoiceFormat("0#" + Initializer.r.getString("p3"));
			return points + " " + fmt.format(u);
		}else{
			//ChoiceFormat fmt = new ChoiceFormat("0#punktów| 1#punktów | 1<punkty | 4<punktów ");
			ChoiceFormat fmt = new ChoiceFormat("0#" + Initializer.r.getString("p3") + "| 1#" + Initializer.r.getString("p1") + "| 1<"+ Initializer.r.getString("p2")+ "| 4<"+ Initializer.r.getString("p3"));
			return points + " " + fmt.format(points);
		}
	}

}
