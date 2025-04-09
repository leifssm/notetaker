package notetaker.models;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.util.Iterator;

public class UserProvider implements Iterable<UserProvider.User> {
  private final String userFile;

  public UserProvider(String userFile) {
    this.userFile = userFile;
  }
  @NotNull
  @Override
  public Iterator<User> iterator() {
    try {
      String[] lines = FileHandler.readFile(this.userFile).split("\n");
      return new UserIterator(lines);
    } catch (FileNotFoundException e) {
      return new UserIterator(new String[0]);
    }
  }

  private static class UserIterator implements Iterator<User> {
    private final String[] lines;
    private int index = 0;

    public UserIterator(String[] lines) {
      this.lines = lines;
    }

    @Override
    public boolean hasNext() {
      return index < lines.length;
    }

    @Override
    public User next() {
      String line = lines[index];
      index++;
      String[] parts = line.split("\\|");
      if (parts.length != 2) {
        throw new IllegalStateException("Invalid user data");
      }
      return new User(parts[0], parts[1].trim());
    }
  }
  public record User(String username, String password) {}
}
