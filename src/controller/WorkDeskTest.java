package controller;

import io.FileTree;
import io.FileTree.tsFile;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorkDeskTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        WorkDesk workDesk = new WorkDesk();
        Scene scene = new Scene(workDesk,600,400);
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        stage.show();

        ColorPicker colorPicker = new ColorPicker();
        ColorPicker colorPicker1 = new ColorPicker();
        workDesk.add(colorPicker, WorkDesk.Way.LEFT_TOP,"RGB");
        workDesk.add(colorPicker1, WorkDesk.Way.LEFT_TOP, "RGB1");

        ColorPlate colorPlate = new ColorPlate();
        ColorPlate colorPlate1 = new ColorPlate();
        workDesk.add(colorPlate, WorkDesk.Way.LEFT_BOTTOM, "plate");
        workDesk.add(colorPlate1, WorkDesk.Way.LEFT_BOTTOM,"plate1");
        colorPicker.setColorPlate(colorPlate);
        colorPicker1.setColorPlate(colorPlate1);

        FileTree fileTree = new FileTree();
        TreeItem<tsFile> root= new TreeItem<>();
        fileTree.setRootOfAll(root);
        fileTree.create(new File("E:\\minecraftDefaultPack_1.8.9"), root);
        TreeView<tsFile> treeView = new TreeView<>();
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        workDesk.add(treeView, WorkDesk.Way.LEFT_TOP, "packView");

        TabPane tabPane = new TabPane();

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<tsFile>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<tsFile>> observable, TreeItem<FileTree.tsFile> oldValue, TreeItem<FileTree.tsFile> newValue) {
                // TODO ??????????????
                EditTab editTab=new EditTab();
                if (newValue != null) {
                    tsFile picture = newValue.getValue();
                    editTab.uploadPicture(picture);
                }
                if (editTab.toTab()!=null) {
                    if (contains(editTab.toTab())) {
                        tabPane.selectionModelProperty().get().select(getInTabList(editTab.toTab()));
                    } else {
                        tabPane.getTabs().add(editTab.toTab());
                    }
                }

                //System.out.println("item:"+newValue);
            }

            private Tab getInTabList(Tab tab) {
                // TODO ??????????????
                List<tsFile> tabNames = new ArrayList<>();
                for (int i = 0; i < tabPane.getTabs().size(); i++) {
                    tabNames.add(new FileTree.tsFile(tabPane.getTabs().get(i).getText()));
                }
                int array = 0;
                for (int i = 0; i < tabPane.getTabs().size(); i++) {
                    if(tabNames.get(i).getName().equals(tab.getText())) array=i;
                }
                return tabPane.getTabs().get(array);
            }
            private boolean contains(Tab tab) {
                // TODO ??????????????
                List<String> tabNames = new ArrayList<>();
                for (int i = 0; i < tabPane.getTabs().size(); i++) {
                    tabNames.add(tabPane.getTabs().get(i).getText());
                }
                return tabNames.contains(tab.getText());
            }

        });
        workDesk.setCenterBasic(tabPane);
    }

    public static void main(String[] args){
        launch(args);
    }
}
