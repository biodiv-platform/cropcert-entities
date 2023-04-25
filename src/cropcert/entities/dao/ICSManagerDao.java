package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import cropcert.entities.model.ICSManager;

public class ICSManagerDao extends AbstractDao<ICSManager, Long> {

	private static final Logger logger = LoggerFactory.getLogger(ICSManagerDao.class);

	@Inject
	protected ICSManagerDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public ICSManager findById(Long id) {
		Session session = sessionFactory.openSession();
		ICSManager entity = null;
		try {
			entity = session.get(ICSManager.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
}
