package org.jtv.backend;

import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.jtv.common.TvController;
import org.jtv.common.TvControllerObserver;
import org.jtv.frontend.ConsoleTvController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringTvBackend
{

  public static void main(String[] args)
  {
    PropertyConfigurator.configure("log4j.properties");

    System.out.println("== starting...");
    ConfigurableApplicationContext applicationContext = new FileSystemXmlApplicationContext("tvBackend.xml");
    applicationContext.registerShutdownHook();
    
    System.out.println("= getting master controller");
    TvController master = (TvController) applicationContext.getBean("masterController");
    
    System.out.println("= registering observers for master controller");
    Map<String, TvControllerObserver> observers = applicationContext.getBeansOfType(TvControllerObserver.class);
    for (Map.Entry<String, TvControllerObserver> observerEntry : observers.entrySet())
    {
      System.out.println(observerEntry.getKey() + " (" + observerEntry.getValue() + ")");
      master.getObservers().addObserver(observerEntry.getValue());
    }

    System.out.println("= getting tv server");
    TvServer server = (TvServer) applicationContext.getBean("tvServer");
    server.start();
    System.out.println("== started");
    ConsoleTvController cte = new ConsoleTvController(master);
    cte.repLoop(System.in);
    System.out.println("== stopping...");
    applicationContext.close();
    System.out.println("== stopped");
  }
}