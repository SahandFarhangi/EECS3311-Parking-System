
public class PaymentMethod 
{
	private Customer owner;
	private String method;
	private double amount;
	
	public PaymentMethod(Customer owner, String method)
	{
		this.owner = owner;
		this.method = method;
		this.amount = 100.00;
	}
	
	public String getMethod()
	{
		return this.method;
	}
	
	public Customer getOwner()
	{
		return this.owner;
	}
	
	public boolean canPay(double amount)
	{
		if (amount >= this.amount)
		{
			this.amount -= amount;
			
			return true;
		}
		
		return false;
	}
}
