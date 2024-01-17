package de.buhl.postgrespringdata.model.entity;
import de.buhl.postgrespringdata.model.entity.submodel.SteuerInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "nutzer")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nutzer{
        @Id
        @Column(name = "Id")
        private String id;
        @Column(name="username")
        private String username;
        @Column(name="password")
        private String password;

        @Embedded
        @Column(name="userInfo")
        private UserInfo userInfo;

        @Embedded
        @Column(name = "steuerInfo")
        private SteuerInfo steuerInfo;



}
