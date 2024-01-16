package de.buhl.postgrespringdata.model.entity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import de.buhl.postgrespringdata.model.entity.submodel.SteuerInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;


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

        @Column(name = "userInfo")
        @JoinTable
        @Embedded
        UserInfo userInfo,

        @Column(name = "steuerInfo")
        @JoinTable
        @Embedded
        SteuerInfo steuerInfo
) {

}
