package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.entities.model.UnionPerson;

public class UnionPersonDao extends AbstractDao<UnionPerson, Long>{

	@Inject
	protected UnionPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public UnionPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		UnionPerson entity = null;
		try {
			entity = session.get(UnionPerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
