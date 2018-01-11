package facades;

import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.*;

@Singleton
public class ImageCommentFacade {
	
	@PersistenceContext
	private EntityManager em;
	
	public int addImageComment(String comment,Date date){
		ImageComment ic=new ImageComment();
		ic.setComment(comment);
		ic.setDate(date);
		em.persist(ic);
		return ic.getId();
	}
	public void editImageComment(int id, String comment,Date date){
		ImageComment ic=em.find(ImageComment.class, id);
		ic.setComment(comment);
		ic.setDate(date);
		em.persist(ic);
	}
	public void deleteImageComment(int id){
		ImageComment ic=em.find(ImageComment.class, id);
		em.remove(ic);
	}
	public List<ImageComment> ImageComments(){
		TypedQuery<ImageComment> req = em.createQuery("FROM ImageComment ic",ImageComment.class);
		return req.getResultList();
	}
	
	public List<ImageComment> imageCommentsByUser(int id){
		User u = em.find(User.class, id);
		return u.getImageComments();
	}
	public void addImageCommentToUser(int idImageComment, int idUser){
		User u = em.find(User.class,idUser);
		ImageComment ic = em.find(ImageComment.class,idImageComment);
		ic.setUser(u);
	}
	public void deleteImageCommentFromUser(int idImageComment){
		ImageComment ic = em.find(ImageComment.class,idImageComment);
		ic.setUser(null);
	}
	
	public List<ImageComment> imageCommentsByImage(int id){
		Image i = em.find(Image.class, id);
		return i.getImageComments();
	}
	public void addCommentToImage(int idImageComment, int idImage){
		ImageComment ic = em.find(ImageComment.class,idImageComment);
		Image i = em.find(Image.class,idImage);
		ic.setImage(i);
	}
	public void deleteCommentFromImage(int idImageComment){
		ImageComment ic = em.find(ImageComment.class,idImageComment);
		ic.setImage(null);
	}
	
}
