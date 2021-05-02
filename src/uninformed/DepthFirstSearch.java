package uninformed;

import java.util.LinkedList;

public class DepthFirstSearch extends Search {

	@Override
	public void enqueue(LinkedList<State> open, State S) {

		open.add(0,  S);
		
	}

}
