import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JTextArea;
import java.awt.Font;

public class StorePage extends JDialog {

	private JTextArea orgTextPane;
	private JPanel buttonPane;
	private JButton storeBtn ;
	private JButton backBtn;
	private JLabel titleLBL;
	private JScrollPane scrollPane;
	private JLabel explainLBL;
	
	/**
	 * Create the dialog.
	 */
	public StorePage(JFrame parentFrame,Book book, MemberInfo User) {
		
		super(parentFrame, false); //make parentFrame accessible while running on current dialog
		
		setTitle("Lee-E Book"); //title
		
		setVisible(true); //make frame visible
		
		//initial status
		setBounds(100, 100, 450, 300); 
		getContentPane().setLayout(null);
		buttonPane = new JPanel();
		buttonPane.setBounds(0, 234, 436, 29);
		getContentPane().add(buttonPane);
		
		//button for storing the input
		storeBtn = new JButton("Store");
		storeBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		storeBtn.setBounds(254, 0, 127, 22);
		storeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedItem = JOptionPane.showConfirmDialog(storeBtn, "Do you want to save this sentence?", "store", JOptionPane.YES_NO_OPTION);
				if(selectedItem == JOptionPane.YES_OPTION)
				{	
					MemberInfo polyAdmin = new Admin();
					polyAdmin.addQuoteList(User.getNickname(), book.getTitle(), book.getAuthor(), 
							orgTextPane.getText()); //add this quote into the user's quote list
					dispose();
				}//end of if
			}//end of actionPerformed
		});//end of actionListener
		
		
		//make the "back" button
		buttonPane.setLayout(null);
		backBtn = new JButton("Back");
		backBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); //recall the book list Page
			}
		});
		backBtn.setBounds(62, 0, 118, 22);
		buttonPane.add(backBtn);
		buttonPane.add(storeBtn);
		
	
		//label for the title
		titleLBL = new JLabel("Store Page");
		titleLBL.setFont(new Font("Baskerville Old Face", Font.BOLD, 17));
		titleLBL.setBounds(0, 0, 436, 47);
		titleLBL.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(titleLBL);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 414, 183);
		getContentPane().add(scrollPane);
		
		
		//label for the explanation
		explainLBL = new JLabel("copy the text and paste to the scroll pane! ");
		explainLBL.setHorizontalAlignment(SwingConstants.CENTER);
		explainLBL.setFont(new Font("Dialog", Font.ITALIC, 16));
		scrollPane.setColumnHeaderView(explainLBL);

		
		//make a textpane for the input
		orgTextPane = new JTextArea();
		orgTextPane.setFont(new Font("Dialog", Font.PLAIN, 15));
		scrollPane.setViewportView(orgTextPane);
		
	}
}
