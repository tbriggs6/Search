package alphabeta;

import java.util.LinkedList;

public class Minimax {

	
	public State minimax_decision(State initial)
	{
		LinkedList<State> children = initial.next();
		
		double best = -Double.MAX_VALUE;
		State bestState = null;
		
		for (State child : children)
		{
			double value = min_value(child);
			if (value > best) {
				best = value;
				bestState = child;
			}
		}
		
		return bestState;
	}
	
	private double min_value(State state)
	{
		System.out.println("MIN " + state.getDepth() + " " + state.isTerminal() + state);
		
		if ((state.getDepth() > 10) || state.isTerminal()) 
			return state.utility();
		
		double best = Double.MAX_VALUE;
		LinkedList<State> children = state.next();
		
		for (State child : children)
		{
			best = Math.min(best, max_value(child));
		}
		
		return best;
	}
	
	private double max_value(State state)
	{
		System.out.println("MAX " + state.getDepth() + " " + state.isTerminal() + state);
		
		if ((state.getDepth() > 10) || state.isTerminal()) 
			return state.utility();
		
		double best = -Double.MAX_VALUE;
		LinkedList<State> children = state.next();
		
		for (State child : children)
		{
			best = Math.max(best, min_value(child));
		}
		
		return best;
	}
}
