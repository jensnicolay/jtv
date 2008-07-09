package org.jtv.common;

import java.util.HashSet;
import java.util.Set;


public class TvControllerObservers
{
  private Set<TvControllerObserver> observers;
  
  public TvControllerObservers()
  {
    super();
    observers = new HashSet<TvControllerObserver>();
  }
  
  public synchronized void event(TvControllerEvent event)
  {
    for (TvControllerObserver observer : observers)
    {
      observer.event(event);
    }
  }
  
  public synchronized void addObserver(TvControllerObserver observer)
  {
    observers.add(observer);
  }

  public synchronized void removeObserver(TvControllerObserver observer)
  {
    observers.remove(observer);
  }

}
