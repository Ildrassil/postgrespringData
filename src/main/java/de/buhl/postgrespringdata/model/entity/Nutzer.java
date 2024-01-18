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
        private String id;

        private String username;

        private String password;

        @Embedded
        private UserInfo userInfo;

        @Embedded
        private SteuerInfo steuerInfo;



}
