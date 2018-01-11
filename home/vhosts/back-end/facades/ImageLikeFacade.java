package facades;

import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.*;

@Singleton
public class ImageLikeFacade {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addImageLike(Date date){
		ImageLike il=new ImageLike();
		il.setDate(date);
		em.persist(il);
	}
	public void editImageLike(int id, Date date){
		ImageLike il=em.find(ImageLike.class, id);
		il.setDate(date);
		em.persist(il);
	}
	public void deleteImageLike(int id){
		ImageLike il=em.find(ImageLike.class, id);
		em.remove(il);
	}
	public List<ImageLike> ImageLike(){
		TypedQuery<ImageLike> req = em.createQuery("FROM ImageLike il",ImageLike.class);
		return req.getResultList();
	}
	
	public List<ImageLike> imageLikesByUser(int id){
		User u = em.find(User.class, id);
		return u.getImageLikes();
	}
	public void addImageLikeToUser(int idImageLike, int idUser){
		User u = em.find(User.class,idUser);
		ImageLike il = em.find(ImageLike.class,idImageLike);
		il.setUser(u);
	}
	public void deleteImageLikeFromUser(int idImageLike){
		ImageLike il = em.find(ImageLike.class,idImageLike);
		il.setUser(null);
	}
	
	public List<ImageLike> imageLikesByImage(int id){
		Image i = em.find(Image.class, id);
		return i.getImageLikes();
	}
	public void addLikeToImage(int idImageLike, int idImage){
		ImageLike il = em.find(ImageLike.class,idImageLike);
		Image i = em.find(Image.class,idImage);
		il.setImage(i);
	}
	public void deleteLikeFromImage(int idImageLike){
		ImageLike il = em.find(ImageLike.class,idImageLike);
		il.setImage(null);
	}
	
}
