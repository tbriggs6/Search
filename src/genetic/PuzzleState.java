package genetic;

import java.util.Arrays;
import java.util.LinkedList;

public class PuzzleState  {

	private final static int SIZE = 3;
	
	int blank_row, blank_col;
	
	int squares[][] = new int[SIZE][SIZE];
	int numMoves = 0;
	static int numStates = 0;
	
	public int getNumNodes( ) { return numStates; }
	
	
	public PuzzleState( int squares[][] )
	{
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				this.squares[i][j] = squares[i][j];
				if (this.squares[i][j] == 0) 
				{
					blank_row = i;
					blank_col = j;
				}
			}
		}
		
		this.numMoves = 0;
		numStates++;
	}
	
	public PuzzleState( PuzzleState state )
	{
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				this.squares[i][j] = state.squares[i][j];
				if (this.squares[i][j] == 0) 
				{
					blank_row = i;
					blank_col = j;
				}
			}
		}
		
		this.numMoves = state.numMoves;
		numStates++;
	}
	
	
	public double fitness( )
	{
		int num_out = 0;
		for (int i = 0; i < SIZE; i++) {
			for  (int j = 0; j < SIZE; j++) {
				if ((i == (SIZE-1)) && (j == (SIZE-1))) {
					if (squares[i][j] != 0) num_out++;
				}
				else 
					if (squares[i][j] != (((i * SIZE) + j) + 1)) num_out++;
				
			}
		}
		
		double f = (double) num_out / (double) ((SIZE * SIZE ) - 1);
		return 1 - f;
	}
	
	
	
	
	
	private void swap(int r, int c, int a, int b)
	{
		squares[r][c] = squares[a][b];
		squares[a][b] = 0;
	}
	

	public void applyMove(int row, int col, Direction direction )
	{
	
		if ((direction == Direction.NORTH) && (row == 0)) throw new RuntimeException("");
		if ((direction == Direction.SOUTH) && (row == (SIZE-1))) throw new RuntimeException("");
		if ((direction == Direction.EAST) && (col == (SIZE-1))) throw new RuntimeException("");
		if ((direction == Direction.WEST) && (col == 0)) throw new RuntimeException("");
		if (squares[row][col] != 0) throw new RuntimeException("thpppt");
		
		
		switch(direction)
		{
		case NORTH: swap(row, col, row-1, col); blank_row--; break;
		case SOUTH: swap(row, col, row+1, col); blank_row++; break;
		case EAST: swap(row, col, row, col+1); blank_col++; break;
		case WEST: swap(row, col, row, col-1); blank_col--; break;
		default: throw new RuntimeException("dfklajfkadsjsaf");
		}
		
	}
	
	public PuzzleState applyMoves(LinkedList<Direction> dirs)
	{
		
		if (this.isGoal()) return this;
		
		// use copy constructor
		PuzzleState curr = new PuzzleState(this);
		
		// apply move history
		try {
			for (Direction D : dirs)
			{
				curr.applyMove(curr.blank_row, curr.blank_col, D);
				if (curr.isGoal()) return curr;
			}
		}
		catch(Throwable E) 
		{
			return curr;
		}
		
		return curr;
		
	}
	public boolean isGoal( )
	{
		for (int i = 0; i < SIZE; i++) 
			for (int j = 0; j < SIZE; j++)
			{
				if ((i == SIZE-1) && (j == SIZE-1)) {
					if (squares[i][j] != 0) return false;	
				}
				else if (squares[i][j] != (((i * SIZE + j)) + 1)) return false;
			}
		
		return true;
	}
	
	
}
