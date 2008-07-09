package org.jtv.common;

import java.io.OutputStream;
import java.util.SortedSet;


public interface TvController
{
  enum Operation
  {
    WATCH, CHANGE_CHANNEL, SYNCHRONIZE_RECORDING_INFO, SCHEDULE_RECORDING, CANCEL_RECORDING_ID
  }

  TvControllerResult watch(int tunerNumber, OutputStream out);
  TvControllerResult changeChannel(int tunerNumber, int channel);  
  TvControllerResult synchronizeRecordingInfo();
  TvControllerResult scheduleRecording(int channel, long start, long end, String name);
  TvControllerResult cancelRecordingId(int id);
  
  long getTime();
  int getNumberOfTuners();  
  int getChannel(int tunerNumber);
  SortedSet<RecordingData> getRecordingsFrom(long start);

  TvControllerObservers getObservers();
  void close();
}