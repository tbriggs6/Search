package uninformed;

import java.util.Iterator;
import java.util.LinkedList;

public class AStar extends Search {

	@Override
	public void enqueue(LinkedList<State> open, State S) {

		int totalCost = S.totalCost();
		
		int count = 0;
		Iterator<State> iterator = open.iterator();
		while (iterator.hasNext()) 
		{
			State Q = iterator.next();
			if (Q.totalCost() > totalCost)
				break;
			count++;
		}
		
		
		open.add(count, S);

	}

}
