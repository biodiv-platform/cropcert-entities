package cropcert.entity.farm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.entity.common.AbstractDao;

public class FarmDao extends AbstractDao<Farm, Long>{

	@Inject
	protected FarmDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Farm findById(Long id) {
		Session session = sessionFactory.openSession();
		Farm entity = null;
		try {
			entity = session.get(Farm.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
