package br.com.mcb.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mcb.ead.course.models.LessonModel;
import br.com.mcb.ead.course.repositories.LessonRepository;
import br.com.mcb.ead.course.services.LessonService;

@Service
public class LessonServiceImpl implements LessonService {

	@Autowired
	private LessonRepository lessonRepository;

	@Override
	public LessonModel save(LessonModel lessonModel) {
		return lessonRepository.save(lessonModel);
	}

	@Override
	public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
		return lessonRepository.findLessonIntoModule(moduleId, lessonId);
	}

	@Override
	public void delete(LessonModel lessonModel) {
		lessonRepository.delete(lessonModel);
	}

	@Override
	public List<LessonModel> findAllByModule(UUID moduleId) {
		return lessonRepository.findAllLessonsIntoModule(moduleId);
	}

}
