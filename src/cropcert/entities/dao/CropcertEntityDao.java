package cropcert.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

import cropcert.entities.model.CropcertEntity;

public class CropcertEntityDao extends AbstractDao<CropcertEntity, Long> {

	@Inject
	protected CropcertEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CropcertEntity findById(Long id) {
		Session session = sessionFactory.openSession();
		CropcertEntity entity = null;
		try {
			entity = session.get(CropcertEntity.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
