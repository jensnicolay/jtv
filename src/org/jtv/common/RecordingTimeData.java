package org.jtv.common;



public class RecordingTimeData implements Comparable<RecordingTimeData>
{
  private long time;
  private RecordingData data;
  private boolean startTime;
  
  public RecordingTimeData(boolean startTime, long time, RecordingData data)
  {
    super();
    this.startTime = startTime;
    this.time = time;
    this.data = data;
  }
  
  public boolean isStartTime()
  {
    return startTime;
  }
  
  public RecordingData getData()
  {
    return data;
  }
  
  public long getTime()
  {
    return time;
  }
  
  public int hashCode()
  {
    final int PRIME = 31;
    int result = super.hashCode();
    result = PRIME * result + ((data == null) ? 0 : data.hashCode());
    result = PRIME * result + (startTime ? 1231 : 1237);
    result = PRIME * result + (int) (time ^ (time >>> 32));
    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    final RecordingTimeData other = (RecordingTimeData) obj;
    if (data == null)
    {
      if (other.data != null)
        return false;
    }
    else if (!data.equals(other.data))
      return false;
    if (startTime != other.startTime)
      return false;
    if (time != other.time)
      return false;
    return true;
  }

  public int compareTo(RecordingTimeData o)
  {
    int timeCompare = (int)(getTime() - o.getTime());
    if (timeCompare == 0)
    {
      if (equals(o))
      {
        return 0;
      }
      else
      {
        return 1;
      }
    }
    else
    {
      return timeCompare;
    }
  }
  
  public String toString()
  {
    return getTime() + (isStartTime() ? " start" : " end");
  }
}