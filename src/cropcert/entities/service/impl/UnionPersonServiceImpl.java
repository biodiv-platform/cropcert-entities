package cropcert.entities.service.impl;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.entities.dao.UnionPersonDao;
import cropcert.entities.model.UnionPerson;
import cropcert.entities.service.UnionPersonServices;

public class UnionPersonServiceImpl implements UnionPersonServices {

	@Inject
	private UnionPersonDao unionPersonDao;

	@Inject
	ObjectMapper objectMapper;

	@Override
	public UnionPerson getByUserId(Long userId) {
		UnionPerson unionPerson = unionPersonDao.findByPropertyWithCondition("userId", userId, "=");
		return unionPerson;
	}

	@Override
	public List<UnionPerson> findAll() {
		List<UnionPerson> unionPerson = unionPersonDao.findAll();
		return unionPerson;
	}

	@Override
	public List<UnionPerson> findAll(Integer limit, Integer offset) {
		List<UnionPerson> unionPerson = unionPersonDao.findAll(limit, offset);
		return unionPerson;
	}

	@Override
	public UnionPerson create(String jsonString) {
		try {
			UnionPerson unionPerson = objectMapper.readValue(jsonString, UnionPerson.class);
			return unionPersonDao.save(unionPerson);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public UnionPerson deleteByUserId(Long userId) {
		UnionPerson unionPerson = getByUserId(userId);
		return unionPersonDao.delete(unionPerson);

	}

	@Override
	public List<UnionPerson> findByUnionId(Long unionCode, Integer limit, Integer offset, String orderBy) {
		List<UnionPerson> unionPerson = unionPersonDao.getByPropertyWithCondtion("unionCode", unionCode, "=", limit,
				limit, orderBy);
		return unionPerson;
	}

}
