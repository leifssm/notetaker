package notetaker.models;

import java.io.FileNotFoundException;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class NoteHandler {
  private static @NotNull String getNotePath(@NotNull String user, @NotNull String note) {
    return "notes/" + user + "/" + note;
  }

  public static @NotNull String getNote(@NotNull String user, @NotNull String note) throws FileNotFoundException {
    return FileHandler.readFile(getNotePath(user, note));
  }
  
  public static void setNote(@NotNull String user, @NotNull String note, @NotNull String content) {
    FileHandler.createFile(getNotePath(user, note), content);
  }
  
  public static void createNote(@NotNull String user, @NotNull String note) {
    FileHandler.createFile(getNotePath(user, note));
  }
  
  public static @NotNull List<String> getNotes(@NotNull String user) {
    if (!FileHandler.directoryExists("notes/" + user)) {
      return List.of();
    }
    try {
      return FileHandler.getFilesInDirectory("notes/" + user);
    } catch (FileNotFoundException e) {
      // Should never happen
      throw new RuntimeException("Could not get notes for " + user);
    }
  }
}
