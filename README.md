
# Notetaker

Notetaker is a JavaFX application created as a school project. The application allows multiple users to log in to their secret notes, and create new, remove old, and rename and edit existing notes. You can either create your own user, or log in with the existing username `leif` and password `password`.

The application has automatic rename protection, which makes sure that the files don't overwrite each-other, and automatic naming, which makes sure that creating a new file will always result in it picking a name that hasn't been taken.

The application has manual saving, which means that the user has to save the file manually. This is done to make sure that the user is aware of what they are doing.

The user can create as many notes as they want, which will be password protected and seperated from other users. And if the user has a note with the same name as another user, the application will automatically rename the file to avoid overwriting. If the user wants to delete a note, they can do so by clicking the red delete button on the note.

![ER Diagram](er-diagram.png)
*This is an ER diagram of the classes used while a user is logged in*

## Features

- Multiple users
- Creation of new users
- Password protected notes
- Creation and deletion of notes
- Renaming and editing of notes

## Run Locally

Clone the project

```bash
git clone https://github.com/leifssm/notetaker
```

Go to the project directory

```bash
cd notetaker
```

Run the project

```bash
mvn javafx:run
```

## Running Tests

To run tests, run the following command

```bash
mvn test
```

## Appendix

For the task, the students had to also answer the following questions:

- Hvilke deler av pensum i emnet dekkes i prosjektet, og på hvilken måte? (For eksempel bruk av arv, interface, delegering osv.)
  - I prosjektet brukes arv bl.a. av LoginError som extender en Exception, og NoteItem som extender en BorderBox.
  - Interface brukes i klassen UserProvider som gir en iterable med brukere.
  - Delegering brukes bl.a. av alle kontrollerene som delegerer arbeidet til modellene. Delegering brukes også bl.a. av FileHandler som delegerer håndteringen av save-staten til filene til FileAccessHandler.
- Dersom deler av pensum ikke er dekket i prosjektet deres, hvordan kunne dere brukt disse delene av pensum i appen?
  - Jeg brukte ikke instanceof eller Comparable da disse ikke var relevant for prosjektet mitt i den skalaen den er, og håndterte ikke Input/OutputStreams direkte. Jeg brukte derimot PrintWriter, som fungerte som et mellomledd for bruk av OutputStreamWriter.
- Hvordan forholder koden deres seg til Model-View-Controller-prinsippet? (Merk: det er ikke nødvendig at koden er helt perfekt i forhold til Model-View-Controller standarder. Det er mulig (og bra) å reflektere rundt svakheter i egen kode)
  - Koden bruker MVC prinsippet ved å delegere funksjonalitet til models, ui komponenter til views og kontrolleren som en mellommann som håndterer kommunikasjonen mellom de to. Minimalt av logikk skal skje i kontrollerene som man kan se ettersom at mesteparten av arbeidet delegeres til modellene.
- Hvordan har dere gått frem når dere skulle teste appen deres, og hvorfor har dere valgt de testene dere har? Har dere testet alle deler av koden? Hvis ikke, hvordan har dere prioritert hvilke deler som testes og ikke? (Her er tanken at dere skal reflektere rundt egen bruk av tester)
  - Ikke alle deler ble testet ettersom at feilmeldinger blir håndtert i programmet med fokus på default behavior, de områdene som det trengtes tester mest er hovedsakelig filhåndteringen og filnavngiving ettersom at det er der all brukerinputen blir håndtert og må saniteres for å sikre en god brukeropplevelse.
