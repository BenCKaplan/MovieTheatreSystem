import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * 
 */
public class Login {
	
	private File customerData;
	private File movieData;
	private Set<String> userNames;
	private Map<String, String>userLoginInfo;
	
	
	public Login() {
		customerData = new File("Customer Data.txt");
		movieData = new File("Movie Data.txt");
		userNames = new HashSet<String>();
		userLoginInfo = new HashMap<String, String>();
	}
	
	public void writeToCustomerFile(String username, String password) throws IOException {
		FileWriter writer = new FileWriter(customerData, true);
		BufferedWriter buff = new BufferedWriter(writer);
		PrintWriter printer = new PrintWriter(buff);
		writer.write("Username: " + username + "\nPassword: " + password + "\n\n");
		
		printer.printf("%s\t%s\t%n", username, password);
		System.out.println(userNames.size());
		printer.close();
		buff.close();
		writer.close();
	}
	
	public void writeToMovieFile(String title, String genre, String date) throws IOException {
		FileWriter writer = new FileWriter(movieData, true);
		BufferedWriter buff = new BufferedWriter(writer);
		PrintWriter printer = new PrintWriter(buff);
		
		printer.printf("\n%s\t%s\t%s", title, genre, date);
		
		printer.close();
		buff.close();
		writer.close();
	}
	
	public void loadData() throws FileNotFoundException {
		if(!customerData.exists()) {
			PrintWriter init = new PrintWriter(customerData);
			init.printf("");
			init.close();
		}
		if(!movieData.exists()) {
			PrintWriter init = new PrintWriter(movieData);
			init.printf("");
			init.close();
		}
		Scanner uScan = new Scanner(customerData);
		Scanner mScan = new Scanner(movieData);
		int counter = 0;
		String user = "";
		String pass = "";
		int amount = 0;
		while(uScan.hasNext()) {
			String nextWord = uScan.next();
			if(counter == 0 && nextWord != null) {
				user = nextWord;
				counter++;
			}
			else if(counter == 1 && nextWord != null) {
				pass = nextWord;
				userLoginInfo.put(user, pass);
				userNames.add(user);
				counter++;
			}
		}
		uScan.close();
	}
	
	//(Admin, password)
	public String checkLogin(String user,String pass) throws IOException {
		if(user.equals("") || pass.equals("")) {
			System.out.println("Please enter a username and password.");
			return "Invalid";
		}

		else if(user.equals("Admin") && pass.equals("password")) {
			System.out.println(userNames.size());
			return "Admin";
		}
		
		else if(!userNames.contains(user)) {
			System.out.printf("New Account: %s%n%n",user);
			userNames.add(user);
			userLoginInfo.put(user, pass);
			writeToCustomerFile(user,pass);
			return "Customer";
		}

		else if(userNames.contains(user)) {
			if(userLoginInfo.get(user).equals(pass)) {
				System.out.printf("%s%n%n",user);
				return "Customer";
			}
		}
		return "Customer";
	}
	
	public boolean checkIfUserExists(String user) {
		return userNames.contains(user);
	}
	
	public void printAllUsersInfo() {
		System.out.println(userLoginInfo.size());
		for(String myKeys : userLoginInfo.keySet()) {
			System.out.printf("Username: %s%nPassword: %s%n%n", myKeys,userLoginInfo.get(myKeys));
		}
	}
	
	//update data to see reward data
	public void updateData() throws IOException {
		File temp = new File("temp.txt");
		FileWriter fw = new FileWriter(temp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		Set<String>keys = userLoginInfo.keySet();
		for(String user : keys) {
			pw.printf("%s\t%s\t%d%n", user,userLoginInfo.get(user));
		}
		pw.close();
		bw.close();
		fw.close();
		customerData.delete();
		System.out.println(temp.renameTo(customerData));
	}
}