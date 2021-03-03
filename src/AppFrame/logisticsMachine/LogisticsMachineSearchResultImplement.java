package AppFrame.logisticsMachine;

import database.SearchSingleResultInterface;

public class LogisticsMachineSearchResultImplement implements SearchSingleResultInterface{

	@Override
	public void CREATE_NEW_FRAME(int id) {
		new LogisticsMachineSingleDetailFrame(id);		
	}

}
