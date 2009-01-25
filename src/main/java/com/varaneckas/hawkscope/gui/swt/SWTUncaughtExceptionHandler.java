package com.varaneckas.hawkscope.gui.swt;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.util.IOUtils;

/**
 * Registers uncaught exception handler that displays error message inside
 * Swing dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTUncaughtExceptionHandler implements UncaughtExceptionHandler {

    /**
     * Logger
     */
    private static final Log log = LogFactory
            .getLog(SWTUncaughtExceptionHandler.class);
    
    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        Display display = Display.getCurrent();
        Shell shell = new Shell (display);
        MessageBox message = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_ERROR);
        message.setText("Hawkscope Error");
        message.setMessage(e.getMessage() 
                + "\nCopy Hawkscope Error Report to Clipboard?");
        log.error("Uncaught exception", e);
        if (message.open() == SWT.OK) {
            IOUtils.copyToClipboard(Version.getBugReport(e));
        }
    }
}
