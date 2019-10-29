import java.rmi.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.rmi.server.UnicastRemoteObject;
import java.text.NumberFormat;
import java.text.ParseException;

/*
 * 
 * SOFE4790U Assignment 2
 * @author Shreyans Rishi (100585817)
 * Due Date: October 29th, 2019
 * 
 * */

public class FileImpl extends UnicastRemoteObject implements FileInterface, Serializable{
	private String name;
	Scanner readFile;
	File productsFile = new File("products.txt");
	File cartFile = new File("cart.txt");
	FileWriter fr;
	BufferedWriter br;
	BufferedReader reader;
	
	protected FileImpl() throws RemoteException {
		super();
	}
	public FileImpl(String s) throws RemoteException{
		super();
		name = s;
	}
	
	//This method will verify if the user exists
	   public boolean validateLogin(String username, String password, String person){

		   boolean verify = false;
		   try{
			   //Read from the customer list if the radio button checked was customer
			   if(person.equals("customer")){
				   readFile = new Scanner(new FileInputStream("./customer.txt"));
			   }
			   //Read from the manager list if the radio button checked was manager
		   	   else if(person.equals("manager")){
		   		   readFile = new Scanner(new FileInputStream("./manager.txt"));
		   	   }
			   String user, pass, u1, p1;
			   user = username;
			   pass = password;
			   //continue to read each line 
			   while(readFile.hasNextLine()){
				   u1 = readFile.next();
				   p1 = readFile.next();
				   if(u1.equals(user)&& p1.equals(pass)){
					   verify = true;
					   break;
				   }
			   }
			   return verify;
		   } catch (FileNotFoundException f){
			   System.out.println("File not found");
		   }
		   return verify;
	   }
	   
	   public String searchProductPrice(String productName) {
		   String pName, p1, price1;
		   boolean productFound = false;
		   p1 = null;
		   price1 = null;
		   pName = productName;
		   try {
			   readFile = new Scanner(new FileInputStream("./products.txt"));

			   while(readFile.hasNextLine()) {
				   p1 = readFile.next();
				   price1 = readFile.next();
				   if(p1.equals(pName)) {
					   productFound = true;
					   break;
				   }
			   }
			   return price1;
		   } catch (FileNotFoundException f) {
			   System.out.println("Product listings not found");
		   }
		   return price1; 
	   }
	   
	   public void addProduct(String productName, String productPrice) {
		   String space = " ";
		   try {
			fr = new FileWriter(productsFile, true);
			br = new BufferedWriter(fr);
			
			br.newLine();
			br.write(productName);
			br.write(space);
			br.write(productPrice);
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	   }
	   
	   public void addToCart(String productName) throws IOException, FileNotFoundException{
		   String pName, p1, price1;
		   String space = " ";
		   boolean productFound = false;
		   
		   p1 = null;
		   price1 = null;
		   pName = productName;

		   readFile = new Scanner(new FileInputStream("./products.txt"));
		   fr = new FileWriter(cartFile, true);
		   br = new BufferedWriter(fr);

		   while(readFile.hasNextLine()) {
			   p1 = readFile.next();
			   price1 = readFile.next();
			   if(p1.equals(pName)) {
				   productFound = true;
				   br.newLine();
				   br.write(productName);
				   br.write(space);
				   br.write(price1);
				   br.close();
				   fr.close();
				   break;
			   }
		   }
   }
	   
	   public int cartTotal() throws IOException, FileNotFoundException{
		   String p1, price1, line;
		   int cartTotal = 0;
		   String[] data;
		   NumberFormat format = NumberFormat.getCurrencyInstance();
		   readFile = new Scanner(new FileInputStream("./cart.txt"));
		   
		   while(readFile.hasNextLine()) {
			   p1 = readFile.next();
			   price1 = readFile.next();
			   price1 = price1.replace("$", "");
			   int num = Integer.parseInt(price1);
			   cartTotal = cartTotal + num;
		   }
		   
		   return cartTotal;
	   }
	   
	
}
