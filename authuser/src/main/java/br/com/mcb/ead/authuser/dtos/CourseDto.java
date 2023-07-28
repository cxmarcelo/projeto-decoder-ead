package br.com.mcb.ead.authuser.dtos;

import java.util.UUID;

import br.com.mcb.ead.authuser.enums.CourseLevel;
import br.com.mcb.ead.authuser.enums.CourseStatus;
import lombok.Data;

@Data
public class CourseDto {

	private UUID courseId;
	private String name;
	private String description;
	private String imageUrl;
	private CourseStatus courseStatus;
	private UUID userInstructor;
	private CourseLevel courseLevel;

}
