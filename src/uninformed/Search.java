package uninformed;

import java.util.LinkedList;

public abstract class Search {

	State found = null;
	
	public abstract void enqueue(LinkedList<State> open, State S);
	
	public boolean search(State initial)
	{
		
		LinkedList<State> open = new LinkedList<State>( );
		//LinkedList<State> closed = new LinkedList<State>( );
		
		
		open.add( initial );
		
		
		while(open.size() > 0) 
		{
		
			State curr = open.removeFirst();
		//	closed.add(curr);
			
			if (curr.isGoal( )) {
				found = curr;
				
				return true;
			}
			
			LinkedList<State> next = curr.nextStates();
			
			
			for (State S : next)
			{
				if (open.contains(S)) continue;
				//if (closed.contains(S)) continue;
			
				enqueue(open, S);
			}
			
		}
		
		return false;
	}
	
}
