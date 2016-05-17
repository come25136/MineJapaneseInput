import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.*;
import java.awt.event.*;

public class Main implements NativeKeyListener {
    private Robot robot = new Robot();
    int ent_flag = 0; // Enter key flag
    int t_flag = 0; // t key flag
    int slash_flag = 0; // slash key flag

    public Main() throws AWTException {
    }

    public static void main(String[] args) {
        // icon file read
        Image image = null;
        try {
            image = ImageIO.read(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("icon/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // tray icon creating
        TrayIcon icon = new TrayIcon(image);

        // pop-up menu
        PopupMenu menu = new PopupMenu();

        // exit menu
        MenuItem exitItem = new MenuItem("終了");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(exitItem);
        icon.setPopupMenu(menu);
        try {
            SystemTray.getSystemTray().add(icon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        try {
            GlobalScreen.addNativeKeyListener(new Main());
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_T) { // t key
            if (slash_flag == 0 && t_flag == 0) {
                robot.keyPress(KeyEvent.VK_HALF_WIDTH); // Press the half-size / em keys
                robot.keyRelease(KeyEvent.VK_HALF_WIDTH); // Releasing the half-size / em keys
                t_flag = 1;
            }
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) { // Enter key
            if (t_flag == 1 && ent_flag == 1) {
                Robot robot = null;
                try {
                    robot = new Robot();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
                robot.keyPress(KeyEvent.VK_HALF_WIDTH); // Press the half-size / em keys
                robot.keyRelease(KeyEvent.VK_HALF_WIDTH); // Releasing the half-size / em keys
                t_flag = 0;
                ent_flag = 0;
            } else if (slash_flag == 1) {
                slash_flag = 0;
            } else {
                ent_flag = 1;
            }
        } else {
            ent_flag = 0;
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_SLASH) { // slash key
            slash_flag = 1;
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}