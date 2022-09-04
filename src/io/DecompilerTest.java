package io;

import controller.DecompilerGui;
import controller.TTrayIcon;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.util.Optional;

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
