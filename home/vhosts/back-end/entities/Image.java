package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String url;
	private String title;
	private int view;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private User user;
	
	@ManyToMany
	private List<Tag> tags;
	
	@ManyToMany(mappedBy="images",fetch=FetchType.EAGER)
	private List<Collection> collections;
	
	@OneToMany(mappedBy="image",fetch=FetchType.EAGER)
	List<ImageComment> imageComments;
	
	@OneToMany(mappedBy="image",fetch=FetchType.EAGER)
	List<ImageLike> imageLikes;
	
	@OneToMany(mappedBy="image",fetch=FetchType.EAGER)
	List<ImageView> imageViews;
	
	public Image() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	public List<ImageComment> getImageComments() {
		return imageComments;
	}

	public void setImageComments(List<ImageComment> imageComments) {
		this.imageComments = imageComments;
	}

	public List<ImageView> getImageViews() {
		return imageViews;
	}

	public void setImageViews(List<ImageView> imageViews) {
		this.imageViews = imageViews;
	}

	public List<ImageLike> getImageLikes() {
		return imageLikes;
	}

	public void setImageLikes(List<ImageLike> imageLikes) {
		this.imageLikes = imageLikes;
	}

}
