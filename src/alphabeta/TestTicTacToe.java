package alphabeta;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class TestTicTacToe {

	@Test
	public void testIsWin1() {

		char board[][] = {
				{ 'X', ' ', 'O' },
				{ 'X', 'O', ' ' },
				{ 'X', ' ', ' ' } };
		
		TicTacToe T = new TicTacToe(board);
		
		assertTrue(T.isWin('X'));
		assertFalse(T.isWin('O'));
	}

	@Test
	public void testIsWin2() {

		char board[][] = {
				{ 'X', 'X', 'O' },
				{ 'X', 'O', 'X' },
				{ 'O', ' ', ' ' } };
		
		TicTacToe T = new TicTacToe(board);
		
		assertTrue(T.isWin('O'));
		assertFalse(T.isWin('T'));
	}

	@Test
	public void testIsDraw() {

		char board[][] = {
				{ 'X', 'X', 'O' },
				{ 'O', 'O', 'X' },
				{ 'X', 'X', 'O' } };
		
		TicTacToe T = new TicTacToe(board);
		
		assertTrue(T.isDraw());
		assertFalse(T.isWin('O'));
		assertFalse(T.isWin('T'));
	}
	
	@Test
	public void testNext() {
		
		TicTacToe T = new TicTacToe();
		LinkedList<State> list = T.next( );
		assertEquals(9, list.size());
		
		char board[][] = {
				{ 'X', 'X', 'O' },
				{ 'O', 'O', 'X' },
				{ 'X', 'X', 'O' } };
		
		T = new TicTacToe(board);
		assertTrue(T.isDraw());
		list = T.next();
		assertEquals(0, list.size());
	}
	
	@Test
	public void testEval() {

		char board[][] = {
				{ 'X', 'O', 'O' },
				{ 'X', 'X', 'O' },
				{ ' ', ' ', ' ' } };
		
		TicTacToe T = new TicTacToe(board);
		
		assertTrue(T.isTerminal());
		assertEquals(56, T.utility( ));
		
	}
	
	@Test
	public void testEvalFive( )
	{
		char board[][] = {
				{ 'X', 'O', 'O',' ',' ' },
				{ 'X', 'X', 'O',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' }
				};
		
		// x down = 5^3, x diag = 5^3, o down = -5^3
		TicTacToe T = new TicTacToe(board);
		
		assertTrue(T.isTerminal());
		assertEquals(173, T.utility( ));
	}

	@Test
	public void testEvalNext( )
	{
		char board[][] = {
				{ 'X', 'O', 'O',' ',' ' },
				{ 'X', 'X', 'O',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' }
				};
 
		TicTacToe T = new TicTacToe(board);
		assertEquals(19, T.next().size());

	}
	
	@Test
	public void testEvalWin( )
	{
		char board[][] = {
				{ 'X', 'O', 'O',' ',' ' },
				{ 'X', 'X', 'O',' ',' ' },
				{ 'X', 'O', ' ',' ',' ' },
				{ 'X', 'O', ' ',' ',' ' },
				{ 'X', ' ', ' ',' ',' ' }
				};
 
		TicTacToe T = new TicTacToe(board);
		assertTrue(T.isWin('X'));
		assertFalse(T.isWin('O'));

	}
	
	@Test
	public void testEvalTerminal5( )
	{
		char board[][] = {
				{ 'X', 'O', 'O',' ',' ' },
				{ 'X', 'X', 'O',' ',' ' },
				{ 'X', 'O', ' ',' ',' ' },
				{ 'X', 'O', ' ',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' }
				};
 
		TicTacToe T = new TicTacToe(board);
		assertTrue(T.isTerminal());
	}
	
	@Test
	public void testDepth( )
	{
		char board[][] = {
				{ 'X', 'O', 'O',' ',' ' },
				{ 'X', 'X', 'O',' ',' ' },
				{ 'X', 'O', ' ',' ',' ' },
				{ 'X', 'O', ' ',' ',' ' },
				{ ' ', ' ', ' ',' ',' ' }
				};
 
		TicTacToe T = new TicTacToe(board);
		T.depth = 5;
		
		for(State S : T.next())
		{
			assertEquals(S.getDepth(), 6);
		}
	}
}
