package GroupC;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import core.Core;

public class Launcher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Group C Final Project Test";
		config.width = 448;
		config.height = 448;
		new LwjglApplication(new Core(), config);
	}
}
