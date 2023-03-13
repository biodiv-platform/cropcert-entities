package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

import cropcert.entities.model.Union;

public class UnionDao extends AbstractDao<Union, Long> {

	@Inject
	protected UnionDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Union findById(Long id) {
		Session session = sessionFactory.openSession();
		Union entity = null;
		try {
			entity = session.get(Union.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
