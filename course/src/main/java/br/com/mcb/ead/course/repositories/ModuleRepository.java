package br.com.mcb.ead.course.repositories;

import java.util.List;
import java.util.UUID;

//import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mcb.ead.course.models.ModuleModel;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>{

	/*
	 * busca module model com course mo modo eager
	@EntityGraph(attributePaths = {"course"})
	ModuleModel findByTitle(String title);
	 */

	//@Modifying com @Query para dar inserir/atualizar/deletar de forma customizada

	@Query(value="select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
	List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

}
