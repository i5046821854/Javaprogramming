import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class ReadPage extends JFrame {

	private JPanel contentPane;
	private JLabel titleLBL;
	private JPanel panel;
	private JButton showAllBtn;
	private JButton autoReadBtn;
	private JButton readByLineBtn;
	private JPanel panel_1;
	private JProgressBar progressBar;
	private JLabel calulateTimeLBL;
	private JPanel panel_2;
	private double readInterval = 0;
	private JButton nextLineBtn;
	private JButton prevLineBtn;
	private JButton pauseBtn;
	private String FileName;
	private String wholeText = "";
	private String printText = "";
	private String tempText = "";
	private int printLine = 0;
	private int totalLine = 0;
	private int currentLine = 0;
	private ArrayList<String>lineArray = new ArrayList<String>();  //arrayList for containing the every line of the book
	private SwingWorker readThread; //for making a new thread
	private int pressedCnt = 0;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JButton backBtn;
	private JButton timerBtn;
	private int readTIme;
	private Timer timer = new Timer(); //for making a timer
	private JButton storeBtn;
	
	/**
	 * to perform a timer
	 **/
	public void startTimer()
	{
		TimerTask task = new TimerTask() {

			/**
			 * calculate and write the reading time
			 **/
			@Override
			public void run() {
				readTIme++; 
				String time = String.format("%02d:%02d",readTIme/60, readTIme % 60);
				calulateTimeLBL.setText("Reading Time: " + time );
			}
		};
		timer = new Timer();
		timer.schedule(task, 1000,1000);
	}
	
	/**
	 * make repeated task as a method
	 **/
	public void defaultBtn()
	{
		nextLineBtn.setVisible(false);
		prevLineBtn.setVisible(false);
		pauseBtn.setVisible(false);
		autoReadBtn.setText("Auto Read");
	}
	
	/**
	 * Create the frame.
	 */
	public ReadPage(JFrame ParentFrame, Book book, MemberInfo User) {
		setTitle("Lee-E Book"); //title
		
		FileName = book.getFileName() + ".txt"; //file to read
		File file = new File(FileName); //read file line by line
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(reader);
			String line = null;
			while((line = bufReader.readLine()) != null)
			{
				wholeText += line + "\n";
				lineArray.add(line); //add each line to the arrayList
				totalLine++; //the number of total line of the book
			}
		} catch (FileNotFoundException e) {      //file not found
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//initial status of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 300);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//title label
		titleLBL = new JLabel("Reading Session");
		titleLBL.setBounds(5, 5, 515, 18);
		titleLBL.setFont(new Font("Baskerville Old Face", Font.BOLD, 17));
		titleLBL.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titleLBL);
		
		//make panel
		panel = new JPanel();
		panel.setBounds(415, 23, 105, 212);
		contentPane.add(panel);
		panel.setLayout(null);
		
		//nextLine Button
		nextLineBtn = new JButton("Next Line");
		nextLineBtn.setBackground(SystemColor.info);
		nextLineBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
		nextLineBtn.setBounds(0, 146, 105, 23);
		panel.add(nextLineBtn);
		
		//prevLIne Button
		prevLineBtn = new JButton("Prev Line");
		prevLineBtn.setBackground(SystemColor.info);
		prevLineBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
		prevLineBtn.setBounds(0, 123, 105, 23);
		panel.add(prevLineBtn);
		nextLineBtn.setVisible(false);
		prevLineBtn.setVisible(false);
		
		//showAll Btn
		showAllBtn = new JButton("Show All Text");
		showAllBtn.setBackground(SystemColor.info);
		showAllBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
		showAllBtn.setBounds(0, 179, 105, 33);
		showAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultBtn();
				textPane.setText("");
				textPane.setText(wholeText); //print out whole text to the txtPane
				printLine = totalLine;  //make progression bar full
				progressBar.setValue(100);
				progressBar.setString(printLine + " / " + totalLine + " (line)");
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					   public void run() { 
					       scrollPane.getVerticalScrollBar().setValue(0); //locate scrollBar at the top
					   }
					});
			}//end of actionPerformed
		});//end of actionListener
		panel.add(showAllBtn);
		
		//auto Read Button
		autoReadBtn = new JButton("Auto Read");
		autoReadBtn.setBackground(SystemColor.info);
		autoReadBtn.setBounds(0, 0, 105, 33);
		autoReadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultBtn();
				String stringInput = "";
				try 
				{
					stringInput = JOptionPane.showInputDialog(null, "Select the interval between pages (lines)", "Auto Read",
					        JOptionPane.QUESTION_MESSAGE);	//user select the speed of the auto read (interval)
					if(stringInput != null)
					{
						//pare the string into double and begin the thread for auto read
						readInterval = Double.parseDouble(stringInput);  
						pauseBtn.setVisible(true);
						autoReadBtn.setText("Auto Read");
						pauseBtn.setText("PAUSE");
						start();
					}
				}
				catch(NumberFormatException ex) //error handling (when input is not valid)
				{
					JOptionPane.showMessageDialog(null, "Invalid Input!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		autoReadBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel.add(autoReadBtn);
		
		
		//read by line button
		readByLineBtn = new JButton("Read by Line");
		readByLineBtn.setBackground(SystemColor.info);
		readByLineBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
		readByLineBtn.setBounds(0, 86, 105, 33);	
		readByLineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				pressedCnt++;
				nextLineBtn.setVisible(true);  //make the two hidden button visible
				prevLineBtn.setVisible(true);
				autoReadBtn.setText("Auto Read");
				pauseBtn.setVisible(false);
				if(pressedCnt == 1)
				{
					//next line button
					nextLineBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(printLine == totalLine) //to alert there's no more next page
								JOptionPane.showMessageDialog(null, "Last Page!", "Last Page", JOptionPane.INFORMATION_MESSAGE, BookListPage.newImage);
							else
							{
								printText += lineArray.get(printLine++) + "\n";
								textPane.setText(printText);
								progressBar.setValue(printLine * 100 / totalLine); //make progressbar depending on current page
								progressBar.setString(printLine + " / " + totalLine + " (line)");
							}//end of if(printline == totalLine)	
						}//end of actionPerformed
					}); //end of Action Listener
					
					//prev line button
					prevLineBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(printLine == 0) //to alert there's no more prev page
								JOptionPane.showMessageDialog(null, "First Page!", "First Page", JOptionPane.INFORMATION_MESSAGE, BookListPage.newImage);
							else
							{
								printText = "";
								for(int i = 0 ; i< printLine-1 ; i++)   //to get prev text
								{
									printText += lineArray.get(i) + "\n";
								}
								textPane.setText(" ");
								textPane.setText(printText);
								printLine--;
								progressBar.setValue(printLine * 100 / totalLine); //make progressbar depending on current page
								progressBar.setString(printLine + " / " + totalLine + " (line)");
							}
						}//end of actionPerformed
					}); //end of actionListenr (prev button)
				}//end of if(pressedcnt ==1)
			}//end of actionPerformed (Read by Line button)
		});//end of actionListener
		panel.add(readByLineBtn);
		
		//Pause button
		pauseBtn = new JButton("PAUSE");
		pauseBtn.setBackground(SystemColor.info);
		pauseBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
		pauseBtn.setBounds(0, 35, 105, 23);
		pauseBtn.setVisible(false);
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pauseBtn.getText().equals("PAUSE"))  //change the button text
				{
					autoReadBtn.setText("<html>Change <br> Interval </html>");
					stop();  //make thread stop
				}
				else if(pauseBtn.getText().equals("RESUME"))
				{	
					start(); //make thread restart
					pauseBtn.setText("PAUSE");
				}
				else //when all text has been read, restart the reading from the beginning
				{
					printLine = 0;
					printText = "";
					textPane.setText("");
					start(); 
					pauseBtn.setText("PAUSE");
				}//end of else
			}//end if actionPerformed
		});//end of action Listener
		panel.add(pauseBtn);
		
		
		//making panel
		panel_1 = new JPanel();
		panel_1.setBounds(5, 235, 515, 23);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		//make of progressBar
		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Dialog", Font.PLAIN, 12));
		progressBar.setBounds(0, 3, 121, 17);
		progressBar.setStringPainted(true);
		panel_1.add(progressBar);
		
		//making timer
		timerBtn = new JButton("Start Timer");
		timerBtn.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		timerBtn.setBounds(252, -3, 103, 23);
		timerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(timerBtn.getText().equals("Start") || timerBtn.getText().equals("Start Timer"))
				{
					startTimer();
					timerBtn.setText("Stop");
				}
				else
				{
					timerBtn.setText("Start");
					timer.cancel();
				}	
			}
		});
		panel_1.add(timerBtn);
		
		
		//label for calculating the reading time
		calulateTimeLBL = new JLabel("Reading Time: 00:00");
		calulateTimeLBL.setFont(new Font("Bookman Old Style", Font.BOLD, 12));
		calulateTimeLBL.setBounds(367, 2, 136, 15);
		calulateTimeLBL.setBackground(Color.WHITE);
		panel_1.add(calulateTimeLBL);
		
		//label for storing the quote
		storeBtn = new JButton("Store");
		storeBtn.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		storeBtn.setBounds(150, -3, 70, 23);
		storeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StorePage store = new StorePage(ReadPage.this, book, User);
			}
		});
		panel_1.add(storeBtn);

		
		//make scrollpane
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 23, 410, 212);
		textPane = new JTextPane();
		textPane.setFont(new Font("Dialog", Font.BOLD, 15));
		scrollPane.setViewportView(textPane);
		contentPane.add(scrollPane);
		
		//make back button
		backBtn = new JButton("Back");
		backBtn.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		backBtn.setBounds(5, 3, 73, 18);
		contentPane.add(backBtn);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop(); //if thread is running, stop the thread and back to book list page
				ParentFrame.setVisible(true);
				dispose();
			}
		});
		setVisible(true);
	}
	
	
	/**
	 * swingWorker Thread
	 */
	private void start() {
			//initialize the thread
			readThread = new SwingWorker<Integer, String>() {
			
			protected Integer doInBackground() throws Exception { //not change GUI
				autoReadBtn.setEnabled(false); //disenable the auto read button
				File file = new File(FileName);
				try {
					while(printLine != totalLine)
					{
						if(isCancelled())  //when user pressed stop or back button return 4
							return 4;
						tempText = lineArray.get(printLine++); //send each line to the process methods
						publish(tempText);
						Thread.sleep((long) readInterval); //interval between each publish
					}
					return 1;
				} 
				catch (InterruptedException  | CancellationException e) { //error handling for cancellation and interrupt
					return 4;
				}
			}//end of doInBackGround
			
			protected void process(List<String> chunks) { //can Change GUI
				String line = chunks.get(chunks.size() -1);
				printText += line + "\n";
				textPane.setText(printText);   //prints out the next line on the textPane
				progressBar.setValue((printLine*100 / totalLine)); //update progressBar
				progressBar.setString(printLine + " / " + totalLine + " (line)");
			}
			
			protected void done()
			{
				try {
					switch(get())
					{
					case 1: //if all of the content in the book has been read
						pauseBtn.setText("Read Again");
						autoReadBtn.setEnabled(true);
						break;
					case 2: //error handling (file not Found)
						JOptionPane.showMessageDialog(null, "File is not found!", "File not found", JOptionPane.ERROR_MESSAGE);
						break;
					case 3: //error handling (Invalid Input)
						JOptionPane.showMessageDialog(null, "Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
						break;
					case 4: //error handling (file not Found)
						JOptionPane.showMessageDialog(null, "File is not found!", "File not found", JOptionPane.ERROR_MESSAGE);
						break;
					}
				} catch (InterruptedException | ExecutionException | CancellationException e ) { //if a thread is stopped
					autoReadBtn.setEnabled(true); 
					pauseBtn.setText("RESUME");
				}			
			}
		};
		readThread.execute();  //execute thread
	}
	
	/**
	 * if a thread is running, stop the thread
	 */
	private void stop()	
	{
		if(this.readThread != null) 
			readThread.cancel(true);
	}

}
