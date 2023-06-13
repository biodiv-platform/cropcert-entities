package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.CropcertEntity;

public class CropcertEntityDao extends AbstractDao<CropcertEntity, Long> {

	private static final Logger logger = LoggerFactory.getLogger(CropcertEntityDao.class);


	@Inject
	protected CropcertEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CropcertEntity findById(Long id) {
		Session session = sessionFactory.openSession();
		CropcertEntity entity = null;
		try {
			entity = session.get(CropcertEntity.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}

}
