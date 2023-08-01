package br.com.mcb.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mcb.ead.course.models.CourseModel;
import br.com.mcb.ead.course.models.CourseUserModel;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {

	boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

}
