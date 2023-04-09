package br.com.mcb.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.mcb.ead.course.models.CourseModel;

public interface CourseService {

	public void delete(CourseModel courseModel);

	public CourseModel save(CourseModel courseModel);

	public Optional<CourseModel> findById(UUID courseId);

	public List<CourseModel> findAll();

}
