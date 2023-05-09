package br.com.mcb.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.mcb.ead.course.models.LessonModel;

public interface LessonService {

	LessonModel save(LessonModel lessonModel);

	Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

	void delete(LessonModel lessonModel);

	List<LessonModel> findAllByModule(UUID moduleId);

}
