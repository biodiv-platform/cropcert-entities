package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.FactoryPerson;

public class FactoryPersonDao extends AbstractDao<FactoryPerson, Long> {

	private static final Logger logger = LoggerFactory.getLogger(FactoryPersonDao.class);

	@Inject
	protected FactoryPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FactoryPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		FactoryPerson entity = null;
		try {
			entity = session.get(FactoryPerson.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
}
