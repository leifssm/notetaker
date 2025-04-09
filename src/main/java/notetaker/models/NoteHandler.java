package notetaker.models;

import java.io.FileNotFoundException;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NoteHandler {
  private final @NotNull FileNamer fileNamer = new FileNamer();
  private @Nullable FileAccessHandler fileAccessHandler;

  public @NotNull List<@NotNull String> getNotes(@NotNull String user) {
    return FileHandler.getFilesInDirectory(getNotePath(user, null), true);
  }

  public @NotNull String getNotePath(@NotNull String user, @Nullable String note) {
    if (note == null) {
      return "notes/" + user + "/";
    }
    return "notes/" + user + "/" + note;
  }

  public void closeNote() {
    fileAccessHandler = null;
  }
  public void openNote(@NotNull String user, @NotNull String fileName) throws FileNotFoundException {
    fileAccessHandler = new FileAccessHandler(getNotePath(user, fileName));
  }

  public @NotNull String readCurrentNoteContent() {
    if (fileAccessHandler == null) {
      throw new IllegalStateException("To read a note a note has to be opened first");
    }
    return fileAccessHandler.getContent();
  }

  public @Nullable String createNewNote(@NotNull String user) {
    String noteName = fileNamer.getValidName("New Note.txt", getNotes(user));
    if (noteName == null) {
      throw new RuntimeException("Could not create new note, error should not happen xd");
    }
    boolean success = FileHandler.createFile(getNotePath(user, noteName));

    return success ? noteName : null;
  }

  public boolean deleteNote(@NotNull String user, @NotNull String fileName) {
    FileHandler.deleteFile(getNotePath(user, fileName));
    boolean deletedCurrentOpenNote = fileAccessHandler != null
        && fileAccessHandler.getPath().equals(getNotePath(user, fileName));
    fileAccessHandler = null;
    return deletedCurrentOpenNote;
  }

  public @NotNull String renameCurrentNote(String newName) {
    if (fileAccessHandler == null) {
      return "";
    }
    boolean success = fileAccessHandler.rename(newName);
    return success ? newName : fileAccessHandler.getName();
  }

  public boolean notifyContentChanged(String content) {
    if (fileAccessHandler != null) {
      fileAccessHandler.setContent(content);
      return fileAccessHandler.hasChanged();
    }
    return false;
  }

  public void saveCurrentFile() {
    if (fileAccessHandler != null) {
      fileAccessHandler.save();
    }
  }
}
