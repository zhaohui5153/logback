package ch.qos.logback.classic.spi;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

public class LoggingEventTest {

  LoggerContext loggerContext = new LoggerContext();
  Logger logger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);

  @Before
  public void setUp() {
  }


  @Test
  public void testFormattingOneArg() {
    String message = "x={}";
    Throwable throwable = null;
    Object[] argArray = new Object[] {12};

    LoggingEvent event = new LoggingEvent("", logger, Level.INFO, message, throwable, argArray);
    assertNull(event.formattedMessage);
    assertEquals("x=12", event.getFormattedMessage());

  }

}