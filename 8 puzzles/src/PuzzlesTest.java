
public class PuzzlesTest {

	public static void main(String[] args) {
		int[][] goalState = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, -1 } };
		int populationCount = 4;
		int maxGenerationCount = 2500;
		int successScore = 0;

		long start_time = System.nanoTime();
		
		int[] bestMemberFittnessArray = new int[maxGenerationCount];
		int i;

		EightPuzzlesImpl puzzle = new EightPuzzlesImpl(goalState, populationCount);
		for (i = 0; i < maxGenerationCount; i++) {
			Member bestMember = puzzle.getBestMember();
			System.out.println("Generation: " + i);
			puzzle.printPuzzle(bestMember);
			bestMemberFittnessArray[i] = bestMember.getFittnessScore();
			if (puzzle.getScore(bestMember.getMemberArray()) == successScore) {
				long end_time = System.nanoTime();
				double passedTime = (end_time - start_time) / 1e6;
				System.out.println("Mission Completed!" + "\n" + "Population Count: " + populationCount + "\n"
						+ "Solving time: " + passedTime / 1000 + " second!");
				break;
			} else {
				puzzle.crossOverPopulation();
				puzzle.mutationPopulation();
			}
		}
		if (i == maxGenerationCount) {
			System.out.println("Mission Failed!\nYou should be use more population or increase  generation count!");
		}
		
		
		i=1;
		for (int value : bestMemberFittnessArray) {

			if (value == 0) {
				System.out.print(" " + value);
				break;
			}
			System.out.print(" " + value);
			if(i%40 == 0)
				System.out.println();
			i++;

		}
	}
}
