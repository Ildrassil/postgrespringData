package de.buhl.postgrespringdata.util;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class IdService {

    /*
    Diese Komponente ist für die Generierung von UUIDs zuständig,
    um sicherzustellen, dass jeder Benutzer eine eindeutige ID hat.
    Diese sollte nicht von außen zugänglich sein, da ihre Funktion
    auf die Generierung von IDs beschränkt ist. Es sollte dem Benutzer
    auch nicht möglich sein, diese selbst festzulegen.
    Die UUID wird in einem String gespeichert. Darüber hinaus
    gewährleistet die UUID-Klasse, dass die generierte UUID eindeutig ist
    und keine Duplikate erzeugt werden.
     */
    public String randomId(){
        return UUID.randomUUID().toString();
    }
}
