package de.buhl.postgrespringdata.repository;

import de.buhl.postgrespringdata.model.entity.Nutzer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Nutzer, String> {

public Boolean existsByUsername(String username);
}
