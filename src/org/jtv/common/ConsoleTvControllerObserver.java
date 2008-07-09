package org.jtv.common;

import java.io.PrintStream;

public class ConsoleTvControllerObserver implements TvControllerObserver
{

  private String prefix;
  private PrintStream out;

  public ConsoleTvControllerObserver(String prefix)
  {
    this(prefix, System.out);
  }

  public ConsoleTvControllerObserver(String prefix, PrintStream out)
  {
    super();
    this.prefix = prefix;
    this.out = out;
  }

  public void event(TvControllerEvent event)
  {
    out.println(prefix + ": " + event.getMessage() + " (" + event + ")");
  }
}
