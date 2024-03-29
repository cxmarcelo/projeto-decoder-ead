package br.com.mcb.ead.authuser.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mcb.ead.authuser.models.UserCourseModel;
import br.com.mcb.ead.authuser.models.UserModel;
import br.com.mcb.ead.authuser.repositories.UserCourseRepository;
import br.com.mcb.ead.authuser.services.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {


	@Autowired
	private UserCourseRepository userCourseRepository;

	@Override
	public boolean existsByUserAndCourseId(UserModel userModel, UUID courseId) {
		return this.userCourseRepository.existsByUserAndCourseId(userModel, courseId);
	}

	@Override
	public UserCourseModel save(UserCourseModel userCourseModel) {
		return this.userCourseRepository.save(userCourseModel);
	}

	@Override
	public boolean existsByCourseId(UUID courseId) {
		return this.userCourseRepository.existsByCourseId(courseId);
	}

	@Transactional
	@Override
	public void deleteUserCourseByCourse(UUID courseId) {
		this.userCourseRepository.deleteAllByCourseId(courseId);
	}
	
}
