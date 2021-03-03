package AppFrame.contourDetection;

import database.SearchSingleResultInterface;

public class ContourDetectionSearchResultImplement implements SearchSingleResultInterface{

	@Override
	public void CREATE_NEW_FRAME(int id) {
		ContourDetectionSingleDetailFrame.showFrame(id);		
	}
}
