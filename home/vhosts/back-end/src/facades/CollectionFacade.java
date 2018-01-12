package facades;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.*;

@Singleton
public class CollectionFacade {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addCollection(String description){
		Collection c=new Collection();
		c.setDescription(description);
		em.persist(c);
	}
	public void editCollection(int id, String description){
		Collection c=em.find(Collection.class, id);
		c.setDescription(description);
		em.persist(c);
	}
	public void deleteCollection(int id){
		Collection c=em.find(Collection.class, id);
		em.remove(c);
	}
	public List<Collection> collections(){
		TypedQuery<Collection> req = em.createQuery("FROM Collection t",Collection.class);
		return req.getResultList();
	}
	public Collection getCollectionById(int idCollection){
		return em.find(Collection.class, idCollection);
	}
	
	public List<Collection> collectionsByUser(int id){
		User u = em.find(User.class, id);
		return u.getCollections();
	}
	public void addCollectionToUser(int idCollection, int idUser){
		User u = em.find(User.class,idUser);
		Collection c = em.find(Collection.class,idCollection);
		c.setUser(u);
	}
	public void deleteCollectionFromUser(int idCollection){
		Collection c = em.find(Collection.class,idCollection);
		c.setUser(null);
	}
	
	public List<Image> imagesByCollection(int id){
		Collection c = em.find(Collection.class, id);
		return c.getImages();
	}
	public void addImageToCollection(int idImage, int idCollection){
		Collection c = em.find(Collection.class,idCollection);
		Image i = em.find(Image.class,idImage);
		c.getImages().add(i);
	}
	public void deleteImageFromCollection(int idImage, int idCollection){
		Collection c = em.find(Collection.class,idCollection);
		Image i = em.find(Image.class,idImage);
		c.getImages().remove(i);
	}
	
}
