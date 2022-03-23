import java.util.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Movies {
	private String title;
	private String genre;
	private String releaseDate;
//	private boolean upcoming;
	
	public Movies() {
//		upcoming = false;
		title = "";
		genre = "";
		releaseDate = "";
	}
	public Movies(String title, String genre, String releaseDate) {
		
		this.title = title;
		this.genre = genre;
		this.releaseDate = releaseDate;
//		this.upcoming = upcoming;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	@Override
	public String toString() {
		String str = title + " (" + genre + ") - " + releaseDate;
		return str;
	}
	
//	public boolean getUpcoming() {
//		return upcoming;
//	}
//	public void setUpcoming(boolean upcoming) {
////		String pattern;
//		
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu/MM/dd");
//		LocalDate today = LocalDate.now();
//		String dateStr = getReleaseDate();
//		Date d = 
//	}
}