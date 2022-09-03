
public class Booking 
{
	private static int numBooked = 0;
	
	private int expiryHour;
	private int expiryMinute;
	private String plateNum;
	private int spaceNum;
	
	private int bookingID;
	private int bookingTime;
	
	private boolean paid;
	
	public Booking(int spaceNum, int bookingHour, int bookingMinute, int bookingTime, String plateNum)
	{
		this.expiryHour = (bookingHour + bookingTime) % 24;
		this.expiryMinute = bookingMinute;
		this.plateNum = plateNum;
		this.spaceNum = spaceNum;
		
		this.bookingID = numBooked;
		this.bookingTime = bookingTime;
		
		this.paid = false;
		numBooked++;
	}
	
	public int getID()
	{
		return this.bookingID;
	}
	
	public int getExpirationHour()
	{
		return this.expiryHour;
	}
	
	public int getExpirationMinute()
	{
		return this.expiryMinute;
	}
	
	public String getPlate()
	{
		return this.plateNum;
	}
	
	public int getBookingTime()
	{
		return this.bookingTime;
	}
	
	public int getSpaceNum()
	{
		return this.spaceNum;
	}
	
	public boolean getPaid()
	{
		return this.paid;
	}
	
	public double cost()
	{
		return this.bookingTime * ParkingSpace.getCost();
	}
	
	public void pay()
	{
		this.paid = true;
	}
}
