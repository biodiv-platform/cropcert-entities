package cropcert.entities.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.dao.AbstractDao;

public abstract class AbstractService<T> {

	protected Class<T> entityClass;
	protected AbstractDao<T, Long> dao;

	private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

	@SuppressWarnings("unchecked")
	protected AbstractService(AbstractDao<T, Long> dao) {
		this.dao = dao;
		entityClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	public T save(T entity) {
		try {
			this.dao.save(entity);
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
		}
		return entity;

	}

	public T update(T entity) {
		try {
			this.dao.update(entity);
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
		}
		return entity;

	}

	public T delete(Long id) {
		T entity = this.dao.findById(id);
		try {
			this.dao.delete(entity);
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
		}
		return entity;

	}

	public T findById(Long id) {
		T entity = null;
		try {
			entity = this.dao.findById(id);

		} catch (RuntimeException re) {
			logger.error(re.getMessage());
		}
		return entity;

	}

	public List<T> findAll(int limit, int offset) {
		List<T> entities = null;

		try {
			entities = this.dao.findAll(limit, offset);

		} catch (RuntimeException re) {
			logger.error(re.getMessage());
		}
		return entities;

	}

	public List<T> findAll() {
		List<T> entities = null;

		try {
			entities = this.dao.findAll();

		} catch (RuntimeException re) {
			logger.error(re.getMessage());
		}
		return entities;

	}

	public T findByPropertyWithCondition(String property, Object value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}

	public List<T> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset,
			String orderBy) {
		return dao.getByPropertyWithCondtion(property, value, condition, limit, offset, orderBy);
	}

	public List<T> getByMultiplePropertyWithCondtion(String[] properties, Object[] values, Integer limit,
			Integer offset) {
		return dao.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
	}

	public T deleteByPropertyWithCondition(String property, Object value, String condition) {
		return dao.deleteByProperty(property, value, condition);
	}

	public List<T> getByMultipleValuesWithCondition(String property, List<Long> values, Integer limit, Integer offset) {
		return dao.getByMultipleValuesWithCondition(property, values, limit, offset);
	}

}
