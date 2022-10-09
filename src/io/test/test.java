package io.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class test extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Tree View Sample"); 
		StackPane root = new StackPane();
		//TreeView<String> treeview=new TreeView<String>();
		TreeView<String> treeview_ = new TreeView<String>();
		root.getChildren().add(treeview_);
		Scene scene=new Scene(root, 300, 250);
		primaryStage.setScene(scene);
		JMetro jMetro = new JMetro(Style.LIGHT);
		jMetro.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
