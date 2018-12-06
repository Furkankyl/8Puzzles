import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EightPuzzlesImpl implements Eightpuzzles {

	private int[][] goalState;
	private List<Member> population;
	private int populationCount;
	private Random generator;

	public EightPuzzlesImpl(int[][] goalState, int populationCount) {
		this.goalState = goalState;
		this.generator = new Random();
		this.population = createPopulation(populationCount);
		this.populationCount = 2;
		sortPopulationByFittnessScore();
	}

	@Override
	public List<Member> createPopulation(int populationCount) {
		List<Member> population = new ArrayList<>();

		for (int i = 0; i < populationCount; i++) {
			population.add(createRandomMember());
		}
		return population;
	}

	@Override
	public Member createRandomMember() {
		int[] tmpArray = new int[9];
		int[][] randomMemberArray = new int[3][3];

		int k = 0;
		int a;
		for (int i = 0; i < 9;) {
			a = (generator.nextInt(9));
			if (a == 0)
				a = -1;
			if (checkArray(tmpArray, a)) {
				tmpArray[i] = a;
				i++;
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				randomMemberArray[i][j] = tmpArray[k++];
			}
		}

		return new Member(randomMemberArray, getScore(randomMemberArray));

	}

	@Override
	public boolean checkArray(int[] randomMember, int a) {
		for (int i : randomMember) {
			if (a == i) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void printPuzzle(Member member) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (member.getMemberArray()[i][j] != -1)
					System.out.print(" " + member.getMemberArray()[i][j]);
				else
					System.out.print(" 0");
			}
			if (i == 1) {
				System.out.print("  Fittness Score: " + member.getFittnessScore());
			}
			System.out.println();
		}
		System.out.println();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sortPopulationByFittnessScore() {
		// sorting population by fittness score
		HashMap<Member, Integer> hashMap = new HashMap<>();
		for (Member member : population) {
			hashMap.put(member, (int) member.getFittnessScore());
		}
		Object[] a = hashMap.entrySet().toArray();
		Arrays.sort(a, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ((Map.Entry<Member, Integer>) o2).getValue()
						.compareTo(((Map.Entry<Member, Integer>) o1).getValue());
			}
		});
		population.clear();
		for (Object e : a) {
			Member member = (Member) ((Map.Entry<Member, Integer>) e).getKey();
			population.add(member);
		}

	}

	@Override
	public int getScore(int[][] array) {
		int score = 0;
		for (int f = 0; f < 3; f++) {
			for (int k = 0; k < 3; k++) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {

						if (array[f][k] == goalState[i][j]) {

							if ((i - f) < 0) {
								score -= (i - f);
							} else {
								score += (i - f);
							}
							if ((j - k) < 0) {
								score -= (j - k);
							} else {
								score += (j - k);
							}
						}
					}
				}
			}
		}
		return score;
	}

	@Override
	public void mutationPopulation() {
		for (int i = 0; i < populationCount; i++)
			//mutation(population.get(generator.nextInt(population.size())));
			mutation(population.get(i));
		sortPopulationByFittnessScore();
	}
	
	@Override
	public void mutation(Member member) {
		int[] array = member.getSimpleArray();
		int[] tmpArray = member.getSimpleArray();
		
		int score = member.getFittnessScore();
		int mutationPoint1 = generator.nextInt(9);
		int mutationPoint2 = generator.nextInt(9);

		int tmp = array[mutationPoint1];
		array[mutationPoint1] = array[mutationPoint2];
		array[mutationPoint2] = tmp;

		member.setMemberArrayWithSimpleArray(array);
		if (getScore(member.getMemberArray()) > score) {
			member.setMemberArrayWithSimpleArray(tmpArray);
		} else {
			member.setFittnessScore(getScore(member.getMemberArray()));
		}
	}
	@Override
	public void crossOverPopulation() {
		
		for (int i = 0; i < populationCount; i++)
			crossOver(population.get(generator.nextInt(population.size())),
					population.get(generator.nextInt(population.size())));
		/*
		for(int i=0;i<populationCount;i+=2) {
			crossOver(population.get(i), population.get(i+1));
		}
		*/
		sortPopulationByFittnessScore();
	}

	@Override
	public void crossOver(Member member1, Member member2) {
		Member tmp1Member = member1;
		Member tmp2Member = member2;
		
		int crossPoint = generator.nextInt(9);
		int[] member1Array = member1.getSimpleArray();
		int[] member2Array = member2.getSimpleArray();
		int[] tmpArray = new int[9];
		int[] tmpArray2 = new int[9];
		for (int i = 0; i < 9; i++) {
			if (i < crossPoint)
				tmpArray[i] = member1Array[i];
			else
				tmpArray2[i] = member1Array[i];
		}
		for (int i = 0; i < 9; i++) {
			if (!contains(tmpArray, member2Array[i])) {
				tmpArray[crossPoint++] = member2Array[i];
			}
		}
		int j = 0;
		for (int i = 0; i < 9; i++) {
			if (!contains(tmpArray2, member2Array[i])) {
				tmpArray2[j++] = member2Array[i];
			}
		}

		member1.setMemberArrayWithSimpleArray(tmpArray);
		member1.setFittnessScore(getScore(member1.getMemberArray()));
		
		if (member1.getFittnessScore() > tmp1Member.getFittnessScore()) {
			member1 = tmp1Member;
		}

		member2.setMemberArrayWithSimpleArray(tmpArray2);
		member2.setFittnessScore(getScore(member2.getMemberArray()));

		if (member2.getFittnessScore() > tmp2Member.getFittnessScore()) {
			member2 = tmp2Member;
		}
	
	}

	@Override
	public boolean contains(int[] array, int value) {
		for (int i : array) {
			if (i == value)
				return true;
		}
		return false;
	}

	

	@Override
	public Member getBestMember() {
		return this.population.get(this.population.size() - 1);
	}

}
