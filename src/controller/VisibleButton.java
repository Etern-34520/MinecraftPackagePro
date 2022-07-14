package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class VisibleButton extends Pane implements Initializable {
@FXML ToggleButton visibleButton;
Window window;
@FXML
private Hyperlink backLink;
	public VisibleButton(Window window) throws IOException {
		// TODO �Զ����ɵĹ��캯�����
		this.window=window;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/VisibleButton.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO �Զ����ɵķ������
		//backLink;
		window.backLink=backLink;
	}
	
	public ToggleButton getButton() {
		// TODO �Զ����ɵķ������
		return visibleButton;
	}

	@FXML private void windowBack() {
		backLink.setTextFill(Color.rgb(255, 255, 255));
		Desktop.setMargin(window, new Insets(window.prefY, 0, 0, window.prefX));
		backLink.setVisible(false);
	}
	
	public void setPrefWidth(int i) {
		// TODO �Զ����ɵķ������
		visibleButton.setPrefWidth(i);
	}
	
	public void setPrefHeight(int i) {
		// TODO �Զ����ɵķ������
		visibleButton.setPrefWidth(i);
	}
	
}