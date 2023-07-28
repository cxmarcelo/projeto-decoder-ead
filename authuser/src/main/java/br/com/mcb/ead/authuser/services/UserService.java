package br.com.mcb.ead.authuser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.mcb.ead.authuser.models.UserModel;

public interface UserService {

	public List<UserModel> findAll();

	public Optional<UserModel> findById(UUID userId);

	public void delete(UserModel userModel);

	public void save(UserModel userModel);

	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

	public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

}
