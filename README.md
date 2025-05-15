
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
Erstellen Sie eine neue MariaDB-Datenbank mit dem Namen *"HausFix"*.
Tragen Sie den angemeldeten User, den Datenbankuser und das Datenbankpasswort in die Propertiesdatei unter *"src/main/resources/database.properties"* ein.  
\<User\>.db.url=jdbc:mariadb://localhost:3306/HausFix  
\<User\>.db.user=\<user\>  
\<User\>.db.pw=\<password\>  

![image](https://github.com/user-attachments/assets/afa99ec8-202c-439a-8e41-9aefd50f086e)  
*Abbildung 1: Beispieluser in der Propertiesdatei*

Führen Sie den Endpunkt *"http://localhost:8080/setupDB"* als DELETE-Methode, über ein API-Client Tool (z.B. Postman) aus, um die Datenbank zu initialisieren.  

![image](https://github.com/user-attachments/assets/06da21c7-202f-4102-80b0-363a98eea505)  
*Abbildung 2: API-Client-Tool mit Endpunkt "setupDB"*   
  
## 3. Software starten

**Backend starten**  
Führen Sie die Klasse *"Server"* im Projekt *"3FA071_Gruppe1"* unter *"src/main/java/rest/Server.java"* aus, um das Backend zu starten.  

**Frontend starten**  
Führen Sie im Projekt *"3FA071_Gruppe1_Frontend"* in der Kommandozeile den Befehl *"ng serve"* aus.  
Geben Sie die URL *"http://localhost:4200"* in ihrem Browser ein, um das Frontend zu starten.  
  
## 4. Software bedienen
  
**Navigation**
Über die Laschen *"Customer"* und *"Readings"* in der Navigationsleiste kann zwischen den jeweiligen Übersichten der Kunden- und Ablesedaten navigiert werden.  

![image](https://github.com/user-attachments/assets/5c3f0cb3-f5a5-404c-89b1-931b044822c1)  
*Abbildung 3: Navigationsleiste*  
  
![image](https://github.com/user-attachments/assets/6971efca-a36d-4de2-b32b-a0866b2122bc)  
*Abbildung 4: Übersicht (Testdaten)*  
  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/3cdf4de3-3078-49ce-8ab2-dc310a0e544c" width="20px" height="20px" /> *"Toggle Darkmode"*, um zwischen dem hellen und dunklen Modus der Anwendung zu wechseln.  

Klicken Sie auf <img src="https://github.com/user-attachments/assets/ddcf6e02-bf99-4f62-a534-7b091fafa531" width="20px" height="20px" /> *"Import File"*, um - oder Ablesedaten als JSON, XML oder CSV zu importieren.  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/ce7e8549-da0a-4d6a-b3fe-e162f99c8588" width="20px" height="20px" /> *"Export File"*, um - oder Ablesedaten als JSON, XML oder CSV zu importieren.  


![image](https://github.com/user-attachments/assets/84995fe4-a39a-47b0-b185-14cb73af4b67)  
*Abbildung 5: Daten importieren*  

![image](https://github.com/user-attachments/assets/843ddea4-b3bb-4132-b02a-71f389c73984)  
*Abbildung 6: Daten exportieren*  

Klicken Sie auf <img src="https://github.com/user-attachments/assets/d95c96d2-e2ae-41b7-9f4c-97ebe2b25417" width="20px" height="20px" /> *"Configure Columns"*, um anzupassen, welche Spalten der Übersicht angezeigt werden sollen.

![image](https://github.com/user-attachments/assets/5d531622-dfb1-4e77-ac5e-b272e06c3a8c)  
*Abbildung 7: Kundenübersicht anpassen*  

Klicken Sie auf <img src="https://github.com/user-attachments/assets/79af178e-8a59-4bbe-85a3-121c37541464" width="20px" height="20px" /> *"Show Filters"*, um den Filter aufzuklappen und die entsprechende Übersicht nach verschiedenen Kriterien zu filtern oder zu durchsuchen:  
Kundendaten können nach *"(Customer-) ID"*, *"First Name"*, *"Last Name"*, *"Gender"* und *"Birthdate"* gefiltert werden.  
Ablesedaten können nach *"(Reading-) ID"*, *"Customer ID"*, *"Customer Name"*, *"Date"*, *"Meter ID"*, *"Meter Count"*, *"Meter Type"*, *"Comment"* und *"Is Substitute" (Yes/No)* gefiltert werden.  

Klicken Sie auf die Spaltenüberschrift, um die Übersicht auf- oder absteigend nach der gewählten Spalte zu sortieren. 

![image](https://github.com/user-attachments/assets/68fddce8-d54c-4adc-9d20-6b387fd5fd20)  
*Abbildung 8: Gefilterte Kundenübersicht (Testdaten)*  

Bei Anklicken eines Kunden oder einer Ablesung auf der entsprechenden Übersicht gelangen Sie auf die Detailansicht des Datensatzes, hier kann der Datensatz mit <img src="https://github.com/user-attachments/assets/e5f1f51a-fb16-49ab-98a5-18dd3eb8db54" width="20px" height="20px" /> *"Edit"* angepasst oder mit <img src="https://github.com/user-attachments/assets/a0cc9308-a96f-4fc2-bfba-c7ffd464c6d4" width="20px" height="20px" /> *"Delete"* gelöscht werden.  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/f8c4b916-d3fa-4c2e-afca-f1749a674b18" width="20px" height="20px" /> *"Create Reading"*, um für diesen Kunden ein Reading zu erstellen.  

![image](https://github.com/user-attachments/assets/e84e8a9a-1397-4fb8-bd77-e70ff0562e26)  
*Abbildung 9: Detailansicht eines Kunden*  

**Neuen Kunden erstellen**  

Klicken Sie in der Kundenübersicht auf <img src="https://github.com/user-attachments/assets/59d3ecc2-6786-4aab-b531-553a524a38e5" width="20px" height="20px" /> *"Create Customer"*, um einen neuen Kunden zu erstellen.  
Füllen Sie die angezeigten Felder mit Kundendaten. 
Klicken Sie auf <img src="https://github.com/user-attachments/assets/d28a9755-258a-49ed-9fba-13ef1bf78375" width="20px" height="20px" /> *"Cancel"*, um den Vorgang abzubrechen und zurück auf die Kundenübersicht zu gelangen.  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/5509110b-b5c6-4519-a861-d90fc445b83f" width="20px" height="20px" /> *"Save"*, um den neuen Kunden in der Datenbank zu speichern.  
Die Felder *"First Name"* und *"Last Name"* sind Pflichtfelder, *"Gender"* ist standardmäßig auf *"unbekannt" (U)* gesetzt, *"Birthdate"* ist optional.  
Nach dem Sichern werden Sie auf die Detailansicht des Kunden (siehe Abbildung 9) weitergeleitet.  

![image](https://github.com/user-attachments/assets/b5f469ce-e7a0-4f24-9578-bff2b7cb8977)  
*Abbildung 10: Anlage eines neuen Kunden*

![image](https://github.com/user-attachments/assets/d6cc92e7-8836-43ab-bf54-580f2e112d98)  
*Abbildung 11: Sequenzdiagramm für die beispielhafte CRUD-Operation CREATE*


**Neue Ablesung erstellen**  

Klicken Sie in der Ablesungsübersicht auf <img src="https://github.com/user-attachments/assets/59d3ecc2-6786-4aab-b531-553a524a38e5" width="20px" height="20px" /> *"Create Reading"*, um eine neue Ablesung zu erstellen.  
Füllen Sie die angezeigten Felder mit Ablesungen.  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/d28a9755-258a-49ed-9fba-13ef1bf78375" width="20px" height="20px" /> *"Cancel"*, um den Vorgang abzubrechen und zurück auf die Ablesungsübersicht zu gelangen.  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/5509110b-b5c6-4519-a861-d90fc445b83f" width="20px" height="20px" /> *"Save"*, um die neue Ablesung in der Datenbank zu speichern.  
Die Felder *"Customer"*, *"Date of Reading"*, *"Meter ID"* und *"Meter Count"* sind Pflichtfelder, "*Meter Type"* ist standardmäßig auf *"HEIZUNG"* und *"Substitute"* auf *"No"* gesetzt, *"Comment"* ist optional.  
Nach dem Sichern werden Sie auf die Detailansicht der Ablesung weitergeleitet.  

![image](https://github.com/user-attachments/assets/714267de-082f-4b42-9840-d68dfbc5b4a1)  
*Abbildung 12: Detailansicht einer Ablesung (Testdaten)*  

Sollte der Kunde, für den eine Ablesung erstellt werden soll, noch nicht existieren:  
Klicken Sie auf <img src="https://github.com/user-attachments/assets/73face5f-85ce-419f-b72d-01e55f5f11b2" width="20px" height="20px" /> *"Create and add Customer"*, um einen neuen Kunden anzulegen (siehe Abbildung 10) und der Ablesung hinzuzufügen.  

![image](https://github.com/user-attachments/assets/88a5b819-db01-49b2-ab59-854df33f14a3)  
*Abbildung 13: Anlage einer neuen Ablesung*  
