package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;


import cropcert.entities.model.Inspector;

public class InspectorDao extends AbstractDao<Inspector, Long>{

	@Inject
	protected InspectorDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Inspector findById(Long id) {
		Session session = sessionFactory.openSession();
		Inspector entity = null;
		try {
			entity = session.get(Inspector.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
