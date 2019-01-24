import java.util.ArrayList;
import java.util.Scanner;

public class FileInput {

	static ArrayList<Word> Words = new ArrayList<Word>();
	static ArrayList<Sentence> Sentences = new ArrayList<Sentence>();
	static ArrayList<Highscore> highscores = new ArrayList<Highscore>();

	// Constructor
	public FileInput() {
		try {
			Scanner input = new Scanner(this.getClass().getResourceAsStream("/words.csv")); 
			
			input.useDelimiter(","); 
			int totalWords = input.nextInt();
			
			for (int x = 0; x < totalWords; x++){	
				Word temp = new Word(input.next(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(),input.nextInt());
				Words.add(temp);	
			}
			input.close();
		} catch (Exception e) {
			System.out.println("File not found! Try again.");
		}

		try {
			Scanner input = new Scanner(this.getClass().getResourceAsStream("/sentences.csv")); 

			input.useDelimiter(","); 
			int totalSentences = input.nextInt();
			
			for (int i = 0; i < totalSentences; i++){
				Sentence temp = new Sentence(input.nextInt(),input.nextInt(),input.next());
				Sentences.add(temp);	
			}
			input.close();
		} catch (Exception e) {
			System.out.println("File not found! Try again.");
		}

		
		try {
			Scanner input = new Scanner(this.getClass().getResourceAsStream("/highscores.csv")); 

			input.useDelimiter(","); 
			for (int i = 0; i < 10; i++){
				Highscore temp = new Highscore(input.next(),input.nextInt());
				highscores.add(temp);
			}
			
			input.close();

		} catch (Exception e) {
			System.out.println("File not found! Try again.");
		}
	}
}