package org.jtv.backend;

import javax.management.Notification;

import org.jtv.common.TvControllerEvent;
import org.jtv.common.TvControllerObserver;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

public class SpringJmxTvControllerObserver implements TvControllerObserver, NotificationPublisherAware
{

  private NotificationPublisher notificationPublisher;
  private int sequence;

  public SpringJmxTvControllerObserver()
  {
    super();
  }
  
  public void event(TvControllerEvent event)
  {
    Notification notification = new Notification("tvControllerEvent", this, ++sequence, event.getMessage());
    notification.setUserData(event);
    notificationPublisher.sendNotification(notification);
  }

  public void setNotificationPublisher(NotificationPublisher notificationPublisher)
  {
    this.notificationPublisher = notificationPublisher;
  }
}
