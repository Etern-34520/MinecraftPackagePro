package io;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Test1 extends Application{
	@FXML TabPane tabpane;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		launch(args);
	}
	Parent root;
	@Override
	public void start(Stage stage) throws Exception {
		// TODO 自动生成的方法存根
		//tabpane=new TabPane();
		root = FXMLLoader.load(getClass().getResource("test2.fxml"));
		
		Scene scene = new Scene(root, 1000, 600);
		stage.setScene(scene);
		stage.show();
		
	}
	@FXML
	public void addImage() {
		//tabpane=(TabPane) root.lookup("#tabpane");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择image");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
				);
				fileChooser.getExtensionFilters().addAll(
				    new FileChooser.ExtensionFilter("All Images", "*.*"),
				    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				    new FileChooser.ExtensionFilter("GIF", "*.gif"),
				    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				    new FileChooser.ExtensionFilter("PNG", "*.png")
						);
				Stage stage1 = null;
				try{
					String path=fileChooser.showOpenDialog(stage1).getPath();
					//System.out.println(path);
					Tab tab=new Read().oneFile(path);
					tabpane.getTabs().add(tab);
				} catch (NullPointerException e) {
					//e.printStackTrace();
				}
	}
}