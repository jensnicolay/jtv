package org.jtv.common;

import java.util.SortedSet;


public interface TvRecordingInfo
{
  SortedSet<RecordingData> getFuture(long from);
  void scheduleRecording(int channelNumber, long start, long end, String name);
  void cancelRecordingId(int id);
}