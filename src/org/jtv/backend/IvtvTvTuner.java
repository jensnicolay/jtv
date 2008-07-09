package org.jtv.backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class IvtvTvTuner implements TvTuner
{

  private static final Logger LOGGER = Logger.getLogger(IvtvTvTuner.class); 
  
  private String device;
  private int frequency = -1;
  private boolean frequencyLocked;

  public IvtvTvTuner(String device)
  {
    super();
    this.device = device;
    initTuner();
    //updateFrequency();
  }

  public synchronized void close()
  {
//    device = null;
    frequency = -1;
    logInfo("closed");
  }

  public synchronized void setFrequency(int frequency)
  {
    if (!isFrequencyLocked())
    {
      int integerPart = frequency / 1000;
      int floatPart = frequency - integerPart * 1000;
      execute("ivtv-tune", "-d", device, "-f", integerPart + "." + floatPart);
      this.frequency = frequency;
    }
  }

  private List<String> execute(String... command)
  {
    List<String> output = new ArrayList<String>();
    logInfo("(execute in) " + Arrays.toString(command));
    ProcessBuilder pb = new ProcessBuilder(command);
    pb.redirectErrorStream(true);
    try
    {
      Process process = pb.start();
      int exitCode = process.waitFor();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = bufferedReader.readLine()) != null)
      {
        output.add(line);
        logDebug("(execute out) " + line);
      }
      logInfo("(execute out) executed with exit code " + exitCode);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return output;
  }

  public synchronized int getFrequency()
  {
    if (frequency == -1)
    {
      updateFrequency();
    }
    return frequency;
  }

  public synchronized void updateFrequency()
  {
    List<String> output = execute("v4l2-ctl", "-d", device, "-R");
    for (String line : output)
    {
      if (line.startsWith("Frequency = "))
      {
        int f = Integer.parseInt(line.substring(12));
        frequency = (int) (f * 62.5);
        break;
      }
    }
  }
  
  public synchronized void initTuner()
  {
    execute("v4l2-ctl", "-c", "volume=65536,video_bitrate=4000000,video_peak_bitrate=5600000", "-d", device);
    execute("v4l2-ctl", "-v", "width=480,height=480", "-d", device);
  }

  public String getDevice()
  {
    return device;
  }

  public synchronized void lockFrequency()
  {
    frequencyLocked = true;
  }

  public synchronized void unlockFrequency()
  {
    frequencyLocked = false;
  }

  public synchronized boolean isFrequencyLocked()
  {
    return frequencyLocked;
  }

  public synchronized InputStream getInputStream()
  {
    try
    {
      return new FileInputStream(device);
    }
    catch (Exception e)
    {
      logError("cannot obtain input stream", e);
      return null;
    }
  }

  private void logDebug(String message)
  {
    LOGGER.debug("(tuner " + device + ") " + message);
  }
  
  private void logInfo(String message)
  {
    LOGGER.info("(tuner " + device + ") " + message);
  }
  
  private void logError(String message, Throwable cause)
  {
    LOGGER.error("(tuner " + device + ") " + message, cause);
  }
  
  public String toString()
  {
    return device;
  }
}
