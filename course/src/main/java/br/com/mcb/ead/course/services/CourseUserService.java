package br.com.mcb.ead.course.services;

import java.util.UUID;

import br.com.mcb.ead.course.models.CourseModel;
import br.com.mcb.ead.course.models.CourseUserModel;

public interface CourseUserService {

	boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

	CourseUserModel save(CourseUserModel courseUserModel);

	CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel);

}
