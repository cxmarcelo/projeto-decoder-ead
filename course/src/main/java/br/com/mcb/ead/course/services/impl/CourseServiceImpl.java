package br.com.mcb.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.mcb.ead.course.clients.AuthUserClient;
import br.com.mcb.ead.course.models.CourseModel;
import br.com.mcb.ead.course.models.CourseUserModel;
import br.com.mcb.ead.course.models.LessonModel;
import br.com.mcb.ead.course.models.ModuleModel;
import br.com.mcb.ead.course.repositories.CourseRepository;
import br.com.mcb.ead.course.repositories.CourseUserRepository;
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
	
	@Autowired
	private CourseUserRepository courseUserRepository;
	
	@Autowired
	private AuthUserClient authUserClient;

	@Transactional
	@Override
	public void delete(CourseModel courseModel) {
		boolean deleteCourseUserInAuthUser = false;
		
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
		
		List<CourseUserModel> courseUserModelList = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
		if(!courseUserModelList.isEmpty()) {
			courseUserRepository.deleteAll(courseUserModelList);
			deleteCourseUserInAuthUser = true;
		}

		courseRepository.delete(courseModel);
		
		if(deleteCourseUserInAuthUser) {
			authUserClient.deleteCourseInAuthUser(courseModel.getCourseId());
		}
		
		
		
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
	public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
		return courseRepository.findAll(spec, pageable);
	}

}
