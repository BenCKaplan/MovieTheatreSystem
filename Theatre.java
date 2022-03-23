import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

/*
 *  Class used for Admin functionality
 */
public class Theatre {

	private JPanel panel;
	public static Set<Movies> allMovies;
	public static Set<String> allMovieTitles;
	private JFrame theatre;
	private File movieDataFile;
	
	public Theatre() {
		allMovies = new HashSet<Movies>();
		allMovieTitles = new HashSet<String>();
		movieDataFile = new File("Movie Data.txt");
		try {
			loadData(movieDataFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		initializeFrame();
	}
	public static Set<Movies> getAllMovies() {
		return allMovies;
	}
	
	public static Set<String> getAllMovieTitles() {
		return allMovieTitles;
	}
	
	public boolean addMovie(Movies data) throws IOException{
		if(!moviePlaying(data.getTitle())) {
			FileWriter fr = new FileWriter(movieDataFile, true);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter pr = new PrintWriter(br);

			pr.printf("%s, %s, %s", data.getTitle(), data.getGenre(), data.getReleaseDate());

			pr.close();
			fr.close();
			br.close();
			allMovies.add(data);
			allMovieTitles.add(data.getTitle());
			return true;	
		}
		return false;
	}
	
	public void printMovies() {
		for(Movies info : allMovies) {
			
			System.out.println(info.getTitle());
			System.out.println(info.getGenre());
			System.out.println(info.getReleaseDate());
		}
	}
	
	public static boolean moviePlaying(String movieTitle) {
		return allMovieTitles.contains(movieTitle);
	}
	
	public static Movies getMovieTitle(String movieTitle) {
		for(Movies info: allMovies) {
			if(info.getTitle().equals(movieTitle))
				return info;
		}
		return null;
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
	
	public static void updateData(File myFile) throws IOException {
		File temp = new File("temp.txt");
		FileWriter fw = new FileWriter(temp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		for(Movies data: allMovies) {
			pw.printf("%s\t%s\t%s%n",data.getTitle(),data.getGenre(), data.getReleaseDate());
		}
		pw.close();
		bw.close();
		fw.close();
		myFile.delete();
		System.out.println(temp.renameTo(myFile));
	}
	
	private void initializeFrame() {
		theatre = new JFrame("Admin view");
		theatre.setSize(600,600);
		theatre.setVisible(true);
		theatre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		JLabel label = new JLabel("Admin View");
		JButton logout = new JButton("Log out");
		
		panel.setBackground(new Color(14,133,201));
		
		label.setFont(new Font("Times New Roman",Font.ITALIC,25));
		label.setBounds(190,0,250,50);
		
		logout.setBounds(450,400,100,100);
		
		logout.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
			    theatre.dispose();
			    try {
			    	LoginMain login = new LoginMain();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} 
		});
		addOptions();
		panel.add(label);
		panel.add(logout);
		panel.setLayout(null);
		theatre.add(panel);
	}
	
	private void addOptions() {
		Font myFont = new Font("Times New Roman",Font.ITALIC,30);
		Cursor myCursor = new Cursor(Cursor.HAND_CURSOR);
		
		JLabel postMovies = new JLabel("Post New Movie");
		JLabel editExistingMovie = new JLabel("Edit Existing Movie");
		JLabel signOut = new JLabel("Sign Out");
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
		postMovies.setBounds(25,100,300,100);
		editExistingMovie.setBounds(25,300,300,100);

		postMovies.setBorder(border);
		editExistingMovie.setBorder(border);
		
		postMovies.setFont(myFont);
		editExistingMovie.setFont(myFont);
		
		postMovies.setCursor(myCursor);
		editExistingMovie.setCursor(myCursor);

		postMovies.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				theatre.dispose();
				drawPostMovies();
			}
			private void drawPostMovies() {
				theatre = new JFrame("Posting Movies");
				theatre.setSize(600,600);
				theatre.setVisible(true);
				theatre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				panel = new JPanel();
				panel.setBackground(new Color(78, 189, 217));
				
				JLabel label = new JLabel("Posting Movies");
				JLabel titleLabel = new JLabel("Movie title");
				JLabel genreLabel = new JLabel("Genre");
				JLabel rDateLabel = new JLabel("Release Date");
				JLabel movieAdded = new JLabel("");
				
				JTextField titleEntry = new JTextField();
				JTextField genreEntry = new JTextField();
				JTextField rDateEntry = new JTextField();
				
				JButton addMovieButton = new JButton("Continue?");
				JButton logout = new JButton("Log out");
				
				label.setFont(new Font("Times New Roman", Font.ITALIC, 25));
				label.setBounds(190, 0, 250, 50);
				titleLabel.setBounds(25, 140, 150, 30);
				genreLabel.setBounds(25, 200, 150, 30);
				rDateLabel.setBounds(25, 260, 150, 30);
				
				movieAdded.setBounds(5, 460, 600, 100);
				logout.setBounds(450, 400, 100, 100);
				
				titleEntry.setLocation(200, 140);
				titleEntry.setSize(200, 25);
				titleEntry.setHorizontalAlignment(JTextField.HORIZONTAL);
				
				genreEntry.setLocation(200, 200);
				genreEntry.setSize(200, 25);
				genreEntry.setHorizontalAlignment(JTextField.HORIZONTAL);
				
				rDateEntry.setLocation(200, 260);
				rDateEntry.setSize(200, 25);
				rDateEntry.setHorizontalAlignment(JTextField.HORIZONTAL);
				
				addMovieButton.setBounds(450, 200, 100, 100);
				logout.setBounds(450, 400, 100, 100);
				logout.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						theatre.dispose();
						try {
							LoginMain main = new LoginMain();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				addMovieButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String movieTitle = titleEntry.getText();
						String movieGenre = genreEntry.getText();
						String movieDate = rDateEntry.getText();
						Movies movie = new Movies();

						if(movieTitle.isBlank() || movieGenre.isBlank() || movieDate.isBlank()) {
							movieAdded.setText("Make sure all details are displayed!");
							movieAdded.setFont(new Font("Times New Roman", Font.ITALIC, 25));
						}
						else {
							movieAdded.setText(movieTitle + "(" + movieGenre + ") - " + movieDate + " has been added.");
							movieAdded.setFont(new Font("Times New Roman", Font.ITALIC, 25));
						}

						movieTitle = movieTitle.replaceAll(" ", "_");
						
						movie.setTitle(movieTitle);
						allMovieTitles.add(movieTitle);
						movie.setGenre(movieGenre);
						movie.setReleaseDate(movieDate);
						allMovies.add(movie);
						
						try {
							Login log = new Login();
							log.writeToMovieFile(movieTitle, movieGenre, movieDate);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						titleEntry.setText("");
						genreEntry.setText("");
						rDateEntry.setText("");
					}
				});
				panel.add(label);
				panel.add(titleLabel);
				panel.add(titleEntry);
				panel.add(genreLabel);
				panel.add(genreEntry);
				panel.add(rDateLabel);
				panel.add(rDateEntry);
				panel.add(addMovieButton);
				panel.add(logout);
				panel.add(movieAdded);
				panel.setLayout(null);
				theatre.add(panel);
			}
		});
		editExistingMovie.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					theatre.dispose();
					drawEditMovies();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			private void drawEditMovies() throws FileNotFoundException {
//				loadData(movieDataFile);
//				Login log = new Login();
//				log.loadData();
//				boolean exists = moviePlaying();
				
				loadData(movieDataFile);
//				System.out.println(allMovies.size());

				theatre = new JFrame("Editing Existing Movies");
				theatre.setSize(600, 600);
				theatre.setVisible(true);
				theatre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				panel = new JPanel();
				panel.setBackground(new Color(78, 189, 217));
								
				JLabel label = new JLabel("Editing Existing Movies");
				JLabel text = new JLabel("Enter the name of the movie you want to edit");
				JTextField movieToEdit = new JTextField();
				
				JButton logout = new JButton("Log out");
				JButton back = new JButton("Back");
				JButton confirm = new JButton("Continue?");

				label.setFont(new Font("Times New Roman", Font.ITALIC, 25));
				label.setBounds(190, 0, 250, 50);
				
				text.setFont(new Font("Times New Roman", Font.ITALIC, 15));
				text.setBounds(150, 375, 400, 50);
				movieToEdit.setBounds(190, 410, 200, 30);

				confirm.setBounds(100, 450, 100, 100);
				back.setBounds(250, 450, 100, 100);
				logout.setBounds(400, 450, 100, 100);

				ArrayList<JLabel> labels = new ArrayList<JLabel>();
				for(Movies m : allMovies) {
//					m.setTitle(m.getT);
					String title = m.getTitle();
//					title = title.replaceAll("_", " ");
					m.setTitle(title);
					labels.add(new JLabel(m.toString()));
				}
				int ySet = 50;
				for(int i = 0; i < labels.size(); i++) {
					JLabel add = labels.get(i);
					
					add.setBounds(100, ySet, 400, 50);
					add.setBorder(border);
					add.setFont(new Font("Times New Roman", Font.ITALIC, 20));
					ySet = ySet+50;
					
					panel.add(add);
				}
				logout.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						theatre.dispose();
						try {
							LoginMain main = new LoginMain();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						theatre.dispose();
						Theatre login = new Theatre();
					}
				});

				confirm.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						String userInput = movieToEdit.getText();
						for(Movies m : allMovies) {
							String title = m.getTitle();
							if(!userInput.toLowerCase().equals(title.toLowerCase())) {
								JLabel invalid = new JLabel("Invalid title input");
								movieToEdit.setText("");
							}
							else {
								theatre.dispose();
								drawEditMovie(userInput);
							}
						}
					}
					private void drawEditMovie(String userInput) {
						theatre = new JFrame("Editing " + userInput);
						theatre.setSize(600, 600);
						theatre.setVisible(true);
						theatre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
						panel = new JPanel();
						panel.setBackground(new Color(78, 189, 217));
						
						
						String genre = "";
						String releaseDate = "";
						String title = "";
						
						for(Movies m : allMovies) {
							if(m.getTitle().toLowerCase().equals(userInput.toLowerCase())) {
								title = m.getTitle();
								genre = m.getGenre();
								releaseDate = m.getReleaseDate();
								break;
							}
						}
						
						JLabel label = new JLabel("Editing " + title);
						JLabel titleLabel = new JLabel("Title"); //These should be textFields
						JLabel genreLabel = new JLabel("Genre");
						JLabel releaseDateLabel = new JLabel("Release date");
						String tempTitle = title.replaceAll("_", " ");
						JTextField titleField = new JTextField(tempTitle);
						JTextField genreField = new JTextField(genre);
						JTextField releaseField = new JTextField(releaseDate);
						
						JButton edit = new JButton("Continue");
						JButton logout = new JButton("Log Out");
						JButton remove = new JButton("Remove");
						
						label.setFont(myFont);
						label.setBounds(190, 0, 250, 50);
						
						titleLabel.setBounds(25, 140, 150, 30);
						genreLabel.setBounds(25, 200, 150, 30);
						releaseDateLabel.setBounds(25, 260, 150, 30);
						
						titleField.setLocation(200, 140);
						titleField.setSize(200, 25);
						titleField.setHorizontalAlignment(JTextField.HORIZONTAL);

						genreField.setLocation(200, 200);
						genreField.setSize(200, 25);
						genreField.setHorizontalAlignment(JTextField.HORIZONTAL);
						
						releaseField.setLocation(200, 260);
						releaseField.setSize(200, 25);
						releaseField.setHorizontalAlignment(JTextField.HORIZONTAL);
						
						edit.setBounds(450, 200, 100, 100);
						logout.setBounds(450, 400, 100, 100);
						remove.setBounds(450, 0, 100, 100);
						
						logout.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								theatre.dispose();
								try {
									LoginMain main = new LoginMain();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						
						
						edit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String titleOrig = titleField.getText().replaceAll(" ", "_");
								titleOrig = titleOrig.replaceAll(" ", "_");
								System.out.println(titleOrig);
								String genreOrig = genreField.getText();
								System.out.println(genreOrig);
								String dateOrig = releaseField.getText();
								System.out.println(dateOrig);
								
								for(Movies m : allMovies) {
									if(m.getTitle().toLowerCase().equals(userInput.toLowerCase())) {
										titleOrig = titleOrig.replaceAll("_", " ");
										m.setTitle(titleOrig);
										m.setGenre(genreOrig);
										m.setReleaseDate(dateOrig);
										break;
									}
								}
								
								try {
									updateData(movieDataFile);
									theatre.dispose();
									Theatre back = new Theatre();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								logout.setBounds(0, 400, 100, 100);

								logout.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										theatre.dispose();
										try {
											LoginMain main = new LoginMain();
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								});
							}
						});
						
						remove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								for(Movies m : allMovies) {
									if(m.getTitle().toLowerCase().equals(userInput.toLowerCase())) {
										allMovies.remove(m);
										break;
									}
								}
								try {
									updateData(movieDataFile);
									theatre.dispose();
									Theatre back = new Theatre();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
						panel.add(remove);
						panel.add(label);
						panel.add(titleLabel);
						panel.add(genreLabel);
						panel.add(releaseDateLabel);
						panel.add(titleField);
						panel.add(genreField);
						panel.add(releaseField);
						panel.add(edit);
						panel.add(logout);
						panel.setLayout(null);
					}
				});
				panel.add(logout);
				panel.add(back);
				panel.add(confirm);
				panel.add(movieToEdit);
				panel.add(label);
				panel.add(text);
				panel.setLayout(null);
				theatre.add(panel);
			}
		});
		panel.add(postMovies);
		panel.add(editExistingMovie);
		panel.add(signOut);
		panel.setLayout(null);
		theatre.add(panel);
	}
}