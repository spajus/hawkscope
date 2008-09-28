/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AboutFrame.java
 *
 * Created on Sep 28, 2008, 12:17:00 PM
 */

package com.varaneckas.hawkscope.gui;

import java.awt.Desktop;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * About box
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AboutFrame extends javax.swing.JFrame {

    /**
     * Singleton instance
     */
    private static AboutFrame instance = null;

    /**
     * Singleton instance getter
     * 
     * @return instance
     */
    public static AboutFrame getInstance() {
        if (instance == null) {
            instance = new AboutFrame();
        }
        return instance;
    }
    
    /**
     * Serial Version UID 
     */
    private static final long serialVersionUID = 2324352047369162981L;
    
    /** Creates new form AboutFrame */
    private AboutFrame() {
        initComponents();
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice().getDisplayMode();
        setLocation(dm.getWidth() / 2 - getWidth() / 2, dm.getHeight() / 2 - getHeight() / 2);
    }

    /** 
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appName = new javax.swing.JLabel();
        appReleased = new javax.swing.JLabel();
        appHomepage = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        appDescription = new javax.swing.JLabel();
        labelVersion = new javax.swing.JLabel();
        appVersion = new javax.swing.JLabel();
        labelReleased = new javax.swing.JLabel();
        labelHomepage = new javax.swing.JLabel();
        logoPanel = new javax.swing.JPanel();
        hawkscopeLogo = new javax.swing.JLabel();

        setTitle("About");
        setResizable(false);

        appName.setFont(appName.getFont().deriveFont(appName.getFont().getStyle() | java.awt.Font.BOLD, appName.getFont().getSize()+3));
        appName.setText("Hawkscope");

        appReleased.setFont(appReleased.getFont());
        appReleased.setText("XXXX-XX-XX");

        appHomepage.setFont(appHomepage.getFont());
        appHomepage.setForeground(new java.awt.Color(0, 22, 255));
        appHomepage.setText("http://hawkscope.googlecode.com");
        appHomepage.setToolTipText("Click to open in browser");

        closeButton.setFont(closeButton.getFont());
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        appDescription.setFont(appDescription.getFont());
        appDescription.setText("Access anything with single click");

        labelVersion.setFont(labelVersion.getFont().deriveFont(labelVersion.getFont().getStyle() | java.awt.Font.BOLD));
        labelVersion.setText("Version:");

        appVersion.setFont(appVersion.getFont());
        appVersion.setText("X.X.X");

        labelReleased.setFont(labelReleased.getFont().deriveFont(labelReleased.getFont().getStyle() | java.awt.Font.BOLD));
        labelReleased.setText("Released:");

        labelHomepage.setFont(labelHomepage.getFont().deriveFont(labelHomepage.getFont().getStyle() | java.awt.Font.BOLD));
        labelHomepage.setText("Homepage:");

        logoPanel.setLayout(new java.awt.BorderLayout());

        hawkscopeLogo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hawkscopeLogo.setToolTipText("Click to visit Homepage");
        hawkscopeLogo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        logoPanel.add(hawkscopeLogo, java.awt.BorderLayout.CENTER);
        hawkscopeLogo.setIcon(IconFactory.getUncachedIcon("hawkscope128.png"));
        hawkscopeLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(Version.HOMEPAGE));
                } catch (final Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(appName)
                    .addComponent(appDescription)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelReleased)
                                .addComponent(labelVersion))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(appVersion)
                                .addComponent(appReleased))
                            .addGap(149, 149, 149))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelHomepage)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(appHomepage)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                .addGap(24, 24, 24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(appName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelVersion)
                            .addComponent(appVersion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelReleased)
                            .addComponent(appReleased))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelHomepage)
                            .addComponent(appHomepage)))
                    .addComponent(logoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addContainerGap())
        );

        appName.setText(Version.formatString());
        appReleased.setText(Version.VERSION_DATE);
        appHomepage.setText(Version.HOMEPAGE);
        appHomepage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(appHomepage.getText()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        appDescription.setText(Version.APP_SLOGAN);
        appVersion.setText(Version.VERSION_NUMBER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
}//GEN-LAST:event_closeButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AboutFrame af = new AboutFrame();
                af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                af.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appDescription;
    private javax.swing.JLabel appHomepage;
    private javax.swing.JLabel appName;
    private javax.swing.JLabel appReleased;
    private javax.swing.JLabel appVersion;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel hawkscopeLogo;
    private javax.swing.JLabel labelHomepage;
    private javax.swing.JLabel labelReleased;
    private javax.swing.JLabel labelVersion;
    private javax.swing.JPanel logoPanel;
    // End of variables declaration//GEN-END:variables

}