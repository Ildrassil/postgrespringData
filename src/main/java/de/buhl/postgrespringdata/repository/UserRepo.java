package de.buhl.postgrespringdata.repository;

import de.buhl.postgrespringdata.model.entity.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AccountUser, String> {

Optional<AccountUser> findByUsername(String username);
}

