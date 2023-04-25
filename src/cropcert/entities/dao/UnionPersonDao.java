package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.UnionPerson;

public class UnionPersonDao extends AbstractDao<UnionPerson, Long> {

	private static final Logger logger = LoggerFactory.getLogger(UnionPersonDao.class);

	@Inject
	protected UnionPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public UnionPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		UnionPerson entity = null;
		try {
			entity = session.get(UnionPerson.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
}
