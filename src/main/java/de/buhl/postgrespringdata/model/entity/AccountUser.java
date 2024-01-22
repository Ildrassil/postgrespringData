package de.buhl.postgrespringdata.model.entity;
import de.buhl.postgrespringdata.model.entity.submodel.TaxInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/*
Ich habe mich entschieden, eine Entität zu erstellen,
die sowohl allgemeine Benutzerinformationen
als auch Steuerinformationen speichert.
Da das Hauptprodukt von Buhl Data eine
Software für die Steuererklärung ist,
erscheint es mir sinnvoll, in der
Probeaufgabe Bezug auf dieses Hauptprodukt zu nehmen.
 */


@Entity
@Table(name = "AccountUser")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountUser {


        // Ich lasse die ID von einem IdService generieren.
        @Id
        private String id;

        @Column(unique = true)
        private String username;


        // Mir ist bewusst das man das Passwort nicht im
        // Klartext speichern sollte.
        // weclhes mit Spring Security sehr einfach adaptierbar
        // jedoch ist dies nicht Teil der Aufgabe
        private String password;


        // Ist ein Record um die UserInfos zu speichern
        @Embedded
        private UserInfo userInfo;


        // Ist eine Klasse um die SteuerInfos zu speichern
        @Embedded
        private TaxInfo taxInfo;



}
