import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;


public class LoginMain extends JPanel{
	private static String user="";
	private static String pass="";
	private static String pos="";
	private static Login login;
	private static JFrame frame;
	private static JPasswordField passwordEntry;
	private static JTextField userNameEntry;

	public LoginMain() throws IOException {
		login = new Login();
		initializeLogin(login);
	}
	
	public static void main(String[] args) throws IOException {
		LoginMain login = new LoginMain();
	}
	
	private static JPanel drawLogin() throws IOException {
		JPanel myPanel = new JPanel();
		
		myPanel.setBorder(new EtchedBorder());
		myPanel.setSize(600, 600);
		myPanel.setBackground(Color.GRAY);
		userNameEntry = new JTextField();
		passwordEntry = new JPasswordField();
		
		BufferedImage background = ImageIO.read(new File("Background.png"));
		JLabel backgroundLabel = new JLabel(new ImageIcon(background));
		JLabel title = new JLabel("Welcome!");
		JLabel userNameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JButton enterButton = new JButton("Log In");
		
		enterButton.setBounds(260,464,100,20);
		enterButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		title.setBounds(85,100,600,200);
		title.setFont(new Font("Times New Roman",Font.ITALIC,90));
		title.setForeground(Color.BLUE);

		userNameLabel.setBounds(130,464,200,50);
		userNameLabel.setForeground(Color.WHITE);
		userNameLabel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		passwordLabel.setBounds(410,464,100,50);
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		
		backgroundLabel.setBounds(0, 0, 600, 450);
		userNameEntry.setLocation(75,500);
		passwordEntry.setLocation(350,500);
		
		userNameEntry.setSize(200,25);
		passwordEntry.setSize(200,25);
		
		userNameEntry.setHorizontalAlignment(JTextField.HORIZONTAL);
		passwordEntry.setHorizontalAlignment(JTextField.HORIZONTAL);
		
		
		enterButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    user = userNameEntry.getText();
			    //since Jpassword's getText is deprecated
			    pass = String.valueOf(passwordEntry.getPassword());
			    try {
					pos = login.checkLogin(user, pass);
					handleNewFrame(pos);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			  } 
			});
		myPanel.add(title);
		myPanel.add(userNameEntry);
		myPanel.add(passwordEntry);
		myPanel.add(userNameLabel);
		myPanel.add(passwordLabel);
		myPanel.add(enterButton);
		myPanel.add(backgroundLabel);
		myPanel.setLayout(null);
		
		return myPanel;
	}

	private static void handleNewFrame(String entry) throws IOException {
		if(entry.equals("Admin")) {
			frame.dispose();
			Theatre admin = new Theatre();
		}
		else if(entry.equals("Customer")) {
			frame.dispose();
			Customer customer = new Customer(user);
		}
		else if(entry.equals("Invalid")) {
			passwordEntry.setText("");
			userNameEntry.setText("");
		}
	}

	private static void initializeLogin(Login backEnd) throws IOException {
		backEnd.loadData();
		backEnd.printAllUsersInfo();
		
		if(!backEnd.checkIfUserExists("Admin"))
			backEnd.writeToCustomerFile("Admin", "123");
		frame = new JFrame("Login");
		frame.setSize(600,600);
		frame.add(drawLogin());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}