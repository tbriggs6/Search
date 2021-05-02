package alphabeta;

import java.util.LinkedList;

public class TicTacToe implements State {

	
	int size;
	
	char board[][];
	char lastPlayed = 'O';
	int depth = 0;
	
	public TicTacToe ( )
	{
		this.size = 3;
		board = new char[size][size];
		for (int r = 0; r < size; r++) 
			for (int c = 0; c < size; c++)
				board[r][c] = ' ';
		
		lastPlayed = 'O';
	}
	
	public TicTacToe ( TicTacToe T)
	{
		this.size = T.size;
		this.board = new char[size][size];
		for (int r = 0; r < size; r++) 
			for (int c = 0; c < size; c++)
				this.board[r][c] = T.board[r][c];
		
		this.lastPlayed = T.lastPlayed;
		this.depth = T.depth;
	}
	
	public TicTacToe(char board[][])
	{
		this.size = board.length;
		this.board = new char[size][size];
		int numX = 0, numO = 0;
		
		for (int r = 0; r < size; r++) 
			for (int c = 0; c < size; c++) {
				this.board[r][c] = board[r][c];
				if (board[r][c] == 'X') numX++;
				if (board[r][c] == 'O') numO++;
			}
			
		this.depth = numX + numO;
		
		if (numX == numO) this.lastPlayed = 'O';
		else if ((numX - numO) == 1) this.lastPlayed = 'X';
		else throw new RuntimeException("Error - invalid board state");
	}
	
	public String toString( )
	{
		final boolean fancy = false;
		
		if (fancy) {
			StringBuffer buff = new StringBuffer( );
			buff.append("\n");
			for (int r = 0; r < size; r++) {
				for (int i = 0; i < depth; i++) buff.append(" ");
				for (int c = 0; c < size; c++) {
					if (c > 0) buff.append("|");
					buff.append(String.format("%c", board[r][c]));
				}
				buff.append("\n");
				
				for (int c = 0; c < size; c++) {
					if (c > 0) buff.append("+");
					buff.append("-");
				}
				buff.append("\n");
			}
			
			return buff.toString();
		}
		
		else {
			
			StringBuffer buff = new StringBuffer( );
			buff.append("(");
			for (int r = 0; r < size; r++) {
				buff.append("(");
				for (int c = 0; c < size; c++) {
					if (c > 0) buff.append(",");
					buff.append(String.format("%c", board[r][c]));
				}
				buff.append(")");
			}
			buff.append(")");
			
			return buff.toString();
		}
	}
	
	private boolean isRowWin(char player, int row)
	{
		for (int c = 0; c < size; c++)
			if (board[row][c] != player) return false;
		
		return true;
	}
	
	private boolean isColWin(char player, int col)
	{
		for (int r = 0; r < size; r++)
			if (board[r][col] != player) return false;
		
		return true;
	}
	
	private boolean isDiagWin(char player)
	{
		if ((board[0][0] == player) &&
			(board[1][1] == player) &&
			(board[2][2] == player)) return true;
		
		if ((board[0][2] == player) &&
			(board[1][1] == player) &&
			(board[2][0] == player)) return true;
		
		return false;
	}
	
	public boolean isWin(char player)
	{
		for (int i = 0; i < size; i++) {
			if (isRowWin(player, i)) return true;
			if (isColWin(player, i)) return true;
		}
		if (isDiagWin(player)) return true;
		return false;
	}

	public boolean isDraw( )
	{
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				if (board[r][c] == ' ') return false;
		
		if (isWin('X')) return false;
		if (isWin('O')) return false;
		
		return true;
	}
	
	public LinkedList<State> next( )
	{
		LinkedList<State> next = new LinkedList<State>( );
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
			{
				if (board[r][c] == ' ') {
					TicTacToe t = new TicTacToe(this);
					t.board[r][c] = (lastPlayed == 'X') ? 'O' : 'X';
					t.lastPlayed = (lastPlayed == 'X') ? 'O' : 'X';
					t.depth++;
					next.add(t);
				}
			}
		
		return next;
	}

	public boolean isTerminal( )
	{
		if (isWin('X')) return true;
		if (isWin('O')) return true;
		if (isDraw()) return true;
		
		if (this.depth > 8) return true;
		
		LinkedList<State> children = next( );
		for (State S : children) {
			
			TicTacToe T = (TicTacToe) S;
			if (T.isWin('X') || T.isWin('O') || T.isDraw())
				return true;
		}
		
		return false;
	}
	
	
	public int utility( )
	{
		if (isWin('X')) return 1000;
		if (isWin('O')) return -1000;
		if (isDraw()) return 0;
		
		if (isTerminal()) {
			int sum = 0;
			LinkedList<State> children = next( );
			for (State S : children) {
				
				TicTacToe T = (TicTacToe) S;
				sum += T.evaluate();
			}	
			
			return sum;
		}
		
		else {
			return evaluate();
		}
	}
	
	private int evaluate( )
	{
		if (isWin('X')) return 1000;
		if (isWin('O')) return -1000;
		if (isDraw( )) return 0;
		
		int total = 0;
		
		for (int r = 0; r < size; r++)
		{
			
			int numX  = 0; 
			int numO = 0;
			int numSp = 0;
			
			for (int c = 0; c < size; c++)
			{
				if (board[r][c] == 'X') numX++;
				if (board[r][c] == 'O') numO++;
				if (board[r][c] == ' ') numSp++;
			}
			
			if ((numX > 0) && (numO == 0) && (numSp > 0)) total += (numX * size);
			if ((numO > 0) && (numX == 0) && (numSp > 0)) total -= (numO * size);
		}
		
		for (int c = 0; c < size; c++)
		{
			
			int numX  = 0; 
			int numO = 0;
			int numSp = 0;
			
			for (int r = 0; r < size; r++)
			{
				if (board[r][c] == 'X') numX++;
				if (board[r][c] == 'O') numO++;
				if (board[r][c] == ' ') numSp++;
			}
			
			if ((numX > 0) && (numO == 0)) total += (numX * size);
			if ((numO > 0) && (numX == 0)) total -= (numO * size);
		}
		
		{
			int numX  = 0; 
			int numO = 0;
			int numSp = 0;
		
		
			for (int i = 0; i < size; i++)
			{
				
				
				if (board[i][i] == 'X') numX++;
				if (board[i][i] == 'O') numO++;
				if (board[i][i] == ' ') numSp++;
			}
				
			if ((numX > 0) && (numO == 0)) total += (numX * size);
			if ((numO > 0) && (numX == 0)) total -= (numO * size); 
		}
		
		
		{
			int numX  = 0; 
			int numO = 0;
			int numSp = 0;
		
		
			for (int i = 0; i < size; i++)
			{
				
				
				if (board[i][size - i - 1] == 'X') numX++;
				if (board[i][size - i - 1] == 'O') numO++;
				if (board[i][size - i - 1] == ' ') numSp++;
			}
				
			if ((numX > 0) && (numO == 0)) total += (numX * size);
			if ((numO > 0) && (numX == 0)) total -= (numO * size);
		}
		
		return total;
		
	}
	
	@Override
	public int getDepth() {
		return this.depth;
	}

	@Override
	public char getPlayer( )
	{
		if (this.lastPlayed == 'X') 
			return 'O';
		else
			return 'X';
	}
	
	public static void main(String args[])
	{
//		char board[][] = {
//				{ 'O', ' ', ' ',' ',' ' },
//				{ ' ', 'X', ' ',' ',' ' },
//				{ ' ', ' ', ' ',' ', ' ' },
//				{ ' ', ' ', ' ',' ', ' ' },
//				{ ' ', ' ', ' ',' ', ' ' }
//				
//		};
		
		char board[][] = { 
				{ ' ', 'X', ' ' },
				{ ' ', 'X', 'O' },
				{ ' ', ' ', ' ' }
		};
		
		TicTacToe T = new TicTacToe(board);

		boolean use_ab = true;
		
		if (! use_ab) {
			Minimax M = new Minimax();
	
			State S = M.minimax_decision(T);
			System.out.println(S);
		}
		else {
			AlphaBeta ab = new AlphaBeta('X', 10);
			State move = ab.getMove(T, false, 7);
			System.out.println(move);
		}
		
	}

}
