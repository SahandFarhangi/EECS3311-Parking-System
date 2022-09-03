import java.util.*;

public class Admin 
{
	private String adminID;
	private String password;
	
	private boolean logged;
	
	public Admin()
	{
		this.adminID = "admin";
		this.password = "pass";
		this.logged = false;
	}
	
	public boolean getLogged()
	{
		return this.logged;
	}
	
	public boolean adminLogIn(String adminID, String password)
	{
		if (this.adminID.equals(adminID) && this.password.equals(password)) this.logged = true;
		
		return this.logged;
	}
	
	public void adminLogOut()
	{
		this.logged = false;
	}
	
	public Officer addOfficer(String fname, String lname, String email)
	{
		if (logged)
			return new Officer(fname, lname, email);
		
		return null;
	}
	
	public Officer removeOfficer(String email)
	{
		if (logged)
			return OnlinePlatform.getInstance().removeOfficer(email);
		
		return null;
	}
	
	public void checkPaid(String email)
	{
		if (logged)
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
}
