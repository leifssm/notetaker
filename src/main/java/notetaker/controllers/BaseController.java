package notetaker.controllers;

import org.jetbrains.annotations.NotNull;

import notetaker.models.Globals;

public interface BaseController {
  public void setGlobals(@NotNull Globals globals);
}
