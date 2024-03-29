package cropcert.entities.service;

import javax.inject.Inject;

import cropcert.entities.dao.CropcertEntityDao;
import cropcert.entities.model.CropcertEntity;

public class CropcertEntityService extends AbstractService<CropcertEntity> {

	@Inject
	public CropcertEntityService(CropcertEntityDao cropcertEntityDao) {
		super(cropcertEntityDao);
	}
}
