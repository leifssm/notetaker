package notetaker.models;

import java.io.FileNotFoundException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoginHandler {
  private final @NotNull String userFile;

  public LoginHandler(@NotNull String userFile) {
    this.userFile = userFile;
  }

  public void login(String username, String password) throws LoginError {
    String storedPassword = getPassword(username);
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

    validateUsername(username);
    validatePassword(password);
    
    try {
      // Stored in plaintext for readability, but should be salted and hashed in a
      // real application
      FileHandler.appendToFile(this.userFile, username + "|" + password + "\n");
    } catch (FileNotFoundException e) {
      throw new LoginError("Could not register user, internal error");
    }
  }

  private @Nullable String getPassword(@NotNull String username) throws LoginError {
    for (UserProvider.User user : new UserProvider(this.userFile)) {
      if (user.username().equals(username)) {
        return user.password();
      }
    }
    return null;
  }

  private boolean userExists(String user) throws LoginError {
    return getPassword(user) != null;
  }

  private void validateUsername(@NotNull String username) throws LoginError {
    if (!username.matches("[\\w-]{2,16}")) {
      throw new LoginError(
          "Username must be 2-16 characters long and contain only letters, numbers, hyphens, and underscores");
    }
  }

  private void validatePassword(@NotNull String password) throws LoginError {
    if (!password.matches("[\\w-]{8,}")) {
      throw new LoginError(
          "Password must be at least 8 characters long and contain only letters, numbers, hyphens, and underscores");
    }
  }

  public static class LoginError extends Exception {
    public LoginError(String message) {
      super(message);
    }
  }
}
