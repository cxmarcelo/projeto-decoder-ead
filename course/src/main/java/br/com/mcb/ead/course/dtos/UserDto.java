package br.com.mcb.ead.course.dtos;

import java.util.UUID;

import br.com.mcb.ead.course.enums.UserStatus;
import br.com.mcb.ead.course.enums.UserType;
import lombok.Data;

@Data
public class UserDto {

	private UUID userId;
	private String username;
	private String email;
	private String fullName;
	private UserStatus userStatus;
	private UserType userType;
	private String phoneNumber;
	private String cpf;
	private String imageURL;
}
