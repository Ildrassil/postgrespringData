package de.buhl.postgrespringdata.model.entity;
import de.buhl.postgrespringdata.model.entity.submodel.SteuerInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name ="User")
public record User(
        @Id
        String id,
        String username,
        String password,
        UserInfo userInfo,
        SteuerInfo steuerInfo
) {

}
