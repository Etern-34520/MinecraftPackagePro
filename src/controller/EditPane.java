package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import io.Tree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

public class EditPane extends GridPane implements Initializable{
	public static ColorPicker colorPicker;
	public static ColorPlate colorPlate;
	@FXML GridPane desktopParent;
	@FXML TreeView<String> packTree;
	@FXML GridPane toolBarParent;
	public Tree fileTree=new Tree();
	public EditPane(){
		URL url;
		try {
			url = new URL("file:"+new File("bin/controller/resource/EditPane.fxml").getPath());
			FXMLLoader loader = new FXMLLoader(url);
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		try {
			
			Desktop desktop=new Desktop();
			colorPicker = new ColorPicker();
			colorPlate = new ColorPlate();
			desktop.add("rgb",colorPicker, 0, 40.0, 70.0, colorPicker.getWidth(), colorPicker.getHeight());
			desktop.add("色板",colorPlate, 0, 40.0, 530.0, colorPlate.getWidth(), colorPlate.getHeight());
			desktopParent.add(desktop, 0, 0);
			//TreeItem<String> root=new TreeItem<String>();
			
			packTree.setRoot(fileTree.readCreate("U://", null));
			packTree.setShowRoot(false);
			packTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
				@Override
				public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
					// TODO 自动生成的方法存根
					EditTab tab=new EditTab();
					tab.uploadPicture(fileTree.getFile(newValue));
					if (tab.toTab()!=null) {
						if (contains(tab.toTab())) {
							desktop.getTabPane().selectionModelProperty().get().select(getInTabList(tab.toTab()));
						} else {
							desktop.getTabPane().getTabs().add(tab.toTab());
						}
					}
					
					//System.out.println("item:"+newValue);
				}

				private Tab getInTabList(Tab tab) {
					// TODO 自动生成的方法存根
					List<String> tabNames = new ArrayList<String>();
					for (int i = 0; i < desktop.getTabPane().getTabs().size(); i++) {
						tabNames.add(desktop.getTabPane().getTabs().get(i).getText());
					}
					int array = 0;
					for (int i = 0; i < desktop.getTabPane().getTabs().size(); i++) {
						if(tabNames.get(i).equals(tab.getText())) array=i;
					}
					return desktop.getTabPane().getTabs().get(array);
				}

				private boolean contains(Tab tab) {
					// TODO 自动生成的方法存根
					List<String> tabNames = new ArrayList<String>();
					for (int i = 0; i < desktop.getTabPane().getTabs().size(); i++) {
						tabNames.add(desktop.getTabPane().getTabs().get(i).getText());
					}
					if (tabNames.contains(tab.getText())) return true;
					else return false;
				}
				
			});
			
			ToolBar bar=new ToolBar();
			toolBarParent.add(bar, 0, 0);
			bar.add(new GridPane(), "");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	@FXML void treeReflash(){
		fileTree.reflash();
	}
}
