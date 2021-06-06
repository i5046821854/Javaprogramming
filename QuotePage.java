import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.Font;

public class QuotePage extends JFrame {

	private JPanel contentPane;
	private JLabel titleLBL;
	private JScrollPane scrollPane;
	private JList list; 
    private JButton backBtn;
    private JLabel explainLBL;
    private String dataForList;
    
	List<String> listData = new ArrayList<String>(); //Arraylist that contains the all sequence of the quotes
    DefaultListModel<String> listModel = new DefaultListModel<>();  //list model for containing the ArrayList



	/**
	 * Create the frame.
	 */
	public QuotePage(JFrame parentFrame, MemberInfo User) {
		setTitle("Lee-E Book"); //title
		
		setVisible(true); //make frame visible
		
		//using polymorphism to collect the QuoteList
		MemberInfo polyAdmin = new Admin();	
		ArrayList iter = polyAdmin.getQuoteList();
		Iterator it = iter.iterator();
		Quote tempQuote;
		while(it.hasNext())
		{
			//find every quote and make them as string formattted as below dataForLIst
			tempQuote = (Quote)it.next();   
			dataForList = tempQuote.getUser() + " /" + tempQuote.getAuthor() +  " / " + tempQuote.getTitle() +  " / " + tempQuote.getDate() + " / " + tempQuote.getContent();
			listData.add(dataForList); //add the string into ArrayList
		}
		listModel.addAll(listData); //add whole ArrayLIst into the list model
		
		//make the contentPane
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//make scrollPane
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 534, 249);
		contentPane.add(scrollPane);
		
		//make the list
		list = new JList(listModel);
		list.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
		scrollPane.setViewportView(list);
		
		//make label for explanation
		explainLBL = new JLabel("Double Click if You want to see the details of the Quote");
		explainLBL.setFont(new Font("Dialog", Font.ITALIC, 13));
		explainLBL.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(explainLBL);
		
		//when user click the memeber of the list, event hanlder is activated
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try
				{
					int index = list.getSelectedIndex(); 
					Quote quote = (Quote)polyAdmin.getQuoteList().get(index);
					SeeQuoteDetail QuoteDetail = new SeeQuoteDetail(QuotePage.this, quote);
				}
				catch (IndexOutOfBoundsException ex)
				{
					JOptionPane.showMessageDialog(parentFrame, "Invalid Index", "Invalid Index", JOptionPane.ERROR_MESSAGE);
				}//end of  catch
			}//end of action handler
		});//end of action listener
		
		//make back button
		backBtn = new JButton("Back");
		backBtn.setBounds(12, 16, 75, 23);
		contentPane.add(backBtn);
		
		//make label for the title of the page
		titleLBL = new JLabel("Quote Page");
		titleLBL.setFont(new Font("Baskerville Old Face", Font.BOLD, 17));
		titleLBL.setBounds(10, 8, 554, 38);
		contentPane.add(titleLBL);
		titleLBL.setHorizontalAlignment(SwingConstants.CENTER);
		
		//return to the book list page
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setVisible(true); 
				dispose();
			}
		}); //end of actionListenr
	}//end of quotePage

}
