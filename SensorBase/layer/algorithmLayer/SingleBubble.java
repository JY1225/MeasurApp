package layer.algorithmLayer;

import java.util.ArrayList;

public class SingleBubble {		
    public enum SINGLE_BUBBLE_CAR_CREATE_BREAK{
    	CAR_CREATE,
    	CAR_BREAK,
    	CAR_CREATE_BREAK
    }

	public boolean bLeft;

	public int lwhCarLengthCreateBreak_CurveFitting[] = new int[SINGLE_BUBBLE_CAR_CREATE_BREAK.CAR_CREATE_BREAK.ordinal()]; 

	public String lwhCarTimeCreateBreak[] = new String[SINGLE_BUBBLE_CAR_CREATE_BREAK.CAR_CREATE_BREAK.ordinal()]; 

	//=============================================
	public float beamDiaInOut[] = new float[SINGLE_BUBBLE_CAR_CREATE_BREAK.CAR_CREATE_BREAK.ordinal()]; 
	
	public int carWidth;
	public int carHeight;
	public int carLength;
	public int carTime;
	public int carNum;

	public boolean bBreaked;
	public boolean bBreak;

	public boolean bBubbleLineAdded;
	public ArrayList<BubbleLine> bubble = new ArrayList<BubbleLine>();  		
}
