package cropcert.entities.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import cropcert.entities.model.Farmer;

public class FarmerDao extends AbstractDao<Farmer, Long> {

	@Inject
	protected FarmerDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Farmer findById(Long id) {
		Session session = sessionFactory.openSession();
		Farmer entity = null;
		try {
			entity = session.get(Farmer.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Farmer> getFarmerForMultipleCollectionCenter(List<Long> ccCodesLong, String firstName, Integer limit,
			Integer offset) {

		String ccCodesString = "(";
		for (Long farmerId : ccCodesLong) {
			ccCodesString += farmerId + ",";
		}
		ccCodesString += "-1)";

		String queryStr = " from " + daoType.getSimpleName() + " t " + " where ccCode in " + ccCodesString
				+ (firstName == null || "".equals(firstName) ? "" : " and firstName like :firstName");

		Session session = sessionFactory.openSession();
		Query query = session.createQuery(queryStr, Farmer.class);
		if (firstName != null)
			query.setParameter("firstName", "%" + firstName + "%");

		try {
			List<Farmer> result = (List<Farmer>) query.getResultList();
			return result;
		} catch (NoResultException e) {
			throw e;
		} finally {
			session.close();
		}
	}
}
