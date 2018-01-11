package facades;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.*;

@Singleton
public class TagFacade {
	
	@PersistenceContext
	private EntityManager em;
	
	public int addTag(String tag){
		Tag t=new Tag();
		t.setTag(tag);
		em.persist(t);
		return t.getId();
	}
	public void editTag(int id, String tag){
		Tag t=em.find(Tag.class, id);
		t.setTag(tag);
		em.persist(t);
	}
	public void deleteTag(int id){
		Tag t=em.find(Tag.class, id);
		em.remove(t);
	}
	public List<Tag> tags(){
		TypedQuery<Tag> req = em.createQuery("FROM Tag t",Tag.class);
		return req.getResultList();
	}
	
	public List<Tag> tagsByImage(int id){
		Image i = em.find(Image.class, id);
		return i.getTags();
	}
	public List<Image> imagesByTag(int id){
		Tag t = em.find(Tag.class, id);
		return t.getImages();
	}
	public void addTagToImage(int idTag, int idImage){
		Tag t = em.find(Tag.class,idTag);
		Image i = em.find(Image.class,idImage);
		i.getTags().add(t);
	}
	public void deleteTagFromImage(int idTag, int idImage){
		Tag t = em.find(Tag.class,idTag);
		Image i = em.find(Image.class,idImage);
		i.getTags().remove(t);
	}
	
}
