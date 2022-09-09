package controller;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorPicker extends GridPane{
	boolean cvs = true;
	boolean pickertop = false;
	boolean platetop = true;
	@FXML
	private GridPane colorPicker;
	@FXML
	private Slider sliderR;
	@FXML
	private Slider sliderG;
	@FXML
	private Slider sliderB;
	@FXML
	private Slider sliderA;
	@FXML
	private TextField textR;
	@FXML
	private TextField textG;
	@FXML
	private TextField textB;
	@FXML
	private TextField textA;
	@FXML
	private ImageView dotLight;
	@FXML
	private ImageView dotDark;
	@FXML
	private Pane alphabg1;
	@FXML
	private Pane colorPane;
	@FXML
	private Rectangle colorBottom;
	@FXML
	private Rectangle colorTop;
	@FXML
	private ToggleGroup color;
	private ColorPlate colorPlate;
	public void setColorPlate(ColorPlate colorPlate){
		this.colorPlate = colorPlate;
		colorPlate.setColorPicker(this);
	}
	/*
	@FXML
	private Rectangle colorShow2;
	@FXML
	private Polygon colorShow1;
	*/
	public ColorPicker() throws Exception{//构造函数
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/ColorPicker.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
		List<TextField> textFields=new ArrayList<TextField>();
		textFields.add(textR);
		textFields.add(textG);
		textFields.add(textB);
		textFields.add(textA);
		for (TextField textField : textFields) {
			textField.focusedProperty().addListener(new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
					String t = textField.getText();
					if (t.equals("")) {
						t = "0";
						if(textField==textA){
							t = "100";
						}
						textField.setText(t);
					}
						((Slider) lookup("#slider"+textField.getId().substring(4, 5))).setValue(Integer.valueOf(t).intValue());
						//sliderRed.setValue(Integer.valueOf(r).intValue());
						colorPosition();
					}
			});
		}
	}
	
	private void colorPosition() {
		double a = 140;
		double border = 0.5;
		int r = (int) sliderR.getValue();
		int g = (int) sliderG.getValue();
		int b = (int) sliderB.getValue();
		Color color = Color.rgb(r, g, b);
		double h = color.getHue();
		double s = color.getSaturation();
		double v = color.getBrightness();
		colorPane.setRotate(-h);
		dotX(s * (a - 2 * border) + border);
		dotY(a - v * (a - 2 * border) - border);
		cvs = false;
		basicColorChange(null);
		cvs = true;
		//colorSet();
		alphabg1.setOpacity((100-sliderA.getValue())/100.0);
		if (alphabg1.getOpacity() < 0.5) {
			dotLight.setVisible(true);
			dotDark.setVisible(false);
		}
		if (v > 0.5) {
			dotLight.setVisible(false);
			dotDark.setVisible(true);
		}
		if (v < 0.5) {
			dotLight.setVisible(true);
			dotDark.setVisible(false);
		}
		
		if (alphabg1.getOpacity() > 0.5) {
			dotLight.setVisible(false);
			dotDark.setVisible(true);
		}
		colorPlate.colorSet(r, g, b, sliderA.getValue());
	}

	@FXML
	public void basicColorChange(MouseEvent e) {
		double ro = colorPane.getRotate();// 度数
		// colorPointer.setCursor(Cursor.CLOSED_HAND);
		if (e == null) {
		} else {
			double x = e.getX();
			double y = e.getY();
			double a=Math.tanh(x*10000/(120-y)/10000.0) * 180 / Math.PI;
			if(y>120) {
				y=120;
			} else {
				colorPane.setRotate(ro + a);
			}
		}

		int r = 0;
		int b = 0;
		int g = 0;
		// if (ro==180) ro=-180;

		ro = -ro;// 纠正左右镜像
		int ch = Math.abs((int) Math.pow(-1, (int) ro / 360));
		ro = ch * ro - ch * 360 * ((int) ro / 360);
		if (0 < ro & ro <= 60) {
			r = 255;
			g = (int) (255 * ro / 60);
			b = 0;
		} else if (60 < ro & ro <= 120) {
			r = (int) (-255 * ro / 60 + 510);
			g = 255;
			b = 0;
		} else if (120 < ro & ro <= 180) {
			r = 0;
			g = 255;
			b = (int) (255 * ro / 60 - 510);
		} else if (180 < ro & ro <= 240) {
			r = 0;
			g = (int) (-255 * ro / 60 + 1020);
			b = 255;
		} else if (240 < ro & ro <= 300) {
			r = (int) (255 * ro / 60 - 1020);
			g = 0;
			b = 255;
		} else if (300 < ro & ro <= 360) {
			r = 255;
			g = 0;
			b = (int) (-255 * ro / 60 + 1530);
		} else if (-360 < ro & ro <= -300) {
			r = 255;
			g = (int) (255 * ro / 60 + 1530);
			b = 0;
		} else if (-300 < ro & ro <= -240) {
			r = (int) (-255 * ro / 60 - 1020);
			g = 255;
			b = 0;
		} else if (-240 < ro & ro <= -180) {
			r = 0;
			g = 255;
			b = (int) (255 * ro / 60 + 1020);
		} else if (-180 < ro & ro <= -120) {
			r = 0;
			g = (int) (-255 * ro / 60 - 510);
			b = 255;
		} else if (-120 < ro & ro <= -60) {
			r = (int) (255 * ro / 60 + 510);
			g = 0;
			b = 255;
		} else if (-60 < ro & ro <= 0) {
			r = 255;
			g = 0;
			b = (int) (-255 * ro / 60);
		}
		colorBottom.setFill(Color.rgb(r, g, b));
		/*
		ColorPlate colorPlate = new ColorPlate();
		colorPlate.colorSet(r, g, b, sliderA.getValue());
		*/
		if (cvs) colorValueSet();
	}

	private void colorValueSet() {
		double dx = dotLight.getLayoutX();
		double dy = dotLight.getLayoutY();
		double a = 140;
		double border = 0.5;

		double h = -colorPane.getRotate() % 360;
		double v = Math.round((a - dy) * 1000 / (a - 2 * border)) / 1000.0 - 0.004;
		double s = Math.round(dx * 1000 / (a - 2 * border)) / 1000.0 - 0.004;
		//System.out.println(v+","+s);
		if (v >= 1)
			v = 1.0;
		if (v <= 0)
			v = 0.0;
		if (s >= 1)
			s = 1.0;
		if (s <= 0)
			s = 0.0;
		// System.out.println("h:"+h);
		// System.out.println("s:"+s);
		// System.out.println("v:"+v);
		Color color = Color.hsb(h, s, v);
		int r = (int) (color.getRed() * 255);
		int g = (int) (color.getGreen() * 255);
		int b = (int) (color.getBlue() * 255);
		sliderR.setValue(r);
		sliderG.setValue(g);
		sliderB.setValue(b);
		textR.setText(Integer.toString(r));
		textG.setText(Integer.toString(g));
		textB.setText(Integer.toString(b));
		if (alphabg1.getOpacity() < 0.5) {
			dotLight.setVisible(true);
			dotDark.setVisible(false);
		}
		if (v > 0.5) {
			dotLight.setVisible(false);
			dotDark.setVisible(true);
		}
		if (v < 0.5) {
			dotLight.setVisible(true);
			dotDark.setVisible(false);
		}
		//ColorPlate colorPlate = new ColorPlate();
		// System.out.print(g);
		//colorPlate.colorSet(r, g, b, a);
		if (alphabg1.getOpacity() > 0.5) {
			dotLight.setVisible(false);
			dotDark.setVisible(true);
		}
		colorPlate.colorSet(r, g, b, sliderA.getValue());
	}

	private void dotX(double x) {
		dotLight.setLayoutX(x);
		dotDark.setLayoutX(x);
	}

	private void dotY(double y) {
		dotLight.setLayoutY(y);
		dotDark.setLayoutY(y);
	}

	@FXML
	public void dotSet(MouseEvent e) {
		colorTop.setCursor(javafx.scene.Cursor.NONE);
		double ex = e.getX();// 鼠标X位置
		double ey = e.getY();// 鼠标Y位置
		double a = colorTop.getWidth();
		boolean top = false;// 触碰到上
		boolean bottom = false;// 触碰到底
		boolean left = false;// 触碰到左
		boolean right = false;// 触碰到右
		double border = 0.5;
		double addx = 0.5;
		double addy = 0.5;
		if (ex + addx < 0 + border) {
			dotX(0 + border);
			dotY(ey + addy);
			left = true;

		}
		if (ex + addx > a - border) {
			dotX(a - border);
			dotY(ey + addy);
			right = true;
		}
		if (ey + addy < 0 + border) {
			dotX(ex + addx);
			dotY(0 + border);
			top = true;
		}
		if (ey + addy > a - border) {
			dotX(ex + addx);
			dotY(a - border);
			bottom = true;
		}
		// 这是一段很有意思的测试代码，不用管它
		/*
		 * System.out.println("  -----"+top+"-----");
		 * System.out.println("  |             |");
		 * System.out.println("  |             |");
		 * System.out.println(left+"          "+right);
		 * System.out.println("  |             |");
		 * System.out.println("  |             |");
		 * System.out.println("  -----"+bottom+"-----");
		 */
		if (left && top) {
			dotX(0 + border);
			dotY(0 + border);
		} else if (left && bottom) {
			dotX(0 + border);
			dotY(a - border);
		} else if (right && top) {
			dotX(a - border);
			dotY(0 + border);
		} else if (right && bottom) {
			dotX(a - border);
			dotY(a - border);
		} else if (!(top | bottom | left | right)) {
			dotX(ex + addx);
			dotY(ey + addy);
		}
		colorValueSet();
	}
	
	@FXML
	public void sliderToText(Event e) {
		Slider slider = (Slider) this.lookup("#"+e.getSource().toString().substring(10,17));
		TextField textField = (TextField) this.lookup("#"+"text"+e.getSource().toString().substring(16,17));
		int value = ((int) slider.getValue());
		textField.setText(String.valueOf(value));
		colorPosition();
	}
	
	@FXML
	public void textToSlider(Event e) {
		TextField textField = (TextField) this.lookup("#"+e.getSource().toString().substring(13,18));
		Slider slider = (Slider) this.lookup("#"+"slider"+e.getSource().toString().substring(17,18));
		String text = textField.getText();
		if (!text.equals("")) {
			try {
				if (text.matches("\\d*")) {
					int Value = Integer.valueOf(text).intValue();
					if (Value > 255) {
						textField.setText("255");
					}
					slider.setValue(Value);
				} else {
					textField.setText("0");
				}
			} catch (NumberFormatException e1) {
			}
			colorPosition();
		}
		if (text.equals("00")) {
			textField.setText("0");
		}
		colorPosition();
	}
	public void setColor(int r, int g, int b, int a) {
		sliderR.setValue(r);
		sliderG.setValue(g);
		sliderB.setValue(b);
		sliderA.setValue(a);
		textR.setText(Integer.toString(r));
		textG.setText(Integer.toString(g));
		textB.setText(Integer.toString(b));
		textA.setText(Integer.toString(a));
		colorPosition();
	}
}
