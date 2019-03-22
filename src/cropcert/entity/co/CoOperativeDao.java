package cropcert.entity.co;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.entity.common.AbstractDao;

public class CoOperativeDao extends AbstractDao<CoOperative, Long>{

	@Inject
	protected CoOperativeDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CoOperative findById(Long id) {
		Session session = sessionFactory.openSession();
		CoOperative entity = null;
		try {
			entity = session.get(CoOperative.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
