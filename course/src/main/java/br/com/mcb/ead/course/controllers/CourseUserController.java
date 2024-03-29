package br.com.mcb.ead.course.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.mcb.ead.course.clients.AuthUserClient;
import br.com.mcb.ead.course.dtos.SubscriptionDto;
import br.com.mcb.ead.course.dtos.UserDto;
import br.com.mcb.ead.course.enums.UserStatus;
import br.com.mcb.ead.course.models.CourseModel;
import br.com.mcb.ead.course.models.CourseUserModel;
import br.com.mcb.ead.course.services.CourseService;
import br.com.mcb.ead.course.services.CourseUserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

	@Autowired
	private AuthUserClient authUserClient;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseUserService courseUserService;

	@GetMapping("/courses/{courseId}/users")
	public ResponseEntity<Object> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
			@PathVariable UUID courseId) {
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
		if(courseModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
	}

	@PostMapping("courses/{courseId}/users/subscription")
	public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId, @RequestBody @Valid SubscriptionDto subscriptionDto) {
		ResponseEntity<UserDto> responseUser;
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);

		if(courseModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
		}

		if(courseUserService.existsByCourseAndUserId(courseModelOptional.get(), subscriptionDto.getUserId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
		}

		//VERIFICAÇÃO DE USER
		try {
			responseUser = authUserClient.getOneUserById(subscriptionDto.getUserId());

			if(responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
			}

		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
			}
		}
		CourseUserModel courseUserModel = courseUserService.saveAndSendSubscriptionUserInCourse(courseModelOptional.get().convertToCourseUserModel(subscriptionDto.getUserId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel);
	}

}
