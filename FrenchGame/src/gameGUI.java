import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

// GUI Class
@SuppressWarnings("serial")
public class gameGUI extends JFrame implements ActionListener{

	// Fields/Variables
	private JPanel menuPanel = new JPanel();
	private JButton[] button = new JButton[3];

	private JPanel infoPanel = new JPanel();
	private JButton nextButton = new JButton("Next");

	private JPanel answerPanel = new JPanel();
	private JButton nextQ = new JButton("Continue");

	private JPanel highscorePanel = new JPanel();
	private JButton backButton = new JButton("Back");	
	private JTextPane[] highscoreList = new JTextPane[3];

	private JPanel difficultyPanel = new JPanel();
	private JButton[] chooseDiff = new JButton[3];

	private JLabel titleLabel = new JLabel();
	private Font font = new Font ("Arial",Font.BOLD,72);

	private JTextArea instructions = new JTextArea();

	private static int difficulty;
	static int questionsAnswered = 0;

	static JButton mainmenu = new JButton("Back to main menu");
	gamePanel gamePanel;

	// Login Buttons
	private JPanel loginPanel = new JPanel();
	private JButton loginButton = new JButton("Login");
	private JButton logoutButton = new JButton("Logout");

	// Objects for the login screen (Ricky)
	private JTextPane username = new JTextPane();
	private JTextPane password = new JTextPane();
	private Font font2 = new Font ("Arial",Font.BOLD,25);
	private JLabel user = new JLabel("Username");
	private JLabel pass = new JLabel("Password");
	private JLabel login = new JLabel("Login");
	private JLabel account = new JLabel("If a non-existing username is entered, a new account will be made with the entered password");
	
	private static String userLogin;
	private static String userPassword;
	private boolean newUser = true;
	private int userNumber =0;
	boolean correct = false;

	// Constructor
	public gameGUI(int x, int y){

		panelCreator();
		setupButtons();
		setupLabels();
		setupImages();
		setupInfoStuff();
		loginPanel();

		this.setTitle("Superhero Scramble");
		this.setVisible(true);
		this.setBounds(0,0,x,y);
		this.setResizable(false);
		this.setContentPane(loginPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Spreadsheets.read("A:C",0);
	}

	// Login Panel (Ricky/Jason)
	private void loginPanel(){

		username.setFont(font2);
		username.setVisible(true);
		username.setBounds(170,170,350,40);
		loginPanel.add(username);

		password.setFont(font2);
		password.setVisible(true);
		password.setBounds(170,280,350,40);
		loginPanel.add(password);

		user.setBounds(20,170,350,40);
		user.setFont(font2);
		user.setVisible(true);
		loginPanel.add(user);

		pass.setBounds(20,280,350,40);
		pass.setFont(font2);
		pass.setVisible(true);
		loginPanel.add(pass);	
		
		login.setBounds(20,15,350,80);
		login.setFont(font);
		login.setVisible(true);
		loginPanel.add(login);
		
		account.setBounds(20,305,600,80);
		account.setVisible(true);
		loginPanel.add(account);
	}

	// Setup High Scores (Ricky/Jason)
	private void setupHighscore(){

		Font titlefont = new Font ("Arial",Font.BOLD,72);
		JLabel highTitle = new JLabel("Highscores");
		highTitle.setVisible(true);
		highTitle.setFont(titlefont);
		highTitle.setBackground(Color.BLACK);
		highTitle.setForeground(Color.YELLOW);
		highTitle.setBounds(100,20,500,100);
		highscorePanel.add(highTitle);

		Font highfont = new Font ("Arial",Font.BOLD,16);

		for (int x = 0; x < highscoreList.length; x++){
			highscoreList[x] = new JTextPane();
			highscoreList[x].setFont(highfont);
			highscoreList[x].setBackground(Color.BLACK);
			highscoreList[x].setForeground(Color.YELLOW);
			highscoreList[x].setVisible(true);
			highscoreList[x].setEditable(false);
			highscorePanel.add(highscoreList[x]);
		}
		
		highscoreList[0].setBounds(180,140,30,370);
		highscoreList[1].setBounds(210,140,150,370);
		highscoreList[2].setBounds(360,140,60,370);
		
		// Get the top 10 highscores
		int max = 0;
		int min = 0;
		int count = 0;
		ArrayList<Integer> topten = new ArrayList<Integer>();
		
		for(int x =0; x < Spreadsheets.getSize(); x++){
			topten.add(Integer.parseInt((String)Spreadsheets.read("A:C",x).get(2)));
		}
		
		Collections.sort(topten);
		
		if(topten.size() > 10){
			max = topten.size();
			min = topten.size() - 10;
		}else{
			max = topten.size();
			min = 0;
		}
		
		// Find out who owns the 10 highest scores, and put them on the labels
		for(int x = max - 1; x >= min; x--){
			for(int y =0; y < Spreadsheets.getSize(); y++){
				if(Integer.parseInt((String)Spreadsheets.read("A:C",y).get(2)) == (topten.get(x))){
					highscoreList[1].setText( highscoreList[1].getText() + Spreadsheets.read("A:C",y).get(0) + "\n\n");
					break;
				}
			}
			// Put the rank and score on the labels
			highscoreList[0].setText( highscoreList[0].getText() + (count+1) + ". \n\n");
			highscoreList[2].setText( highscoreList[2].getText() + topten.get(x) + "\n\n");
			count++;
		}
	}

	// Setup Images 
	private void setupImages(){

		JLabel thor = new JLabel();
		thor.setVisible(true);
		thor.setBounds(-20,25,300,150);
		thor.setIcon(new ImageIcon(this.getClass().getResource("thor.gif")));
		difficultyPanel.add(thor);	

		JLabel heroes = new JLabel();
		heroes.setBounds(0,0,600,600);
		heroes.setVisible(true);
		heroes.setIcon(new ImageIcon(this.getClass().getResource("heroes.jpg")));
		menuPanel.add(heroes);	
		
		JLabel flashLogo = new JLabel();
		flashLogo.setVisible(true);
		flashLogo.setBounds(450, 250, 100, 100);
		flashLogo.setIcon(new ImageIcon(this.getClass().getResource("thumbs_flash-logo.gif")));
		infoPanel.add(flashLogo);
		
		JLabel infoLabel = new JLabel();
		infoLabel.setBounds(50, 250, 300, 288);
		infoLabel.setVisible(true);
		infoLabel.setIcon(new ImageIcon(this.getClass().getResource("GamePanelScreenshot.jpg")));
		infoPanel.add(infoLabel);
	}

	// Setup stuff for Info Panel 
	public void setupInfoStuff(){
		infoPanel.setBackground(Color.orange);
		instructions.setBounds(25, 25, 500, 500);
		instructions.setEditable(false);
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setFont(new Font("Calibri", Font.BOLD, 12));
		instructions.setForeground(Color.black);
		instructions.setBackground(Color.orange);
		instructions.setText("INSTRUCTIONS:\n"
				+ "1) After clicking NEXT (only after reading all this), select a difficulty.\n"
				+ "2) Create a sentence using the Word Bank provided (You will be timed for this!).\n"
				+ "3) When you feel your sentence is complete, use the 'Check Answer' button.\n"
				+ "4) The 'Check Answer' will check your sentence structure and grammar.\n"
				+ "Good luck!\n\n"
				+ "*(Below you see a sample image of what the quiz looks like)\n"
				+ "**(We also got you covered for writing accented letters ;) )\n"
				+ "***(NOTE: If you have not touched the High Score button at this point, there is a minor cooldown of ~5 seconds you should wait before accessing it.)");
		infoPanel.add(instructions);
	}

	// Setup Labels

	public void setupLabels(){

		titleLabel.setVisible(true);
		titleLabel.setBounds(50,120,500,150);
		titleLabel.setForeground(Color.YELLOW);
		titleLabel.setFont(font);
		titleLabel.setText("<html><div style='text-align:center;'>SUPERHERO SCRAMBLE</div><html>");
		menuPanel.add(titleLabel);
	}

	// Panel Creator
	public void panelCreator() {

		menuPanel.setLayout(null);
		menuPanel.setVisible(true);

		difficultyPanel.setLayout(null);
		difficultyPanel.setVisible(true);
		difficultyPanel.setBackground(Color.BLACK);

		highscorePanel.setLayout(null);
		highscorePanel.setVisible(true);
		highscorePanel.setBackground(Color.BLACK);

		infoPanel.setLayout(null);
		infoPanel.setVisible(true);
		infoPanel.setBackground(Color.yellow);

		answerPanel.setLayout(null);
		answerPanel.setVisible(true);

		loginPanel.setLayout(null);
		loginPanel.setVisible(true);


	}

	// Setup buttons 
	public void setupButtons(){

		for(int x = 0 ; x <chooseDiff.length; x++){
			chooseDiff[x] = new JButton();
			chooseDiff[x].setVisible(true);
			chooseDiff[x].addActionListener(this);
			chooseDiff[x].setBounds(200 * x,200,200,200);
			chooseDiff[x].setFont(new Font ("Arial",Font.BOLD,43));
			difficultyPanel.add(chooseDiff[x]);

			button[x] = new JButton();
			button[x].setBackground(Color.lightGray);
			button[x].setVisible(true);
			button[x].addActionListener(this);
			menuPanel.add(button[x]);

			if (x==0){
				button[x].setBounds(225,350,150,50);
			} else {
				button[x].setBounds(145*x + 5,400,150,50);
			}

		}

		chooseDiff[0].setText("Easy");
		chooseDiff[1].setText("Medium");
		chooseDiff[2].setText("Hard");

		chooseDiff[0].setBackground(Color.GREEN);
		chooseDiff[1].setBackground(Color.YELLOW);
		chooseDiff[2].setBackground(Color.RED);

		nextButton.setVisible(true);
		nextButton.addActionListener(this);
		nextButton.setBounds(475,450,100,100);
		infoPanel.add(nextButton);

		button[0].setText("Start");
		button[1].setText("High scores");
		button[2].setText("Exit");

		backButton.setVisible(true);
		backButton.addActionListener(this);
		backButton.setBounds(475,450,100,100);
		highscorePanel.add(backButton);

		nextQ.setVisible(true);
		nextQ.addActionListener(this);
		answerPanel.add(nextQ);

		mainmenu.setVisible(false);
		mainmenu.addActionListener(this);
		mainmenu.setBounds(430,325,150,50);

		loginButton.setVisible(true);
		loginButton.addActionListener(this);
		loginButton.setBounds(400,400,125,50);
		loginPanel.add(loginButton);

		logoutButton.setVisible(true);
		logoutButton.addActionListener(this);
		logoutButton.setBounds(0,525,100,50);
		menuPanel.add(logoutButton);
	}

	// Action Performed 
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button[0]){
			setContentPane(infoPanel);
			revalidate();
		}
		else if (e.getSource() == button[1]){
			setupHighscore();
			setContentPane(highscorePanel);
			revalidate();
		}
		else if (e.getSource() == button[2]){
			System.exit(0);
		}
		else if (e.getSource() == backButton){
			setContentPane(menuPanel);
			revalidate();
		}
		else if (e.getSource() == chooseDiff[0]){
			setDifficulty(1);
			gamePanel = new gamePanel(getDifficulty());
			gamePanel.add(mainmenu);
			setContentPane(gamePanel);
			answerPanel.setBackground(Color.GREEN);
			revalidate();
		}
		else if (e.getSource() == chooseDiff[1]){
			setDifficulty(2);
			gamePanel = new gamePanel(getDifficulty());
			gamePanel.add(mainmenu);
			setContentPane(gamePanel);
			answerPanel.setBackground(Color.YELLOW);
			revalidate();
		}
		else if (e.getSource() == chooseDiff[2]){
			setDifficulty(3);
			gamePanel = new gamePanel(getDifficulty());
			gamePanel.add(mainmenu);
			setContentPane(gamePanel);
			answerPanel.setBackground(Color.RED);
			revalidate();
		}else if (e.getSource() == nextButton){
			setContentPane(difficultyPanel);
			revalidate();
		}else if (e.getSource() == mainmenu){
			mainmenu.setVisible(false);
			setContentPane(menuPanel);
			revalidate();
		}else if (e.getSource() == loginButton){

			// Get the user info
			userLogin = username.getText();
			userPassword = password.getText();

			// Check if the username is registered
			for(int x =0; x < Spreadsheets.getSize(); x++){
				if(Spreadsheets.read("A:C",x).get(0).equals(userLogin)){
					userNumber = x;
					newUser = false;
					break;
				}
			}

			// If it is, check the password entered matches
			if(newUser == false){
				if(Spreadsheets.read("A:C",userNumber).get(1).equals(userPassword)){
					correct = true;
				}
			}

			// If the username exists and password is incorrect, give the user a message, else go to the game
			if(newUser == false && correct == false){
				account.setText("Username already exists, incorrect password");
			}else{
				setContentPane(menuPanel);
				revalidate();
			}

		}else if (e.getSource() == logoutButton){
			setContentPane(loginPanel);
			revalidate();
		}
	}

	// Get/Set Difficulty and Login stuff
	public static void setDifficulty(int difficulty){
		gameGUI.difficulty = difficulty;
	}
	public static int getDifficulty(){
		return difficulty;
	}
	public static String getUsername(){
		return userLogin;
	}
	public static String getPassword(){
		return userPassword;
	}
}