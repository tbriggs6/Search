package genetic;

import java.util.LinkedList;

public class GeneticAlgorithm {

	public final static int generation_size = 1000;
	public final static int max_length = 27;
	
	PuzzleState initial;
	
	public GeneticAlgorithm(PuzzleState initial)
	{
		this.initial = initial;
	}
	
	
	public LinkedList<Direction> makeRandom( )
	{
		LinkedList<Direction> directions = new LinkedList<Direction>( );
		for (int i = 0; i < max_length; i++) {
			directions.add(getRandomDirection());
		}
		return directions;
	}


	Direction getRandomDirection() {
		double f = Math.random();
		if (f < 0.25) return Direction.NORTH;
		else if (f < 0.5) return Direction.EAST;
		else if (f < 0.75) return Direction.WEST;
		else return Direction.SOUTH;
	}
	
	
	public Gene[] makeInitialGeneration( )
	{
		Gene generation[] = new Gene[generation_size];
		for (int i = 0; i < generation_size; i++)
		{
			generation[i] = new Gene();
			generation[i].moves = makeRandom();
			PuzzleState state = initial.applyMoves(generation[i].moves);
			generation[i].fitness = state.fitness(); 
		}
		
		return generation;
	}
	
	public double avgFitness(Gene generation[])
	{
		double sum = 0;
		for (int i = 0; i < generation.length; i++)
		{
			sum += generation[i].fitness;
		}
		return sum / ((double) generation.length);
	}
	
	public double maxFitness(Gene generation[])
	{
		double max = Double.MIN_VALUE;
		
		for (int i = 0; i < generation.length; i++)
		{
			if (generation[i].fitness > max)
				max = generation[i].fitness;
		}
		return max;
	}
	
	public void search( )
	{
		
		Gene[] genes = makeInitialGeneration();
		
		System.out.format("Initial avg fitness: %f\n", avgFitness(genes));
		
		
		double sum = 0;
		for (int i = 0; i < genes.length; i++) {
			sum += genes[i].fitness;
			System.out.format("%f %s\n", genes[i].fitness, genes[i].moves);
		}
		
		/******** Search successive generations *********/
		for (int g = 0; g < 5000; g++) {
			Gene[] next = new Gene[generation_size];
		
			sum = 0;
			for (int i = 0; i < genes.length; i++) {
				sum += genes[i].fitness;
			}
			
			 
			for (int i = 0; i < genes.length; i++) {
			
				if (Math.random() < 0.05) {
					next[i] = mutation(genes);
				}
				else { 
					next[i] = selection(genes, sum);
				}
			}
			genes = next;
			
			System.out.format("g: %d Current avg fitness: %f max: %f\n", g, avgFitness(next), maxFitness(next));
		}
	}

	Gene mutation(Gene[] genes)
	{
		// pick a random member of prev generation
		int i = (int) (Math.random() * genes.length);
		Gene parent = genes[i];
		
		Gene mutant = new Gene( );
		mutant.moves = new LinkedList<Direction>( );
		mutant.moves.addAll(parent.moves);
	
		int loc = (int) (Math.random() * mutant.moves.size());
		
		mutant.moves.set(loc, getRandomDirection());
		
		PuzzleState result = initial.applyMoves(mutant.moves);
		mutant.fitness = result.fitness();
		
		return mutant;
	}
	
	private Gene selection(Gene[] genes, double sum) {
		Gene parentA = pickParent(genes, sum);
		Gene parentB = pickParent(genes, sum);

		int crossOver = (int) (Math.random() * max_length);
		Gene child = new Gene( );
		child.moves = new LinkedList<Direction>( );
		
		for (int k = 0; k < crossOver; k++)
		{
			child.moves.add( parentA.moves.get(k));
		}
		
		for (int k = crossOver; k < max_length; k++)
		{
			child.moves.add( parentB.moves.get(k));
		}
		
		PuzzleState result = initial.applyMoves(child.moves);
		child.fitness = result.fitness();
		return child;
	}


	private Gene pickParent(Gene[] genes, double sum) {
		
		double p = Math.random( ) * sum;
		
		double sum2 = 0;
		int j = 0;
		do {		
			sum2 += genes[j].fitness;
		} while ((++j < genes.length) && (sum2 < p));
		if (j == genes.length) j--;
		return genes[j];
	}

	
	public static void main(String args[])
	{
		int board[][] = { 
				{ 2,4,3 },
				{ 1, 0, 6 },
				{ 7,5,8} };
		
		
		PuzzleState S = new PuzzleState( board );
		GeneticAlgorithm GA = new GeneticAlgorithm(S);
		GA.search();
		
	}
	
}
