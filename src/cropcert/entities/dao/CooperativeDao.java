package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

import cropcert.entities.model.Cooperative;

public class CooperativeDao extends AbstractDao<Cooperative, Long> {

	@Inject
	protected CooperativeDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Cooperative findById(Long id) {
		Session session = sessionFactory.openSession();
		Cooperative entity = null;
		try {
			entity = session.get(Cooperative.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
