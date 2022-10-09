package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Window extends GridPane{
	public static final Integer AUTOARRENGE = null;
	double x;
	double y;
	double sx;
	double sy;
	int arrenge;
	@FXML
	Pane windowTitle;
	@FXML
	Text close;
	@FXML
	GridPane basic;
	@FXML
	private Pane paneNW;

	@FXML
	private Pane paneN;

	@FXML
	private Pane paneNE;

	@FXML
	private Pane paneW;

	@FXML
	private Pane paneE;

	@FXML
	private Pane paneSW;

	@FXML
	private Pane paneSE;

	@FXML
	private Pane paneS;
	private final List<Pane> panes = new ArrayList<>();
	ToggleButton button;
	Desktop desktop;
	Hyperlink backLink;
	double prefX;
	double prefY;
	
	public Window(Node node, Double width, Double height) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/Window.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
		basic.add(node,2,2,1,1);
		//windowTitle.setMinWidth();
		//windowTitle.setPrefHeight(node.getLayoutBounds().getHeight());
		this.setMaxWidth(width);
		this.setMaxHeight(height);
		panes.add(paneNE);
		panes.add(paneN);
		panes.add(paneNW);
		panes.add(paneE);
		panes.add(paneW);
		panes.add(paneSE);
		panes.add(paneS);
		panes.add(paneSW);
		this.getStyleClass().add(JMetroStyleClass.BACKGROUND);
	}
	
	public void setPrefLocation(double x,double y) {
		prefX=x;
		prefY=y;
		GridPane.setMargin(this , new Insets(y,0,0,x));
	}
	
	@FXML void windowClose(){
		button.setSelected(false);
	}
	@FXML void windowMove(MouseEvent event){
		Insets margin = GridPane.getMargin(this);
		double x = margin.getLeft();
		double y = margin.getTop();
		double newX = x + event.getX() - sx;
		double newY = y + event.getY() - sy;
		Region windowBasic=(GridPane) getParent();
		if (newX + sx < 0) {
			newX = 0 - sx;
		} else if (newX + sx > windowBasic.getWidth()) {
			newX = windowBasic.getWidth() - sx;
		}

		if (newY + sy < 0) {
			newY = 0 - sy;
		} else if (newY + sy > windowBasic.getHeight()) {
			newY = windowBasic.getHeight() - sy;
		}
		//System.out.println(windowBasic.getHeight());

		GridPane.setMargin(this, new Insets(newY, 0, 0, newX));
		thisTop();
		backLink.setVisible(true);
		backLink.setTextFill(Color.rgb(255, 255, 255));
		//System.out.println("x"+newX+"y"+newY);
	}
	@FXML
	private void thisTop() {
		// TODO �Զ����ɵķ������
		for (int i = 0;i<desktop.windows.size();i=i+1) {
			Window window =desktop.windows.get(i);
			window.setStyle("-fx-border-color:rgb(50,50,50)");
			if (window != this) {
				desktop.windowBasic.getChildren().remove(window);
				desktop.windowBasic.getChildren().add(window.arrenge+1, window);
			}
			arrenge=0;
		}
		this.setStyle("-fx-border-color:rgb(0,120,215)");
	}
	public void pin(){
		for (int i = 0; i < panes.size(); i++) {
			panes.get(i).setCursor(Cursor.DEFAULT);
		}
	}
	public void  unPin(){
		paneNE.setCursor(Cursor.NE_RESIZE);
		paneN.setCursor(Cursor.N_RESIZE);
		paneNW.setCursor(Cursor.NW_RESIZE);
		paneE.setCursor(Cursor.E_RESIZE);
		paneW.setCursor(Cursor.W_RESIZE);
		paneSE.setCursor(Cursor.SE_RESIZE);
		paneS.setCursor(Cursor.S_RESIZE);
		paneSW.setCursor(Cursor.SW_RESIZE);
	}
	@FXML
	void closeButtonFocused() {
		Font focus=new Font(19);
		close.setFont(focus);
		close.setFill(Color.rgb(0, 120, 215));
	}
	@FXML
	void closeButtonFocusedOut() {
		Font focusout=new Font(17);
		close.setFont(focusout);
		close.setFill(Color.rgb(255, 255, 255));
	}
	@FXML
	void movePrepare(MouseEvent event){
		sx=event.getX();
		sy=event.getY();
		thisTop();
	}

	@FXML
	void draggedE(MouseEvent event) {

	}

	@FXML
	void draggedN(MouseEvent event) {

	}

	@FXML
	void draggedNE(MouseEvent event) {

	}

	@FXML
	void draggedNW(MouseEvent event) {

	}

	@FXML
	void draggedS(MouseEvent event) {

	}

	@FXML
	void draggedSE(MouseEvent event) {

	}

	@FXML
	void draggedSW(MouseEvent event) {

	}

	@FXML
	void draggedW(MouseEvent event) {

	}

}
