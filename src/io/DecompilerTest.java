package io;

import controller.DecompilerGui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.File;

public class DecompilerTest extends Application {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//long startTime = System.currentTimeMillis();
		launch(args);
		//System.out.println("解压缩jar中");
		//long endTime = System.currentTimeMillis();
		//long usedTime = (endTime-startTime)/1000;
		//System.out.println(usedTime);
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
