package com.varaneckas.hawkscope.gui.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.SharedStyle;

public abstract class AbstractSettingsTabItem {
    
    protected final Log log = LogFactory.getLog(getClass());

	protected final TabFolder folder;

	protected final TabItem item;
	
	protected final Composite container;
	
	protected static final Configuration cfg = ConfigurationFactory
		.getConfigurationFactory().getConfiguration();
	
	public AbstractSettingsTabItem(final TabFolder folder, final String title) {
		this.folder = folder;
		item = new TabItem(folder, SWT.NONE);
		item.setText(title);
		container = new Composite(folder, SWT.NONE);
		container.setLayout(SharedStyle.LAYOUT);
		item.setControl(container);
	}
	
	public FormData ident(final FormData source) {
		final FormAttachment left = source.left;
		if (left == null) {
			source.left = new FormAttachment(SharedStyle.LAYOUT.marginWidth, 0);
		} else if (left.offset == 0) {
			left.offset = SharedStyle.LAYOUT.marginWidth;
		}
		return source;
	}

	
	protected Label addSectionLabel(final String text) {
		final Label l = new Label(container, SWT.NONE);
		l.setText(text);
		l.setFont(SharedStyle.FONT_BOLD);
		return l;
	}
	
	protected Button addCheckbox(final String text) {
	    final Button c = new Button(container, SWT.CHECK | SWT.LEFT);
        c.setText(text);
        return c;
	}
	
	protected Label addLabel(final String text) {
		final Label l = new Label(container, SWT.NONE);
		l.setText(text);
		return l;
	}
	
	protected Button addButton(final String text) {
	    final Button b = new Button(container, SWT.PUSH);
	    b.setText(text);
	    return b;
	}
	
	protected Text addText(final String text, final int limit) {
		final Text t = new Text(container, SWT.BORDER);
		t.setText(text);
		if (limit > 0) {
		    t.setTextLimit(limit);
		}
		return t;
	}
	
	abstract protected void saveConfiguration();
	
}
