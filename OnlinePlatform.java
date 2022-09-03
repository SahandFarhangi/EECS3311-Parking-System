import java.util.*;

public class OnlinePlatform 
{
	private ArrayList<Customer> customers;
	private ArrayList<Officer> officers;
	private ArrayList<ParkingSpace> spaces;
	
	private Admin admin;
	
	private static OnlinePlatform platform = null;
	
	public static OnlinePlatform getInstance()
	{
		if (platform == null) platform = new OnlinePlatform();
		
		return platform;
	}
	
	private OnlinePlatform()
	{
		this.customers = new ArrayList<Customer>();
		this.officers = new ArrayList<Officer>();
		this.spaces = new ArrayList<ParkingSpace>();
		
		this.spaces.add(new ParkingSpace());
		
		this.admin = new Admin();
	}
	
	public static void clearInstance() /// FOR TESTING PURPOSES
	{
		OnlinePlatform.platform = null;
	}
	
	public ArrayList<ParkingSpace> getSpaces() 
	{ 
		return this.spaces; 
	}
	
	public ArrayList<Customer> getCustomers()
	{
		return this.customers;
	}
	
	public ArrayList<Officer> getOfficers()
	{
		return this.officers;
	}
	
	public Admin getAdmin() 
	{ 
		return this.admin; 
	}
	
	public boolean register(Customer customer, String email)
	{
		for (int i = 0; i < this.customers.size(); i++)
			if (this.customers.get(i).getEmail().equals(email)) return false;
		
		this.customers.add(customer);
		
		return true;
	}
	
	public boolean logIn(String email, String password)
	{
		for (int i = 0; i < this.customers.size(); i++)
		{
			if (this.customers.get(i).getEmail().equals(email))
				return this.customers.get(i).logIn(email, password);
		}
		
		return false;
	}
	
	public boolean addOfficer(Officer officer, String email)
	{
		for (int i = 0; i < this.officers.size(); i++)
			if (this.officers.get(i).getEmail().equals(email)) return false;
		
		this.officers.add(officer);
		
		return true;
	}
	
	public Officer removeOfficer(String email)
	{
		for (int i = 0; i < this.officers.size(); i++)
		{
			if (this.officers.get(i).getEmail().equals(email))
			{
				Officer officer = this.officers.get(i);
				
				this.officers.remove(i);
				officer.remove();
				
				return officer;
			}
		}
		
		return null;
	}
	
	public void addSpace()
	{
		this.spaces.add(new ParkingSpace());
	}
	
	public ParkingSpace removeSpace(int spaceNum)
	{
		if (this.spaces.size() == 1) return null;
		
		for (int i = 0; i < this.spaces.size(); i++)
		{
			if (this.spaces.get(i).getNum() == spaceNum)
			{
				ParkingSpace temp = this.spaces.get(i);
				
				temp.remove();
				
				this.spaces.remove(i);
				
				return temp;
			}
		}
		
		return null;
	}
}