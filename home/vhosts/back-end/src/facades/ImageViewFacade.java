package facades;

import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.*;

@Singleton
public class ImageViewFacade {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addImageView(String ip, Date date, int imageId){
		ImageView iv=new ImageView();
		IdImageView idi=new IdImageView();
		idi.setDate(date);
		idi.setImageId(imageId);
		idi.setIp(ip);
		iv.setId(idi);
		//Image i=em.find(Image.class, imageId);
		//iv.setImage(i);
		em.persist(iv);
	}
	public void editImageView(int id, String ip, Date date){
		ImageView iv=em.find(ImageView.class, id);
		IdImageView idi=iv.getId();
		idi.setDate(date);
		idi.setIp(ip);
		iv.setId(idi);
		em.persist(iv);
	}
	public void deleteImageView(int id){
		ImageView iv=em.find(ImageView.class, id);
		em.remove(iv);
	}
	public List<ImageView> imageViews(){
		TypedQuery<ImageView> req = em.createQuery("FROM ImageView iv",ImageView.class);
		return req.getResultList();
	}
	
	public List<ImageView> imageViewsByImage(int id){
		Image i=em.find(Image.class, id);
		return i.getImageViews();
	}
	public void addImageViewToImage(int idImageView, int idImage){
		Image i=em.find(Image.class, idImage);
		ImageView iv=em.find(ImageView.class, idImageView);
		iv.setImage(i);
	}
	public void deleteImageViewFromImage(int idImageView){
		ImageView iv=em.find(ImageView.class, idImageView);
		iv.setImage(null);
	}
	public void deleteImageViewFromImageBeforeDate(int idImage, Date date){
		String sql = "delete FROM ImageView iv where iv.id.imageId="+idImage+" and iv.id.date<="+date;
		em.createQuery(sql);
	}
	public int isView(String ip,int idImage){
		Query req = em.createQuery("select count(iv.id.ip) FROM ImageView iv where iv.id.imageId="+idImage+" and iv.id.ip="+ip);
		@SuppressWarnings("unchecked")
		List<Long> list=req.getResultList();
		if(list.size()>0)return (new Long((Long)list.get(0))).intValue();
		return 0;
	}
	
}

