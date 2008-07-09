package org.jtv.common;

import java.beans.ConstructorProperties;

public class TvControllerResult
{

  private TvController.Operation operation;
  private boolean ok;
  private String message;

  @ConstructorProperties({"operation", "ok", "message"})
  public TvControllerResult(TvController.Operation operation, boolean ok, String message)
  {
    super();
    this.operation = operation;
    this.ok = ok;
    this.message = message;
  }

  public TvController.Operation getOperation()
  {
    return operation;
  }

  public boolean isOk()
  {
    return ok;
  }

  public String getMessage()
  {
    return message;
  }

  public String toString()
  {
    return "[" + operation + " " + ok + "]";
  }
}
