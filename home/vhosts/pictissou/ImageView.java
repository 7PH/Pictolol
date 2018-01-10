package entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ImageView {
	
	@EmbeddedId
	IdImageView id;
	
	@ManyToOne
    @JoinColumn(name="imageId", insertable=false, updatable=false, nullable=true)
	private Image image;
	
	public ImageView() {
		super();
	}

	public IdImageView getId() {
		return id;
	}

	public void setId(IdImageView id) {
		this.id = id;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
