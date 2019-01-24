
public class Sentence{
								// 1   ,  2  ,   3
	private String sentence;
	private int difficulty;		// Easy,  Med,  Hard
	private int tense; 			// Past, Present, Future
	
	public Sentence(int difficulty,int tense,String sentence){
		
		setDifficulty(difficulty);
		setSentence(sentence);
		setTense(tense);
		
	}
	
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getTense() {
		return tense;
	}

	public void setTense(int tense) {
		this.tense = tense;
	}
	
}
