package entities;

import com.google.gson.JsonObject;
import utils.Jsonable;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category implements Jsonable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String cat;
	
	@OneToMany(mappedBy="category",fetch=FetchType.EAGER)
	List<Image> images;

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("id", this.getId());
		json.addProperty("cat", this.getCat());
		return json;
	}
	
	public Category() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
}
