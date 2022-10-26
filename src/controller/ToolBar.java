package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ToolBar extends GridPane implements Initializable {
@FXML GridPane tabBar;
@FXML HBox buttonBar;
ToggleGroup buttons;
	public ToolBar() {
		// TODO 自动生成的构造函数存根
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/ToolBar.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void add(GridPane gridPane,String name) {
		tabBar.add(gridPane, 0, 0);
		ToggleButton tabButton=new ToggleButton();
		tabButton.setText(name);
		tabButton.setPrefSize(60, 60);
		tabButton.setToggleGroup(buttons);
		gridPane.visibleProperty().bind(tabButton.selectedProperty());
		buttonBar.getChildren().add(tabButton);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自动生成的方法存根

	}

}
