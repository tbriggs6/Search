package alphabeta;

import java.util.LinkedList;

public interface State {

	
	public boolean isTerminal( );
	LinkedList<State> next( );
	public int utility( );
	int getDepth( ); 
	char getPlayer( );
}
