package br.com.mcb.ead.course.dtos;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.mcb.ead.course.enums.CourseLevel;
import br.com.mcb.ead.course.enums.CourseStatus;
import lombok.Data;

@Data
public class CourseDto {

	@NotBlank
	private String name;

	@NotBlank
	private String description;
	private String imageUrl;

	@NotNull
	private CourseStatus courseStatus;

	@NotNull
	private UUID userInstructor;

	@NotNull
	private CourseLevel courseLevel;

}
