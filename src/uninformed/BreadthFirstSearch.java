package uninformed;

import java.util.LinkedList;

public class BreadthFirstSearch extends Search {

	@Override
	public void enqueue(LinkedList<State> open, State S) {

		open.add(S);

	}

}
