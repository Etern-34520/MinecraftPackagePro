package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.MeshView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditTab extends GridPane {
@FXML FlowPane picturesPane;
@FXML MeshView modelView;
Tab tab;
	public EditTab() {
		// TODO 自动生成的构造函数存根
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/EditTab.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public MeshView getMeshView() {
		return modelView;
	}
	public FlowPane getPictureView() {
		return picturesPane;
	}
	public void uploadPicture(File file) {
		if (file!=null&&file.isFile()) {
			try {
				//System.out.println(root);
				FileInputStream input;
				input = new FileInputStream(file);
				Image image=new Image(input);
				String name=file.getName();
				String suffix = null;
				for (String i: name.split("\\.")) suffix=i;
				if (name.split("\\.").length==1) suffix="null";
				List<String> allowedSuffix=new ArrayList<String>();
				allowedSuffix.add("jpg");
				allowedSuffix.add("png");
				allowedSuffix.add("bmp");
				allowedSuffix.add("gif");
				int length=allowedSuffix.size();
				boolean unSupport = true;
				for (String s : allowedSuffix) {
					assert suffix != null;
					if (suffix.toLowerCase().equals(s)) {
						tab = new Tab(name, this);
						unSupport = false;
					}
				}
				if (unSupport) System.out.println("not support suffix:"+suffix);
				else {
					List<Canvas> canvases = new ArrayList<>();
					int size = 100;
					if (image.getHeight()%image.getWidth() == 0){
						WritableImage writableImage = new WritableImage(image.getPixelReader(),(int) image.getWidth(),(int) image.getHeight());
						double cutIndex = image.getHeight() / image.getWidth();
						for (int i = 0; i < cutIndex; i++) {
							Canvas canvas=new Canvas(size,size);
							canvas.setStyle( "-fx-background-color:rgb(255,255,255);");
							picturesPane.getChildren().add(canvas);
							canvases.add(canvas);
							GraphicsContext gc=canvas.getGraphicsContext2D();
							gc.setImageSmoothing(false);
							gc.drawImage(image,0,i*size, image.getWidth(), image.getHeight()/cutIndex,
									0,i* size, size, size);
						}
//						gc.drawImage(writableImage,0,0,100,100);
					}
					int picturesPaneSize = (int) Math.sqrt(canvases.size());
					picturesPane.setPrefWidth(picturesPaneSize*size);
					picturesPane.setPrefHeight(picturesPaneSize*size);
				}
				
				//System.out.println(file.getName());
			} catch (IOException e) {
				String message=e.getMessage();
				if(message.endsWith("(拒绝访问。)")) {
					System.out.println(message);
				} else {
					e.printStackTrace();
				}
				
			}
		}
		
		//new EditPane().waitingFinish();
	}
		public Tab toTab() {
			return tab;
		}
}
