package notetaker.models;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;

public class Router {
  static private Map<String, Supplier<@NotNull Parent>> routes = new HashMap<>();
  static private SimpleStringProperty route = new SimpleStringProperty("/");
  static private SimpleObjectProperty<Parent> view = new SimpleObjectProperty<>(null);

  static {
    route.addListener((observable, oldValue, newValue) -> {
      if (routes.containsKey(newValue)) {
        view.set(routes.get(newValue).get());
      } else {
        view.set(null);
      }
    });
  }
  
  static private boolean isValidRoute(@NotNull String route) {
    return route.matches("/[\\w/]*");
  }

  static public void gotoRoute(@NotNull String route) {
    if (!isValidRoute(route)) {
      throw new IllegalArgumentException("Invalid route");
    }
    Router.route.set(route);
  }
  
  static public void addRoute(@NotNull String route, @Nullable Supplier<@NotNull Parent> view) {
    if (routes.containsKey(route)) {
      throw new IllegalArgumentException("Route already exists");
    }
    if (!isValidRoute(route)) {
      throw new IllegalArgumentException("Invalid route");
    }

    routes.put(route, view);
  }

  static public SimpleStringProperty routeProperty() {
    return route;
  }

  static public @NotNull String getRoute() {
    return route.get();
  }

  static public SimpleObjectProperty<Parent> viewProperty() {
    return view;
  }

  static public @Nullable Parent getView() {
    return view.get();
  }
}
