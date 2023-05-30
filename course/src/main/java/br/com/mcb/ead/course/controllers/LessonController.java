package br.com.mcb.ead.course.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcb.ead.course.dtos.LessonDto;
import br.com.mcb.ead.course.models.LessonModel;
import br.com.mcb.ead.course.models.ModuleModel;
import br.com.mcb.ead.course.services.LessonService;
import br.com.mcb.ead.course.services.ModuleService;
import br.com.mcb.ead.course.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

	@Autowired
	private LessonService lessonService;

	@Autowired
	private ModuleService moduleService;


	@PostMapping("/modules/{moduleId}/lessons")
	public ResponseEntity<Object> saveLesson(@PathVariable UUID moduleId, @RequestBody @Valid LessonDto lessonDto) {
		log.debug("POST saveLesson lessonDto received {} ", lessonDto.toString());
		Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId);

		if(moduleModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found.");
		}

		var lessonModel = new LessonModel();
		BeanUtils.copyProperties(lessonDto, lessonModel);
		lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		lessonModel.setModule(moduleModelOptional.get());

		log.debug("POST saveLesson lessonId saved {} ", lessonModel.getLessonId());
		log.info("Lesson saved successfully lessonId {} ", lessonModel.getLessonId());
		return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
	}

	@DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<Object> deleteLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId) {
		log.debug("DELETE deleteLesson lessonId received {} ", lessonId);
		Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

		if(lessonModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
		}

		lessonService.delete(lessonModelOptional.get());

		log.debug("DELETE deleteLesson lessonId deleted {} ", lessonId);
		log.info("Lesson deleted successfully lessonId {} ", lessonId);
		return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully.");
	}

	@PutMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<Object> updateLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId, @RequestBody @Valid LessonDto lessonDto) {
		log.debug("PUT updateLesson lessonDto received {} ", lessonDto.toString());
		Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

		if(lessonModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
		}

		var lessonModel = lessonModelOptional.get();
		lessonModel.setTitle(lessonDto.getDescription());
		lessonModel.setDescription(lessonDto.getDescription());
		lessonModel.setVideoUrl(lessonDto.getVideoUrl());

		log.debug("PUT updateLesson lessonId saved {} ", lessonModel.getLessonId());
		log.info("Lesson updated successfully lessonId {} ", lessonModel.getLessonId());
		return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel));
	}

	@GetMapping("/modules/{moduleId}/lessons")
	public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable UUID moduleId,
			SpecificationTemplate.LessonSpec spec, 
			@PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
	}

	@GetMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<Object> getOneLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId) {
		Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

		if(lessonModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get());
	}

}
