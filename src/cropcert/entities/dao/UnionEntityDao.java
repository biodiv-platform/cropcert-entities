package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

import cropcert.entities.model.UnionEntities;

public class UnionEntityDao extends AbstractDao<UnionEntities, Long> {

	@Inject
	protected UnionEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public UnionEntities findById(Long id) {
		Session session = sessionFactory.openSession();
		UnionEntities entity = null;
		try {
			entity = session.get(UnionEntities.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}