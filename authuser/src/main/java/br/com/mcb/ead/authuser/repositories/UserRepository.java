package br.com.mcb.ead.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mcb.ead.authuser.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

}
