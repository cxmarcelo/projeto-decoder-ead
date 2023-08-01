package br.com.mcb.ead.course.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mcb.ead.course.services.UtilsService;

@Service
public class UtilsServiceImpl implements UtilsService {

	@Override
	public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
		return "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() + 
				"&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
	}

}
