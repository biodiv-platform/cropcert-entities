package cropcert.entities.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cropcert.entities.model.CooperativeEntity;

public class CooperativeEntityDao extends AbstractDao<CooperativeEntity, Long>{
	
	@Inject
	protected CooperativeEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CooperativeEntity findById(Long id) {
		Session session = sessionFactory.openSession();
		CooperativeEntity entity = null;
		try {
			entity = session.get(CooperativeEntity.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
