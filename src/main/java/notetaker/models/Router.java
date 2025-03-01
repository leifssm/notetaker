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
  private final Map<String, Supplier<@NotNull Parent>> routes = new HashMap<>();
  private final SimpleStringProperty route = new SimpleStringProperty("/");
  private final SimpleObjectProperty<Parent> view = new SimpleObjectProperty<>(null);

  public Router() {
    route.addListener((observable, oldValue, newValue) -> {
      if (routes.containsKey(newValue)) {
        view.set(routes.get(newValue).get());
      } else {
        view.set(null);
      }
    });
  }
  
  private boolean isValidRoute(@NotNull String route) {
    return route.matches("/[\\w/]*");
  }

  public void gotoRoute(@NotNull String newRoute) {
    if (!isValidRoute(newRoute)) {
      throw new IllegalArgumentException("Invalid route");
    }
    route.set(newRoute);
  }
  
  public void addRoute(@NotNull String route, @Nullable Supplier<@NotNull Parent> view) {
    if (routes.containsKey(route)) {
      throw new IllegalArgumentException("Route already exists");
    }
    if (!isValidRoute(route)) {
      throw new IllegalArgumentException("Invalid route");
    }

    routes.put(route, view);
  }

  public SimpleStringProperty routeProperty() {
    return route;
  }

  public @NotNull String getRoute() {
    return route.get();
  }

  public SimpleObjectProperty<Parent> viewProperty() {
    return view;
  }

  public @Nullable Parent getView() {
    return view.get();
  }
}
