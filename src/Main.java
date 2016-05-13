import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.*;
import java.awt.event.*;

public class Main implements NativeKeyListener {
    private Robot robot = new Robot();
    int ent_flag = 0;
    int t_flag = 0;

    public Main() throws AWTException {
    }

    public static void main(String[] args) {
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
        if (e.getKeyCode() == NativeKeyEvent.VC_T) {
            if (t_flag == 0) {
                robot.keyPress(KeyEvent.VK_HALF_WIDTH);
                robot.keyRelease(KeyEvent.VK_HALF_WIDTH);
                t_flag = 1;
            }
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
            if (t_flag == 1) {
                if (ent_flag == 1) {
                    Robot robot = null;
                    try {
                        robot = new Robot();
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    }
                    robot.keyPress(KeyEvent.VK_HALF_WIDTH);
                    robot.keyRelease(KeyEvent.VK_HALF_WIDTH);
                    t_flag = 0;
                    ent_flag = 0;
                } else {
                    ent_flag = 1;
                }
            }
        } else {
            ent_flag = 0;
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}