import java.rmi.*;
import java.io.*;
import java.util.*;

/*
 * 
 * SOFE4790U Assignment 2
 * @author Shreyans Rishi (100585817)
 * Due Date: October 29th, 2019
 * 
 * */
public interface FileInterface extends Remote{
	public final static String SERVICENAME = "FileService";
	public boolean validateLogin(String userName, String password, String person) throws RemoteException;
	public String searchProductPrice(String productName) throws RemoteException;
	public void addProduct(String productName, String productPrice) throws RemoteException;
	public void addToCart(String productName) throws RemoteException, IOException, FileNotFoundException;
	public int cartTotal() throws RemoteException, IOException, FileNotFoundException;
}
