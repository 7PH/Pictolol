package entities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.persistence.*;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String pseudo;
	private String email;
	private transient String password;
	private int right;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<Image> images;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<ImageComment> imageComments;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<Collection> collections;

	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	List<ImageLike> imageLikes;

    public static String hashPassword(String password) {
        password = "@{-" + password + "-}@";
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {}
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        return encoded.substring(0, encoded.lastIndexOf('='));
    }

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

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setImageLikes(List<ImageLike> imageLikes) {
		this.imageLikes = imageLikes;
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

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	public List<ImageLike> getImageLikes() {
		return imageLikes;
	}

	public void setImageViews(List<ImageLike> imageLikes) {
		this.imageLikes = imageLikes;
	}
}
