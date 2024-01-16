package de.buhl.postgrespringdata.model.entity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import de.buhl.postgrespringdata.model.entity.submodel.SteuerInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "user")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public record User(
        @Id
        @Column(name = "id")
        String id,
        @Column(name ="username")
        String username,
        @Column(name="password")
        String password,
        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        UserInfo userInfo,
        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        SteuerInfo steuerInfo
) {

}
