import java.io.*;
import java.rmi.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.*;

/*
 * 
 * SOFE4790U Assignment 2
 * @author Shreyans Rishi (100585817)
 * Due Date: October 29th, 2019
 * 
 * */

public class FileApp {
	public static void main(String[] args) {
		try {
			FileInterface fi = (FileInterface)Naming.lookup(FileInterface.SERVICENAME);
			FileApp project = new FileApp();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		

		public FileApp() {
			JFrame GUI = new JFrame();
			GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			GUI.setTitle("Grocery Store");
			GUI.setSize(500, 300);
			
			GUI.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {
					System.exit(0);
				}
			});
			JPanel panel = new JPanel();
			panel.setLayout(null);
			
			JLabel userLabel = new JLabel("Username: ");
			userLabel.setBounds(10, 10, 80, 25);
			JTextField username = new JTextField(9);
			username.setBounds(100, 10, 160, 25);
			
			JLabel passLabel = new JLabel("Password: ");
			passLabel.setBounds(10, 40, 80, 25);
			JPasswordField password = new JPasswordField(20);
			password.setBounds(100, 40, 160, 25);
			password.setEchoChar('*');
			
			
			JRadioButton customerButton = new JRadioButton("customer");
			customerButton.setActionCommand(customerButton.getText());
			customerButton.setSelected(false);
			customerButton.setBounds(10, 70, 80, 25);
			
			JRadioButton managerButton = new JRadioButton("manager");
			managerButton.setActionCommand(managerButton.getText());
			managerButton.setSelected(false);
			managerButton.setBounds(10, 100, 80, 25);
			
			ButtonGroup group = new ButtonGroup();
			group.add(customerButton);
			group.add(managerButton);
			
			//Login Button
			JButton loginButton = new JButton("Login");
			loginButton.setBounds(180, 80, 80, 25);
			
			//Status label to tell user current state
			JLabel status = new JLabel("");
			status.setBounds(50, 125, 250, 25);
			
			ActionListener loginAction = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					FileImpl implement = null;
					try {
						implement = new FileImpl();
					} catch (RemoteException re) {
						re.printStackTrace();
					}
					
					String user = username.getText();
					String pass = password.getText();
					String person = group.getSelection().getActionCommand();
					
					//Case if radio button checked is customer
					if(person.equals("customer")){
						if(implement.validateLogin(user, pass, person)==true){
							status.setText("Correct customer credentials!");
							GUI.getContentPane().removeAll();
							GUI.repaint();
							GUI.setTitle("Welcome " + user);
							GUI.setSize(600, 800);
							
							JPanel customerPanel = new JPanel();
							customerPanel.setLayout(null);
							
							JTextArea menu = new JTextArea("Available Products: ");
							JScrollPane scrollPane = new JScrollPane(menu);
							menu.setBounds(10, 120, 250, 250);
							menu.setBorder(new LineBorder(Color.BLACK));
							menu.setLayout(null);
							menu.setEditable(false);
							// Search price for a product
							JLabel productLabel = new JLabel("Search Product Price: ");
							productLabel.setBounds(10, 10, 160, 25);
							JTextField productInput = new JTextField(9);
							productInput.setText("Enter a product name...");
							productInput.setBounds(160, 10, 160, 25);
							JLabel productPriceLabel = new JLabel("Price: ");
							productPriceLabel.setBounds(420, 10, 75, 25);
							
							//Price Button
							JButton priceButton = new JButton("Price");
							priceButton.setBounds(330, 10, 75, 25);
							
							
							//Add product to cart
							JLabel productLabel2 = new JLabel("Add product to cart: ");
							productLabel2.setBounds(10, 50, 160, 25);
							JTextField productInput2 = new JTextField(9);
							productInput2.setText("Enter a product name...");
							productInput2.setBounds(160, 50, 160, 25);
							JLabel productCartLabel = new JLabel(" ");
							productCartLabel.setBounds(440, 50, 120, 25);
							JLabel productCartTotal = new JLabel("Cart Total: ");
							productCartTotal.setBounds(10, 80, 120, 25);
							
							//Add to cart Button
							JButton cartButton = new JButton("Add to cart");
							cartButton.setBounds(330, 50, 100, 25);
							
							Scanner readFile;
							try {
								readFile = new Scanner(new FileInputStream("./products.txt"));
							   while(readFile.hasNextLine()) {
								   menu.append("\n");
								   menu.append(readFile.next());
								   readFile.next();
							   }
							} catch (FileNotFoundException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}


							
							ActionListener priceAction = new ActionListener() {
								public void actionPerformed(ActionEvent e){
									FileImpl implement = null;
									try {
										implement = new FileImpl();
									} catch (RemoteException re) {
										re.printStackTrace();
									}
									
									String product = productInput.getText();
									productPriceLabel.setText("Price: " + implement.searchProductPrice(product));
								}
							};
							
							ActionListener cartAction = new ActionListener() {
								public void actionPerformed(ActionEvent e){
									FileImpl implement = null;
									try {
										implement = new FileImpl();
									} catch (RemoteException re) {
										re.printStackTrace();
									}
									
									String product = productInput2.getText();
									try {
										implement.addToCart(product);
										productCartLabel.setText(product + " added to cart!");
										int cartTotal = implement.cartTotal();
										productCartTotal.setText("Cart Total: " + String.valueOf(cartTotal));
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
									
									
								}
							};
							
							
							GUI.add(customerPanel);
							GUI.setVisible(true);
							
							
							priceButton.addActionListener(priceAction);
							cartButton.addActionListener(cartAction);
							customerPanel.add(productLabel);
							customerPanel.add(GUI.add(menu));
							customerPanel.add(productLabel2);
							customerPanel.add(productInput);
							customerPanel.add(productInput2);
							customerPanel.add(productPriceLabel);
							customerPanel.add(productCartTotal);
							customerPanel.add(productCartLabel);
							customerPanel.add(priceButton);
							customerPanel.add(cartButton);
							
						}
					
						else if(implement.validateLogin(user, pass, person)==false){
							status.setText("Incorrect customer credentials!");
						}
					}
					//Case if radio button checked is manager
					else if(person.equals("manager")){
						if(implement.validateLogin(user, pass, person)==true){
							status.setText("Correct manager credentials!");
							GUI.getContentPane().removeAll();
							GUI.repaint();
							GUI.setTitle("Welcome " + user);
							GUI.setSize(600, 800);
							
							JPanel managerPanel = new JPanel();
							managerPanel.setLayout(null);
							
							JLabel productLabel = new JLabel("Add a product to inventory: ");
							productLabel.setBounds(10, 10, 160, 25);
							JTextField productNameInput = new JTextField(9);
							productNameInput.setText("Enter product name...");
							productNameInput.setBounds(165, 10, 120, 25);
							JTextField productPriceInput = new JTextField(9);
							productPriceInput.setText("Enter product price...");
							productPriceInput.setBounds(290, 10, 120, 25);
							JLabel productAddLabel = new JLabel("");
							productAddLabel.setBounds(10, 40, 250, 25);
							
							//Price Button
							JButton priceButton = new JButton("Add");
							priceButton.setBounds(420, 10, 75, 25);
							
							ActionListener priceAction = new ActionListener() {
								public void actionPerformed(ActionEvent e){
									FileImpl implement = null;
									try {
										implement = new FileImpl();
									} catch (RemoteException re) {
										re.printStackTrace();
									}
									
									String productAddName = productNameInput.getText();
									String productAddPrice = productPriceInput.getText();
									implement.addProduct(productAddName, productAddPrice);
									productAddLabel.setText(productAddName + " succesfully added to inventory");
								}
							};
							GUI.add(managerPanel);
							GUI.setVisible(true);
							priceButton.addActionListener(priceAction);
							managerPanel.add(productLabel);
							managerPanel.add(productNameInput);
							managerPanel.add(productPriceInput);
							managerPanel.add(productAddLabel);
							managerPanel.add(priceButton);
						}
						else if(implement.validateLogin(user, pass, person)==false){
							status.setText("Incorrect manager credentials!");
						}
					}
				}
					
			};
			
			
			GUI.add(panel);
			GUI.setVisible(true);
			loginButton.addActionListener(loginAction);
			panel.add(userLabel);
			panel.add(username);
			panel.add(passLabel);
			panel.add(password);
			panel.add(customerButton);
			panel.add(managerButton);
			panel.add(loginButton);
			panel.add(status);
			
		}
		
	}

