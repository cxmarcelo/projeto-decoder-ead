package br.com.mcb.ead.course.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.mcb.ead.course.enums.CourseLevel;
import br.com.mcb.ead.course.enums.CourseStatus;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_COURSES ")
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime creationDate;

	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime lastUpdateDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseStatus courseStatus;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseLevel courseLevel;

	@Column(nullable = false)
	private UUID userInstructor;

	//NÃO VEM EM CONSULTAS GET
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	//@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	//cascada apaga todos modulos vinculados // orphaRemoval apaga modulos que nao tem um course associado
	//SE FOR JOIN ele vai trazer como EAGER / se nao definir o default é JOIN (porem respeita o lazy)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	//esse onDelete joga a responsabilidade pro banco de dados apagar
	@Fetch(FetchMode.SUBSELECT)
	private Set<ModuleModel> modules;

	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private Set<CourseUserModel> coursesUsers;
	
	public CourseUserModel convertToCourseUserModel(UUID userId) {
		return new CourseUserModel(null, this, userId);
	}

}
