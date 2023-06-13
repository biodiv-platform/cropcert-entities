package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.Inspector;

public class InspectorDao extends AbstractDao<Inspector, Long> {

	private static final Logger logger = LoggerFactory.getLogger(InspectorDao.class);

	@Inject
	protected InspectorDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Inspector findById(Long id) {
		Session session = sessionFactory.openSession();
		Inspector entity = null;
		try {
			entity = session.get(Inspector.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
}
