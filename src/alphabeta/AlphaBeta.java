package alphabeta;

import java.util.LinkedList;

public class AlphaBeta {

	
	int max_depth;
	char maxPlayer;
	
	public AlphaBeta(char maxPlayer, int max_depth)
	{
		this.max_depth = max_depth;
		this.maxPlayer = maxPlayer;
	}
	
	public double alpha_beta(State S, int depth, double alpha, double beta, boolean maxPlayer)
	{
		if ((depth == 0) || S.isTerminal())
			return S.utility();
		
		
		if (maxPlayer) {
			double v = -Double.MAX_VALUE;
			for (State child : S.next()) {
				v = Math.max(v, 
						alpha_beta(child, depth-1, alpha, beta, false));
				if (beta <= alpha) 
					break;
			}
			return v;
		}
		else {
			double v = Double.MAX_VALUE;
			for(State child : S.next()) {
				v = Math.min(v,  
						alpha_beta(child, depth-1, alpha, beta, true));
				
				if (beta <= alpha)
					break;
			}
			return v;
		}
	}

	
	public State getMove(State current, boolean maxPlayer, int max_depth)
	{
		LinkedList<State> children = current.next();
		
		double maxV = -Double.MAX_VALUE;
		State best = null;
		
		for (State child : children)
		{
			double value = alpha_beta(child, max_depth, -Double.MAX_VALUE, Double.MAX_VALUE, maxPlayer);
			System.out.format("%f %s\n", value, child);
			if (value > maxV) {
				maxV = value;
				best = child;
			}
		}
		
		return best;
	}
	
}
