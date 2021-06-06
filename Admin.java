import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Admin extends MemberInfo {
	
	static ArrayList<MemberInfo>memList = new ArrayList(); //arraylist that contains memberinfo of all users
	
	/**
	 * constructor with three parameters
	 */
	public Admin(String iD, String password, String nickname) {
		super(iD, password, nickname);
	}
	
	/**
	 * constructor without any parameter
	 */
	public Admin() {   //make admin account as a super user
		super("admin", "admin", "admin");
	}


	/**
	 * return a memlist to caller
	 **/
	public ArrayList getMemLIst()
	{
		return memList;
	}
	

	/**
	 * add new user to userList
	 */
	public void addUser(String ID, String password, String nickname)   //add new user to memlist
	{
		memList.add(new MemberInfo(ID,password,nickname));
	}
	
	/**
	 * find a user in the memlist and return the index of the user
	 */
	public MemberInfo getUser(int index)
	{
		return memList.get(index);
	}
	

	/**
	 * check ID duplication
	 */
	public int findIDduplicate(String name)
	{
		for (int i = 0 ; i< memList.size(); i++)
		{
			if(name.equals(memList.get(i).getID()))
			{
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * check if the inputs for ID/PW is correct
	 **/
	public int validity(String inputID, String inputPWD)
	{
		for(int i= 0 ; i < memList.size() ; i++)
		{
			if(inputID.equals(memList.get(i).getID()))
			{	
				if(inputPWD.equals(memList.get(i).getPassword()))
				{   
					return i; //correct input
				}
				else 
					return -2; //wrong input
			}
		}
		return -1; //there's no input at ID textArea
	}

}
