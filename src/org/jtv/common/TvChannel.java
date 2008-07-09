package org.jtv.common;

import java.beans.ConstructorProperties;


public class TvChannel
{
  private int number;
  private String name;
  private int frequency;
  
  @ConstructorProperties({"number", "name", "frequency"})
  public TvChannel(int number, String name, int frequency)
  {
    super();
    this.number = number;
    this.name = name;
    this.frequency = frequency;
  }
  
  public String getName()
  {
    return name;
  }
  
  public int getFrequency()
  {
    return frequency;
  }
  
  public int getNumber()
  {
    return number;
  }

  public String toString()
  {
    return "[" + getNumber() + " " + getName() + "]";
  }

  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + number;
    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final TvChannel other = (TvChannel) obj;
    if (number != other.number)
      return false;
    return true;
  }
  
}
