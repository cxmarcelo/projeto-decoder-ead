package br.com.mcb.ead.authuser.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mcb.ead.authuser.services.UtilsService;

@Service
public class UtilsServiceImpl implements UtilsService {

	String REQUEST_URI = "http://localhost:8082";

	@Override
	public String createUrl(UUID userId, Pageable pageable) {
		return REQUEST_URI + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() + 
				"&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
	}

}
