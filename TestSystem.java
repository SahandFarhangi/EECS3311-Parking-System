import java.util.*;
import org.junit.Assert;
import org.junit.Test;

public class TestSystem 
{
	@Test // test OnlinePlatform instance existence, automatic existence of parking space, automatic existance of admin
	public void test_platform_creation()
	{
		OnlinePlatform.clearInstance();
		
		Assert.assertNotEquals(OnlinePlatform.getInstance(),null);
		
		Assert.assertEquals(OnlinePlatform.getInstance().getSpaces().size(), 1);
		Assert.assertEquals(OnlinePlatform.getInstance().getCustomers().size(), 0);
		Assert.assertEquals(OnlinePlatform.getInstance().getOfficers().size(), 0);
		Assert.assertNotEquals(OnlinePlatform.getInstance().getAdmin(), null);
	}
	
	@Test // creating customer w/o registering
	public void test_customer_unreg()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Assert.assertEquals(OnlinePlatform.getInstance().getCustomers().size(), 0);
		Assert.assertEquals(Bob.getReg(), false);
	}
	
	@Test // creating and registering customer
	public void test_customer_reg()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Assert.assertEquals(Bob.register("Robert", "Roberts", "robert@gmail.com", "robert"), true);
		
		Assert.assertEquals(OnlinePlatform.getInstance().getCustomers().size(), 1);
		Assert.assertEquals(Bob.getReg(), true);
		Assert.assertEquals(Bob.getEmail(), "robert@gmail.com");
		Assert.assertEquals(Bob.getFirstName(), "Robert");
		Assert.assertEquals(Bob.getLastName(), "Roberts");
		Assert.assertEquals(Bob.getPassword(), "robert");
		Assert.assertEquals(Bob.getLogged(), false);
		Assert.assertEquals(Bob.getBookings().size(), 0);
		Assert.assertEquals(Bob.getPayMethods().size(), 0);
		Assert.assertEquals(Bob.getPaid(), true);
	}
	
	@Test // trying to register customer with existing email
	public void test_customer_error() 
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Bob", "Roberts", "robert@gmail.com", "robert");
		
		Assert.assertEquals(OnlinePlatform.getInstance().getCustomers().size(), 1);
		Assert.assertEquals(Bob.getReg(), true);
		
		Customer Robert = new Customer();
		
		Assert.assertEquals(Robert.register("Robert", "Roberts", "robert@gmail.com", "robert"), false);
		
		Assert.assertEquals(OnlinePlatform.getInstance().getCustomers().size(), 1);
		Assert.assertEquals(Robert.getReg(), false);
	}
	
	@Test // creating, registering and logging in customer
	public void test_customer_login()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		
		Assert.assertEquals(OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert"), true);
		Assert.assertEquals(Bob.getLogged(), true);
	}
	
	@Test // attempting to login with email that does not exist
	public void test_customer_login_wrongemail()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		Assert.assertEquals(OnlinePlatform.getInstance().logIn("rob@gmail.com", "robert"), false);
		Assert.assertEquals(Bob.getLogged(), false);
	}
	
	@Test // logging out of customer
	public void test_customer_logout()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Bob.logOut();
		
		Assert.assertEquals(Bob.getLogged(), false);
	}
	
	@Test // customer attempts to book while logged out
	public void test_customer_book_loggedout()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		Bob.logOut();
		
		Assert.assertEquals(Bob.book(OnlinePlatform.getInstance().getSpaces().get(0).getNum(), 2, 24, 4, "JJJ111"), false);
		
		Assert.assertEquals(Bob.getBookings().size(), 0);
		Assert.assertEquals(OnlinePlatform.getInstance().getSpaces().get(0).getBooked(), false);
		Assert.assertEquals(OnlinePlatform.getInstance().getSpaces().get(0).seeBooked(), null);
	}
	
	@Test // customer registers, logs in, books space
	public void test_customer_book()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Assert.assertEquals(Bob.getLogged(), true);
		
		Assert.assertEquals(Bob.book(OnlinePlatform.getInstance().getSpaces().get(0).getNum(), 2, 24, 4, "JJJ111"), true);
		
		Assert.assertEquals(Bob.getBookings().size(), 1);
		Assert.assertEquals(OnlinePlatform.getInstance().getSpaces().get(0).getBooked(), true);
		Assert.assertEquals(OnlinePlatform.getInstance().getSpaces().get(0).seeBooked(), Bob);
		Assert.assertEquals(Bob.getBookings().get(0).getExpirationHour(), 6);
		Assert.assertEquals(Bob.getBookings().get(0).getExpirationMinute(), 24);
		Assert.assertEquals(Bob.getBookings().get(0).getBookingTime(), 4);
		Assert.assertEquals(Bob.getBookings().get(0).cost(), 10.00,0.01);
		Assert.assertEquals(Bob.getBookings().get(0).getPlate(), "JJJ111");
	}
	
	@Test // customer attempts to book non-existent parking space
	public void test_customer_book_invalid()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Assert.assertEquals(Bob.getLogged(), true);
		
		Assert.assertEquals(Bob.book(577, 2, 24, 4, "JJJ111"), false);
		
		Assert.assertEquals(Bob.getBookings().size(), 0);
	}
	
	@Test // customer books parking space and cancels
	public void test_customer_cancel()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Assert.assertEquals(Bob.getLogged(), true);
		
		Assert.assertEquals(Bob.book(OnlinePlatform.getInstance().getSpaces().get(0).getNum(), 2, 24, 4, "JJJ111"), true);
		
		Booking cancel = Bob.getBookings().get(0);
		
		Assert.assertEquals(Bob.cancel(cancel.getID()), cancel);
	}
	
	@Test // customer attempts cancellation while logged out
	public void test_customer_cancel_loggedout()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Assert.assertEquals(Bob.book(OnlinePlatform.getInstance().getSpaces().get(0).getNum(), 2, 24, 4, "JJJ111"), true);
		
		Booking cancel = Bob.getBookings().get(0);
		
		Bob.logOut();
		
		Assert.assertEquals(Bob.getLogged(), false);
		
		Assert.assertEquals(Bob.cancel(cancel.getID()), null);
	}
	
	@Test // customer adds payment method
	public void test_customer_addpayment()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Bob.addPaymentMethod("PayPal");
		
		Assert.assertEquals(Bob.getPayMethods().size(), 1);
		Assert.assertEquals(Bob.getPayMethods().get(0).getMethod(), "PayPal");
		Assert.assertEquals(Bob.getPayMethods().get(0).getOwner(), Bob);
	}
	
	@Test // customer attempts to add payment method w/o logging in
	public void test_customer_addpayment_nologin()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		Bob.addPaymentMethod("PayPal");
		
		Assert.assertEquals(Bob.getPayMethods().size(), 0);
	}

	@Test // customer pays for booking
	public void test_customer_pay()
	{
		OnlinePlatform.clearInstance();
		
		Customer Don = new Customer();
		
		Don.register("Don", "Donalds", "don@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("don@gmail.com", "robert");
		
		Don.getLogged();
		
		Don.book(OnlinePlatform.getInstance().getSpaces().get(0).getNum(), 2, 24, 4, "JJJ111");
		
		Don.addPaymentMethod("PayPal");
		
		Assert.assertTrue(Don.payFor("PayPal", OnlinePlatform.getInstance().getSpaces().get(0).getNum()));
		
		Assert.assertTrue(Don.getBookings().get(0).getPaid());
	}
	
	@Test // admin logs in
	public void test_admin_logIn()
	{
		OnlinePlatform.clearInstance();
		
		Assert.assertFalse(OnlinePlatform.getInstance().getAdmin().getLogged());
		Assert.assertTrue(OnlinePlatform.getInstance().getAdmin().adminLogIn("admin", "pass"));
		Assert.assertTrue(OnlinePlatform.getInstance().getAdmin().getLogged());
	}
	
	@Test // admin logs out
	public void test_admin_logOut()
	{
		OnlinePlatform.clearInstance();
		
		Assert.assertTrue(OnlinePlatform.getInstance().getAdmin().adminLogIn("admin", "pass"));
		
		OnlinePlatform.getInstance().getAdmin().adminLogOut();
		
		Assert.assertFalse(OnlinePlatform.getInstance().getAdmin().getLogged());
	}
	
	@Test //admin changes customer payment status to unpaid
	public void test_admin_checkpaid()
	{
		OnlinePlatform.clearInstance();
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Assert.assertEquals(Bob.getLogged(), true);
		
		Assert.assertEquals(Bob.book(OnlinePlatform.getInstance().getSpaces().get(0).getNum(), 2, 24, 4, "JJJ111"), true);
		
		OnlinePlatform.getInstance().getAdmin().adminLogIn("admin", "pass");
		
		OnlinePlatform.getInstance().getAdmin().checkPaid("robert@gmail.com");
		
		Assert.assertFalse(Bob.getPaid());
	}
	
	@Test // admin adds an officer to the system
	public void test_admin_addofficer()
	{
		OnlinePlatform.clearInstance();
		
		Admin Dave = OnlinePlatform.getInstance().getAdmin();
		
		Dave.adminLogIn("admin", "pass");
		
		Officer newGuy = Dave.addOfficer("Harvey", "Roberts", "hr@gmail.com");
		
		Assert.assertEquals(newGuy, OnlinePlatform.getInstance().getOfficers().get(0));
		
		Assert.assertEquals(newGuy.getEmail(), "hr@gmail.com");
		Assert.assertEquals(newGuy.getFirstName(), "Harvey");
		Assert.assertEquals(newGuy.getLastName(), "Roberts");
	}
	
	@Test // admin removes an officer from system
	public void test_admin_removeofficer()
	{
		OnlinePlatform.clearInstance();
		
		Admin Dave = OnlinePlatform.getInstance().getAdmin();
		
		Dave.adminLogIn("admin", "pass");
		
		Officer newGuy = Dave.addOfficer("Harvey", "Roberts", "hr@gmail.com");
		
		Assert.assertEquals(newGuy, Dave.removeOfficer("hr@gmail.com"));
		
		Assert.assertEquals(OnlinePlatform.getInstance().getOfficers().size(), 0);
		
		Assert.assertTrue(newGuy.getRemoved());
	}
	
	@Test // officer checks payment
	public void test_officer_checkpayment()
	{
		OnlinePlatform.clearInstance();
		
		Admin Dave = OnlinePlatform.getInstance().getAdmin();
		
		Dave.adminLogIn("admin", "pass");
		
		Officer newGuy = Dave.addOfficer("Harvey", "Roberts", "hr@gmail.com");
		
		Customer Bob = new Customer();
		
		Bob.register("Robert", "Roberts", "robert@gmail.com", "robert");
		
		OnlinePlatform.getInstance().logIn("robert@gmail.com", "robert");
		
		Assert.assertEquals(Bob.getLogged(), true);
		
		Assert.assertEquals(Bob.book(OnlinePlatform.getInstance().getSpaces().get(0).getNum(), 2, 24, 4, "JJJ111"), true);
		
		newGuy.checkPaid("robert@gmail.com");
		
		Assert.assertFalse(Bob.getPaid());
	}
	
	@Test // officer attempts removing parking space while only one exists
	public void test_officer_removesonly()
	{
		OnlinePlatform.clearInstance();
		
		Admin Dave = OnlinePlatform.getInstance().getAdmin();
		
		Dave.adminLogIn("admin", "pass");
		
		Officer newGuy = Dave.addOfficer("Harvey", "Roberts", "hr@gmail.com");
		
		Assert.assertEquals(newGuy.removeSpace(OnlinePlatform.getInstance().getSpaces().get(0).getNum()), null);
	}
}
