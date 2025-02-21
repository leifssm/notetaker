package hoteller.models;

import java.io.FileNotFoundException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoginHandler {
  private final FileReader reader = new FileReader("users.txt");

  public LoginHandler() throws FileNotFoundException {
  }

  public void login(String username, String password) throws LoginError {
    final var storedPassword = getPassword(username);
    if (storedPassword == null) {
      throw new LoginError("User not found, please register");
    }
    if (!storedPassword.equals(password)) {
      throw new LoginError("Incorrect password, please try again");
    }
  }

  public void register(String username, String password) throws LoginError {
    if (userExists(username)) {
      throw new LoginError("User already exists, please login");
    }
    try {
      // Stored in plaintext for readability, but should be salted and hashed in a
      // real application
      reader.append(username + "|" + password + "\n");
    } catch (FileReaderError e) {
      throw new LoginError("Could not register user, internal error");
    }
  }

  private @Nullable String getPassword(@NotNull String user) {
    try {
      String[] lines = reader.read().split("\n");
      for (String line : lines) {
        String[] parts = line.split("\\|");
        if (parts.length != 2) {
          continue;
        }
        if (parts[0].equals(user)) {
          return parts[1];
        }
      }
    } catch (FileReaderError e) {
    }
    return null;
  }

  private boolean userExists(String user) {
    return getPassword(user) != null;
  }

  public class LoginError extends Exception {
    public LoginError(String message) {
      super(message);
    }
  }
}
