package hoteller.models;

public class LoginHandler {
  private final FileReader reader;

  public LoginHandler() {
    reader = new FileReader("users.txt");
  }
}
