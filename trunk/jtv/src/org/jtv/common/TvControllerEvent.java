package org.jtv.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class TvControllerEvent implements Serializable
{
  
  private static final long serialVersionUID = 1L;

  public enum Type
  {
    ERROR, STOPPED,
    CHANNEL_CHANGED, 
    SCHEDULED_RECORDING, STARTED_RECORDING, STOPPED_RECORDING, SYNCHRONIZED_RECORDINGS
  }
  
  private Map<String, Object> properties;
  
  public TvControllerEvent(Type event, String message)
  {
    super();
    properties = new HashMap<String, Object>();
    properties.put("event", event);
    properties.put("message", message);
  }
  
  public Type getEvent()
  {
    return (Type) properties.get("event");
  }
  
  public String getMessage()
  {
    return getString("message");
  }
  
  public String getString(String key)
  {
    return (String) properties.get(key);
  }
  
  public int getInt(String key)
  {
    return (Integer) properties.get(key);
  }
  
  public void addProperty(String name, Object value)
  {
    properties.put(name, value);
  }
  
  public String toString()
  {
    return properties.toString();
  }
}
