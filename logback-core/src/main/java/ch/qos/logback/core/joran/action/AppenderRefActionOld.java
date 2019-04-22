/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.core.joran.action;

import static ch.qos.logback.core.joran.JoranConstants.APPENDER_BAG;
import static ch.qos.logback.core.joran.JoranConstants.REF_ATTRIBUTE;

import java.util.HashMap;

import org.xml.sax.Attributes;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.spi.AppenderAttachable;
import ch.qos.logback.core.util.OptionHelper;

public class AppenderRefActionOld<E> extends Action {
    boolean inError = false;

    @SuppressWarnings("unchecked")
    public void begin(InterpretationContext ec, String tagName, Attributes attributes) {
        // Let us forget about previous errors (in this object)
        inError = false;

        // logger.debug("begin called");

        Object o = ec.peekObject();

        if (!(o instanceof AppenderAttachable)) {
            String errMsg = "Could not find an AppenderAttachable at the top of execution stack. Near [" + tagName + "] line " + getLineNumber(ec);
            inError = true;
            addError(errMsg);
            return;
        }

        AppenderAttachable<E> appenderAttachable = (AppenderAttachable<E>) o;

        String appenderName = ec.subst(attributes.getValue(REF_ATTRIBUTE));

        if (OptionHelper.isNullOrEmpty(appenderName)) {
            // print a meaningful error message and return
            String errMsg = "Missing appender ref attribute in <appender-ref> tag.";
            inError = true;
            addError(errMsg);

            return;
        }

        HashMap<String, Appender<E>> appenderBag = (HashMap<String, Appender<E>>) ec.getObjectMap().get(APPENDER_BAG);
        Appender<E> appender = (Appender<E>) appenderBag.get(appenderName);

        if (appender == null) {
            String msg = "Could not find an appender named [" + appenderName + "]. Did you define it below instead of above in the configuration file?";
            inError = true;
            addError(msg);
            addError("See " + CoreConstants.CODES_URL + "#appender_order for more details.");
            return;
        }

        addInfo("Attaching appender named [" + appenderName + "] to " + appenderAttachable);
        appenderAttachable.addAppender(appender);
    }

    public void end(InterpretationContext ec, String n) {
    }

}