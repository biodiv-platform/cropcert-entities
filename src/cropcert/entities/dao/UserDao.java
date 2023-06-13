package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

import cropcert.entities.model.User;

public class UserDao extends AbstractDao<User, Long> {

	@Inject
	protected UserDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public User findById(Long id) {
		Session session = sessionFactory.openSession();
		User entity = null;
		try {
			entity = session.get(User.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
