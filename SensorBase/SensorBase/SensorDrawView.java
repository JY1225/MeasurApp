package SensorBase;

public class SensorDrawView {
	private SensorDrawViewInterface sensorDrawViewInterface;
		
	public void paintSetColorMAGENTA() {
		sensorDrawViewInterface.paintSetColorMAGENTA();
	}
	
	public void paintSetColorPINK() {
		sensorDrawViewInterface.paintSetColorPINK();
	}

	public void paintSetColorORANGE() {
		sensorDrawViewInterface.paintSetColorORANGE();
	}

	public void paintSetColorWHITE() {
		sensorDrawViewInterface.paintSetColorWHITE();
	}

	public void paintSetColorBLACK() {
		sensorDrawViewInterface.paintSetColorBLACK();
	}

	public void paintSetColorRED() {
		sensorDrawViewInterface.paintSetColorRED();
	}

	public void paintSetColorBLUE() {
		sensorDrawViewInterface.paintSetColorBLUE();
	}

	public void paintSetColorGREEN() {
		sensorDrawViewInterface.paintSetColorGREEN();
	}

	public void paintSetColorYELLOW() {
		sensorDrawViewInterface.paintSetColorYELLOW();
	}

	public void paintSetBOLD() {
		sensorDrawViewInterface.paintSetBOLD();
	}

	public void setStrokeWidth(int width) {
		sensorDrawViewInterface.setStrokeWidth(width);
	}

	public void drawPoint(int x,int y) {
		sensorDrawViewInterface.drawPoint(x,y);
	}
	
	public void drawPoints(float[] point) {
		sensorDrawViewInterface.drawPoints(point);
	}

	public void drawText(String str,int x,int y) {
		sensorDrawViewInterface.drawText(str,x,y);
	}

	public void drawLine(int x,int y,int widht,int height) {
		sensorDrawViewInterface.drawLine(x,y,widht,height);
	}
}
