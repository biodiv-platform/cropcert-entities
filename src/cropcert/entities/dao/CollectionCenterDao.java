package cropcert.entities.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;


import cropcert.entities.model.CollectionCenter;

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

	public CollectionCenter findByName(String name, String code) {
		
		String queryStr = "" +
			    "from CollectionCenter t where trim(lower(t.name)) = :name";
		Session session = sessionFactory.openSession();
		Query<CollectionCenter> query = session.createQuery(queryStr, CollectionCenter.class);
		query.setParameter("name", name.toLowerCase().trim());
		
		try {
			List<CollectionCenter> ccs = query.getResultList();
			CollectionCenter cc = null;
			if(ccs.size() > 1) {
				for(CollectionCenter c : ccs) {
					if(c.getCode().toString().equals(code)) {
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
