import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Scrollbar;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

public class BookListPage extends JFrame{

	private JPanel contentPane;
	private JLabel logoLBL;
	private JMenu sortByMenu;
	private JMenu serviceMenu;
	private JMenuItem authorMenuItem;
	private JMenuItem genreMenuItem;
	private JMenuItem cUserMenuItem;
	private JMenuItem terminateMenuItem;
	private JMenuItem showAllMenuItem;
    private JMenuItem quoteMenuItem;
	private JList list;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel titleLBL;
	private JButton createBtn;
	private JButton removeBtn;
	private JButton addBtn;
	private JButton readBtn;
	private JScrollPane scrollPane_1;
    private JMenuBar menuBar;
	private int selectedItem;
	private int selectedCol;
	private int selectedRow;
	private String [] AuthorList = new String[300];
	private String [] GenreList = new String[300];
	private ArrayList<Book>bookList;
	private Book tempBook;
	private String[]header = {"Title", "Author", "Genre", "Recent Read Date"};
	private String[][]contents = new String[100][4]; //table to contain the book list
	private DefaultTableModel model;
	private int row = 0;
	private final JButton tempBtn = new JButton("New button"); //non-visual btn to sense the change made by Create JFrame
	
	//image for logo, and icon at the option dialog
    static ImageIcon image = new ImageIcon("logo.png");
    static Image img = image.getImage();
    static Image changedImg = img.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
    static ImageIcon newImage = new ImageIcon(changedImg); 

    
    
	/**
	 * to renew the table whenever there's a change in the book list
	 */
	public void makeTable(MemberInfo User)
	{
		contents = new String[100][4];
		bookList = User.getBookList();
		Iterator memIt = bookList.iterator();
		row = 0;
		
		while(memIt.hasNext()) 		//fill out from 0th row
		{   
			tempBook = (Book)memIt.next();
			contents[row][0] = (String)tempBook.getTitle();
			contents[row][1] = (String)tempBook.getAuthor();
			contents[row][2] = (String)tempBook.getGenre();
			contents[row++][3] =  (String)tempBook.getRecentReadDate();
		}
		model.setDataVector(contents, header); //make new model
	}
	
	
	/**
	 * Create the frame.
	 */
	public BookListPage(JFrame parentFrame, MemberInfo User) {
		
		setTitle("Lee-E Book"); //title
		
		//sense there's a creation from CREATE page
		tempBtn.addPropertyChangeListener(new PropertyChangeListener() {  
			public void propertyChange(PropertyChangeEvent evt) {
				makeTable(User);
			}
		});
		
		Admin admin = new Admin();  // make a admin instance to access System level data
		
		setVisible(true); //make the frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 328);
		
		//make first table
		String []header = {"Title", "Author", "Genre", "Recent Read Date"};
		String [][]contents = new String[100][4];
		bookList = User.getBookList();
		Iterator memIt = bookList.iterator();
		int row = 0;
		while(memIt.hasNext())
		{
			tempBook = (Book)memIt.next();
			contents[row][0] = tempBook.getTitle(); //store the input from all the text fields
			contents[row][1] = tempBook.getAuthor();
			contents[row][2] = tempBook.getGenre();
			contents[row++][3] = tempBook.getRecentReadDate();		
		}
		model = new DefaultTableModel(contents,header);  //set the contents array as the model of the table
		
		//set the visual attribute of the table
		JTable table = new JTable(model);
		table.setGridColor(SystemColor.controlHighlight);
		table.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setRowHeight(30);
		table.getTableHeader().setBackground(UIManager.getColor("ScrollBar.foreground"));
		table.addMouseListener(new MouseAdapter() {   //if the member of the table is clicked, the position of the member is stored
			public void mousePressed(MouseEvent e) {
				selectedRow = table.getSelectedRow();
				selectedCol = table.getSelectedColumn();
			}
		});
		table.setSurrendersFocusOnKeystroke(false);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		

		//menubar
		menuBar = new JMenuBar();
		menuBar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setJMenuBar(menuBar);
		
		
		//"sort by"
		sortByMenu = new JMenu("SORT BY"); 
		sortByMenu.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		sortByMenu.setMargin(new Insets(2, 23, 2, 2));
		menuBar.add(sortByMenu);
		
		
		//"author" in the "sort by" menu
		authorMenuItem = new JMenuItem("Author"); 
		authorMenuItem.setFont(new Font("Dialog", Font.PLAIN, 13));
		authorMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashSet iter = User.getAuthorSet();
				Iterator it = iter.iterator();
				int i = 0;
				AuthorList = new String[300];
				while(it.hasNext())  //making authorList
				{
					AuthorList[i++] = (String)it.next();
				}
				String SelectedAuthor = (String)JOptionPane.showInputDialog(parentFrame, "Please choose a Author", "Sort By Author",    
				        JOptionPane.QUESTION_MESSAGE, newImage, AuthorList, AuthorList[0]);    //to make a dialog to make user select author
				if(SelectedAuthor != null)
				{
					makeTable(User); //refresh the table
					int idx = 0;
					try {
						while(table.getValueAt(idx, 1) != " ")
						{
							if(model.getValueAt(idx, 1).equals(SelectedAuthor))
								++idx;
							else
								model.removeRow(idx);
						} // end of while
					} // end of try
					catch(NullPointerException ex)
					{
						if(idx <= 1)
							JOptionPane.showMessageDialog(parentFrame, idx + " book is found!", "found", JOptionPane.INFORMATION_MESSAGE, newImage);
						else
							JOptionPane.showMessageDialog(parentFrame, idx + " books are found!","found", JOptionPane.INFORMATION_MESSAGE, newImage);	
					}// end of catch
				} // end of if
			 }// end of actionPerformed
		});//end of actionListener
		sortByMenu.add(authorMenuItem);
		
	
		//"Genre" in the "sort by" menu
		genreMenuItem = new JMenuItem("Genre");
		genreMenuItem.setFont(new Font("Dialog", Font.PLAIN, 13));
		genreMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashSet iter = User.getGenreSet();
				Iterator it = iter.iterator();
				int i = 0;
				GenreList = new String[300]; //making GenreList
				while(it.hasNext())
				{
					GenreList[i++] = (String)it.next();
				}
				String SelectedGenre = (String)JOptionPane.showInputDialog(parentFrame, "Please choose a Genre", "Sort By Genre",
				        JOptionPane.QUESTION_MESSAGE, newImage, GenreList, GenreList[0]); //to make a dialog to make user select author
				if(SelectedGenre != null)
				{
					makeTable(User);  //refresh the table
					int idx = 0;
					try {
						while(model.getValueAt(idx, 2) != " ")
						{
							if(model.getValueAt(idx, 2).equals(SelectedGenre))
								++idx;
							else
								model.removeRow(idx);
						}//end of while
					}//end of try
					catch(NullPointerException ex)
					{
						if(idx <= 1)
							JOptionPane.showMessageDialog(parentFrame, idx + " book is found!", "found", JOptionPane.INFORMATION_MESSAGE, newImage);
						else
							JOptionPane.showMessageDialog(parentFrame, idx + " books are found!", "fount", JOptionPane.INFORMATION_MESSAGE, newImage);	
					}//end of catch
				}//end of if
			}//end of actionPerformed
		}); //end of actionListener
		sortByMenu.add(genreMenuItem);
		
		
		//show All menu of in the "sort by" menu
		showAllMenuItem = new JMenuItem("Show All");
		showAllMenuItem.setFont(new Font("Dialog", Font.PLAIN, 13));
		showAllMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(parentFrame, "All resources are recovered!", "recovered", JOptionPane.INFORMATION_MESSAGE, newImage);
				makeTable(User);   //refresh the table
			}
		});
		sortByMenu.add(showAllMenuItem);
		
		
		//My service menu
		serviceMenu = new JMenu("MY SERVICE");
		serviceMenu.setDelay(100);
		serviceMenu.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		serviceMenu.setMargin(new Insets(2, 45, 2, 2));
		menuBar.add(serviceMenu);
		
		//Change User Menu in "My service"
		cUserMenuItem = new JMenuItem("Change User");
		cUserMenuItem.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		cUserMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setVisible(true);
				dispose();  //dispose current page, and recall the login page
			}
		});
		
		//Stored Quotes menu in "My service"
		quoteMenuItem = new JMenuItem("Stored Quotes");
		quoteMenuItem.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		quoteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuotePage quotePage = new QuotePage(BookListPage.this, User);  //call the Quote Page
				setVisible(false);
			}
		});
		serviceMenu.add(quoteMenuItem);
		serviceMenu.add(cUserMenuItem);
		
		//terminate Menu in "My service"
		terminateMenuItem = new JMenuItem("Terminate");
		terminateMenuItem.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
		terminateMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedItem = JOptionPane.showConfirmDialog(parentFrame, "You really want to terminate whole Application?", "Terminate",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(selectedItem == JOptionPane.YES_OPTION)
				{	JOptionPane.showMessageDialog(parentFrame, "Thank You For Using Our Application", "System Termination", JOptionPane.INFORMATION_MESSAGE, newImage);
					dispose(); //terminate the whole program
				}
			}
		});
		serviceMenu.add(terminateMenuItem);
		
		//panel for the buttons
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(78, 10));
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(4, 0, 0, 0));
		
		//"add" button
		addBtn = new JButton("Add");
		addBtn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		addBtn.setBackground(SystemColor.inactiveCaption);
		addBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JTextField field1 = new JTextField(); //textFields that will contain the input that corresponds to each information
				JTextField field2 = new JTextField();
				JTextField field3 = new JTextField();
				JTextField field4 = new JTextField();
				
				String tempTitle;   //temp variables that contains each information
				String tempAuthor;
				String tempGenre;
				String tempFile;
				while(true)
				{
					Object[] fields = {   //list for entering 4 information
							"Title", field1,
							"Author", field2,
							"Genre", field3,
							"Txt File Name", field4
					}; 
					int Selected = JOptionPane.showConfirmDialog(parentFrame,fields, "Add", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, newImage);
					if(Selected == JOptionPane.YES_OPTION)
					{
						File file = new File(field4.getText() + ".txt");  //make file name by adding tempFile + ".txt"
						try {
							FileReader fileReader = new FileReader(file);    //find a file in the directory
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(parentFrame, "No such file in directory!","No file exist", JOptionPane.ERROR_MESSAGE);
							continue;
						}
						tempTitle = field1.getText(); 
						tempAuthor = field2.getText();
						tempGenre = field3.getText();
						tempFile = field4.getText();
						User.addAuthorList(tempAuthor); //add author, and genre to the AuthorList, and GenreList 
						User.addGenreList(tempGenre);
						User.addBookListwithFile(tempTitle, tempAuthor, tempGenre, tempFile); //make a new member at the user's book list
						makeTable(User); //refresh the table
					}//end of if
					break;
				}//end of while
			}//end of actionPerformed
		});//end of actionListener
		panel.add(addBtn);
		
		//"Remove" button
		removeBtn = new JButton("Remove");
		removeBtn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		removeBtn.setBackground(SystemColor.inactiveCaption);
		removeBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedItem = JOptionPane.showConfirmDialog(parentFrame,"Are You Really want to delete this book?","Remove",JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if(selectedItem == JOptionPane.YES_OPTION)
				{
					try 
					{
						admin.removeAuthorList((String)table.getValueAt(selectedRow, 1));   //to remove the author, and genre from the AuthorList, and GenreList
						admin.removeGenreList((String)table.getValueAt(selectedRow, 2));
						User.deleteList(selectedRow);
						model.removeRow(selectedRow);
					}
					catch(NullPointerException | IndexOutOfBoundsException ex)  //exception when there's no file or no focus.
					{
						JOptionPane.showMessageDialog(parentFrame, "No file!", "No file exist", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panel.add(removeBtn);
		
		
		//"Create" button
		createBtn = new JButton("Create");
		createBtn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		createBtn.setBackground(SystemColor.inactiveCaption);
		createBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatePage create= new CreatePage(BookListPage.this, User, tempBtn); //move to the Create Page
				setVisible(false);
			}
		});
		panel.add(createBtn);
		
		
		//"Read" Button
		readBtn = new JButton("Read");
		readBtn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		readBtn.setBackground(SystemColor.inactiveCaption);
		readBtn.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		readBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedItem = JOptionPane.showConfirmDialog(parentFrame, "Are you Going to Read this Book?", "Book Selection",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(selectedItem == JOptionPane.YES_OPTION)
				{	
					try 
					{
						SimpleDateFormat format1 = new SimpleDateFormat ( "MM-dd HH:mm:ss");   //calculate the current date
						Date time = new Date();
						String formattedDate = format1.format(time);
						
						String title = (String)table.getValueAt(selectedRow, 0); //extract the information from the talbe
						String author = (String)table.getValueAt(selectedRow, 1);
						String genre = (String)table.getValueAt(selectedRow, 2);
						Book selectedBook = User.findFileName(title);
						String fileName = selectedBook.getFileName();
						
						selectedBook.setRecentReadDate(formattedDate);  //to fill out the Recent read date		
						model.setValueAt(formattedDate,selectedRow, 3);
						
						ReadPage frame = new ReadPage(BookListPage.this, new Book(title, author, genre, fileName), User); //move to the ReadPage
						setVisible(false);
					}
					catch(NullPointerException ex)
					{
						JOptionPane.showMessageDialog(parentFrame, "No file!", "No file exist", JOptionPane.ERROR_MESSAGE); //exception handling when there's no file or no focus
					}
				}//end of if
			}//end of actionPerformed
		});//end of actionListener
		panel.add(readBtn);
		
		
		//making a panel
		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setFont(new Font("Baskerville Old Face", Font.PLAIN, 16));
		panel_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		//making a title label
		titleLBL = new JLabel(User.getNickname() + "'s BOOK LIST");
		titleLBL.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		panel_1.add(titleLBL);
		getContentPane().add(table, BorderLayout.CENTER);
		
		//making a scrollpane
		scrollPane_1 = new JScrollPane(table);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBorder(null);
		getContentPane().add(scrollPane_1, BorderLayout.CENTER);
		
	}
}
