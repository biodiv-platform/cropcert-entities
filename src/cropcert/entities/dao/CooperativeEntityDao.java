package cropcert.entities.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.model.CooperativeEntity;

public class CooperativeEntityDao extends AbstractDao<CooperativeEntity, Long> {

	private static final Logger logger = LoggerFactory.getLogger(CooperativeEntityDao.class);

	@Inject
	protected CooperativeEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CooperativeEntity findById(Long id) {
		Session session = sessionFactory.openSession();
		CooperativeEntity entity = null;
		try {
			entity = session.get(CooperativeEntity.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}

}
