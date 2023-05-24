package br.com.mcb.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mcb.ead.course.models.LessonModel;
import br.com.mcb.ead.course.models.ModuleModel;
import br.com.mcb.ead.course.repositories.LessonRepository;
import br.com.mcb.ead.course.repositories.ModuleRepository;
import br.com.mcb.ead.course.services.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private LessonRepository lessonRepository;

	@Transactional
	@Override
	public void delete(ModuleModel moduleModel) {
		List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());

		if(!lessonModelList.isEmpty()) {
			lessonRepository.deleteAll(lessonModelList);
		}

		moduleRepository.delete(moduleModel);
	}

	@Override
	public ModuleModel save(ModuleModel moduleModel) {
		return moduleRepository.save(moduleModel);
	}

	@Override
	public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
		return moduleRepository.findModuleIntoCourse(courseId, moduleId);
	}

	@Override
	public List<ModuleModel> findAllByCourse(UUID courseId) {
		return moduleRepository.findAllModulesIntoCourse(courseId);
	}

	@Override
	public Optional<ModuleModel> findById(UUID moduleId) {
		return moduleRepository.findById(moduleId);
	}

	@Override
	public Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable) {
		return moduleRepository.findAll(spec, pageable);
	}

}
