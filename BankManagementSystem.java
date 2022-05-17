import java.util.*;
import java.text.*;
interface SavingsAccount
{
	final double rate = 0.04,limit = 10000,limit1 = 200;
	void deposit(double n,Date d);
	void withdraw(double n,Date d);
}
class Customer implements SavingsAccount
{
	String username,password,name,address,phone;
	double balance;
	ArrayList<String> transactions;
	Customer(String username,String password,String name,String address,String phone,double balance,Date date)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.balance = balance;
		transactions  =  new ArrayList<String>(5);
		addTransaction(String.format("Initial deposit - " +NumberFormat.getCurrencyInstance().format(balance)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	void update(Date date)
	{
		if(balance>= 10000)
		{
			balance += rate*balance;
		}
		else
		{
			balance -= (int)(balance/100.0);
		}
		addTransaction(String.format("Account updated. Balance - " +NumberFormat.getCurrencyInstance().format(balance)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	@Override
	public void deposit(double amount,Date date)
	{
		balance += amount;
		addTransaction(String.format(NumberFormat.getCurrencyInstance().format(amount)+" credited to your account. Balance - " +NumberFormat.getCurrencyInstance().format(balance)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	@Override
	public void withdraw(double amount,Date date)
	{
		if(amount>(balance-200))
		{
			System.out.println("Insufficient balance.");
			return;
		}
		balance -= amount;
		addTransaction(String.format(NumberFormat.getCurrencyInstance().format(amount)+" debited from your account. Balance - " +NumberFormat.getCurrencyInstance().format(balance)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	private void addTransaction(String message)
	{
			transactions.add(0,message);
			if(transactions.size()>5)
			{
				transactions.remove(5);
				transactions.trimToSize();
			}
	}
}
class Bank
{
	Map<String,Customer> customerMap;
	Bank()
	{
		customerMap = new HashMap <String,Customer>();
	}
	public static void main(String []args)
	{
		Scanner sc  =  new Scanner(System.in);
		Customer customer;
		String username,password;double amount;
		Bank bank  =  new Bank();
		int choice;
	outer:	while(true)
		{
			System.out.println("\n-------------------");
			System.out.println("BANK    OF     JAVA");
			System.out.println(":-------------------\n");
			System.out.println("1. Register account.");
			System.out.println("2. Login.");
			System.out.println("3. Update accounts.");
			System.out.println("4. Exit.");
			System.out.print("\nEnter your choice : ");
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice)
			{
				case 1:
					System.out.print("Enter name : ");
					String name = sc.nextLine();
					System.out.print("Enter address : ");
					String address = sc.nextLine();
					System.out.print("Enter contact number : ");
					String phone = sc.nextLine();
					System.out.println("Set username : ");
					username = sc.next();
					while(bank.customerMap.containsKey(username))
					{
                      System.out.println("Username already exists. Set again : ");
                      username = sc.next();
                  }
                  System.out.println("Set a password (minimum 8 chars; minimum 1 digit, 1 lowercase, 1 uppercase, 1 special character[!@#$%^&*_]) :");
                  password = sc.next();
                  sc.nextLine();
                  while(!password.matches((("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_]).{8,}"))))
                  {
                      System.out.println("Invalid password condition. Set again :");
                      password=sc.next();
                  }
                  System.out.print("Enter initial deposit : ");
                  sc.nextLine();
                  while(!sc.hasNextDouble())
                {
                      System.out.println("Invalid amount. Enter again :");
                    sc.nextLine();
                          }
                  amount=sc.nextDouble();
                  sc.nextLine();
                  customer = new Customer(username,password,name,address,phone,amount,new Date());
                  bank.customerMap.put(username,customer);
                  break;
              case 2:
                  System.out.println("Enter username : ");
                  username = sc.next();
                  sc.nextLine();
                  System.out.println("Enter password : ");
                password = sc.next();
                sc.nextLine();
                if(bank.customerMap.containsKey(username))
                {
                      customer = bank.customerMap.get(username);
                      if(customer.password.equals(password))
                      {
                          while(true)
                          {
                              System.out.println("\n-------------------");
                              System.out.println("W  E  L  C  O  M  E");
                              System.out.println("-------------------\n");
                              System.out.println("1. Deposit.");
                              System.out.println("2. Transfer.");
                              System.out.println("3. Last 5 transactions.");
                              System.out.println("4. User information.");
                              System.out.println("5. Log out.");
                              System.out.print("\nEnter your choice : ");
                              choice = sc.nextInt();
                              sc.nextLine();
                              switch(choice)
                              {
                                  case 1:
                                         System.out.print("Enter amount : ");
                                         while(!sc.hasNextDouble())
									       {
										       System.out.println("Invalid amount. Enter again :");
										       sc.nextLine();
									       }
									       amount = sc.nextDouble();
									       sc.nextLine();
	                                                                       customer.deposit(amount,new Date());
									       break;
									case 2:
									       System.out.print("Enter payee username : ");
									       username = sc.next();
									       sc.nextLine();
									       System.out.println("Enter amount : ");
									       while(!sc.hasNextDouble())
							    	       {
                                       System.out.println("Invalid amount. Enter again :");
										       sc.nextLine();
									       }
									       amount = sc.nextDouble();
									       sc.nextLine();
									       if(amount > 300000)
									       {
										       System.out.println("Transfer limit exceeded. Contact bank manager.");
										       break;
									       }
									       if(bank.customerMap.containsKey(username))
									       {
										       Customer payee = bank.customerMap.get(username);
										       payee.deposit(amount,new Date());
										       customer.withdraw(amount,new Date());
									       }
									       else
									       {
										       System.out.println("Username doesn't exist.");
									       }
									       break;
									case 3:
									       for(String transactions : customer.transactions)
									       {
										       System.out.println(transactions);
									       }
									       break;
									case 4:
									       System.out.println("Accountholder name : "+customer.name);
									       System.out.println("Accountholder address : "+customer.address);
									       System.out.println("Accountholder contact : "+customer.phone);
									       break;
									case 5:
									       continue outer;
								        default:
									        System.out.println("Wrong choice !");
								}
							}
						}
						else
						{
							System.out.println("Wrong username/password.");
						}
					}
					else
					{
						System.out.println("Wrong username/password.");
					}
					break;
				case 3:
					System.out.println("Enter username : ");
					username = sc.next();
					if(bank.customerMap.containsKey(username))
					{
						bank.customerMap.get(username).update(new Date());
					}
					else
					{
						System.out.println("Username doesn't exist.");
					}
					break;
				case 4:
					System.out.println("\nThank you for choosing Bank Of Java."); 
					System.exit(1);
					break;
				default:
					System.out.println("Wrong choice !");
			}
		}
	}
}
