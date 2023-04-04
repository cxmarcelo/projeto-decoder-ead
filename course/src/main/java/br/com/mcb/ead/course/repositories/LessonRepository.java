package br.com.mcb.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mcb.ead.course.models.LessonModel;

public interface LessonRepository extends JpaRepository<LessonModel, UUID>{

}
