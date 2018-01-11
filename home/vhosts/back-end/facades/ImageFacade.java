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
	public Category getCategoryById(int idCategory){
		return em.find(Category.class, idCategory);
	}
	
	public void ajoutImage(String url, String title, int idCat, int idUser){
		Image i=new Image();
		i.setUrl(url);
		i.setTitle(title);
		em.persist(i);
		int idi=i.getId();
		addImageToUser(idi,idUser);
		addImageToCategory(idi,idCat);
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
	public void viewUpdate(){
		String sql = "update Image i set i.view=(select count(im.imageId) from ImageView im where im.imageId=i.id)";
		em.createQuery(sql);
	}
	public void viewUpdateById(int idImage){
		String sql = "update Image i set i.view=(select count(im.imageId) from ImageView im where im.imageId"+idImage+")";
		em.createQuery(sql);
	}
	public List<Image> images(){
		viewUpdate();
		TypedQuery<Image> req = em.createQuery("FROM Image i",Image.class);
		return req.getResultList();
	}
	public Image getImageById(int idImage){
		viewUpdateById(idImage);
		return em.find(Image.class, idImage);
	}
	
	public List<Image> imagesByCat(int id){
		viewUpdate();
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
	
	public void addImageToUser(int idImage, int idUser){
		User u = em.find(User.class,idUser);
		Image i = em.find(Image.class,idImage);
		i.setUser(u);
	}
	
	public List<ImageComment> imageCommentsByImage(int id){
		Image i = em.find(Image.class, id);
		return i.getImageComments();
	}
	
	public List<Tag> tagsByImage(int id){
		Image i = em.find(Image.class, id);
		return i.getTags();
	}
	
	public int nbrLikesByImage(int idImage){
		Image i = em.find(Image.class, idImage);
		return i.getImageLikes().size();
	}
	
	public User getUserById(int idUser){
		return em.find(User.class, idUser);
	}
	
	public List<Image> searchByTitle(String chaine){
		TypedQuery<Image> req = em.createQuery("FROM Image i where i.title like '%"+chaine+"%'",Image.class);
		return req.getResultList();
	}
	
}
