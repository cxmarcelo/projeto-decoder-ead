package br.com.mcb.ead.authuser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.mcb.ead.authuser.models.UserModel;

public interface UserService {

	public List<UserModel> findAll();

	public Optional<UserModel> findById(UUID userId);

	public void delete(UserModel userModel);

}
