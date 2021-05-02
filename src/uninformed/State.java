package uninformed;

import java.util.LinkedList;

public interface State {

	LinkedList<State> nextStates( );
	
	int totalCost( );
	int getNumNodes( );
	
	boolean isGoal( );
}
