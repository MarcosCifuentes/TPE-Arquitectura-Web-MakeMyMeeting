package services;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import entities.Calendar;
import entities.User;

public class DAOCalendar {

	private static DAOCalendar daoCalendar;

	private DAOCalendar(){
	}

	public static DAOCalendar getInstance() {
		if(daoCalendar == null)
			daoCalendar = new DAOCalendar();
		return daoCalendar;
	}

	public Calendar createCalendar(String name, User user) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction( ).begin( );
		String jpql = "SELECT u FROM User u WHERE u.id = ?1"; 
		Query query = em.createQuery(jpql); 
		query.setParameter(1, user.getId());
		User newUser = (User) query.getSingleResult();
//		User newUser = DAOUser.getInstance().getUser(user.getId());
		Calendar newCalendar = new Calendar (name, newUser);
		em.persist(newCalendar);
		em.getTransaction().commit();
		em.close();
		return newCalendar;
	}

	public Calendar getCalendar(int idCalendar) {
		EntityManager em=EMF.createEntityManager();
		String jpql = "SELECT c FROM Calendar c WHERE c.id = ?1"; 
		Query query = em.createQuery(jpql); 
		query.setParameter(1, idCalendar);
		return (Calendar) query.getSingleResult();
	}
	
	public Calendar update(int id, String name, User user) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction().begin();		
		String jpql = "UPDATE Calendar SET name=?2, user=?3, WHERE Calendar.id = ?1"; 
		Query query = em.createQuery(jpql);
		query.setParameter(1, id);
		query.setParameter(2, name);
		query.setParameter(3, user);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
		Calendar calendar = getCalendar(id);

		return calendar;
	}

	public boolean delete(Integer id) {
		boolean deleted = false;

		EntityManager em=EMF.createEntityManager();
		em.getTransaction().begin();
		String jpql = "DELETE FROM Calendar c WHERE c.id = ?1"; 
		Query query = em.createQuery(jpql);
		query.setParameter(1, id);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
		Calendar calendar =getCalendar(id);
		if (calendar == null) {
			deleted = true;
		}	
		return deleted;
	}
}
