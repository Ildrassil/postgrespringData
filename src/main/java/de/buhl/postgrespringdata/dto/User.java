package de.buhl.postgrespringdata.dto;
import de.buhl.postgrespringdata.util.IdService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record User(
        @Id
        String id,
        String username,
        String password,
        UserInfo userInfo,
        SteuerInfo steuerInfo
) {

}
