package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;


import cropcert.entities.model.ICSManager;

public class ICSManagerDao extends AbstractDao<ICSManager, Long> {

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
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
