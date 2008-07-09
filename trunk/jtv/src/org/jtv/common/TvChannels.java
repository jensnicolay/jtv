package org.jtv.common;

import java.util.ArrayList;
import java.util.List;


public class TvChannels
{
  private List<TvChannel> channels;
  
  public TvChannels(TvChannel... channels)
  {
    super();
    ArrayList<TvChannel> sparseChannels = new ArrayList<TvChannel>();
    for (TvChannel channel : channels)
    {
      int size = sparseChannels.size();
      for (int i = 0; i < channel.getNumber() - size + 1; i++)
      {
        sparseChannels.add(null);
      }
      sparseChannels.set(channel.getNumber(), channel);
    }
    this.channels = sparseChannels;
  }
  
  public TvChannel get(int i)
  {
    return channels.get(i); 
  }
  
  public TvChannel getFirst()
  {
    for (TvChannel channel : channels)
    {
      if (channel != null)
      {
        return channel;
      }
    }
    return null;
  }
  
  
}
