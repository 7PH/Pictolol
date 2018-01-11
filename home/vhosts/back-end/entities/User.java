package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String pseudo;
	private String email;
	private String password;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<Image> images;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<ImageComment> imageComments;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<ImageLike> imageLikes;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<Collection> collections;

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<ImageComment> getImageComments() {
		return imageComments;
	}

	public void setImageComments(List<ImageComment> imageComments) {
		this.imageComments = imageComments;
	}
	
	public List<ImageLike> getImageLikes() {
		return imageLikes;
	}

	public void setImageLikes(List<ImageLike> imageLikes) {
		this.imageLikes = imageLikes;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}
	
}
