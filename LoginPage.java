import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Color;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JTextField idTxt;
	private JTextField pwdTxt;
	private JButton loginBtn;
	private int LoginCheck = 999;
	private MemberInfo memberHandler;
	private JButton logoutBtn;
	private JButton regBtn;
	private JLabel pwdLBL;
	private JLabel idLBL;
	private JLabel titleLBL;
	private JLabel lblNewLabel;
    private static ImageIcon logImage = new ImageIcon("logo.png");
    private static Image logImg = logImage.getImage();
    private static Image changedLogImg = logImg.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
    private static ImageIcon newLogImage = new ImageIcon(changedLogImg);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}//end of run
		}); //end of invokaLater
	} //end of main

	/**
	 * Create the frame.
	 */
	public LoginPage() {

		setTitle("Lee-E Book"); //title
		
		Admin admin = new Admin(); // make a admin instance to access System level data
		
		//to make initial states of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//make lablel for ID
		idLBL = new JLabel("ID");
		idLBL.setHorizontalAlignment(SwingConstants.CENTER);
		idLBL.setFont(new Font("Bookman Old Style", Font.BOLD, 17));
		idLBL.setBackground(Color.GRAY);
		idLBL.setBounds(50, 72, 90, 28);
		
		//make label for PW
		pwdLBL = new JLabel("Password");
		pwdLBL.setBackground(Color.GRAY);
		pwdLBL.setHorizontalAlignment(SwingConstants.CENTER);
		pwdLBL.setFont(new Font("Bookman Old Style", Font.BOLD, 17));
		pwdLBL.setBounds(50, 124, 90, 28);
		
		//make the pane
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		//make Log in button
		loginBtn = new JButton("LOG IN");
		loginBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 12));
		loginBtn.setBounds(166, 193, 100, 27);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputID = idTxt.getText();
				String inputPWD = pwdTxt.getText();
				LoginCheck = admin.validity(inputID, inputPWD);  //to check if it is valid ID-PW
				if(LoginCheck == -1) //if it is not valid (Wrong ID)
				{
					JOptionPane.showMessageDialog(loginBtn, "Invalid ID. Try again!", "Fail to Login", JOptionPane.ERROR_MESSAGE);
					idTxt.setText("");
					pwdTxt.setText("");
				}
				else if(LoginCheck == -2) //if it is invalid (wrong PWD)
				{
					JOptionPane.showMessageDialog(loginBtn, "Password is not correct. Try again!", "Fail to Login", JOptionPane.ERROR_MESSAGE);
					pwdTxt.setText("");
				}
				else //if it is right ID-PW
				{
					memberHandler = admin.getUser(LoginCheck);	//set member handler for manipulating the following processes
					JOptionPane.showMessageDialog(loginBtn, "Welcome " + memberHandler.getNickname() + "!", "WelCome", JOptionPane.INFORMATION_MESSAGE, BookListPage.newImage);
					idTxt.setText("");
					pwdTxt.setText("");
					BookListPage bookList = new BookListPage(LoginPage.this, memberHandler); //call next page
					setVisible(false);
				}
			}
		});
		panel.setLayout(null);
		
		//textfields for entering ID
		idTxt = new JTextField();
		idTxt.setHorizontalAlignment(SwingConstants.CENTER);
		idTxt.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		idTxt.setBounds(208, 72, 180, 28);
		panel.add(idTxt);
		idTxt.setColumns(10);
		
		//textfield for entering PWD
		pwdTxt = new JTextField();
		pwdTxt.setHorizontalAlignment(SwingConstants.CENTER);
		pwdTxt.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		pwdTxt.setBounds(208, 124, 180, 28);
		panel.add(pwdTxt);
		pwdTxt.setColumns(10);
		panel.add(loginBtn);
		
		//Log out Button
		logoutBtn = new JButton("LOG OUT");
		logoutBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 12));
		logoutBtn.setBounds(298, 193, 100, 27);
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(logoutBtn, "Successfully logged out!", "Log out", JOptionPane.INFORMATION_MESSAGE, BookListPage.newImage);
				memberHandler = null;   //release memberhandler
				idTxt.setText("");
				pwdTxt.setText("");
			}
		});
		panel.add(logoutBtn);
		
		//Regiter Button
		regBtn = new JButton("REGISTER");
		regBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 12));
		regBtn.setBounds(30, 193, 106, 27);
		regBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPage register = new RegisterPage(LoginPage.this); //call register page
				setVisible(false);
			}
		});
		panel.add(regBtn);
		panel.add(pwdLBL);
		panel.add(idLBL);
		
		//title label
		titleLBL = new JLabel("Lee-E BOOK");
		titleLBL.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
		titleLBL.setHorizontalAlignment(SwingConstants.CENTER);
		titleLBL.setBounds(147, 23, 155, 30);
		panel.add(titleLBL);
		
		//logo lable
		lblNewLabel = new JLabel(newLogImage);
		lblNewLabel.setBounds(99, 10, 62, 52);
		panel.add(lblNewLabel);
	}
}
