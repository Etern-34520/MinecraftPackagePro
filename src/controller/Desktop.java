package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Desktop extends GridPane{
	@FXML
	private HBox bar;
	@FXML GridPane windowBasic;
	@FXML TabPane editTabPane;
	//@FXML ToggleButton visibleButton;
	//List<Integer> windowsArrenge=new ArrayList<Integer>();
	//List<Double> windowsLocation=new ArrayList<Double>();
	//List<ToggleButton> buttons=new ArrayList<ToggleButton>();
	List<Window> windows=new ArrayList<Window>();
	int windowsQuantity=0;
	public Desktop() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/Desktop.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
		List<ReadOnlyDoubleProperty> property=new ArrayList<ReadOnlyDoubleProperty>();
		property.add(widthProperty());
		property.add(heightProperty());
		for (int i = 0; i < property.size(); i++) {
			property.get(i).addListener(new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
					// TODO �Զ����ɵķ������
					//windowBasic.setMaxWidth(getWidth());
					//windowBasic.setMaxHeight(getHeight());
				}
			});
		}
	}
	
	public HBox getBar() {
		return bar;
	}
	
	public void add(String name,Node node,Integer index,Double x,Double y,Double width,Double height) {
		try {
			windowsQuantity=windowsQuantity+1;
			if(index>windowsQuantity) {
				index=windowsQuantity;
			} else if(index==Window.AUTOARRENGE) {
				index=windowsQuantity;
			} else if(index<0) {
				index=windowsQuantity;
			}
			//System.out.println(windowsQuantity);
			Window window = new Window(node,width,height);
			window.arrenge=0;
			window.x=x;
			window.y=y;
			windows.add(window);
			//this.add(window,1,0);
			//Insets inset=new Insets(0,0,0,0);
			windowBasic.getChildren().add(window);
			Desktop.setValignment(window, VPos.TOP);
			Desktop.setHalignment(window, HPos.LEFT);
			window.setPrefLocation(x,y);
			//GridPane.setMargin(window, inset);
			//Hyperlink backLink;
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/VisibleButton.fxml"));
			//loader.setController(window);
			//loader.setRoot(this);
			//Pane visibleButtonPane=loader.<Pane>load();
			//visibleButton.selectedProperty().bind(window.visibleProperty());
			VisibleButton visibleButtonPane=new VisibleButton(window);
			ToggleButton visibleButton = visibleButtonPane.getButton();
			visibleButton.setText(name);
			window.visibleProperty().bind(visibleButton.selectedProperty());
			getBar().getChildren().add(visibleButtonPane);
			window.button=visibleButton;
			window.desktop=this;
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	@Override@Deprecated
	public void add(Node node,int a,int b) {
		throw new UnsupportedOperationException("add(Node node,int a,int b)in class:\"Desktop\" is not allowed");
	}
	/*
	public void add(Node node,Integer index) {
		add(node,index,40.0+windowsQuantity*20,10.0+windowsQuantity*15);
	}
	
	public void add(Node node) {
		add(node,Window.AUTOARRENGE);
	}
	*/
	@FXML
	public void focusChanged() {
		for (int i = 0;i<windows.size();i=i+1) {
			Window window =windows.get(i);
			window.setStyle("-fx-border-color:rgb(50,50,50)");
		}
	}
	
	public TabPane getTabPane() {
		return editTabPane;
	}
}
