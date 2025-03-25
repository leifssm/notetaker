package notetaker.controllers;

import org.jetbrains.annotations.NotNull;

import notetaker.models.Globals;

public interface BaseController {
  void setGlobals(@NotNull Globals globals);
}
