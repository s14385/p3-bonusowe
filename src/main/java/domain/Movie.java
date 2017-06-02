package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@NamedQueries({
	
	@NamedQuery(name="movie.all", query="SELECT m FROM Movie m"),
	@NamedQuery(name="movie.id", query="FROM Movie m WHERE m.id=:movieId")
})
public class Movie{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String production;
	private int releaseYear;
	private int boxOffice;
	private List <Comment> comments = new ArrayList <Comment>();
	private List <Integer> rating = new ArrayList <Integer>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProduction() {
		return production;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	public int getBoxOffice() {
		return boxOffice;
	}
	public void setBoxOffice(int boxOffice) {
		this.boxOffice = boxOffice;
	}
	@XmlTransient
	@OneToMany(mappedBy="movie")
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public int AverageRating(){
		
		if(rating == null){
			
			return 0;
		}
		
		int avg = 0;
		
		for(int value : rating){
			
			avg += value;
		}
		
		avg /= rating.size();
		
		return avg;
	}
}