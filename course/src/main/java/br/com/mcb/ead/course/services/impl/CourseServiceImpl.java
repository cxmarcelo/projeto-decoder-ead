package br.com.mcb.ead.course.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mcb.ead.course.models.CourseModel;
import br.com.mcb.ead.course.models.LessonModel;
import br.com.mcb.ead.course.models.ModuleModel;
import br.com.mcb.ead.course.repositories.CourseRepository;
import br.com.mcb.ead.course.repositories.LessonRepository;
import br.com.mcb.ead.course.repositories.ModuleRepository;
import br.com.mcb.ead.course.services.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private LessonRepository lessonRepository;

	@Transactional
	@Override
	public void delete(CourseModel courseModel) {
		List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());

		if(!moduleModelList.isEmpty()) {

			for(ModuleModel module: moduleModelList) {
				List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());

				if(!lessonModelList.isEmpty()) {
					lessonRepository.deleteAll(lessonModelList);
				}
			}

			moduleRepository.deleteAll(moduleModelList);
		}

		courseRepository.delete(courseModel);
	}

}
