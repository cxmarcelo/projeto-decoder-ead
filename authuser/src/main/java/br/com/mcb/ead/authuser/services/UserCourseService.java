package br.com.mcb.ead.authuser.services;

import java.util.UUID;

import br.com.mcb.ead.authuser.models.UserCourseModel;
import br.com.mcb.ead.authuser.models.UserModel;

public interface UserCourseService {

	boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

	UserCourseModel save(UserCourseModel userCourseModel);

	boolean existsByCourseId(UUID courseId);

	void deleteUserCourseByCourse(UUID courseId);

}
