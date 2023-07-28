package br.com.mcb.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mcb.ead.course.repositories.CourseUserRepository;
import br.com.mcb.ead.course.services.CourseUserService;

@Service
public class CourseUserServiceImpl implements CourseUserService{

	@Autowired
	private CourseUserRepository courseUserRepository;

}
