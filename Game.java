import java.awt.*;
import java.awt.event.*;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;
import java.util.Timer; 
import java.util.TimerTask; 

public class Game extends JFrame implements ActionListener {
	private JPanel topPanel, selectPanel, gamePanel;
	private JLabel topLabel, infoLabel, bottomLabel;
	private JButton buttonS, buttonM, buttonL, reset;
	public JFrame frame;
	private Matrix temp;
	private Matrix [][] chocolate;
	private int selectedrow;
	private int selectedcol;
	private int rowMax;
	private int colMax;
	private String firstMover;
	private int count;
	private String lastPlayer;
	
	private Timer clock;
	private int clockCount;
	private int remainingSquare;
	public Game() {
		//Frame
		frame = new JFrame("Chomp!");
		frame.setSize(650, 400);
		frame.setLayout(new BorderLayout());
		//Panel
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());	
		selectPanel = new JPanel();
		selectPanel.setLayout(new FlowLayout());
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout());
		//Label
		topLabel = new JLabel("Chomp!");
		bottomLabel = new JLabel("Let's play! Please select the size of the chocolate");
		//button
		buttonS= new JButton("Small");
		buttonM= new JButton("Medium");
		buttonL= new JButton("Large");	
		//font
		Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		Font textFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);	
		topLabel.setFont(titleFont);
		bottomLabel.setFont(textFont);
		
		infoLabel = new JLabel(" ");
		//Font textFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);	
		infoLabel.setFont(textFont);	
		
		//Configuration
		topPanel.add(topLabel);
		topPanel.add(infoLabel);
		selectPanel.add(bottomLabel);
		selectPanel.add(buttonS);
		selectPanel.add(buttonM);
		selectPanel.add(buttonL);
		
	    Container c= frame.getContentPane();
		c.add(topPanel, BorderLayout.NORTH);
		c.add(selectPanel, BorderLayout.SOUTH);
		c.add(gamePanel, BorderLayout.CENTER);	
		
		buttonS.addActionListener(this);
		buttonM.addActionListener(this);
		buttonL.addActionListener(this);
		pack();
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);		
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	public void playGround(int x, int y) {
	
		chocolate = new Matrix[x][y];
		gamePanel.setLayout(new GridLayout(x,y,1,1));
		for ( int row = 0; row < x; row ++)
		{
			for (int column= 0; column < y; column ++)
			{
				if ((row == 0)&&(column == 0)) 
				{
					Icon cross = new ImageIcon("resources/cross.jpg");
					temp = new Matrix(cross, row, column);	
				}
				else 
				{
					temp = new Matrix(row,column);
				}
				chocolate[row][column] = temp;
				temp.setSize(6,10);
				temp.setBackground();
				temp.setOpaque(true);				
				temp.setBorderPainted(false);											
				temp.addActionListener(this);					
				gamePanel.add(temp);
				}
			}
		pack();
		frame.setVisible(true);
		
	}
	
	public String firstMove() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
		if (randomNum == 0) {
			firstMover = "player";
		}
		else {
			firstMover = "computer";
			}
		
		return firstMover;

		}
	
	public void computerMove(int rowMax, int colMax) {
		Timer timer = new Timer();
		infoLabel.setText("Hmmmm...let me think...");
		pack();
		frame.setVisible(true);
		timer.schedule(new TimerTask(){
			public void run() {
				androidMove(rowMax, colMax);
			}
		},1500);
		}
	
	public void androidMove(int rowMax, int colMax) {
		lastPlayer = "Player";
		result();
		if (count == 1)
			return;

		for (int row = 0; row < rowMax; row ++) 
		{
			for(int column = 0; column < colMax; column ++) 
			{	
				if((row == 0) &&(column == 0)) {
					continue;
				}
				if(chocolate[row][column].getBackground()!= Color.white)
				{
					selectedrow = row;
					selectedcol = column;
					break;		
				}
			}
		}

	

		for (int row = selectedrow; row < rowMax; row ++) 
		{
			for(int column = selectedcol; column < colMax; column ++) 
			{		
				chocolate[row][column].changeColor();
			}
			}
		squareLeft();
		infoLabel.setText("Remaining Squares: "+remainingSquare+"."+" "+"It's your turn!");
		lastPlayer = "Computer";
		result();
		pack();
		frame.setVisible(true);

		}
	
	public int result() {
		count = 0;
		for (int row = 0; row < rowMax; row ++) {
			for(int column = 0; column < colMax; column ++) {
				if(chocolate[row][column].getState() == 1) 
				{
					count += 1;
				}
			}	
		}
		if (count == 1) {
			if(lastPlayer == "Computer") {
				infoLabel.setText("You lose! Try again? Select the size of the chocolate");
				frame.setVisible(true);
				
			}
			if(lastPlayer == "Player")
			{
				infoLabel.setText("You win! Try again?Select the size of the chocolate");
				frame.setVisible(true);
				
			}
		}
		pack();
		frame.setVisible(true);
		return count;
	}
	public void squareLeft() {
		remainingSquare = 0;
		for (int i = 0; i < rowMax; i ++) {
			for(int n = 0; n < colMax; n ++) {
				if ((chocolate[i][n].getState() == 1)) {
					remainingSquare += 1;
				}
			}

		}
	}
	public void actionPerformed(ActionEvent e) {
		Object selected = e.getSource();
		//chocolate size:small
		if (selected.equals(buttonS)) 
		{
			gamePanel.removeAll();
			rowMax = 3;
			colMax = 6;
			playGround(3,6);
			remainingSquare = 18;
			if(firstMove()=="computer")
			{	
				computerMove(rowMax, colMax);
			}
		
		}
		//chocolate size:medium
		if (selected.equals(buttonM)) {
			gamePanel.removeAll();
			rowMax = 4;
			colMax = 7;
			playGround(4,7);
			remainingSquare = 28;
			if(firstMove()=="computer")
			{	
				computerMove(rowMax, colMax);
			}
		}
		//chocolate size:large
		if (selected.equals(buttonL)) {
			gamePanel.removeAll();
			rowMax = 6;
			colMax = 10;
			playGround(6,10);	
			remainingSquare = 60;
			if(firstMove()=="computer")
			{	
				computerMove(rowMax, colMax);
			}
		}
		//click on the soup
		if (selected.equals(chocolate[0][0])) {
			
		}
		if (((Matrix)e.getSource()).getState() == 0) {
			
		}
		//click on the chocolate other than the soup
		if ((((Matrix)e.getSource()).getState() != 0)&&(selected instanceof Matrix) && ((selected.equals(chocolate[0][0]) == false))) 
		{
			squareLeft();
			lastPlayer = "Computer";
			result();
			if (count == 1)
				return;
			infoLabel.setText("Remaining Squares: "+remainingSquare+"."+" "+"It's your turn!");
		
			selectedrow = ((Matrix)e.getSource()).getRow();
			selectedcol = ((Matrix)e.getSource()).getCol();
			for (int row = selectedrow; row < rowMax; row ++) {
				for(int column = selectedcol; column < colMax; column ++) {

					chocolate[row][column].changeColor();
					
				}
				
			}
			pack();
			frame.setVisible(true);
			
			computerMove(rowMax, colMax);
			lastPlayer = "Player";
			result();
		}

	}
	
	public static void main(String[] args) 
	{
		new Game();
	}
}
