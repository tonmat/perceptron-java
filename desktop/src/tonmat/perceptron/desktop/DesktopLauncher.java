package tonmat.perceptron.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import tonmat.perceptron.Perceptron;

public class DesktopLauncher {
	public static void main(String[] arg) {
		final var config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Perceptron");
		config.setWindowedMode(800, 800);
		config.setBackBufferConfig(8, 8, 8, 0, 0, 0, 4);
		new Lwjgl3Application(new Perceptron(), config);
	}
}
