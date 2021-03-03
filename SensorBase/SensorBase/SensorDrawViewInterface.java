package SensorBase;

public interface SensorDrawViewInterface {
	public void paintSetColorMAGENTA();
	public void paintSetColorPINK();
	public void paintSetColorORANGE();
	public void paintSetColorWHITE();
	public void paintSetColorBLACK();
	public void paintSetColorRED();
	public void paintSetColorBLUE();
	public void paintSetColorGREEN();
	public void paintSetColorYELLOW();
	public void paintSetBOLD();
	public void setStrokeWidth(int width);
	public void drawPoint(int x,int y);
	public void drawPoints(float[] point);
	public void drawText(String str,int x,int y);
	public void drawLine(int x,int y,int widht,int height);
	public void drawOval(int x, int y, int width, int height);
}
