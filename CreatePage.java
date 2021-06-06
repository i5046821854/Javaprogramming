import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class CreatePage extends JFrame {

	private JPanel contentPane;
	private JLabel titleOnTop;
	private JPanel panel;
	private JTextField titleTxt;
	private JTextField authorTxt;
	private JTextField genreTxt;
	private JButton createBtn;
	private JTextArea contentTxt;
	private int selectedItem;
	private String tempAuthor;
	private String tempTitle;
	private String tempGenre;
	private JButton cancelBtn;
	private JLabel titleLBL;
	private JLabel authorLBL;
	private JLabel genreLBL;
	private JLabel contentLBL;



	/**
	 * Create the frame.
	 */
	public CreatePage(JFrame parentFrame, MemberInfo User, JButton fakeButton) {
		
		setTitle("Lee-E Book"); //title
		
		Admin admin = new Admin(); // make a admin instance to access System level data
		
		
		setVisible(true); //make frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//make title label
		titleOnTop = new JLabel("Create Your Own Book!");
		titleOnTop.setFont(new Font("Baskerville Old Face", Font.BOLD, 17));
		titleOnTop.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titleOnTop, BorderLayout.NORTH);
		
		//panel for the textfields
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		//textfield that hold the input about the title of the new book
		titleTxt = new JTextField();
		titleTxt.setBounds(53, 31, 314, 29);
		panel.add(titleTxt);
		titleTxt.setColumns(10);
		
		//textfield that hold the input about the author of the new book
		authorTxt = new JTextField();
		authorTxt.setBounds(53, 83, 314, 29);
		panel.add(authorTxt);
		authorTxt.setColumns(10);
		
		//textfield that hold the input about the genre of the new book
		genreTxt = new JTextField();
		genreTxt.setBounds(53, 141, 314, 29);
		panel.add(genreTxt);
		genreTxt.setColumns(10);
		
		//textfield that hold the input about the content of the new book
		contentTxt = new JTextArea();
		contentTxt.setBounds(53, 192, 314, 84);
		contentTxt.setLineWrap(true);
		panel.add(contentTxt);
		
		//make the button
		createBtn = new JButton("Create");
		createBtn.setBounds(250, 297, 91, 23);
		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tempTitle = titleTxt.getText();
				tempAuthor = authorTxt.getText();
				tempGenre = genreTxt.getText();
				
				//to check if there's a input or not
				if(tempAuthor.length() == 0)
					JOptionPane.showMessageDialog(null, "You didn't enter the author's name!");  
				else if(tempTitle.length() == 0)
					JOptionPane.showMessageDialog(null, "You didn't enter the title!");
				else if(tempGenre.length() == 0)
					JOptionPane.showMessageDialog(null, "You didn't enter the genre of the book!");
				else
				{
					selectedItem = JOptionPane.showConfirmDialog(parentFrame,"Are You really want to create?","Creation",JOptionPane.YES_NO_OPTION);
					if(selectedItem == JOptionPane.YES_OPTION)
					{
						String FileName = tempTitle + ".txt";
						try {
							//write the contents at the contentTxt to new file
							BufferedWriter writer = new BufferedWriter(new FileWriter(FileName)); 
							writer.write(contentTxt.getText());
							User.addAuthorList(tempAuthor);
							User.addGenreList(tempGenre);
							writer.close();
					
							User.addBookList(tempTitle,tempAuthor, tempGenre); //add the information to corresponding lists
							
							if(fakeButton.getBackground() == Color.black) //send a signal to bookList page to refresh the table
								fakeButton.setBackground(Color.green);
							else
								fakeButton.setBackground(Color.black);
							
							parentFrame.setVisible(true); //call the Booklist page again
							dispose();
						} catch (IOException e1) {
							e1.printStackTrace();
						}//end of catch
					}//end of if(selectedItem == JOptionPane.YES_OPTION)
				}//end of else
			}//end of actionPerform
		});//end of actionListener
		panel.add(createBtn);
		
		//make cancel button
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setVisible(true); //recall bookList page
				dispose();
			}
		});
		cancelBtn.setBounds(78, 297, 91, 23);
		panel.add(cancelBtn);
		
		//make labels for title
		titleLBL = new JLabel("Title");
		titleLBL.setFont(new Font("Baskerville Old Face", Font.PLAIN, 14));
		titleLBL.setBounds(53, 15, 50, 15);
		panel.add(titleLBL);
		
		//make labels for author
		authorLBL = new JLabel("Author");
		authorLBL.setFont(new Font("Baskerville Old Face", Font.PLAIN, 14));
		authorLBL.setBounds(53, 70, 50, 15);
		panel.add(authorLBL);
		
		//make labels for genre
		genreLBL = new JLabel("Genre");
		genreLBL.setFont(new Font("Baskerville Old Face", Font.PLAIN, 14));
		genreLBL.setBounds(53, 125, 50, 15);
		panel.add(genreLBL);
		
		//make labels for content
		contentLBL = new JLabel("Contents");
		contentLBL.setFont(new Font("Baskerville Old Face", Font.PLAIN, 14));
		contentLBL.setBounds(53, 177, 50, 15);
		panel.add(contentLBL);
	}

}
