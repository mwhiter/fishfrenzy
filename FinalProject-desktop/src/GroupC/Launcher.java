package GroupC;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import core.Constants;
import core.Core;

public class Launcher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Group C Final Project Test";
		config.width = Constants.WIDTH;//448
		config.height = Constants.HEIGHT;//448
		new LwjglApplication(new Core(), config);
	}
}
