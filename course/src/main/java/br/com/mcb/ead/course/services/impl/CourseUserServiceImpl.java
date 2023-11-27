package br.com.mcb.ead.course.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mcb.ead.course.clients.AuthUserClient;
import br.com.mcb.ead.course.models.CourseModel;
import br.com.mcb.ead.course.models.CourseUserModel;
import br.com.mcb.ead.course.repositories.CourseUserRepository;
import br.com.mcb.ead.course.services.CourseUserService;

@Service
public class CourseUserServiceImpl implements CourseUserService {

	@Autowired
	private CourseUserRepository courseUserRepository;

	@Autowired
	private AuthUserClient authUserClient;

	@Override
	public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {
		return this.courseUserRepository.existsByCourseAndUserId(courseModel, userId);
	}

	@Override
	public CourseUserModel save(CourseUserModel courseUserModel) {
		return this.courseUserRepository.save(courseUserModel);
	}

	@Transactional
	@Override
	public CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {
		courseUserModel = this.courseUserRepository.save(courseUserModel);
		authUserClient.postSubscriptionUserInCourse(courseUserModel.getCourse().getCourseId(), courseUserModel.getUserId());
		return courseUserModel;
	}

}
