package br.com.mcb.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		System.out.println("Testando antes de chamar");
		List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
		System.out.println("Testando depois de chamar");

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

	@Override
	public CourseModel save(CourseModel courseModel) {
		return courseRepository.save(courseModel);
	}

	@Override
	public Optional<CourseModel> findById(UUID courseId) {
		return courseRepository.findById(courseId);
	}

	@Override
	public List<CourseModel> findAll() {
		return courseRepository.findAll();
	}

}
