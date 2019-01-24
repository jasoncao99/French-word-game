import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;

// Panel Class
@SuppressWarnings("serial")
public class gamePanel extends JPanel implements ActionListener {

	// Fields/Variables
	Timer gameTimer = new Timer(1000, this);

	private int timeLeft = 60;
	private JLabel timerLabel = new JLabel();

	private JTextPane wordbank = new JTextPane();

	private int score = 0;
	private int questionsAnswered = 0;
	private JLabel scoreLabel = new JLabel();

	private JTextPane textBox = new JTextPane();
	Font font = new Font ("Arial",Font.BOLD,12);

	private JButton checkAnswer = new JButton("Check Answer");
	private JButton nextQuestion = new JButton("Next Question");
	private JLabel answerLabel = new JLabel();
	boolean answer;

	// Buttons for Accented letters
	private JButton[] accents = new JButton[7];
	private String[] accentLetters = {"É","é","è","ï","â","ô","û"};

	Random rand = new Random();

	String[] words1;
	String[] words2;

	// Put the words from an array to an arraylist to shuffle the words
	ArrayList<String> Words = new ArrayList<String>();

	// Background images
	private ImageIcon background1, background2, background3, background4, background5, background6, background7, background8, background9, background10;
	private JLabel backgroundLabel = new JLabel();

	// Constructor
	public gamePanel(int difficulty){

		super();
		setFocusable(true);
		setLayout(null);
		setDoubleBuffered(true);
		requestFocus();

		// Set gamePanel background color based on difficulty
		if (gameGUI.getDifficulty() == 1){
			setBackground(Color.GREEN);
		}else if (gameGUI.getDifficulty() == 2) {
			setBackground(Color.YELLOW);
		}else{
			setBackground(Color.RED);
		}
		setupAnswer();
		setupTimer();
		setupText();
		setupWordBank();
		setupScoreLabel();
		setupButtons();
		answerLabel.setVisible(false);
		setupBackground();
	}

	// Setup Accent Buttons
	public void setupButtons(){

		accents[0] = new JButton("É");
		accents[1] = new JButton("é");
		accents[2] = new JButton("è");
		accents[3] = new JButton("ï");
		accents[4] = new JButton("â");
		accents[5] = new JButton("ô");
		accents[6] = new JButton("û");

		for (int x = 0; x < accents.length; x++){
			accents[x].setFont(font);
			accents[x].setVisible(true);
			accents[x].addActionListener(this);
			accents[x].setBounds(40 + (50 * x),270,50,50);
			this.add(accents[x]);
			//textBox.setBounds(40,320,350,60);
			//è é ï É â
		}
	}

	// Setup Word Bank
	public void setupWordBank(){

		wordbank.setVisible(true);
		wordbank.setBounds(30,400,400,150);
		wordbank.setOpaque(true);
		wordbank.setEditable(false);
		wordbank.setFont(new Font("Arial",Font.BOLD,16));
		this.add(wordbank);

		if (gameGUI.getDifficulty() == 1){
			wordbank.setBackground(Color.GREEN);
		}else if (gameGUI.getDifficulty() == 2) {
			wordbank.setBackground(Color.YELLOW);
		}else{
			wordbank.setBackground(Color.RED);
		}

		ArrayList<Sentence> options = new ArrayList<Sentence>();
		// Pick the sentence
		for (int x = 0; x < FileInput.Sentences.size() ; x++){
			if (FileInput.Sentences.get(x).getDifficulty() == gameGUI.getDifficulty()){
				options.add(FileInput.Sentences.get(x));
			}
		}

		int randomSentence = rand.nextInt(options.size());
		String sentence1 = options.get(randomSentence).getSentence();
		words1 = sentence1.split("\\s+");

		options.remove(randomSentence);

		String sentence2 = options.get(rand.nextInt(options.size())).getSentence();
		words2 = sentence2.split("\\s+");

		for(int x = 0; x < words1.length;x++){
			Words.add(words1[x]);
		}
		for(int x = 0; x < words2.length;x++){
			Words.add(words2[x]);
		}

		for(int x = 0; x < 24; x++){
			int random = rand.nextInt(Words.size());
			String randomWord = Words.get(random);
			Words.remove(random);
			Words.add(randomWord);
		}


		Collections.shuffle(Words);
		wordbank.setText("");
		for(int i = 0; i < Words.size(); i++){
			wordbank.setText(wordbank.getText() + Words.get(i) +"    " );
		}
	}

	// Set up background images
	private void setupBackground(){

		background1 = new ImageIcon();
		background1 = new ImageIcon(this.getClass().getResource("Image1.jpg"));
		background2 = new ImageIcon();
		background2 = new ImageIcon(this.getClass().getResource("Image2.jpg"));
		background3 = new ImageIcon();
		background3 = new ImageIcon(this.getClass().getResource("Image3.jpg"));
		background4 = new ImageIcon();
		background4 = new ImageIcon(this.getClass().getResource("Image4.png"));
		background5 = new ImageIcon();
		background5 = new ImageIcon(this.getClass().getResource("Image5.jpg"));
		background6 = new ImageIcon();
		background6 = new ImageIcon(this.getClass().getResource("Image6.jpg"));
		background7 = new ImageIcon();
		background7 = new ImageIcon(this.getClass().getResource("Image7.jpg"));
		background8 = new ImageIcon();
		background8 = new ImageIcon(this.getClass().getResource("Image8.jpeg"));
		background9 = new ImageIcon();
		background9 = new ImageIcon(this.getClass().getResource("Image9.jpg"));
		background10 = new ImageIcon();
		background10 = new ImageIcon(this.getClass().getResource("Image10.jpg"));

		backgroundLabel.setVisible(false);
		backgroundLabel.setBounds(0,0,600,600);
		backgroundLabel.setIcon(background1);
		this.add(backgroundLabel);
	}

	// Change Word Bank 
	private void changeWordBank(){
		Words.clear();
		wordbank.setVisible(true);

		ArrayList<Sentence> options = new ArrayList<Sentence>();
		// Pick the sentence
		for (int x = 0; x < FileInput.Sentences.size() ; x++){
			if (FileInput.Sentences.get(x).getDifficulty() == gameGUI.getDifficulty()){
				options.add(FileInput.Sentences.get(x));
			}
		}

		int randomSentence = rand.nextInt(options.size());
		String sentence1 = options.get(randomSentence).getSentence();
		words1 = sentence1.split("\\s+");

		options.remove(randomSentence);

		String sentence2 = options.get(rand.nextInt(options.size())).getSentence();
		words2 = sentence2.split("\\s+");

		for(int x = 0; x < words1.length;x++){
			Words.add(words1[x]);
		}
		for(int x = 0; x < words2.length;x++){
			Words.add(words2[x]);
		}

		for(int x = 0; x < 24; x++){
			int random = rand.nextInt(Words.size());
			String randomWord = Words.get(random);
			Words.remove(random);
			Words.add(randomWord);
		}


		Collections.shuffle(Words);
		wordbank.setText("");

		for(int i = 0; i < Words.size(); i++){
			wordbank.setText(wordbank.getText() + Words.get(i)+ "    ");
		}

	}

	// Check Answer
	public boolean checkAnswer(){

		boolean check = false;
		boolean article = false;
		boolean noun = false;		
		boolean verb = false;

		String answer = textBox.getText();
		String [] answers = answer.split("\\s+");

		ArrayList<String> words = new ArrayList<String>();

		// Put the user's answer into an arraylist
		for(int x = 0; x < answers.length; x++){
			words.add(answers[x]);
		}

		// If a verb is made of two words, combine them into one string
		for(int x = 0; x < words.size(); x++){
			if(words.get(x).replaceAll("\\s+","").equalsIgnoreCase("A".replaceAll("\\s+",""))){
				if(words.get(x+1).replaceAll("\\s+","").equalsIgnoreCase("Détruit".replaceAll("\\s+",""))){
					words.remove(x+1);
					words.remove(x);
					words.add(x,"A Détruit");
				}else if(words.get(x+1).replaceAll("\\s+","").equalsIgnoreCase("Volé".replaceAll("\\s+",""))){
					words.remove(x+1);
					words.remove(x);
					words.add(x,"A Volé");
				}
			}

			if(words.get(x).replaceAll("\\s+","").equalsIgnoreCase("Ont".replaceAll("\\s+",""))){
				if(words.get(x+1).replaceAll("\\s+","").equalsIgnoreCase("Sauvé".replaceAll("\\s+",""))){
					words.remove(x+1);
					words.remove(x);
					words.add(x,"Ont Sauvé");
				}
			}

			if(words.get(x).replaceAll("\\s+","").equalsIgnoreCase("Se".replaceAll("\\s+",""))){
				if(words.get(x+1).replaceAll("\\s+","").equalsIgnoreCase("Battront".replaceAll("\\s+",""))){
					words.remove(x+1);
					words.remove(x);
					words.add(x,"se battront");
				}else if(words.get(x+1).replaceAll("\\s+","").equalsIgnoreCase("Protéger".replaceAll("\\s+",""))){
					words.remove(x+1);
					words.remove(x);
					words.add(x,"Se Protéger");
				}
			}

		}

		String [] answerWords = new String[words.size()];

		for(int x = 0; x < words.size(); x++){
			answerWords[x] = words.get(x);
		}

		// Check if each word typed is from the word bank
		for(int x = 0; x < answerWords.length; x++){
			for(int y = 0; y < Words.size(); y++){
				if(answers[x].replaceAll("\\s+","").equalsIgnoreCase(Words.get(y).replaceAll("\\s+",""))){
					check = true;
				}
			}

			if (check == true && x != answerWords.length -1){
				check = false;
			}else if (check == false){
				break;
			}
		}

		// If a word is not from the word bank, then return false
		if(check == false){
			return false;
		}


		// Check if the answer has an article, noun, and a verb 
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){				

					// Check for noun
					if(FileInput.Words.get(y).getType() == 0){
						noun = true;
						// Check for verb
					}else if(FileInput.Words.get(y).getType() == 1){
						verb = true;
					}
					// Check for article
					else if(FileInput.Words.get(y).getType() == 5){
						article = true;
					}
				}
			}
		}

		// If a noun, verb, or article wasn't found,then return false
		if(noun == false || verb == false || article == false){
			return false;
		}

		// Les can be both used masculine or feminine
		boolean les = false;
		boolean nounafter = false;
		boolean adjectiveafter = false;

		// Check that every article has a adjective or noun after it, if an adjective is after the article, check if there is a noun after the adjective, check that gender and that singular/plural match the noun
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){
					if(FileInput.Words.get(y).getType() == 5){
						if(FileInput.Words.get(y).getWord().replaceAll("\\s+","").equalsIgnoreCase("Les")){
							les = true;
						}
						// If the last word is an article, then return false
						if(x == answerWords.length - 1){
							return false;
						}

						for(int z =0; z < FileInput.Words.size() ;z++){
							if(answerWords[x+1].equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

								// If it is a noun, make sure the gender, and singular/plural match
								if(FileInput.Words.get(z).getType() == 0){
									if(FileInput.Words.get(y).getGender() != FileInput.Words.get(z).getGender() && les == false){
										return false;
									}else if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(z).getNumber()){
										return false;
									}
									nounafter = true;

									// If it is an adjective
								}else if (FileInput.Words.get(z).getType() == 2){
									if(x == answer.length() -2){
										return false;
									}

									for(int i =0; i < FileInput.Words.size() ;i++){
										if(answerWords[x+2].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(i).getWord().replaceAll("\\s+",""))){
											// If the second word is a noun, check if the gender, and singular/plural match
											if(FileInput.Words.get(i).getType() == 0){
												if(FileInput.Words.get(y).getGender() != FileInput.Words.get(i).getGender() && les == false){
													return false;
												}else if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(i).getNumber()){
													return false;
												}
												adjectiveafter = true;
												// If it is not a noun, return false
											}else{
												return false;
											}
										}
									}										
								}
							}
						}
						if(nounafter == false && adjectiveafter == false){
							return false;
						}
					}
				}
			}
		}

		// Check that every verb is after a noun or adjective, if it is after a adjective, check that the adjective is after a noun. Check that the verb's gender and singular/plural matches with the noun
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){
					if(FileInput.Words.get(y).getType() == 1){
						for(int z =0; z < FileInput.Words.size() ;z++){
							// If the first word is a verb, return false
							if(x == 0){
								return false;
							}

							if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){
								// If the word before the verb isn't a noun or adjective, then return false
								if(FileInput.Words.get(z).getType() != 0 && FileInput.Words.get(z).getType() != 2){
									return false;
								}else{
									// If it is a noun check that the gender and singular/plural matches
									if(FileInput.Words.get(z).getType() == 0){
										if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(z).getNumber()){
											return false;
										}else if(FileInput.Words.get(y).getGender() != FileInput.Words.get(z).getGender()){
											return false;
										}

									}else if(FileInput.Words.get(z).getType() == 2){

										// If the second word is a verb, and the first is an adjective
										if(x == 1){
											return false;
										}

										for(int i =0; i < FileInput.Words.size() ;i++){
											if(answerWords[x-2].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(i).getWord().replaceAll("\\s+",""))){
												// If the second word is a noun, check if the gender, and singular/plural match
												if(FileInput.Words.get(i).getType() == 0){
													if(FileInput.Words.get(y).getGender() != FileInput.Words.get(i).getGender()){
														return false;
													}else if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(i).getNumber()){
														return false;
													}
													// If it is not a noun, return false
												}else{
													return false;
												}
											}
										}			
									}
								}
							}
						}
					}
				}
			}
		}

		boolean checknoun = false;
		boolean checkadverb = false;
		boolean checkverb = false;

		// Check if the adjective is in the right place, if it follows BAGS, it goes before the noun, else it goes after. Adjectives can also be after a verb, and adverb. Gender and singular/plural must agree with the noun. 
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){

					// Find the adjectives
					if(FileInput.Words.get(y).getType() == 2){

						// If it has BAGS, make sure there is a noun after it
						if(FileInput.Words.get(y).getBags() == 1){

							// If the last word is the adjective, return false
							if(x == answerWords.length - 1){
								return false;
							}

							for(int z =0; z < FileInput.Words.size() ;z++){
								if(answerWords[x+1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

									// If it is a noun, check the gender, and singular/plural match
									if(FileInput.Words.get(z).getType() == 0){
										if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(z).getNumber()){
											return false;
										}else if(FileInput.Words.get(y).getGender() != FileInput.Words.get(z).getGender()){
											return false;
										}

									}else{
										return false;
									}
								}
							}


							// If it doesn't have BAGS, it's either noun + verb + adverb + adjective, or noun + verb + adjective, or noun + adjective
						}else if(FileInput.Words.get(y).getBags() == 0){

							// If it is the first word is the adjective 
							if(x == 0){
								return false;
							}

							for(int z =0; z < FileInput.Words.size() ;z++){
								if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

									// If the word behind is a noun, check the gender, and singular/plural match
									if(FileInput.Words.get(z).getType() == 0){
										if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(z).getNumber()){
											return false;
										}else if(FileInput.Words.get(y).getGender() != FileInput.Words.get(z).getGender()){
											return false;
										}
										checknoun = true;
									}
								}
							}

							// Check if it is after a verb
							if(checknoun == false){
								for(int z =0; z < FileInput.Words.size() ;z++){
									if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

										// Check if it is after a verb
										if(FileInput.Words.get(z).getType() == 1){
											if(x == 1){
												return false;
											}

											// Check if the verb is after a noun
											for(int i =0; i < FileInput.Words.size() ;i++){
												if(answerWords[x-2].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(i).getWord().replaceAll("\\s+",""))){

													// If it is, check the gender and plural/singular match
													if(FileInput.Words.get(i).getType() == 0){
														if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(i).getNumber()){
															return false;
														}else if(FileInput.Words.get(y).getGender() != FileInput.Words.get(i).getGender()){
															return false;
														}
														checkverb = true;
													}
												}
											}
										}
									}
								}																
							}

							// Check if it is after an adverb
							if(checknoun == false && checkverb == false){
								for(int z =0; z < FileInput.Words.size() ;z++){
									if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

										// If the word before is an adverb
										if(FileInput.Words.get(z).getType() == 3){
											if(x == 1){
												return false;
											}

											// Check if the adverb is after a verb
											for(int i =0; i < FileInput.Words.size() ;i++){
												if(answerWords[x-2].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(i).getWord().replaceAll("\\s+",""))){

													// If it is, check if the verb is after a noun
													if(FileInput.Words.get(i).getType() == 1){

														if(x==2){
															return false;
														}

														for(int j =0; j < FileInput.Words.size() ;j++){
															if(answerWords[x-3].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(j).getWord().replaceAll("\\s+",""))){

																// If it is noun, check the gender and plura/singular match
																if(FileInput.Words.get(j).getType() == 0){
																	if(FileInput.Words.get(y).getNumber() != FileInput.Words.get(j).getNumber()){
																		return false;
																	}else if(FileInput.Words.get(y).getGender() != FileInput.Words.get(j).getGender()){
																		return false;
																	}
																	checkadverb = true;
																}
															}
														}
													}
												}
											}
										}
									}
								}						
							}

							// If the adjective wasn't in any of the places, return false
							if(checknoun ==false && checkverb == false && checkadverb == false){
								return false;
							}
						}
					}
				}
			}
		}

		boolean afterverb = false;
		boolean beginning = false;
		boolean end = false;

		// Adverbs go after a verb, at the beginning of a sentence, or at the end
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){

					// Find the adverbs
					if(FileInput.Words.get(y).getType() == 3){

						// If the adverb is at the beginning of the sentence
						if(x == 0){
							beginning = true;
						}

						// If the adverb is at the end of a sentence
						if(beginning == false && x == answerWords.length - 1){

							end = true;
						}


						// If it is after a verb
						if(beginning == false && end == false){

							// Check if it is after a verb
							for(int z =0; z < FileInput.Words.size() ;z++){

								if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){
									// If it is not after a verb, return false
									if(FileInput.Words.get(z).getType() != 1){

										return false;
									}else{
										afterverb = true;
									}
								}							
							}
						}

						// If a adverb was found, and none of the requirements was met, return false
						if(afterverb == false && beginning == false && end == false){
							return false;
						}
					}
				}
			}
		}

		// Prepositions are after a noun or after an adjective that is after a noun, or after a adjective after a verb after a noun
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){

					// Find the preposition
					if(FileInput.Words.get(y).getType() == 6){

						for(int z =0; z < FileInput.Words.size() ;z++){
							// If the first word is a preposition, return false
							if(x == 0){
								return false;
							}

							if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

								// If the word before the verb isn't a noun or adjective, then return false
								if(FileInput.Words.get(z).getType() != 0 && FileInput.Words.get(z).getType() != 2){
									return false;
								}else{
									// If it is a an adjective, check it is after a noun, or verb
									if(FileInput.Words.get(z).getType() == 2){

										// If the second word is a preposition, and the first is an adjective/verb
										if(x == 1){
											return false;
										}

										for(int i =0; i < FileInput.Words.size() ;i++){
											// Check if the second word is a noun/verb
											if(answerWords[x-2].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(i).getWord().replaceAll("\\s+",""))){
												if(FileInput.Words.get(i).getType() != 0 && FileInput.Words.get(i).getType() != 1 && FileInput.Words.get(i).getType() != 3){	
													return false;
												}else if(FileInput.Words.get(i).getType() == 1){
													for(int j =0; j < FileInput.Words.size() ;j++){
														if(answerWords[x-3].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(j).getWord().replaceAll("\\s+",""))){
															if(FileInput.Words.get(j).getType() != 0){
																return false;
															}
														}
													}
												}
											}
										}			
									}
								}
							}
						}
					}
				}
			}
		}

		// Possesive pronouns are after prepositions
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){

					// Find the possesuve pronoun
					if(FileInput.Words.get(y).getType() == 8){
						for(int z =0; z < FileInput.Words.size() ;z++){
							// If the first word is a possesive pronoun, return false
							if(x == 0){
								return false;
							}

							if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){
								// If the word before isn't a preposition, return false
								if(FileInput.Words.get(z).getType() != 6){
									return false;
								}
							}
						}
					}
				}
			}
		}

		//verb infinitives are after prepositions
		for(int x = 0; x < answerWords.length; x++){
			for(int y =0; y < FileInput.Words.size() ;y++){
				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){

					// Find the verb infinitive
					if(FileInput.Words.get(y).getType() == 9){

						for(int z =0; z < FileInput.Words.size() ;z++){
							// If the first word is a verb infinitive, return false
							if(x == 0){
								return false;
							}

							if(answerWords[x-1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

								// If the word before isn't a preposition, return false
								if(FileInput.Words.get(z).getType() != 6){
									return false;
								}
							}
						}
					}
				}
			}
		}

		// Conjuctions go before an article
		for(int x = 0; x < answerWords.length; x++){

			for(int y =0; y < FileInput.Words.size() ;y++){

				if(answerWords[x].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(y).getWord().replaceAll("\\s+",""))){

					// Find the conjuction
					if(FileInput.Words.get(y).getType() == 10){

						for(int z =0; z < FileInput.Words.size() ;z++){

							// If the last word is a conjunction, return false
							if(x == answerWords.length - 1){
								return false;
							}

							if(answerWords[x+1].replaceAll("\\s+","").equalsIgnoreCase(FileInput.Words.get(z).getWord().replaceAll("\\s+",""))){

								// If the word before isn't an article, return false
								if(FileInput.Words.get(z).getType() != 5){
									return false;
								}
							}
						}
					}
				}
			}
		}
		// Return true if no errors are found
		return true;
	}


	// Setup Text 
	private void setupText(){
		textBox.setVisible(true);
		textBox.setBounds(40,320,350,60);
		this.add(textBox);

		checkAnswer.setVisible(true);
		checkAnswer.addActionListener(this);
		checkAnswer.setBounds(430,325,150,50);
		this.add(checkAnswer);
	}

	// Setup Quiz Timer
	private void setupTimer(){

		gameTimer.addActionListener(null);
		gameTimer.start();
		timerLabel.setVisible(true);
		timerLabel.setBounds(20,20,150,50);
		timerLabel.setText("Time remaining: " + timeLeft);
		this.add(timerLabel);
	}

	// Setup Score Labels 
	private void setupScoreLabel(){

		scoreLabel.setVisible(true);
		scoreLabel.setBounds(500,20,150,50);
		scoreLabel.setText("Score "+ score);
		this.add(scoreLabel);

	}

	// Setup Answer Button
	private void setupAnswer(){

		answerLabel.setVisible(true);
		answerLabel.setOpaque(true);
		answerLabel.setBounds(100,420,350,60);
		answerLabel.setFont(new Font("Arial",Font.BOLD,32));
		answerLabel.setForeground(Color.WHITE);
		answerLabel.setBackground(Color.BLACK);
		this.add(answerLabel);

		nextQuestion.setVisible(false);
		nextQuestion.addActionListener(this);
		nextQuestion.setBounds(430,325,150,50);
		this.add(nextQuestion);

		// Update the score label
		if(answer == true){
			answerLabel.setText("Correct!");
			score = (int) (score + (gameGUI.getDifficulty() * 1.5) + timeLeft);
			scoreLabel.setText("Score " +score);

		}else{
			answerLabel.setText("Invalid Sentence");	
		}

	}

	// Remove Labels 
	private void removeAllLabels(){

		timerLabel.setVisible(false);
		wordbank.setVisible(false);
		textBox.setVisible(false);
		checkAnswer.setVisible(false);

		answerLabel.setVisible(true);
		answerLabel.setOpaque(true);
		answerLabel.setBounds(100,420,350,60);
		answerLabel.setFont(new Font("Arial",Font.BOLD,32));
		answerLabel.setForeground(Color.WHITE);
		answerLabel.setBackground(Color.BLACK);

		nextQuestion.setVisible(true);
		nextQuestion.setBounds(430,325,150,50);

		if(answer == true){
			answerLabel.setText("Correct!");
			score = (int) (score + (gameGUI.getDifficulty() * 1.5) + timeLeft);
			scoreLabel.setText("Score " +score);

		}else{
			answerLabel.setText("Invalid Sentence");	
		}
		backgroundLabel.setVisible(true);
	}

	// Set up Labels 
	private void setupLabels(){

		timerLabel.setVisible(true);
		scoreLabel.setVisible(true);
		wordbank.setVisible(true);
		textBox.setVisible(true);
		checkAnswer.setVisible(true);

		answerLabel.setVisible(false);
		nextQuestion.setVisible(false);
		backgroundLabel.setVisible(false);
	}

	
	public void actionPerformed(ActionEvent e) {

		// Background image array
		ImageIcon[] background = {background1, background2, background3, background4, background5, background6, background7, background8, background9, background10};
		Random rnd = new Random();
		int rndB = 0;

		if (e.getSource() == gameTimer){
			timeLeft--;
			timerLabel.setText("Time remaining: " + timeLeft);

			if(timeLeft < 1){
				answer = checkAnswer();
				gameTimer.stop();
				removeAllLabels();

				for (JButton list: accents){
					list.setVisible(false);
				}
			}

		}else if (e.getSource() == checkAnswer){

			rndB = rnd.nextInt(background.length);
			System.out.println(rndB);
			answer = checkAnswer();
			gameTimer.stop();
			removeAllLabels();
			for (JButton list: accents){
				list.setVisible(false);
			}
			backgroundLabel.setIcon(background[rndB]);
			
		}else if(e.getSource() == nextQuestion){

			questionsAnswered++;
			if(questionsAnswered < 10){
				textBox.setText("");
				changeWordBank();
				timeLeft = 60;
				gameTimer.start();
				setupLabels();
				for (JButton list: accents){
					list.setVisible(true);
				}
			}else{
				gameGUI.mainmenu.setVisible(true);
				scoreLabel.setVisible(false);
				nextQuestion.setVisible(false);
				answerLabel.setBounds(100,420,550,60);
				answerLabel.setText("Quiz Finished, Final Score: " + score);
				updateHighscore();
				score = 0;
			}
		}
		for (int x = 0; x < accents.length; x++){
			if (e.getSource() == accents[x]){
				textBox.setText(textBox.getText() + accentLetters[x]);
			}
		}
	}

	// Updates High Scores
	public void updateHighscore(){

		ArrayList<Object> list = new ArrayList<Object>();
		list.add(gameGUI.getUsername());
		list.add(gameGUI.getPassword());
		list.add(score);
		Spreadsheets.append(list);
	}

}