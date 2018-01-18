package entities;

import com.google.gson.JsonObject;
import utils.Jsonable;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag implements Jsonable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String tag;
	
	@ManyToMany
	private List<Image> images;

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("id", this.getId());
		json.addProperty("tag", this.getTag());
		return json;
	}

	public Tag() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}

