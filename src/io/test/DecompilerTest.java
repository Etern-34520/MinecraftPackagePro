package io.test;

import controller.DecompilerGui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class DecompilerTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		DecompilerGui decompilerGui = new DecompilerGui();
		Scene scene = new Scene(decompilerGui);
		stage.setScene(scene);
		JMetro jMetro = new JMetro();
		jMetro.setScene(scene);
		jMetro.setStyle(Style.DARK);
		stage.show();
	}
}
