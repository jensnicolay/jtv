package org.jtv.backend;

import java.io.InputStream;

public interface TvTuner
{
  void close();
  void setFrequency(int frequency);
  int getFrequency();
  void updateFrequency();
  void initTuner();
  void lockFrequency();
  void unlockFrequency();
  boolean isFrequencyLocked();
  InputStream getInputStream();
}