import java.io.*;
import java.rmi.*;

/*
 * 
 * SOFE4790U Assignment 2
 * @author Shreyans Rishi (100585817)
 * Due Date: October 29th, 2019
 * 
 * */

public class FileServer {
	@SuppressWarnings("deprecation")
	public static void main(String argv[]) {
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			FileImpl fi = new FileImpl();
			Naming.rebind(FileInterface.SERVICENAME, fi);
			System.out.println("RMI registry running....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
