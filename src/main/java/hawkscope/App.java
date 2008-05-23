package hawkscope;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

/**
 * Hello world!
 * 
 */
public class App {
    private class FileMenu extends JMenu {
        private static final long serialVersionUID = 2512823752109399974L;
        private volatile File file;

        public FileMenu(final File f) {
            this.file = f;
            if (file != null) {
                setText(file.getName());
                addMouseListener(new MouseAdapter() {
                    private boolean loaded = false;

                    @Override
                    public synchronized void mouseEntered(final MouseEvent e) {
                        if (!loaded && file != null && file.isDirectory()) {
                            for (final File ff : file.listFiles()) {
                                if (ff.isDirectory()) {
                                    add(new FileMenu(ff));
                                } else {
                                    add(new FileMenuItem(ff));
                                }

                            }
                            loaded = true;
                        }
                        e.consume();
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        e.consume();
                        if (e.getButton() != MouseEvent.BUTTON1) {
                            JPopupMenu pop = new JPopupMenu();
                            JMenuItem explore = new JMenuItem("Explore");
                            explore.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        Runtime
                                                .getRuntime()
                                                .exec(
                                                        "explorer.exe "
                                                                + file
                                                                        .getAbsolutePath());
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
                            pop.setLocation(e.getXOnScreen(), e.getYOnScreen());
                            pop.add(explore);
                            pop.setVisible(true);

                        }
                    }
                });
            }
        }

        public File getFile() {
            return file;
        }
    }

    private class FileMenuItem extends JMenuItem {
        private static final long serialVersionUID = 2512823752109399974L;
        private volatile File file;

        public FileMenuItem(final File f) {
            this.file = f;
            setText(file.getName());
        }

        public File getFile() {
            return file;
        }
    }

    public static void main(String[] args) {
        new App();
    }

    public App() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // JFrame frame = new JFrame("HD");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setVisible(true);
        // JMenuBar menubar = new JMenuBar();
        // frame.setJMenuBar(menubar);
        File[] roots = File.listRoots();
        final JMenu menu = new JMenu("Hard Drives");
        // menubar.add(menu);
        for (File root : roots) {
            final FileMenu item = new FileMenu(root);
            item.setText(root.getAbsolutePath());
            menu.add(item);
        }
        ;
        // frame.pack();

        TrayIcon ti = new TrayIcon(new ImageIcon(ClassLoader
                .getSystemClassLoader().getResource("icon-vault.png"))
                .getImage());
        final JPopupMenu pop = new JPopupMenu();
        pop.setInvoker(pop);
        pop.add(menu);
        pop.setVisible(true);
        ti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    e.consume();
                    if (e.getButton() != MouseEvent.BUTTON1) {
                        if (pop.isVisible()) {
                            pop.setVisible(false);
                        } else {
                            pop.setLocation(e.getX(), e.getY());
                            pop.setInvoker(pop);
                            pop.setVisible(true);
                        }
                    } else {
                    }
                }
                catch (Exception ae) {
                    System.out.println("tEst");
                    ae.printStackTrace();
                }                
            }
        });
        try {
            SystemTray.getSystemTray().add(ti);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
}
