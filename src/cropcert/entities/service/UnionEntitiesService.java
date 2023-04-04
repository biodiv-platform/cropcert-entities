package cropcert.entities.service;

import java.util.List;

import cropcert.entities.model.UnionEntities;

public interface UnionEntitiesService {

	public UnionEntities getUnionById(Long id);

	public UnionEntities getUnionByCode(Long code);

	public List<UnionEntities> findAll();

	public List<UnionEntities> findAll(Integer limit, Integer offset);

	public UnionEntities createUnion(String jsonString);

	public UnionEntities deleteUnion(Long id , Boolean code);

}
