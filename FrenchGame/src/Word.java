
public class Word {
		
	private String word;
						// 0  ,  1  ,     2    ,   3   ,      4     ,    5   ,  6    	  ,  7      8				  9					10
	private int type;	//noun, verb, adjective, adverb, conjunction, article, preposition, pronoun,possesive pronoun, verb infinitive, conjunction 
	private int gender;	//null, masculine, feminine
	private int number;	//null, singular, plural
	private int tense;	//null, past,present,future, pluperfect, imperfect, infinitive, ongoing
	private int person; //null, first person, second person, third person
	private int bags;   //no  , yes

	public Word(String word, int type, int gender, int number, int tense, int person, int bags){
		
		setWord(word);
		setType(type);
		setGender(gender);
		setNumber(number);
		setTense(tense);
		setPerson(person);
		setBags(bags);
		
	}
	
	public int getPerson() {
		return person;
	}
	public void setPerson(int view) {
		this.person = view;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTense() {
		return tense;
	}
	public void setTense(int tense) {
		this.tense = tense;
	}
	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}

}
