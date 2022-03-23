import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

/*
 * Class used for Customer functionality
 */
public class Customer {
	private JFrame customer;
	private JPanel panel;
	private String username;
	private File userData;
	private File movieData;
	private static Set<Movies> allMovies;
	private static Set<String> allMovieTitles;
	private static Set<Movies> purchasedMovies;
	
	public Customer(String username) {
		this.username = username;
		purchasedMovies = new HashSet<Movies>();
		allMovies = new HashSet<Movies>();
		allMovieTitles = new HashSet<String>();
		movieData = new File("Movie Data.txt");
		try {
			loadData(movieData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		initializeFrame();
	}
	
	public static void loadData(File file) throws FileNotFoundException {
		allMovies = new HashSet<Movies>();
		allMovieTitles = new HashSet<String>();
		
		if(!file.exists()) {
			PrintWriter makeFile = new PrintWriter(file);
			makeFile.printf("");
			makeFile.close();
		}
		else {
			Scanner sc = new Scanner(file);
			int counter = 0;
			Movies movie = new Movies();
			while(sc.hasNext()) {
				String str = sc.next();
				if(counter == 0 && str != null) {
					movie = new Movies();
					movie.setTitle(str);
					allMovieTitles.add(str);
					counter++;
				}
				else if(counter == 1 && str != null) {
					movie.setGenre(str);
					counter++;
				}
				else if(counter == 2 && str != null) {
					movie.setReleaseDate(str);
					allMovies.add(movie);
					counter = 0;
				}
			}
		}
	}

	private void initializeFrame() {
		customer = new JFrame("Customer View");
		customer.setSize(600,600);
		customer.setVisible(true);
		customer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		JLabel label = new JLabel("Customer View");
		
		panel.setBackground(new Color(14,133,201));

		label.setFont(new Font("Times New Roman",Font.ITALIC,25));
		label.setBounds(180,0,250,50);
		
		addOptions();
		panel.add(label);
		panel.setLayout(null);
		customer.add(panel);
	}
	
	private void addOptions() {
		Font myFont = new Font("Times New Roman", Font.ITALIC, 30);
		Cursor myCursor = new Cursor(Cursor.HAND_CURSOR);

		Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
		
		JLabel viewHistory = new JLabel("View History");
		JLabel browseMovies = new JLabel("Browse Movies");
		JLabel viewTickets = new JLabel("View Tickets");
		JLabel signOut = new JLabel("Sign Out");
		JLabel deleteAccount = new JLabel("Delete Account");
		
		JButton logout = new JButton("Log out");
		logout.setBounds(500, 500, 75, 45);

		viewHistory.setBounds(25, 75, 250, 75);
		browseMovies.setBounds(25, 175, 250, 75);
		viewTickets.setBounds(25, 275, 250, 75);
		signOut.setBounds(25, 375, 250, 75);
		deleteAccount.setBounds(25, 475, 250, 75);
		
		viewHistory.setBorder(border);
		browseMovies.setBorder(border);
		viewTickets.setBorder(border);
		signOut.setBorder(border);
		deleteAccount.setBorder(border);
		
		viewHistory.setFont(myFont);
		browseMovies.setFont(myFont);
		viewTickets.setFont(myFont);
		signOut.setFont(myFont);
		deleteAccount.setFont(myFont);
		
		viewHistory.setCursor(myCursor);
		browseMovies.setCursor(myCursor);
		viewTickets.setCursor(myCursor);
		signOut.setCursor(myCursor);
		deleteAccount.setCursor(myCursor);
		
		logout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				customer.dispose();
				try {
					LoginMain main = new LoginMain();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		browseMovies.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				allMovies = Theatre.getAllMovies();
//				allMovieTitles = Theatre.getAllMovieTitles();
				customer.dispose();
				
				drawBrowseMovies();
			}
			private void drawBrowseMovies() {
				customer = new JFrame("Browsing Movie Options");
				customer.setSize(600, 600);
				customer.setVisible(true);
				customer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				panel = new JPanel();
				panel.setBackground(new Color(78, 189, 217));
				
				JLabel label = new JLabel("Browsing Movies");
				JButton logout = new JButton("Log out");
				JButton search = new JButton("Search");

				JTextField searchTitle = new JTextField();
				JLabel searchTitleLabel = new JLabel("Search by title");
				JTextField searchGenre = new JTextField();
				JLabel searchGenreLabel = new JLabel("Search by genre");
				JLabel bottom = new JLabel("");
				
				bottom.setBounds(5, 460, 500, 100);
				bottom.setFont(new Font("Serif", Font.ITALIC, 25));
				
				searchTitle.setBounds(420, 200, 150, 30);
				searchTitleLabel.setBounds(300, 200, 90, 30);
				searchTitleLabel.setFont(new Font("Serif", Font.ITALIC, 14));
				
				searchGenre.setBounds(420, 290, 150, 30);
				searchGenreLabel.setBounds(300, 290, 90, 30);
				searchGenreLabel.setFont(new Font("Serif", Font.ITALIC, 14));

				label.setFont(new Font("Serif", Font.ITALIC, 25));
				label.setBounds(190, 0, 250, 50);
				logout.setBounds(500, 500, 75, 45);
				search.setBounds(500, 415, 75, 45);
				
				ArrayList<JLabel> labels = new ArrayList<JLabel>();
				for(Movies m : allMovies) {
					String title = m.getTitle();
					m.setTitle(title);
					labels.add(new JLabel(m.toString()));
				}
				int ySet = 50;
				for(int i = 0; i < labels.size(); i++) {
					JLabel add = labels.get(i);
					add.setBounds(25, ySet, 200, 50);
					add.setBorder(border);
					add.setFont(new Font("Serif", Font.ITALIC, 14));
					ySet += 50;
					add.addMouseListener(new MouseAdapter() {
						
					});
					
					panel.add(add);
				}
				search.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String titleSearch = searchTitle.getText();
						String genreSearch = searchGenre.getText();
						
						if(titleSearch.isBlank()) {
							if(genreSearch.isBlank()) {
								bottom.setText("Enter a parameter to search");
							}
							else {
								
							}
						}
					}
					
				});
//				search.addActionListener(new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						ArrayList<JLabel> temp = new ArrayList<JLabel>();
//						int ySet = 50;
//						if(!searchTitle.getText().isBlank() && searchGenre.getText().isBlank()) {
//							for(Movies m : allMovies) {
//								if(searchTitle.getText().equals(m.getTitle())) {
//									JLabel sort = new JLabel();
//									sort.setText(m.toString());
//									sort.setBounds(25, ySet, 200, 50);
//									sort.setBorder(border);
//									sort.setFont(new Font("Serif", Font.ITALIC, 14));
//									ySet += 50;
//									temp.add(sort);
////									myP.add(sort);
//								}
//							}
//						}
//						else if(searchTitle.getText().isBlank() && !searchGenre.getText().isBlank()) {
//							for(Movies m : allMovies) {
//								if(searchGenre.getText().equals(m.getGenre())) {
//									JLabel sort = new JLabel();
//									sort.setText(m.toString());
//									sort.setBounds(25, ySet, 200, 50);
//									sort.setBorder(border);
//									sort.setFont(new Font("Serif", Font.ITALIC, 14));
//									ySet += 50;
//									temp.add(sort);
//								}
//							}
//						}
////						else if(searchTitle.getText().isBlank() && searchGenre.getText().isBlank()) {
////							error = new JLabel("Type a title, genre, or both to search.");
////							error.setBounds(5, 460, 500, 100);
////							error.setFont(new Font("Serif", Font.ITALIC, 25));
////						}
//					}
//				});
				
//				search.addMouseListener(new MouseAdapter() {
//					public void mouseClicked(MouseEvent e) {
//						customerView.dispose();
//						drawSearch();
//					}
//					private void drawSearch() {
//						customerView = new JFrame("Sorting Movies");
//						customerView.setSize(600, 600);
//						customerView.setVisible(true);
//						customerView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//						
//						myP = new JPanel();
//						myP.setBackground(new Color(78, 189, 217));
//						
////						Set<Movies>temp = new HashSet<Movies>();
//						ArrayList<JLabel> temp = new ArrayList<JLabel>();
//						int ySet = 50;
//						if(!searchTitle.getText().isBlank() && searchGenre.getText().isBlank()) {
//							for(Movies m : allMovies) {
//								if(searchTitle.getText().equals(m.getTitle())) {
//									JLabel sort = new JLabel();
//									sort.setText(m.toString());
//									sort.setBounds(25, ySet, 200, 50);
//									sort.setBorder(border);
//									sort.setFont(new Font("Serif", Font.ITALIC, 14));
//									ySet += 50;
//									temp.add(sort);
////									myP.add(sort);
//								}
//							}
//						}
//						else if(searchTitle.getText().isBlank() && !searchGenre.getText().isBlank()) {
//							for(Movies m : allMovies) {
//								if(searchGenre.getText().equals(m.getGenre())) {
//									JLabel sort = new JLabel();
//									sort.setText(m.toString());
//									sort.setBounds(25, ySet, 200, 50);
//									sort.setBorder(border);
//									sort.setFont(new Font("Serif", Font.ITALIC, 14));
//									ySet += 50;
//									temp.add(sort);
//								}
//							}
//						}
//						if(searchTitle.getText().isBlank() && searchGenre.getText().isBlank()) {
//							JLabel error = new JLabel("Type a title, genre, or both to search.");
//							error.setBounds(5, 460, 500, 100);
//							error.setFont(new Font("Serif", Font.ITALIC, 25));
//						}
////						myP.setLayout(null);
////						customerView.add(myP);
//					}
//				});
				logout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						customer.dispose();
						try {
							LoginMain main = new LoginMain();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
				panel.add(search);
				panel.add(searchGenreLabel);
				panel.add(searchGenre);
				panel.add(searchTitleLabel);
				panel.add(searchTitle);
				panel.add(logout);
				panel.add(label);
				panel.setLayout(null);
				customer.add(panel);
			}
		});
		
		panel.add(logout);
		panel.add(viewHistory);
		panel.add(browseMovies);
		panel.add(viewTickets);
		panel.add(signOut);
		panel.add(deleteAccount);
		panel.setLayout(null);
		customer.add(panel);
	
	}
}