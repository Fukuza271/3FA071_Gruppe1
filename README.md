
# Benutzerdokumentation Hausverwaltungssoftware

## 1. Softwareaufbau

Die Software besteht aus den folgenden Hauptkomponenten:  
**Datenbank**: Die MariaDB-Datenbank bildet die Grundlage für die Speicherung der Kunden- und Ablesedaten.  
**CRUD-Logik**: Grundlegende Operationen zur Manipulation der Kunden- und Ablesedaten.  
**REST-API**: Ermöglicht den Zugriff auf die Funktionen der Software über definierte Endpoints.  
**Frontend**: Benutzerschnittstelle, die über Angular CLI, die Interaktion mit der Software ermöglicht.  

## 2. Software einrichten

Zum erstmaligen Einrichten der Software muss die Datenbank aufgebaut werden:  
Installieren Sie MariaDB.  

//TODO-----------------------------------------------------------------------------
Herr Niedermeier fragen, wie das mit der Properties Datei gemacht werden soll:   
 ![image](https://github.com/user-attachments/assets/41809403-fd1d-436d-93a0-25d4e703c98b)   

Führen Sie den Endpunkt „http://localhost:8080/setupDB“ als DELETE-Methode, über ein API-Client Tool (z.B. Postman) aus, um die Datenbank zu initialisieren.  

![image](https://github.com/user-attachments/assets/14db85ea-b90e-488e-8a1c-a8138fd816a1)   
*Abbildung 1 API-Client-Tool mit Endpunkt "setupDB"*   

## 3. Software starten

**Backend starten**  
Führen Sie die Klasse „Server“ im Projekt „3FA071_Gruppe1“ unter src/main/java/rest/Server.java“ aus, um das Backend zu starten.  

**Frontend starten**  
Führen Sie im Projekt „3FA071_Gruppe1_Frontend“ in der Kommandozeile den Befehl „ng serve“ aus.  
Geben Sie die URL http://localhost:4200 in ihrem Browser ein, um das Frontend zu starten.  

## 4. Software bedienen

**Navigation**
Über die Laschen „Customer“ und „Readings“ in der Navigationsleiste kann zwischen den jeweiligen Übersichten der Kunden und Ablesedaten navigiert werden.  

![image](https://github.com/user-attachments/assets/5c3f0cb3-f5a5-404c-89b1-931b044822c1)  
*Abbildung 2 Navigationsleiste*  

![image](https://github.com/user-attachments/assets/6971efca-a36d-4de2-b32b-a0866b2122bc)  
*Abbildung 3 Kundenübersicht (Testdaten)*  

Klicken Sie auf <img src="https://github.com/user-attachments/assets/3cdf4de3-3078-49ce-8ab2-dc310a0e544c" width="20px" height="20px" /> „Toggle Darkmode“, um zwischen dem hellen und dunklen Modus der Anwendung zu wechseln.  

Klicken Sie auf <img src="https://github.com/user-attachments/assets/ddcf6e02-bf99-4f62-a534-7b091fafa531" width="20px" height="20px" /> „Import File“, um Kunden- oder Ablesedaten als JSON, XML oder CSV zu importieren.  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/ce7e8549-da0a-4d6a-b3fe-e162f99c8588" width="20px" height="20px" /> „Export File“, um Kunden- oder Ablesedaten als JSON, XML oder CSV zu importieren.  


*Abbildung 4 Kundendaten importieren*  
 
*Abbildung 5 Kundendaten exportieren*  

Klicken Sie auf <img src="" width="20px" height="20px" /> „Configure Columns“, um anzupassen, welche Spalten der Übersicht angezeigt werden sollen.
 
Abbildung 6 Kundenübersicht anpassen

Klicken Sie auf <img src="" width="20px" height="20px" /> „Show Filters“, um den Filter aufzuklappen und die entsprechende Übersicht nach verschiedenen Kriterien zu filtern oder zu durchsuchen:
Kundendaten können nach KundenID, Vor- und Nachnamen, Geschlecht und Geburtsdatum gefiltert werden.
Ablesedaten können nach AblesungsID, KundenID, Kundenname, Datum, ZählerID, Zählerstand, Zählertyp, Kommentar und Ersatzzähler (Ja/Nein) gefiltert werden.

Klicken Sie auf die Spaltenüberschrift, um die Übersicht auf- oder absteigend nach der gewählten Spalte zu sortieren. 


*Abbildung 7 Gefilterte Kundenübersicht (Testdaten)*  

Bei Anklicken eines Kunden oder einer Ablesung auf der entsprechenden Übersicht gelangen Sie auf die Detailansicht des Datensatzes, hier kann der Datensatz mit <img src="" width="20px" height="20px" /> „Edit“ angepasst oder mit 
<img src="" width="20px" height="20px" /> „Delete“ gelöscht werden.  
Klicken Sie auf „Create Reading“, um für diesen Kunden ein Reading zu erstellen.  
 
Abbildung 8 Detailansicht eines Kunden

Neuen Kunden erstellen
Klicken Sie in der Kundenübersicht auf „Create Customer“, um einen neuen Kunden zu erstellen.
Füllen Sie die angezeigten Felder mit Kundendaten. 
Klicken Sie auf „Cancel“, um den Vorgang abzubrechen und zurück auf die Kundenübersicht zu gelangen.
Klicken Sie auf „Save“, um den neuen Kunden in der Datenbank zu speichern.
Die Felder „First Name“ und „Last Name“ sind Pflichtfelder, „Gender“ ist standardmäßig auf „unbekannt“ (U) gesetzt, „Birthdate“ ist optional.
Nach dem Sichern werden Sie auf die Detailansicht des Kunden (siehe Abbildung 8) weitergeleitet.
 
	Abbildung 9 Anlage eines neuen Kunden
 
Abbildung 10 Sequenzdiagramm für die beispielhafte CRUD-Operation CREATE


Neue Ablesedaten erstellen
Klicken Sie in der Ablesungsübersicht auf „Create Reading“, um eine neue Ablesung zu erstellen.
Füllen Sie die angezeigten Felder mit Ablesedaten.
Klicken Sie auf „Cancel“, um den Vorgang abzubrechen und zurück auf die Ablesungsübersicht zu gelangen.
Klicken Sie auf „Save“, um die neuen Ablesedaten in der Datenbank zu speichern.
Die Felder „Customer“, „Date of Reading“, „Meter ID“ und „Meter Count“ sind Pflichtfelder, „Meter Type“ ist standardmäßig auf „HEIZUNG“ und „Substitute“ auf „No“ gesetzt, „Comment“ ist optional.
Nach dem Sichern werden Sie auf die Detailansicht der Ablesung (siehe Abbildung 8) weitergeleitet.

Sollte der Kunde, für den eine Ablesung erstellt werden soll, noch nicht existieren:
Klicken Sie auf „Create and add Customer“, um einen neuen Kunden anzulegen (siehe Abbildung 9) und der Ablesung hinzuzufügen
 
Abbildung 11 Anlage einer neuen Ablesung





