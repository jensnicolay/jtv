package org.jtv.backend;

import java.util.SortedSet;

import javax.management.MXBean;

import org.jtv.common.RecordingData;
import org.jtv.common.TvControllerResult;

@MXBean
public interface TvControllerMXBean
{
  TvControllerResult changeChannel(int tunerNumber, int channel);  
  TvControllerResult synchronizeRecordingInfo();
  TvControllerResult scheduleRecording(int channel, long start, long end, String name);
  TvControllerResult cancelRecordingId(int id);
  
  long getTime();
  int getNumberOfTuners();  
  int getChannel(int tunerNumber);
  SortedSet<RecordingData> getRecordingsFrom(long start);
}
