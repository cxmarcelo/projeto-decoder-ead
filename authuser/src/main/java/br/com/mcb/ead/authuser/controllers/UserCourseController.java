package br.com.mcb.ead.authuser.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcb.ead.authuser.clients.CourseClient;
import br.com.mcb.ead.authuser.dtos.CourseDto;
import br.com.mcb.ead.authuser.dtos.UserCourseDto;
import br.com.mcb.ead.authuser.models.UserCourseModel;
import br.com.mcb.ead.authuser.models.UserModel;
import br.com.mcb.ead.authuser.services.UserCourseService;
import br.com.mcb.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class UserCourseController {


	@Autowired
	private CourseClient courseClient;

	@Autowired
	private UserService userService;

	@Autowired
	private UserCourseService userCourseService;

	@GetMapping("/users/{userId}/courses")
	public ResponseEntity<Object> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
			@PathVariable UUID userId) {
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
	}

	@PostMapping("/users/{userId}/courses/subscription")
	public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID userId, @RequestBody @Valid UserCourseDto userCourseDto) {
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}

		if(userCourseService.existsByUserAndCourseId(userModelOptional.get(), userCourseDto.getCourseId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already existis!");
		}

		UserCourseModel userCourseModel = userCourseService.save(userModelOptional.get().convertToUserCourseModel(userCourseDto.getCourseId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel);

	}

	@DeleteMapping("/users/courses/{courseId}")
	public ResponseEntity<Object> deleteUserCourseByCourse(@PathVariable UUID courseId) {
		if(!userCourseService.existsByCourseId(courseId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserCourse not found.");
		}

		userCourseService.deleteUserCourseByCourse(courseId);

		return ResponseEntity.status(HttpStatus.OK).body("UserCourse deleted successfully.");
	}


}
