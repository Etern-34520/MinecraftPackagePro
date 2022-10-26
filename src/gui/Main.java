package gui;

import controller.EditPane;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.util.prefs.Preferences;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override//36,8
	public void start(Stage stage) throws Exception {
		Preferences rootPreferences = Preferences.userRoot();// 使用上次关闭时的位置和大小
		double x = rootPreferences.getDouble("X", 265);
		double y = rootPreferences.getDouble("Y", 140);
		double width = rootPreferences.getDouble("W", 1350);
		double height = rootPreferences.getDouble("H", 730);
		System.out.println("Program Start");
		System.out.println("Location:[x:"+x+"y:"+y+"]Size:["+width+"X"+height+"]");
		System.out.println("information");
		System.out.println("──────────────────────────────");
		EditPane editPane=new EditPane();
		Scene scene = new Scene(editPane,width,height);
		stage.setScene(scene);
		JMetro jMetro = new JMetro(Style.DARK);
		jMetro.setScene(scene);
		stage.setX(x);
		stage.setY(y);
		stage.show();
		stage.setOnCloseRequest(event -> {
			double x1 =stage.getX();
			double y1 =stage.getY();
			double width1 =scene.getWidth();
			double height1 =scene.getHeight();
			Preferences preferences = Preferences.userRoot();
			preferences.putDouble("X", x1);
			preferences.putDouble("Y", y1);
			preferences.putDouble("W", width1);
			preferences.putDouble("H", height1);
			// io.Settings.save();
			System.out.println("──────────────────────────────");
			System.out.println("Program Over");
			System.out.println("Location:[x:"+ x1 +"y:"+ y1 +"]Size:["+ width1 +"X"+ height1 +"]");
		});
		stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
			// TODO 自动生成的方法存根
			editPane.fileTree.refresh();
		});
	}

}
