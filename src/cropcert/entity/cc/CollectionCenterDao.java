package cropcert.entity.cc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.entity.common.AbstractDao;

public class CollectionCenterDao extends AbstractDao<CollectionCenter, Long>{

	@Inject
	protected CollectionCenterDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CollectionCenter findById(Long id) {
		Session session = sessionFactory.openSession();
		CollectionCenter entity = null;
		try {
			entity = session.get(CollectionCenter.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
