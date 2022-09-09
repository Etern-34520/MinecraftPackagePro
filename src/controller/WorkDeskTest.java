package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

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
    }

    public static void main(String[] args){
        launch(args);
    }
}
