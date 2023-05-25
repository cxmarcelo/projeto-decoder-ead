package br.com.mcb.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcb.ead.authuser.dtos.UserDto;
import br.com.mcb.ead.authuser.enums.UserStatus;
import br.com.mcb.ead.authuser.enums.UserType;
import br.com.mcb.ead.authuser.models.UserModel;
import br.com.mcb.ead.authuser.services.UserService;

@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


	@PostMapping("/signup")
	public ResponseEntity<Object> registerUser(
			@RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
		if(userService.existsByUsername(userDto.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
		}
		
		if(userService.existsByEmail(userDto.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
		}
		
		var userModel = new UserModel();
		BeanUtils.copyProperties(userDto, userModel);
		userModel.setUserStatus(UserStatus.ACTIVE);
		userModel.setUserType(UserType.STUDENT);
		userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		userService.save(userModel);

		return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
	}

	@GetMapping("/")
	public String index() {
		logger.trace("LOG TRACE"); // muito detalhado
		logger.debug("LOG DEBUG"); //Desenvolvimento
		logger.info("LOG INFO"); 
		logger.warn("LOG WARN"); //Uso em avisos, nao chega a ser um erro
		logger.error("LOG ERROR"); //Boa pratica utilizar em try catch
		return "Logging Spring Boot";
	}
}
