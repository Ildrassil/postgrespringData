package de.buhl.postgrespringdata.repo;

import de.buhl.postgrespringdata.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    public Boolean findUserByUsername(String userName) {

    }
}
