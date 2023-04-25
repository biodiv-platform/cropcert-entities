package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.CollectionCenterPerson;

public class CollectionCenterPersonDao extends AbstractDao<CollectionCenterPerson, Long> {

	private static final Logger logger = LoggerFactory.getLogger(CollectionCenterPersonDao.class);

	@Inject
	protected CollectionCenterPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CollectionCenterPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		CollectionCenterPerson entity = null;
		try {
			entity = session.get(CollectionCenterPerson.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
}
