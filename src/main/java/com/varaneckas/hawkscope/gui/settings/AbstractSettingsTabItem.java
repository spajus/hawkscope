package com.varaneckas.hawkscope.gui.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.SharedStyle;

/**
 * Settings {@link TabFolder} {@link TabItem}
 * 
 * Extend this to plug into {@link SettingsWindow}'s tab folder.
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class AbstractSettingsTabItem {
    
    /**
     * Logger
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * Settings {@link TabFolder}
     */
	protected final TabFolder folder;

	/**
	 * This {@link TabItem}
	 */
	protected final TabItem item;
	
	/**
	 * Layout container for this {@link TabItem}
	 */
	protected final Composite container;
	
	/**
	 * Instance of current {@link Configuration}
	 */
	protected static final Configuration cfg = ConfigurationFactory
		.getConfigurationFactory().getConfiguration();
	
	/**
	 * Constructor that requires Settings {@link TabFolder} and Tab title
	 * 
	 * @param folder Settings {@link TabFolder}
	 * @param title Title for the new {@link TabItem}
	 */
	public AbstractSettingsTabItem(final TabFolder folder, final String title) {
		this.folder = folder;
		item = new TabItem(folder, SWT.NONE);
		item.setText(title);
		container = new Composite(folder, SWT.NONE);
		container.setLayout(SharedStyle.LAYOUT);
		item.setControl(container);
	}
	
	/**
	 * Idents the layout data from left. Use for leftmost {@link Control}s
	 * after section labels.
	 * 
	 * @param source Layout data to ident
	 * @return idented layout data
	 */
	public FormData ident(final FormData source) {
		final FormAttachment left = source.left;
		if (left == null) {
			source.left = new FormAttachment(SharedStyle.LAYOUT.marginWidth, 0);
		} else if (left.offset == 0) {
			left.offset = SharedStyle.LAYOUT.marginWidth;
		}
		return source;
	}
	
	/**
	 * Creates a Label with <b>bold</b> text 
	 * 
	 * @param text Text for the section
	 * @return {@link Label} with <b>bold</b> text
	 */
	protected Label addSectionLabel(final String text) {
		final Label l = new Label(container, SWT.NONE);
		l.setText(text);
		l.setFont(SharedStyle.FONT_BOLD);
		return l;
	}
	
	/**
	 * Adds a Checkbox ({@link Button})
	 * 
	 * @param text Message near the checkbox
	 * @return Checkbox
	 */
	protected Button addCheckbox(final String text) {
	    final Button c = new Button(container, SWT.CHECK | SWT.LEFT);
        c.setText(text);
        return c;
	}
	
	/**
	 * Adds a {@link Label}
	 * 
	 * @param text Text for the label
	 * @return Label
	 */
	protected Label addLabel(final String text) {
		final Label l = new Label(container, SWT.NONE);
		l.setText(text);
		return l;
	}
	
	/**
	 * Adds a push {@link Button}
	 * 
	 * @param text Text on button
	 * @return Button
	 */
	protected Button addButton(final String text) {
	    final Button b = new Button(container, SWT.PUSH);
	    b.setText(text);
	    return b;
	}
	
	/**
	 * Adds a {@link Text} input
	 * @param text Default value
	 * @param limit Max length. 0 - unlimited.
	 * @return Text input
	 */
	protected Text addText(final String text, final int limit) {
		final Text t = new Text(container, SWT.BORDER);
		t.setText(text);
		if (limit > 0) {
		    t.setTextLimit(limit);
		}
		return t;
	}
	
	/**
	 * Implementation should set {@link #cfg} properties.
	 * 
	 * @see #cfg
	 */
	abstract protected void saveConfiguration();
	
}
