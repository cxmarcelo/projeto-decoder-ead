package br.com.mcb.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mcb.ead.authuser.dtos.UserDto;
import br.com.mcb.ead.authuser.models.UserModel;
import br.com.mcb.ead.authuser.services.UserService;
import br.com.mcb.ead.authuser.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;;

@Log4j2
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
			@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
			@RequestParam(required = false) UUID courseId) {


		Page<UserModel> userModelPage = null;

		if(courseId != null) {
			userModelPage = userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
		} else {
			userModelPage = userService.findAll(spec, pageable);
		}


		if(!userModelPage.isEmpty()) {
			for(UserModel user: userModelPage.toList()) {
				user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());;
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Object> getOneUser(@PathVariable UUID userId) {
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
	}


	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable UUID userId) {
		log.debug("DELETE deleteUser userId received {} ", userId);
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		userService.delete(userModelOptional.get());

		log.debug("DELETE deleteUser userId deleted {} ", userId);
		log.info("User deleted  successfully userId {} ", userId);
		return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
	}

	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable UUID userId, 
			@RequestBody @Validated(UserDto.UserView.UserPut.class) @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
		log.debug("PUT updateUser userDto received {} ", userDto.toString());
		Optional<UserModel> userModelOptional = userService.findById(userId);

		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		var userModel = userModelOptional.get();
		userModel.setFullName(userDto.getFullName());
		userModel.setPhoneNumber(userDto.getPhoneNumber());
		userModel.setCpf(userDto.getCpf());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		userService.save(userModel);

		log.debug("PUT updateUser userId saved {} ", userModel.getUserId());
		log.info("User updated successfully userId {} ", userModel.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(userModel);
	}

	@PutMapping("/{userId}/password")
	public ResponseEntity<Object> updatePassword(@PathVariable UUID userId, 
			@RequestBody @Validated(UserDto.UserView.PasswordPut.class) @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
		Optional<UserModel> userModelOptional = userService.findById(userId);

		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		//POR ENQUANTO TESTES
		if(!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
			log.warn("Mimatched old password! userId {}", userDto.getUserId());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mimatched old password!");
		}

		var userModel = userModelOptional.get();
		userModel.setPassword(userDto.getPassword());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		userService.save(userModel);

		return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
	}

	@PutMapping("/{userId}/image")
	public ResponseEntity<Object> updateImage(@PathVariable UUID userId, 
			@RequestBody @Validated(UserDto.UserView.ImagePut.class) @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
		log.debug("PUT updateImage userDto received {} ", userDto.toString());		
		Optional<UserModel> userModelOptional = userService.findById(userId);

		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		var userModel = userModelOptional.get();
		userModel.setImageUrl(userDto.getImageUrl());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		userService.save(userModel);
		log.debug("PUT updateImage userId saved {} ", userModel.getUserId());
		log.info("Image updated successfully userId {} ", userModel.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(userModel);
	}

}
