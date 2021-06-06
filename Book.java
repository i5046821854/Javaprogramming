import java.util.Iterator;

public class Book {
	
	private String author;
	private String title;
	private String genre;
	private String fileName;
	private String recentReadDate = "Not read Yet";
	
	/**
	 * Constructor without any parameter
	 */
	public Book() {
	}
	
	/**
	 * Constructor without fileName
	 */
	public Book(String title, String author, String genre) {
		this.author = author;
		this.title = title;
		this.genre = genre;
		this.fileName = title;
	}
	
	/**
	 * Constructor with fileName
	 */
	public Book(String title, String author, String genre, String fileName) {
		this.author = author;
		this.title = title;
		this.genre = genre;
		this.fileName = fileName;
	}
	
	
	/**
	 * getter of the fileName
	 **/
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * setter of the fileName
	 **/
	public void setfileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * setter of RecentReadDate
	 */
	public void setRecentReadDate(String recentReadDate) {
		this.recentReadDate = recentReadDate;
	}
	
	/**
	 * getter of RecentReadDate
	 */
	public String getRecentReadDate() {
		return recentReadDate;
	}
	
	/**
	 * getter of Genre
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	 * setter of Genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * getter of Author
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * setter of Author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * getter of title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * setter of title
	 */
	public void settitle(String title) {
		this.title = title;
	}
	
}
