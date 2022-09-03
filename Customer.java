import java.util.*;

public class Customer 
{	
	private String fname;
	private String lname;
	private String email;
	private String password;
	
	private boolean reg;
	private boolean logged;
	
	private ArrayList<Booking> bookings;
	private ArrayList<PaymentMethod> payMethods;
	
	private boolean paid;
	
	public Customer()
	{
		this.reg = false; this.logged = false;
		
		this.bookings = new ArrayList<Booking>();
		this.payMethods = new ArrayList<PaymentMethod>();
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getFirstName()
	{
		return this.fname;
	}
	
	public String getLastName()
	{
		return this.lname;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public boolean register(String fname, String lname, String email, String password)
	{
		if (OnlinePlatform.getInstance().register(this, email))
		{
			this.fname = fname;
			this.lname = lname;
			this.email = email;
			this.password = password;
		
			this.reg = true;
			this.paid = true;
			
			return true;
		}
		else return false;
	}
	
	public boolean logIn(String email, String password)
	{
		if (this.email.equals(email) && this.password.equals(password)) logged = true;
		
		return logged;
	}
	
	public void logOut() 
	{ 
		this.logged = false; 
	}
	
	public boolean getLogged() 
	{ 
		return this.logged; 
	}
	
	public boolean getReg()
	{
		return this.reg;
	}
	
	public ArrayList<Booking> getBookings()
	{
		return this.bookings;
	}
	
	public boolean getPaid()
	{
		return this.paid;
	}
	
	public void setPaid(boolean paid)
	{
		this.paid = paid;
	}
	
	public boolean book(int spaceNum, int bookingHour, int bookingMinute, int bookingTime, String plateNum)
	{
		if (this.bookings.size() < 3 && logged && reg)
		{
			if (bookingHour > 23 || bookingHour < 0 || bookingMinute < 0 || bookingHour > 59 || bookingTime < 0) 
				return false;
			
			ArrayList<ParkingSpace> spaces = OnlinePlatform.getInstance().getSpaces();
			
			for (int i = 0; i < spaces.size(); i++)
			{
				if (spaces.get(i).getNum() == spaceNum && !spaces.get(i).getBooked())
				{
					spaces.get(i).book(this);
					
					this.bookings.add(new Booking(spaceNum, bookingHour, bookingMinute, bookingTime, plateNum));
					
					return true;
				}
			}
			
			return false;
		}
		else return false;
	}
	
	public Booking cancel(int bookingID)
	{
		if (logged && reg)
		{
			for (int i = 0; i < this.bookings.size(); i++)
			{
				if (this.bookings.get(i).getID() == bookingID && !this.bookings.get(i).getPaid())
				{
					Booking temp = this.bookings.get(i);
					
					this.bookings.remove(i);
					
					return temp;
				}
			}
			
			return null;
		}
		else return null;
	}
	
	public void addPaymentMethod(String method)
	{
		if (logged & reg)
			this.payMethods.add(new PaymentMethod(this, method));
	}
	
	public ArrayList<PaymentMethod> getPayMethods()
	{
		return this.payMethods;
	}
	
	public boolean payFor(String method, int spaceNum)
	{
		PaymentMethod chosen = null;
		
		for (int i = 0; i < this.payMethods.size(); i++)
		{
			if (this.payMethods.get(i).getMethod().equals(method))
				chosen = this.payMethods.get(i);
		}
		
		if (chosen == null) return false;
		
		for (int i = 0; i < this.bookings.size(); i++)
		{
			if (this.bookings.get(i).getSpaceNum() == spaceNum)
			{
			//	if (chosen.canPay(this.bookings.get(i).cost()))
			//	{
					this.bookings.get(i).pay();
					
					return true;
			//	}
			}
		}
		
		return false;
	}
}
