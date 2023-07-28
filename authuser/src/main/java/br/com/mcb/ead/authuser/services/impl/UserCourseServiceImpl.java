package br.com.mcb.ead.authuser.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mcb.ead.authuser.repositories.UserCourseRepository;
import br.com.mcb.ead.authuser.services.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {


	@Autowired
	private UserCourseRepository userCourseRepository;
	
}
