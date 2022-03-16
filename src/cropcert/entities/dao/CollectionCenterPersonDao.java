package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.entities.model.CollectionCenterPerson;

public class CollectionCenterPersonDao extends AbstractDao<CollectionCenterPerson, Long>{

	@Inject
	protected CollectionCenterPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CollectionCenterPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		CollectionCenterPerson entity = null;
		try {
			entity = session.get(CollectionCenterPerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
