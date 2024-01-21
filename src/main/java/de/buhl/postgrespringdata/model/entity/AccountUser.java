package de.buhl.postgrespringdata.model.entity;
import de.buhl.postgrespringdata.model.entity.submodel.TaxInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "AccountUser")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountUser {

        @Id
        private String id;

        @Column(unique = true)
        private String username;

        private String password;

        @Embedded
        private UserInfo userInfo;

        @Embedded
        private TaxInfo taxInfo;



}
