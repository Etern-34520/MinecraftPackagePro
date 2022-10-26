package controller;

import io.FileTree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditPane extends GridPane implements Initializable{
	public static ColorPicker colorPicker;
	public static ColorPlate colorPlate;
	@FXML GridPane desktopParent;
	@FXML TreeView<FileTree.tsFile> packTree;
	@FXML GridPane toolBarParent;
	public FileTree fileTree=new FileTree();
	public EditPane(){
		URL url;
		try {
			url = new URL("file:"+new File("bin/controller/resource/EditPane.fxml").getPath());
			FXMLLoader loader = new FXMLLoader(url);
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			// TODO ???????? catch ??
			e.printStackTrace();
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO ??????????????
		try {
			
			Desktop desktop=new Desktop();
			colorPicker = new ColorPicker();
			colorPlate = new ColorPlate();
			desktop.add("rgb",colorPicker, 0, 40.0, 70.0, colorPicker.getWidth(), colorPicker.getHeight());
			desktop.add("???",colorPlate, 0, 40.0, 530.0, colorPlate.getWidth(), colorPlate.getHeight());
			desktopParent.add(desktop, 0, 0);
			//TreeItem<FileTree.tsFile> root=new TreeItem<FileTree.tsFile>();

			TreeItem<FileTree.tsFile> root= new TreeItem<>();
			fileTree.setRootOfAll(root);
			fileTree.create(new File("E:\\minecraftDefaultPack_1.8.9"), root);
			packTree.setRoot(root);
			packTree.setShowRoot(false);
			packTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<FileTree.tsFile>>() {
				@Override
				public void changed(ObservableValue<? extends TreeItem<FileTree.tsFile>> observable, TreeItem<FileTree.tsFile> oldValue, TreeItem<FileTree.tsFile> newValue) {
					// TODO ??????????????
					EditTab tab=new EditTab();
//					tab.uploadPicture(fileTree.getFile(newValue));
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
					// TODO ??????????????
					List<FileTree.tsFile> tabNames = new ArrayList<>();
					for (int i = 0; i < desktop.getTabPane().getTabs().size(); i++) {
//						tabNames.add(desktop.getTabPane().getTabs().get(i).getText());
					}
					int array = 0;
					for (int i = 0; i < desktop.getTabPane().getTabs().size(); i++) {
						if(tabNames.get(i).equals(tab.getText())) array=i;
					}
					return desktop.getTabPane().getTabs().get(array);
				}

				private boolean contains(Tab tab) {
					// TODO ??????????????
					List<FileTree.tsFile> tabNames = new ArrayList<>();
					for (int i = 0; i < desktop.getTabPane().getTabs().size(); i++) {
//						tabNames.add(desktop.getTabPane().getTabs().get(i).getText());
					}
                    return tabNames.contains(tab.getText());
				}
				
			});
			
			ToolBar bar=new ToolBar();
			toolBarParent.add(bar, 0, 0);
			bar.add(new GridPane(), "");
		} catch (Exception e) {
			// TODO ???????? catch ??
			e.printStackTrace();
		}
		
	}
	@FXML void treeRefresh(){
		fileTree.refresh();
	}
}
