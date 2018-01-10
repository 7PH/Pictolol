package facades;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.*;

@Singleton
public class UserFacade {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addUser(String pseudo, String email, String password){
		User u=new User();
		u.setPseudo(pseudo);
		u.setEmail(email);
		u.setPassword(password);
		em.persist(u);
	}
	public void editUser(int id, String pseudo, String email, String password){
		User u=em.find(User.class, id);
		u.setPseudo(pseudo);
		u.setEmail(email);
		u.setPassword(password);
		em.persist(u);
	}
	public void deleteUser(int id){
		User u = em.find(User.class, id);
		em.remove(u);
	}
	public List<User> users(){
		TypedQuery<User> req = em.createQuery("FROM User u",User.class);
		return req.getResultList();
	}
	
	public List<Image> imagessByUser(int id){
		User u = em.find(User.class, id);
		return u.getImages();
	}
	public void addImageToUser(int idImage, int idUser){
		User u = em.find(User.class,idUser);
		Image i = em.find(Image.class,idImage);
		i.setUser(u);
	}
	public void deleteImageFromUser(int idImage){
		Image i = em.find(Image.class,idImage);
		i.setUser(null);
	}
	
}
