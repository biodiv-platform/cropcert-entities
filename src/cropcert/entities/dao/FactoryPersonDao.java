package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

import cropcert.entities.model.FactoryPerson;

public class FactoryPersonDao extends AbstractDao<FactoryPerson, Long> {

	@Inject
	protected FactoryPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FactoryPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		FactoryPerson entity = null;
		try {
			entity = session.get(FactoryPerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
