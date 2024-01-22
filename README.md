Buhl Data Interview Aufgabe
Aufgabenstellung war:
- Spring Boot applikation für PostgreSQL Datenbank bauen
- Rest schnittstelle bauen
- Projekt soll CRUD funktionen aufweisen
- Soll keine generische Entity tabelle beinhalten

  Projekt beinhaltet:

  AccountUser Entity: (bsp. für einen Account in einem Steuerhilfsprogramm)
  Diese Entity beinhaltet einen Account der eine generierte 128-bit generierte Id beinhaltet
  und überprüft ob der UserName bereits schon einmal benutzt wird. Da dieser auch in der Daten-
  bank als Unique gekennzeichnet wurde.

  Die UserInfo subclass ist für die speicherung der Persönlichen Informationen die der User
  für eine Steuer Erklärung bspw. bereitstellen müsste.

  Die SteuerInfo subclass beinhaltet einmal die SteuerID sowie Jahresgehalt. Darunter noch zwei
  Listen für jeweils einmal Steuer erleichterung durch abziehbare kosten (Deductabiles)
  und Extra einkommen neben dem ganz Normalen Einkommen.

  Neben der überlegung für die möglichen Daten habe ich die CRUD funktionen eingebaut und einen
  Unit test, sowie einen IntegrationTest gebaut um die Funktionen ausführlich zu testen.
  
  

