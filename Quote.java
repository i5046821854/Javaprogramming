
public class Quote extends Book{
	private String user;
	private String author;
	private String title;
	private String date;
	private String content;
	
	/**
	 * constructor
	 **/
	public Quote(String user, String author, String title, String date, String content) {
		this.user = user;
		this.author = author;
		this.title = title;
		this.date = date;
		this.content = content;
	}

	
	/**
	 * getter of the Author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * setter of the Author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * getter of the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * setter of the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * getter of the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * setter of the date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * getter of the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * setter of the content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * getter of the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * setter of the user
	 */
	public void setUser(String user) {
		this.user = user;
	}	
	
	
	
}
