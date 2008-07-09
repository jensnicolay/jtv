package org.jtv.backend;

import java.util.SortedSet;
import java.util.TreeSet;

import org.jtv.common.RecordingData;
import org.jtv.common.TvRecordingInfo;


public class VolatileTvRecordingInfo implements TvRecordingInfo
{

  private SortedSet<RecordingData> datas;
  
  public VolatileTvRecordingInfo()
  {
    super();
    datas = new TreeSet<RecordingData>();
  }
  
  public synchronized SortedSet<RecordingData> getFuture(long start)
  {
    for (RecordingData data : datas)
    {
      if (data.getStart() >= start)
      {
        return datas.tailSet(data);
      }
    }
    return new TreeSet<RecordingData>();
  }

  public synchronized void scheduleRecording(int channel, long start, long end, String name)
  {
    RecordingData data = new RecordingData(channel, start, end, name);
    datas.add(data);
  }

  public void cancelRecordingId(int id)
  {
    for (RecordingData recording : datas)
    {
      if (recording.getId() == id)
      {
        datas.remove(recording);
        break;
      }
    }
  }

}
