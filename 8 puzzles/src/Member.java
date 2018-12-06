
public class Member {
	int[][] memberArray;
	int fittnessScore;
	
	public Member(int[][] member, int fittnessScore) {
		super();
		this.memberArray = member;
		this.fittnessScore = fittnessScore;
	}
	
	
	public int[][] getMemberArray() {
		return memberArray;
	}


	public void setMemberArray(int[][] memberArray) {
		this.memberArray = memberArray;
	}


	public int getFittnessScore() {
		return fittnessScore;
	}
	public void setFittnessScore(int fittnessScore) {
		this.fittnessScore = fittnessScore;
	}
	
	public int[] getSimpleArray() {
		int[] array = new int[9];
		int k=0;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				array[k++] = this.memberArray[i][j];
			}
		}
		return array;
	}
	
	public void setMemberArrayWithSimpleArray(int[] array) {
		int k=0;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				this.memberArray[i][j] = array[k++] ;
			}
		}
	}
}
