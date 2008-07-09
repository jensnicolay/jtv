package org.jtv.common;

import java.util.SortedSet;
import java.util.TreeSet;


public class RecordingTimeWindow
{
  private long start;
  private long end;
  private SortedSet<RecordingTimeData> timeDatas;
  
  public RecordingTimeWindow(long start, long end)
  {
    super();
    this.start = start;
    this.end = end;
    timeDatas = new TreeSet<RecordingTimeData>();
  }
  
  public void add(RecordingData data)
  {
    if (data.getEnd() < start || data.getStart() > end)
    {
      return;
    }
    
    timeDatas.add(new RecordingTimeData(true, Math.max(data.getStart(), start), data));
    timeDatas.add(new RecordingTimeData(false, Math.min(data.getEnd(), end), data));
  }
  
  public int tunersNeeded()
  {
    int tunersUsed = 0;
    int tunersNeeded = 0;
    for (RecordingTimeData timeData : timeDatas)
    {
      if (timeData.isStartTime())
      {
        tunersUsed++;
        tunersNeeded = Math.max(tunersNeeded, tunersUsed);
      }
      else
      {
        tunersUsed--;
      }
    }
    return tunersNeeded;
  }
  
  public String toString()
  {
    return timeDatas.toString();
  }
}