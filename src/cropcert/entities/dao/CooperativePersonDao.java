package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.CooperativePerson;

public class CooperativePersonDao extends AbstractDao<CooperativePerson, Long> {

	private static final Logger logger = LoggerFactory.getLogger(CooperativePersonDao.class);

	@Inject
	protected CooperativePersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CooperativePerson findById(Long id) {
		Session session = sessionFactory.openSession();
		CooperativePerson entity = null;
		try {
			entity = session.get(CooperativePerson.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
}
