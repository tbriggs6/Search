package alphabeta;

import java.util.LinkedList;

public class AlphaBetaRec {

	public double alphabeta(State node, int depth, double alpha, double beta, boolean maxPlayer)
	{
		double v;
		
		if ((depth == 0) || (node.isTerminal()))
				return node.evaluate();
		
		if (maxPlayer) {
			v = - Double.MAX_VALUE;
			LinkedList<State> children = node.getNext();
			for (State child : children) 
			{
				double cv = alphabeta(child,depth-1,alpha, beta, false);
				v = Math.max( v, cv);
				alpha = Math.max(alpha, v);
				if (beta <= alpha)
					break;
			}
		}
		else {
			v = Double.MAX_VALUE;
			LinkedList<State> children = node.getNext();
			for (State child : children) 
			{
				double cv =  alphabeta(child,depth-1,alpha, beta, true);
				v = Math.min( v, cv);
				beta = Math.min(beta, v);
				if (beta <= alpha)
					break;
			}
			
		}
		System.out.println("Node: " + node + " v: " + v);
		return v;
	}
	
	public State getMove(State current)
	{
		LinkedList<State> children = current.getNext();
		
		double maxV = -Double.MAX_VALUE;
		State best = null;
		
		for (State child : children)
		{
			double value = alphabeta(child, 2, -Double.MAX_VALUE, Double.MAX_VALUE, false);
			if (value > maxV) {
				maxV = value;
				best = child;
			}
		}
		
		return best;
	}
	
	
}
