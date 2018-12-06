import java.util.List;

interface Eightpuzzles {
	//list of random 8 puzzle
	List<Member> createPopulation(int populationCount);
	// random 8 puzzle
	Member createRandomMember();
	// member is correct
	boolean checkArray(int[] randomMember, int a);
	
	void printPuzzle(Member member);
	// sort population
	void sortPopulationByFittnessScore();
	
	int getScore(int[][] array);

	void mutation(Member member);

	void crossOver(Member member1, Member member2);
	// is array contains this value?
	boolean contains(int[] array, int value);

	void crossOverPopulation();

	void mutationPopulation();
	
	Member getBestMember();
}