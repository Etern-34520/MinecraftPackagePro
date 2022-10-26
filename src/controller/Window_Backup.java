package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class Window_Backup extends GridPane{
//	public static final Integer AUTOARRENGE = null;
//	double x;
//	double y;
//	double sx;
//	double sy;
//	int arrenge;
//	@FXML Pane windowTitle;
//	@FXML Text close;
//	ToggleButton button;
//	Desktop desktop;
//	Hyperlink backLink;
//	double prefX;
//	double prefY;
//
//	public Window_Backup(Node node, Double width, Double height) throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/Window.fxml"));
//		loader.setController(this);
//		loader.setRoot(this);
//		loader.load();
//
//		this.add(node,0,1,2,1);
//		//windowTitle.setMinWidth();
//		//windowTitle.setPrefHeight(node.getLayoutBounds().getHeight());
//		this.setMaxWidth(width);
//		this.setMaxHeight(height);
//	}
//
//	public void setPrefLocation(double x,double y) {
//		prefX=x;
//		prefY=y;
//		GridPane.setMargin(this , new Insets(y,0,0,x));
//	}
//
//	@FXML void windowClose(){
//		button.setSelected(false);
//	}
//	@FXML void windowMove(MouseEvent event){
//		Insets margin = GridPane.getMargin(this);
//		double x = margin.getLeft();
//		double y = margin.getTop();
//		double newX = x + event.getX() - sx;
//		double newY = y + event.getY() - sy;
//		Region windowBasic=(GridPane) getParent();
//		if (newX + sx < 0) {
//			newX = 0 - sx;
//		} else if (newX + sx > windowBasic.getWidth()) {
//			newX = windowBasic.getWidth() - sx;
//		}
//
//		if (newY + sy < 0) {
//			newY = 0 - sy;
//		} else if (newY + sy > windowBasic.getHeight()) {
//			newY = windowBasic.getHeight() - sy;
//		}
//		//System.out.println(windowBasic.getHeight());
//
//		GridPane.setMargin(this, new Insets(newY, 0, 0, newX));
//		thisTop();
//		backLink.setVisible(true);
//		backLink.setTextFill(Color.rgb(255, 255, 255));
//		//System.out.println("x"+newX+"y"+newY);
//	}
//	@FXML
//	private void thisTop() {
//		// TODO 自动生成的方法存根
//		for (int i = 0;i<desktop.windows.size();i=i+1) {
//			Window_Backup window =desktop.windows.get(i);
//			window.setStyle("-fx-border-color:rgb(50,50,50)");
//			if (window != this) {
//				desktop.windowBasic.getChildren().remove(window);
//				desktop.windowBasic.getChildren().add(window.arrenge+1, window);
//			}
//			arrenge=0;
//		}
//		this.setStyle("-fx-border-color:rgb(0,120,215)");
//	}
//	@FXML
//	void closeButtonFocused() {
//		Font focus=new Font(19);
//		close.setFont(focus);
//		close.setFill(Color.rgb(0, 120, 215));
//	}
//	@FXML
//	void closeButtonFocusedOut() {
//		Font focusout=new Font(17);
//		close.setFont(focusout);
//		close.setFill(Color.rgb(255, 255, 255));
//	}
//	@FXML
//	void movePrepare(MouseEvent event){
//		sx=event.getX();
//		sy=event.getY();
//		thisTop();
//	}
//
}
