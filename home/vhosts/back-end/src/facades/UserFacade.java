package facades;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	public User getUserById(int idUser){
		return em.find(User.class, idUser);
	}
	
	public List<Image> imagesByUser(int id){
		viewUpdate();
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
	
	public void viewUpdate(){
		String sql = "update Image i set i.view = (select count(im.imageId) from ImageView im where im.imageId=i.id)";
		em.createQuery(sql);
	}
	public User verifyUser(String pseudo, String password){
		Query req = em.createQuery("SELECT u FROM User u WHERE u.pseudo = ?1 AND u.password = ?2");
        req.setParameter(1, pseudo).setParameter(2, password);
        @SuppressWarnings("unchecked")
		List<User> list=req.getResultList();
		if(list.size()>0)return list.get(0);
		return null;
	}
	
}
