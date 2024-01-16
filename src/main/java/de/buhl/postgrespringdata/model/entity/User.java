package de.buhl.postgrespringdata.model.entity;
import de.buhl.postgrespringdata.model.entity.submodel.SteuerInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import jakarta.persistence.*;


@Entity
@Table(name = "user")
public record User(
        @Id
        @Column(name = "id")
        String id,
        @Column(name ="username")
        String username,
        @Column(name="password")
        String password,

        @Embedded
        UserInfo userInfo,

        @Embedded
        SteuerInfo steuerInfo
) {

}
