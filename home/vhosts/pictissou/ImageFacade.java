package facades;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.*;

@Singleton
public class ImageFacade {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addCategory(String cat){
		Category c=new Category();
		c.setCat(cat);
		em.persist(c);
	}
	public void editCategory(int id, String cat){
		Category c=em.find(Category.class, id);
		c.setCat(cat);
		em.persist(c);
	}
	public void deleteCategory(int id){
		Category c=em.find(Category.class, id);
		em.remove(c);
	}
	public List<Category> categories(){
		TypedQuery<Category> req = em.createQuery("FROM Category c",Category.class);
		return req.getResultList();
	}
	
	public void ajoutImage(String url, String title, int idCat, int idUSer){
		Image i=new Image();
		i.setUrl(url);
		i.setTitle(title);
		Category c=em.find(Category.class, idCat);
		c.getImages().add(i);
		User u=em.find(User.class, idUSer);
		u.getImages().add(i);
		em.persist(i);
	}
	public void editImage(int id, String url, String title){
		Image i=em.find(Image.class, id);
		i.setUrl(url);
		i.setTitle(title);
		em.persist(i);
	}
	public void deleteImage(int id){
		Image i=em.find(Image.class, id);
		em.remove(i);
	}
	public List<Image> images(){
		TypedQuery<Image> req = em.createQuery("FROM Image i",Image.class);
		return req.getResultList();
	}
	
	public List<Image> imagesByCat(int id){
		Category c=em.find(Category.class, id);
		return c.getImages();
	}
	public void addImageToCategory(int idImage, int idCategory){
		Image i=em.find(Image.class, idImage);
		Category c=em.find(Category.class, idCategory);
		i.setCategory(c);
	}
	public void deleteImageFromCategory(int idImage){
		Image i=em.find(Image.class, idImage);
		i.setCategory(null);
	}
	
}
