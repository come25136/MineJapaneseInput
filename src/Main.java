import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends NativeKeyAdapter {
	private Robot robot;
	private int ent_flag = 0;
	private int t_flag = 0;
	private int slash_flag = 0;

	public Main() throws Exception {
		robot = new Robot();
		Logger logger = Logger.getLogger("GlobalScreen");
		logger.setLevel(Level.WARNING);
		SystemTray.getSystemTray().add(createMenu());

		if (!GlobalScreen.isNativeHookRegistered()) {
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		GlobalScreen.addNativeKeyListener(this);
	}

	public static Image roadImageInJar(String path) throws IOException {
		return new ImageIcon(ClassLoader.getSystemClassLoader().getResource(path)).getImage();
	}

	public static TrayIcon createMenu() throws IOException {
		PopupMenu menu = new PopupMenu();
		MenuItem exitItem = new MenuItem("終了");
		exitItem.addActionListener(e -> System.exit(0));
		menu.add(exitItem);
		return new TrayIcon(roadImageInJar("icon/icon.png"), "MJI", menu);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
		if(nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ENTER) {
			if(t_flag == 1 && ent_flag == 1) {
				this.pushHankaku();
				t_flag = 1;
				ent_flag = 0;
			} else if (slash_flag == 1) {
				slash_flag = 0;
			} else {
				ent_flag = 1;
			}
			return;
		} else if(nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_T) {
			this.pushHankaku();
			t_flag = 1;
		} else if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_SLASH){
			slash_flag = 1;
		}
		ent_flag = 0;
	}

	public void pushHankaku() {
		robot.keyPress(KeyEvent.VK_HALF_WIDTH);
		robot.keyRelease(KeyEvent.VK_HALF_WIDTH);
	}

	public static void main(String[] args) {
		try {
			Main main = new Main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}