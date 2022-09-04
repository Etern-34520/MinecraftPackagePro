package controller;

import io.PackDecompiler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DecompilerGui extends SplitPane {
    @FXML
    private TextField minecraftPathTextField;
    @FXML
    private ListView<String> minecraftVersionsView;

    @FXML
    private CheckBox onlyPathCheck;

    @FXML
    private CheckBox librariesCheck;

    @FXML
    private CheckBox jarCheck;
    @FXML
    private Button startButton;
    @FXML
    private Label tips;
    @FXML
    private TilePane decompileProgress;
    @FXML
    private ScrollPane decompileProgressParent;
    File minecraftPath;
    public String putPath = "C:\\Users\\zzc\\Desktop\\test\\";
    @FXML
    void selectAll(){
        minecraftVersionsView.getSelectionModel().selectAll();
    }
    @FXML
    void reflashPath(KeyEvent event){
        if (event.getCode().equals(KeyCode.ENTER)){
            File minecraftPath = new File(minecraftPathTextField.getText());
            if (!minecraftPath.exists()){
                tip("路径不存在",2000);
            } else if (!new File(minecraftPath.getPath()+"\\"+"versions").exists()|!new File(minecraftPath.getPath()+"\\"+"libraries").exists()){
                tip("路径不正确或者minecraft文件缺失",2000);
            } else {
                this.minecraftPath = minecraftPath;
                startButton.setDisable(false);
            }
            minecraftVersionsView.setDisable(false);
            versionsViewFlash();
        }
    }

    @FXML
    void selectPath(MouseEvent event) {
        DirectoryChooser minecraftPathChooser = new DirectoryChooser();
        minecraftPathChooser.setTitle("选择.minecraft文件夹路径");
        //minecraftPathChooser.setInitialDirectory(new File("C:\\Users\\.minecraft"));
        File minecraftPath =
        minecraftPathChooser.showDialog(this.getScene().getWindow());
        if (minecraftPath != null){
            this.minecraftPath = minecraftPath;
            minecraftPathTextField.setText(minecraftPath.getPath());
            minecraftVersionsView.setDisable(false);
            versionsViewFlash();
        }
    }

    @FXML
    void start(MouseEvent event) {
        List<String> versions = minecraftVersionsView.getSelectionModel().getSelectedItems();
        for (String version : versions) {
            PackDecompiler packDecompiler = new PackDecompiler(
                    minecraftPath,
                    version,
                    putPath+"minecraftDefaultPack_"+version
            );
            decompileProgress.getChildren().add(new ProgressPane(packDecompiler,version));
        }

    }
    public void versionsViewFlash(){
        minecraftVersionsView.getItems().removeAll(minecraftVersionsView.getItems());
        File file=new File(minecraftPath.getPath()+"\\versions");
        //判断是否有目录
        if(file.isDirectory()) {
            //获取目录中的所有文件名称
            File[] files=file.listFiles();
            //对指定路径下的文件或目录进行遍历
            assert files != null;
            Arrays.stream(files).forEach(f-> {
                System.out.println(f);
                String regVersion = "(\\d)+\\.+(\\d)+(\\.+(\\d))?";//x.x.x或x.x形式
                if (f.getName().matches(regVersion)) {
                    minecraftVersionsView.getItems().add(f.getName());
                }
            });
            try {
                minecraftVersionsView.getSelectionModel().selectFirst();
                startButton.setDisable(false);
            } catch (Exception e) {
                tip("还没有任何Minecraft版本",5000);
            }
        }
    }
    public void tip(String text,int time){
        new Thread(() -> {
            Platform.runLater(() ->{
               tips.setText(text);
            });
            try {
                Thread.sleep(time);
            } catch (Exception e){
                e.printStackTrace();
            }
            Platform.runLater(() ->{
                tips.setText("");
            });
        }).start();
    }
    public DecompilerGui(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/DecompilerGui.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
            decompileProgress.setHgap(20);
            decompileProgress.setVgap(20);
            minecraftVersionsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            this.getStyleClass().add("text_color: rgb(135,147,154);");
            this.getStyleClass().add(JMetroStyleClass.BACKGROUND);
            decompileProgress.prefWidthProperty().bind(decompileProgressParent.widthProperty().subtract(5));
            minecraftPathTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                File minecraftPathNew = new File(minecraftPathTextField.getText());
                if (!minecraftPathNew.exists()){
                    tip("路径不存在",2000);
                } else if (!new File(minecraftPathNew.getPath()+"\\"+"versions").exists()|!new File(minecraftPathNew.getPath()+"\\"+"libraries").exists()){
                    tip("路径不正确或者minecraft文件缺失",2000);
                } else {
                    minecraftPath = minecraftPathNew;
                }
            });
            minecraftVersionsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    startButton.setDisable(false);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
