import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class RegisterPage extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JTextField IDtxt;
	private JTextField pwdTxt;
	private JTextField nickNameTxt;
	private JButton valCheckBtn;
	private JButton regBtn;
	private JLabel validPwdLBL;
	private JLabel validNickLBL;
	private JLabel IDlbl;
	private JLabel pwdLBL;
	private JLabel nickNameLBL;
	private JLabel regLBL;
	private int IDcheck = 0;
	private int PWDcheck = 0;
	private int NickNameCheck = 0;
	private JButton backBtn;
	
	/**
	 * Create the frame.
	 */
	public RegisterPage(JFrame parentFrame)  {
		setTitle("Lee-E Book"); //table
		
		setVisible(true); //make frame visible
		
		Admin admin = new Admin(); //// make a admin instance to access System level data
		
		//initial status of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setBounds(100, 100, 449, 286);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//make the panel
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		//textFiled for ID
		IDtxt = new JTextField();
		IDtxt.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		IDtxt.setBounds(35, 71, 190, 21);
		panel.add(IDtxt);
		IDtxt.setColumns(10);
		
		//button for ID valididation check
		valCheckBtn = new JButton("Validity Check");
		valCheckBtn.setBackground(SystemColor.inactiveCaption);
		valCheckBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 12));
		valCheckBtn.setBounds(253, 71, 135, 23);
		valCheckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempID = IDtxt.getText();
				if(tempID.length() == 0)   //if there's no input on the ID textField
				{
					JOptionPane.showMessageDialog(valCheckBtn, "You have to enter at least 1 character!","Invalid ID", JOptionPane.ERROR_MESSAGE);
					IDcheck = 0;
				}	
				else if(admin.findIDduplicate(tempID) == 0) //if there's already been a same ID used by other user
				{
					JOptionPane.showMessageDialog(valCheckBtn, "The ID has already been used. Try again!", "Invalid ID", JOptionPane.ERROR_MESSAGE);
					IDtxt.setText("");
					IDcheck = 0;
				}
				else //if all the conditions are satisfied
				{
					JOptionPane.showMessageDialog(regBtn, "You can use it as your ID!", "Valid ID", JOptionPane.INFORMATION_MESSAGE, BookListPage.newImage);
					IDcheck = 1;
				}
			}
		});
		panel.add(valCheckBtn);
		
		//textFidls for Password
		pwdTxt = new JTextField();
		pwdTxt.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		pwdTxt.setBounds(35, 114, 190, 21);
		panel.add(pwdTxt);
		pwdTxt.setColumns(10);
		
		//textFidls for Nickname
		nickNameTxt = new JTextField();
		nickNameTxt.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		nickNameTxt.setBounds(35, 157, 190, 21);
		panel.add(nickNameTxt);
		nickNameTxt.setColumns(10);
		
		//button for Register
		regBtn = new JButton("REGISTER");
		regBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		regBtn.setBounds(236, 207, 118, 23);
		regBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempID = null;
				String tempPassword = null;
				String tempNickname = null;

				tempID = IDtxt.getText();
				tempPassword = pwdTxt.getText();
				if(tempPassword.length() <= 4)   //check the validity of the password
					PWDcheck = 0;
				else  
					PWDcheck = 1;
				
				tempNickname = nickNameTxt.getText(); //check the validity of nickname (more than 0 character)
				if(tempNickname.length() == 0) 
					NickNameCheck = 0;
				else
					NickNameCheck = 1;

				if(IDcheck == 0) //check the validity of ID
					JOptionPane.showMessageDialog(regBtn, "You haven't done the ID check. Try again!", "Fail to Register", JOptionPane.ERROR_MESSAGE);
				else if(PWDcheck == 0) //if ID is valid, but PWD is not 
				{
					JOptionPane.showMessageDialog(regBtn, "The password is not valid. Try again!", "Fail to Register", JOptionPane.ERROR_MESSAGE);
					pwdTxt.setText("");
				}
				else if(NickNameCheck == 0) //if ID, and PWD are valid, but nickname not 
				{
					JOptionPane.showMessageDialog(regBtn, "Nickname should be more than 0 letter. Try again!", "Fail to Register", JOptionPane.ERROR_MESSAGE);
					nickNameTxt.setText("");
				}
				else //all conditions are satisfied
				{
					JOptionPane.showMessageDialog(regBtn, "Your Account is successfully made", "Register Completed!", JOptionPane.INFORMATION_MESSAGE, BookListPage.newImage);
					admin.addUser(tempID, tempPassword, tempNickname);
					parentFrame.setVisible(true);
					dispose();
				}
			}
		});
		panel.add(regBtn);
		
		
		//make a "back" button
		backBtn = new JButton("BACK");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setVisible(true);
				dispose();
			}
		});
		backBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		backBtn.setBounds(70, 207, 118, 23);
		panel.add(backBtn);
		
		//label for the PWD condition
		validPwdLBL = new JLabel("More than 4 characters!");
		validPwdLBL.setFont(new Font("Bookman Old Style", Font.PLAIN, 13));
		validPwdLBL.setBounds(253, 114, 192, 15);
		panel.add(validPwdLBL);
		
		//label for the nickname condition
		validNickLBL = new JLabel("Anything You Want!");
		validNickLBL.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		validNickLBL.setBounds(253, 157, 192, 15);
		panel.add(validNickLBL);
		
		//label for indicating ID-PWD-nickName textFields
		IDlbl = new JLabel("ID");
		IDlbl.setFont(new Font("Dialog", Font.BOLD, 12));
		IDlbl.setBounds(35, 55, 147, 15);
		panel.add(IDlbl);
		
		//label for indicating the Password
		pwdLBL = new JLabel("Password");
		pwdLBL.setFont(new Font("Dialog", Font.BOLD, 12));
		pwdLBL.setBounds(35, 100, 147, 15);
		panel.add(pwdLBL);
		
		//label for indicating the nickname
		nickNameLBL = new JLabel("Nickname");
		nickNameLBL.setFont(new Font("Dialog", Font.BOLD, 12));
		nickNameLBL.setBounds(35, 142, 147, 15);
		panel.add(nickNameLBL);
		
		//label for the title
		regLBL = new JLabel("Register Your Account");
		regLBL.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		regLBL.setBounds(35, 20, 196, 15);
		panel.add(regLBL);
		
	}
}
