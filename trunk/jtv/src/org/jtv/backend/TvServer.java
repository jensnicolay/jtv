package org.jtv.backend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.jtv.common.TvController;

public class TvServer extends Thread
{  
  
  private TvController controller;
  private int port;
  private volatile boolean active;

  public TvServer(TvController controller, int port)
  {
    super();
    this.controller = controller;
    this.port = port;
  }

  public void run()
  {
    active = true;
    ServerSocket server;
    try
    {
      server = new ServerSocket(port);
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
      return;
    }
    System.out.println("TvServer: will be listening on port " + port);
    while (active)
    {
      try
      {
        Socket client = server.accept();
        System.out.println("TvServer: connected");
        byte[] buf = new byte[1024];
        int requestSize = client.getInputStream().read(buf);
        String request = new String(buf, 0, requestSize);
        System.out.println("TvServer.run: " + request);
        String[] text = request.split("\\s");
        if (text.length > 1 && "GET".equals(text[0]) && text[1].startsWith("/"))
        {
          String[] requested = text[1].substring(1).split("\\?");
          String requestName = requested[0];
          Properties propertiesIn = new Properties();
          if (requested.length > 1)
          {
            propertiesIn.load(new ByteArrayInputStream(requested[1].replace('&', '\n').getBytes()));
          }
          System.out.print("TvServer: request name '" + requestName + "' ");
          propertiesIn.list(System.out);
          OutputStream out = client.getOutputStream();
          out.write("HTTP/1.0 200 OK\n\n".getBytes());
          out.flush();
          if ("watch".equals(requestName))
          {
            controller.watch(Integer.parseInt(propertiesIn.getProperty("tunerNumber")), out);
          }
        }
        else if (text.length == 1)
          {
            if ("STOP".equals(text[0]))
            {
              active = false;
              System.out.println("TvServer: stop issued");
            }
          }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    System.out.println("tvserver: stopped");
  }

  public void close()
  {
    try
    {
      Socket socket = new Socket("localhost", port);
      socket.getOutputStream().write("STOP\n".getBytes());
      socket.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
