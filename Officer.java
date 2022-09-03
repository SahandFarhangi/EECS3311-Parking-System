import java.util.*;

public class Officer 
{
	private static int officerNum = 0;
	
	private int officerID;
	private String fname;
	private String lname;
	private String email;
	
	private boolean removed;

	public Officer(String fname, String lname, String email)
	{
		if (OnlinePlatform.getInstance().addOfficer(this, email))
		{
			this.fname = fname;
			this.lname = lname;
			this.email = email;
			this.officerID = officerNum;
			
			this.removed = false;
			
			officerNum++;
		}
	}
	
	public int getID()
	{
		return this.officerID;
	}
	
	public String getFirstName()
	{
		return this.fname;
	}
	
	public String getLastName()
	{
		return this.lname;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public boolean getRemoved()
	{
		return this.removed;
	}
	
	public void checkPaid(String email)
	{
		if (!removed)
		{
			ArrayList<Customer> customers = OnlinePlatform.getInstance().getCustomers();
		
			for (int i = 0; i < customers.size(); i++)
			{
				if (customers.get(i).getEmail().equals(email))
				{
					customers.get(i).setPaid(true);
					
					for (int j = 0; j < customers.get(i).getBookings().size(); j++)
					{
						if (!customers.get(i).getBookings().get(j).getPaid()) customers.get(i).setPaid(false);
					}
					
					break;
				}
			}
		}
	}
	
	public void addSpace()
	{
		if (!removed)
		{
			OnlinePlatform.getInstance().addSpace();
		}
	}
	
	public ParkingSpace removeSpace(int spaceNum)
	{
		if (!removed)
		{
			return OnlinePlatform.getInstance().removeSpace(spaceNum);
		}
		
		return null;
	}
	
	public Customer checkBooking(int spaceNum)
	{
		if (!removed)
		{
			ArrayList<ParkingSpace> spaces = OnlinePlatform.getInstance().getSpaces();
			
			for (int i = 0; i < spaces.size(); i++)
			{
				if (spaces.get(i).getNum() == spaceNum)
				{
					return spaces.get(i).seeBooked();
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<Booking> getBooking(String email)
	{
		if (!removed)
		{
			ArrayList<Customer> customers = OnlinePlatform.getInstance().getCustomers();
		
			for (int i = 0; i < customers.size(); i++)
			{
				if (customers.get(i).getEmail().equals(email)) return customers.get(i).getBookings();
			}
		}
		
		return null;
	}
	
	public void remove()
	{
		this.removed = true;
	}
}
