package notetaker.models;

import javafx.beans.property.SimpleStringProperty;

public class Globals {
  public final SimpleStringProperty user = new SimpleStringProperty(null);
  public final Router router = new Router();
  public Globals() {}
}
