import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class SeeQuoteDetail extends JDialog {

	private boolean remove;
	private final JPanel contentPanel = new JPanel();
	private JLabel authorDLbl;
	private JLabel authorSLbl;
	private JLabel titleSLbl;
	private JLabel titleDLbl;
	private JLabel dateSLbl;
	private JLabel dateDLbl;
	private JLabel orgTextLbl;
	private JScrollPane scrollPane;
	private JTextPane orgTextArea;
	private JLabel userSLBL;
	private JLabel userDLBL;
	
	
	/**
	 * Create a frame
	 */
	public SeeQuoteDetail(JFrame parentFrame, Quote quote) {
		setTitle("Lee-E Book"); //title
		
		setVisible(true); //make frame visible
		
		setBounds(100, 100, 450, 300); //initial status
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//lable for the user (static)
		userSLBL = new JLabel("User");
		userSLBL.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		userSLBL.setBounds(64, 10, 121, 15);
		contentPanel.add(userSLBL);
			
		//label for the user (dynamic)
		userDLBL = new JLabel(quote.getUser());
		userDLBL.setHorizontalAlignment(SwingConstants.LEFT);
		userDLBL.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		userDLBL.setBounds(256, 10, 201, 15);
		contentPanel.add(userDLBL);
		
		//label for author (dynamic)
		authorDLbl = new JLabel(quote.getTitle());
		authorDLbl.setHorizontalAlignment(SwingConstants.LEFT);
		authorDLbl.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		authorDLbl.setBounds(256, 30, 181, 15);
		contentPanel.add(authorDLbl);
		
		//label for author (static)
		authorSLbl = new JLabel("Author");
		authorSLbl.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		authorSLbl.setBounds(64, 30, 121, 15);
		contentPanel.add(authorSLbl);

		//label for title (static)
		titleSLbl = new JLabel("Title");
		titleSLbl.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		titleSLbl.setBounds(64, 50, 121, 15);
		contentPanel.add(titleSLbl);

		//label for title (dynamic)
		titleDLbl = new JLabel(quote.getAuthor());
		titleDLbl.setHorizontalAlignment(SwingConstants.LEFT);
		titleDLbl.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		titleDLbl.setBounds(256, 50, 181, 15);
		contentPanel.add(titleDLbl);
		
		//label for added date (static)
		dateSLbl = new JLabel("Added Date");
		dateSLbl.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		dateSLbl.setBounds(64, 70, 121, 15);
		contentPanel.add(dateSLbl);

		//label for added date (dynamic)
		dateDLbl = new JLabel(quote.getDate());
		dateDLbl.setHorizontalAlignment(SwingConstants.LEFT);
		dateDLbl.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		dateDLbl.setBounds(256, 70, 181, 15);
		contentPanel.add(dateDLbl);

		//label for original text (static)
		orgTextLbl = new JLabel("Original Text");
		orgTextLbl.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		orgTextLbl.setBounds(172, 102, 106, 15);
		contentPanel.add(orgTextLbl);
		
		//make scroll pane for the original text
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 120, 425, 100);
		contentPanel.add(scrollPane);
		
		//textpane for the orginal text
		orgTextArea = new JTextPane();
		orgTextArea.setFont(new Font("Dialog", Font.PLAIN, 17));
		orgTextArea.setEditable(false); //cannot edit the textArea
		orgTextArea.setText(quote.getContent());
		scrollPane.setViewportView(orgTextArea);


	
	}
}
