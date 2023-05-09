package br.com.mcb.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.mcb.ead.course.models.ModuleModel;

public interface ModuleService {

	public void delete(ModuleModel moduleModel);

	public ModuleModel save(ModuleModel moduleModel);

	public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

	public List<ModuleModel> findAllByCourse(UUID courseId);

	public Optional<ModuleModel> findById(UUID moduleId);

}
