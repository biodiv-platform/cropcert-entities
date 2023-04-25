package cropcert.entities.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.entities.dao.UnionEntityDao;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.service.UnionEntitiesService;

public class UnionEntitiesServiceImpl implements UnionEntitiesService {

	private static final Logger logger = LoggerFactory.getLogger(UnionEntitiesServiceImpl.class);

	@Inject
	private UnionEntityDao unionDao;

	@Inject
	ObjectMapper objectMapper;

	@Override
	public UnionEntities getUnionById(Long id) {
		UnionEntities union = unionDao.findById(id);
		if (union == null) {
			return null;
		}
		return union;
	}

	@Override
	public UnionEntities getUnionByCode(Long code) {
		UnionEntities union = unionDao.findByPropertyWithCondition("code", code, "=");
		if (union == null) {
			return null;
		}
		return union;
	}

	@Override
	public List<UnionEntities> findAll() {
		List<UnionEntities> union = unionDao.findAll();
		if (union == null || union.isEmpty()) {
			return Collections.emptyList();
		}
		return union;
	}

	@Override
	public List<UnionEntities> findAll(Integer limit, Integer offset) {
		List<UnionEntities> union = unionDao.findAll(limit, offset);
		if (union == null || union.isEmpty()) {
			return Collections.emptyList();
		}
		return union;
	}

	@Override
	public UnionEntities createUnion(String jsonString) {
		UnionEntities union = null;
		try {
			union = objectMapper.readValue(jsonString, UnionEntities.class);
			unionDao.save(union);

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return union;
	}

	@Override
	public UnionEntities deleteUnion(Long id, Boolean code) {

		UnionEntities union = Boolean.TRUE.equals(code) ? getUnionByCode(id) : getUnionById(id);
		unionDao.delete(union);

		return null;
	}

}
