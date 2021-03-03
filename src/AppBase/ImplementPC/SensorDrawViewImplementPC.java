package AppBase.ImplementPC;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import SensorBase.LMSLog;
import SensorBase.SensorDrawViewInterface;

public class SensorDrawViewImplementPC implements SensorDrawViewInterface{
	private final static String DEBUG_TAG="LmsDrawViewPC";

	public Graphics2D _g2;
	
	public void initGraphics2D(Graphics2D g2)
	{
		_g2 = g2;		
	}
	
	@Override
	public void paintSetColorMAGENTA() {
		_g2.setColor(Color.MAGENTA);
	}

	@Override
	public void paintSetColorPINK() {
		_g2.setColor(Color.PINK);
	}

	@Override
	public void paintSetColorORANGE() {
		_g2.setColor(Color.ORANGE);
	}

	@Override
	public void paintSetColorWHITE() {
		_g2.setColor(Color.WHITE);
	}

	@Override
	public void paintSetColorBLACK() {
		_g2.setColor(Color.BLACK);
	}

	@Override
	public void paintSetColorRED() {
		_g2.setColor(Color.RED);
	}

	@Override
	public void paintSetColorBLUE() {
		_g2.setColor(Color.BLUE);		
	}

	@Override
	public void paintSetColorGREEN() {
		_g2.setColor(Color.GREEN);		
	}

	@Override
	public void paintSetColorYELLOW() {
		_g2.setColor(Color.YELLOW);		
	}

	@Override
	public void paintSetBOLD() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStrokeWidth(int width) {
		// TODO Auto-generated method stub
		//BasicStroke pen = new BasicStroke(width, BasicStroke .CAP_BUTT, BasicStroke .JOIN_ROUND);
		//g2.setStroke(pen);
	}

	@Override
	public void drawPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawPoints(float[] point) {		
		for(int i=0;i<point.length;i+=2)
		{		
			Line2D p1=new Line2D.Float(point[i], point[i+1], point[i], point[i+1]);
			_g2.draw(p1);
		}
	}

	@Override
	public void drawText(String str, int x, int y) {
		_g2.drawString(str,x,y);
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2) {
        Line2D p1=new Line2D.Double(x,y,x2,y2);
        _g2.draw(p1);
	}
	
	@Override
	public void drawOval(int x, int y, int width, int height) {
        _g2.drawOval(x, y, width, height);
	}
}
