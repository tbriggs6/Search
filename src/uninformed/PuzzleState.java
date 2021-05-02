package uninformed;

import java.util.Arrays;
import java.util.LinkedList;

public class PuzzleState implements State {

	private final static int SIZE = 3;
	
	int squares[][] = new int[SIZE][SIZE];
	int numMoves = 0;
	static int numStates = 0;
	
	public int getNumNodes( ) { return numStates; }
	
//	long ivalue = 0;
	
	public enum Direction { NORTH, EAST, WEST, SOUTH };
	
	LinkedList<Direction> moves = new LinkedList<Direction>( );
		
	public PuzzleState( int squares[][] )
	{
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				this.squares[i][j] = squares[i][j];
//				ivalue |= (squares[i][j] << (((i * SIZE)+j) * 4));
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
//				ivalue |= (squares[i][j] << (((i * 3)+j) * 4));
			}
		}
		
		this.numMoves = state.numMoves;
		this.moves.addAll(state.moves);
		numStates++;
	}
	
	
	public int heuristic( )
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
		
		return num_out;
	}
	
	
	public int totalCost( ) {
		return heuristic() + numMoves;
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + (int) (ivalue ^ (ivalue >>> 32));
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		PuzzleState other = (PuzzleState) obj;
//		if (ivalue != other.ivalue)
//			return false;
//		return true;
//	}

	
	
	
	private void swap(int r, int c, int a, int b)
	{
		squares[r][c] = squares[a][b];
		squares[a][b] = 0;
		
//		for (int i = 0; i < 3; i++) {
//			for (int j = 0; j < 3; j++) {
//				ivalue |= (squares[i][j] << (((i * 3)+j) * 4));
//			}
//		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(squares);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuzzleState other = (PuzzleState) obj;
		if (!Arrays.deepEquals(squares, other.squares))
			return false;
		return true;
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
		case NORTH: swap(row, col, row-1, col); break;
		case SOUTH: swap(row, col, row+1, col); break;
		case EAST: swap(row, col, row, col+1); break;
		case WEST: swap(row, col, row, col-1); break;
		default: throw new RuntimeException("dfklajfkadsjsaf");
		}
		
		this.moves.add(direction);
		numMoves++;
	}
	
	@Override
	public LinkedList<State> nextStates() {
	
		LinkedList<State> next = new LinkedList<State>( );
		
		int zero_row = 0, zero_col = 0;
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (squares[i][j] == 0) {
					zero_row = i;
					zero_col = j;
					break;
				}
			}
		}
		
		// can move north?
		if (zero_row > 0) { 
			PuzzleState north = new PuzzleState( this );
			north.applyMove(zero_row, zero_col, Direction.NORTH);
			next.add(north);
		}

		// can move north?
		if (zero_row < SIZE-1) { 
			PuzzleState south = new PuzzleState( this );
			south.applyMove(zero_row, zero_col, Direction.SOUTH);
			next.add(south);
		}

		// can move east?
		if (zero_col < SIZE-1) { 
			PuzzleState east = new PuzzleState( this );
			east.applyMove(zero_row, zero_col, Direction.EAST);
			next.add(east);
		}

		// can move west?
		if (zero_col  > 0) { 
			PuzzleState west = new PuzzleState( this );
			west.applyMove(zero_row, zero_col, Direction.WEST);
			next.add(west);
		}

		return next;
		
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
	
//	public String toString( )
//	{
//		StringBuffer buff = new StringBuffer( );
//		buff.append(String.format("%d %d\n", this.numMoves, PuzzleState.numStates));
//		for (int i = 0; i < SIZE; i++) {
//			buff.append(String.format("[%d, %d, %d]\n",  squares[i][0], squares[i][1], squares[i][2]));
//		}
//		
//		buff.append(moves.toString());
//		return buff.toString();
//	}
//	
	public static void main(String args[])
	{
		
		int board[][] = { 
				{ 2,4,3 },
				{ 1, 0, 6 },
				{ 7,5,8} };
		
		
		PuzzleState S = new PuzzleState( board );

		long start = System.nanoTime();
		BreadthFirstSearch BFS = new BreadthFirstSearch();
		BFS.search(S);
		
//		AStar A = new AStar( );
//		A.search(S);
		
		long end = System.nanoTime();
		
		System.out.println("took: " + (end-start) + " ns");
		System.out.println(BFS.found.getNumNodes());
//		DepthFirstSearch DFS = new DepthFirstSearch();
//		DFS.search(S);
//		
	}
	
}
