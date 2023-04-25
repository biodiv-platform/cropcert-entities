package cropcert.entities.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.model.CollectionCenterEntity;

public class CollectionCenterEntityDao extends AbstractDao<CollectionCenterEntity, Long>{

	private static final Logger logger = LoggerFactory.getLogger(CollectionCenterEntityDao.class);

	@Inject
	protected CollectionCenterEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CollectionCenterEntity findById(Long id) {
	    try (Session session = sessionFactory.openSession()) {
	        return session.get(CollectionCenterEntity.class, id);
	    } catch (Exception e) {
			logger.error(e.getMessage());
	    }
		return null;
	}

	public CollectionCenterEntity findByName(String name, String code) {

		String queryStr = "" + "from CollectionCenter t where trim(lower(t.name)) = :name";
		Session session = sessionFactory.openSession();
		Query<CollectionCenterEntity> query = session.createQuery(queryStr, CollectionCenterEntity.class);
		query.setParameter("name", name.toLowerCase().trim());

		try {
			List<CollectionCenterEntity> ccs = query.getResultList();
			CollectionCenterEntity cc = null;
			if (ccs.size() > 1) {
				for (CollectionCenterEntity c : ccs) {
					if (c.getCode().toString().equals(code)) {
						cc = c;
						break;
					}
				}
			} else
				cc = ccs.get(0);
			return cc;
		} finally {
			session.close();
		}
	}


}
