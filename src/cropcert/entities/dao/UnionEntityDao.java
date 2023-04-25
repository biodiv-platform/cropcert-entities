package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.UnionEntities;

public class UnionEntityDao extends AbstractDao<UnionEntities, Long> {

	private static final Logger logger = LoggerFactory.getLogger(UnionEntityDao.class);

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
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
}