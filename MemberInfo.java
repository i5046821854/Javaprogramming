import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class MemberInfo extends Book{

	private String ID;
	private String Password;
	private String nickname;
	private ArrayList<Book>bookList = new ArrayList(); //list of books that a certain user holds
	private static ArrayList<Quote>quoteList = new ArrayList(); //list of quotes that a certain user has stored
	private HashSet<String>AuthorList = new HashSet(); //arraylist that contains every author from all users
	private HashSet<String>GenreList = new HashSet(); //arraylist that contains every genre from all users
	
	
	/**
	 * constructor except for bookList, quoteList
	 */
	public MemberInfo(String iD, String password, String nickname) {
		super();
		ID = iD;
		Password = password;
		this.nickname = nickname;
	}
	

	/**
	 * getter of ID
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * setter of ID
	 */
	public void setID(String iD) {
		ID = iD;
	}
	
	/**
	 * getter of Nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * setter of Nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/**
	 * getter of Password
	 */
	public String getPassword() {
		return Password;
	}
	
	/**
	 * setter of Password
	 */
	public void setPassword(String password) {
		Password = password;
	}
	
	/**
	 * getter of BookList
	 */
	public ArrayList getBookList() {
		return bookList;
	}
	
	/**
	 * getter of quoteList
	 */
	public <Quote>ArrayList getQuoteList()
	{
		return quoteList;
	}
	
	/**
	 * add a new book to Book List
	 */
	public void addBookList(String name, String author, String genre) {
		bookList.add(new Book(name, author, genre));
	}
	
	/**
	 * add a new book to Book List (with file name)
	 */
	public void addBookListwithFile(String name, String author, String genre, String fileName) {
		bookList.add(new Book(name, author, genre, fileName));
	}
	
	/**
	 * delete a book from BookList
	 */
	public void deleteList(int idx)
	{
		bookList.remove(idx);
	}
	
	/**
	 * add a new Quote to QuoteList
	 */
	public void addQuoteList(String user, String title, String author, String content) {
		SimpleDateFormat format1 = new SimpleDateFormat ( "MM-dd");
		Date date = new Date();
		String time1 = format1.format(date);
		quoteList.add(new Quote(user, title, author, time1, content));
	}
	
	/**
	 * delete a quote from QuoteList
	 */
	public void deleteQuoteList(int idx)
	{
		quoteList.remove(idx);
	}
	
	/**
	 * to file name of a book
	 */
	public Book findFileName(String title)
	{
		Iterator<Book> iter = bookList.iterator();
		int i = 0;
		Book tempBook;
		while(iter.hasNext())
		{
			tempBook = iter.next();
			if(tempBook.getTitle().equals(title))
				return tempBook;
		}
		return null;
	}
	
	/**
	 * add new author to AuthorList
	 */
	public void addAuthorList(String Author)
	{
		AuthorList.add(Author);
	}
	
	/**
	 * remove a author from author list
	 */
	public void removeAuthorList(String Author)
	{
		AuthorList.remove(Author);
	}
	
	/**
	 * add new genre to GenreList
	 */
	public void addGenreList(String Genre)
	{
		GenreList.add(Genre);
	}
	
	/**
	 * remove a genre from Genre list
	 */
	public void removeGenreList(String Genre)
	{
		GenreList.remove(Genre);
	}
	
	/**
	 * return a authorlist to caller
	 **/
	public HashSet getAuthorSet() {
		return AuthorList;
	}
	
	/**
	 * return a genrelist to caller
	 **/
	public HashSet getGenreSet() {
		return GenreList;
	}
	
	
	
}
