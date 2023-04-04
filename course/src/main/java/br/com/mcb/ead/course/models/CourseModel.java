package br.com.mcb.ead.course.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.mcb.ead.course.enums.CourseLevel;
import br.com.mcb.ead.course.enums.CourseStatus;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tb_courses")
public class CourseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID courseId;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false, length = 255)
	private String description;

	@Column()
	private String imageUrl;

	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss")
	private LocalDateTime creationDate;

	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss")
	private LocalDateTime lastUpdateDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseStatus coursesStatus;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseLevel courseLevel;

	@Column(nullable = false)
	private UUID userInstructor;

}
