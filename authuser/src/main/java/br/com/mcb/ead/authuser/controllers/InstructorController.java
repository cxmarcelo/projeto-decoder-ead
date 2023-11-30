package br.com.mcb.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcb.ead.authuser.dtos.InstructorDto;
import br.com.mcb.ead.authuser.enums.UserType;
import br.com.mcb.ead.authuser.models.UserModel;
import br.com.mcb.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

	@Autowired
	private UserService userService;

	@PostMapping("/subscription")
	public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto) {
		Optional<UserModel> userModelOptional = userService.findById(instructorDto.getUserId());
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		} else {
			var userModel = userModelOptional.get();
			userModel.setUserType(UserType.INSTRUCTOR);
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			userService.save(userModel);
			return ResponseEntity.status(HttpStatus.OK).body(userModel);
		}
	}

}
