/**
 * LOGBack: the reliable, fast and flexible logging library for Java.
 *
 * Copyright (C) 1999-2006, QOS.ch
 *
 * This library is free software, you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation.
 */
package ch.qos.logback.core.util;

import java.util.Iterator;

import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusManager;

public class StatusPrinter {

  public static void print(StatusManager sm) {
  	
    Iterator it = sm.iterator();
    while(it.hasNext()) {
      Status s = (Status) it.next();
      System.out.println(s);
      if(s.getThrowable() != null) {
        s.getThrowable().printStackTrace(System.out);
      }
    }
  }
}
