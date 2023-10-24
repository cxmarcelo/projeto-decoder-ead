package br.com.mcb.ead.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mcb.ead.authuser.models.UserCourseModel;
import br.com.mcb.ead.authuser.models.UserModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID>{

	boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);
	
}
