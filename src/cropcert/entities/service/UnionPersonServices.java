package cropcert.entities.service;

import java.util.List;

import cropcert.entities.model.UnionPerson;

public interface UnionPersonServices {

	UnionPerson getByUserId(Long id);

	List<UnionPerson> findAll();

	List<UnionPerson> findAll(Integer limit, Integer offset);

	UnionPerson create(String jsonString);

	UnionPerson deleteByUserId(Long id);

	List<UnionPerson> findByUnionId(Long unionCode, Integer limit, Integer offset, String orderBy);

}
