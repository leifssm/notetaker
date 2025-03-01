
# Notetaker

Notetaker is a JavaFX application created as a school project. The application allows multiple users to sign into their secret notes, and create new, remove old, and rename and edit existing notes. You can either create your own user, or log in with the existing username `leif` and password `password`.

The application has automatic rename protection, which makes sure that the files dont overwrite eachother, and automatic naming, which makes sure that creating a new file will always result in it picking a name that hasnt been taken.


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

## Features

- Multiple users
- Creation of new users
- Password protected notes
- Creation and deletion of notes
- Renaming and editing of notes


## Running Tests

To run tests, run the following command

```bash
  mvn test
```
