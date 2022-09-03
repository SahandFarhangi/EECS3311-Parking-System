
public class ParkingSpace 
{
	private static final double spaceCost = 2.50;
	private static int spaceID = 0;
	
	private int spaceNum;
	private boolean booked;
	
	private boolean removed;
	
	private Customer bookedBy;
	
	public ParkingSpace()
	{
		this.spaceNum = spaceID;
		this.booked = false;
		this.removed = false;
		
		this.bookedBy = null;
		
		spaceID++;
	}
	
	public static double getCost()
	{
		return ParkingSpace.spaceCost;
	}
	
	public int getNum()
	{
		return this.spaceNum;
	}
	
	public boolean getBooked()
	{
		return this.booked;
	}
	
	public boolean book(Customer customer)
	{
		if (this.booked || this.removed) return false;
		else
		{
			this.booked = true;
			
			this.bookedBy = customer;
			
			return true;
		}
	}
	
	public void unbook(int spaceNum)
	{
		if (this.spaceNum == spaceNum)
		{
			booked = false;
			this.bookedBy = null;
		}
	}
	
	public void remove()
	{
		this.removed = true;
	}
	
	public Customer seeBooked()
	{
		return this.bookedBy;
	}
}
