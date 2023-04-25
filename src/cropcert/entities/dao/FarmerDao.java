package cropcert.entities.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.model.Farmer;
import java.util.Collections;

public class FarmerDao extends AbstractDao<Farmer, Long> {

	private static final Logger logger = LoggerFactory.getLogger(FarmerDao.class);

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
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Farmer> getFarmerForMultipleCollectionCenter(List<Long> ccCodesLong, String firstName, Integer limit,
			Integer offset) {

		StringBuilder ccCodesStringBuilder = new StringBuilder("(");
		for (Long farmerId : ccCodesLong) {
			ccCodesStringBuilder.append(farmerId).append(",");
		}
		ccCodesStringBuilder.append("-1)");
		String ccCodesString = ccCodesStringBuilder.toString();

		String queryStr = " from " + daoType.getSimpleName() + " t " + " where ccCode in " + ccCodesString
				+ (firstName == null || "".equals(firstName) ? "" : " and firstName like :firstName");

		Session session = sessionFactory.openSession();
		Query query = session.createQuery(queryStr, Farmer.class);

		try {
			if (StringUtils.isNotBlank(firstName)) {
				query.setParameter("firstName", "%" + firstName + "%");
			}
			if (limit != null && limit != -1) {
				query.setMaxResults(limit);
			}
			if (offset != null && limit != -1) {
				query.setFirstResult(offset);
			}
			return query.getResultList();
		} catch (NoResultException e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return Collections.emptyList();

	}
}
