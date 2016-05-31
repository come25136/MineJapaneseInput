import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Created by syuchan on 2016/05/31.
 */
public abstract class NativeKeyAdapter implements NativeKeyListener {
	public void nativeKeyPressed(NativeKeyEvent e) {}

	public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}

	public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}
}
