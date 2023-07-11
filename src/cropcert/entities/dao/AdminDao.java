package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.model.Admin;

public class AdminDao extends AbstractDao<Admin, Long> {
	private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);

	@Inject
	protected AdminDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Admin findById(Long id) {
		Session session = sessionFactory.openSession();
		Admin entity = null;
		try {
			entity = session.get(Admin.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			session.close();
		}
		return entity;
	}
}
