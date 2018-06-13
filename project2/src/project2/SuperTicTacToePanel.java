package project2;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.util.*;

import project2.SuperTicTacToeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**********************************************************************
 * This is the class that makes the Graphical User Interface for the 
 * Super tic tac toe game.
 *
 * @author Kelly Hancox and Isfar Baset
 * @version June 13, 2018
 **********************************************************************/

public class SuperTicTacToePanel extends JPanel {

	/** double array for the buttons on the board */
	private JButton[][] board;

	/** double array for the status of the cell on the board */
	private CellStatus[][] iBoard;

	/** creates the center panel */
	private JPanel center;

	/** creates the right panel */
	private JPanel right;

	/** creates the quit button */
	private JButton quitButton;

	/** creates the undo button */
	private JButton undoButton;

	/** creates the reset button */
	private JButton resetButton;

	/** holds the tic tac toe game */
	private SuperTicTacToeGame game;

	/** holds the X image */
	private ImageIcon xIcon;

	/** holds the O image */
	private ImageIcon oIcon;

	/** holds the empty image */
	private ImageIcon emptyIcon;

	/** creates the size of the game board */
	protected int boardSize;

	/** holds the input number of the user */
	protected int inputSize;

	/** holds the input of the user as to who goes first */
	public int first;

	/** if human goes first or not */
	public boolean humanFirst;

	/** number of consecutive X's or O's */
	private int connections;
	
	/** initial pixel size for buttons*/
	private int buttonSize;

	/******************************************************************
	 * Constructor calls on resetMethod(); which holds all the basic 
	 * functionalities of the tic tac toe game
	 ******************************************************************/
	SuperTicTacToePanel() {
		resetMethod();
	}

	/******************************************************************
	 * This methods holds all the functionality for the game of 
	 * tic tac toe game,takes inputs from the user and makes the 
	 * board appear.
	 ******************************************************************/
	public void resetMethod() {
		inputSizeMsgDialogue();
		inputConnectionsMsgDialogue();
		inputWhoGoesFirst();

		boardSize = inputSize;

		try {
			game = new SuperTicTacToeGame(boardSize, connections,
humanFirst);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Size must be between" + " 3 and 9."
							+ "\nConnections may not be smaller than 3"
							+ "\nor larger than the board size. "
							+ "\n" + "This game will close."
									+ "\nPlease try again.");
			System.exit(0);
		}

		// create game listeners
		ButtonListener listener = new ButtonListener();

		// center = new JPanel();
		right = new JPanel();

		// instantiating quit button
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);

		// instantiating the undo button
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);

		// instantiating the reset button
		resetButton = new JButton("Reset");
		resetButton.addActionListener(listener);

		center = new JPanel();

		// instantiating and resizing images
		buttonSize = 645;
		int finalButtonSize = buttonSize / boardSize;
		emptyIcon = new ImageIcon(new ImageIcon
				("ticTacToeBlank.jpeg").getImage().
				getScaledInstance(finalButtonSize,
				finalButtonSize, Image.SCALE_DEFAULT));
		xIcon = new ImageIcon(new ImageIcon("ticTacToeX.jpeg").
				getImage().
				getScaledInstance(finalButtonSize, finalButtonSize,
				Image.SCALE_DEFAULT));
		oIcon = new ImageIcon(new ImageIcon("ticTacToeO.jpeg").
				getImage().
				getScaledInstance(finalButtonSize, finalButtonSize,
				Image.SCALE_DEFAULT));

		center.setLayout(new GridLayout(boardSize, boardSize));

		// represents board
		board = new JButton[boardSize][boardSize];

		// create listeners for every JButton
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new JButton(emptyIcon);
				board[row][col].addActionListener(listener);
				center.add(board[row][col]);
			}
		}
		// adding the quit, reset and undo buttons
		right.add(quitButton);
		right.add(undoButton);
		right.add(resetButton);

		displayBoard();

		setLayout(new BorderLayout());

		removeAll();
		add(right, BorderLayout.SOUTH);
		add(center, BorderLayout.CENTER);

		revalidate();
		repaint();

		setSize(new Dimension(80 * boardSize, 80 * boardSize));

	}

	/******************************************************************
	 * This methods shows the dialogue box which lets the user input 
	 * how big they want size of the board to be
	 ******************************************************************/
	public void inputSizeMsgDialogue() {
		try {
			inputSize = Integer.parseInt(JOptionPane.
					showInputDialog(null,
					"How big would you " + 
					"like this board?\n" + 
							"Choose a number between 3-9."));
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(null, 
					"Invalid input.\n" + "Please try again.");
			System.exit(0);
		}
	}

	/******************************************************************
	 * This methods shows the dialogue box which lets the user input 
	 * how many connections they should have to win
	 ******************************************************************/
	public void inputConnectionsMsgDialogue() {
		connections = Integer
				.parseInt(JOptionPane.showInputDialog(null, 
						"Enter number of Xs " + "or Os needed "
								+ "in a row to win. \n"
						+ "Number must be at least 3 and less "
						+ "than \n" + "the board size"));
	}

	/******************************************************************
	 * This methods shows the dialogue box which lets the user 
	 * decide who goes first
	 ******************************************************************/
	public void inputWhoGoesFirst() {
		first = JOptionPane.showConfirmDialog(null, 
				"Would you like " + "to go first?", "Yes or No",
				JOptionPane.YES_NO_OPTION);
		if (first == 0) {
			humanFirst = true;
		} else
			humanFirst = false;
	}

	/******************************************************************
	 * This method displays the images of X or O based on what the 
	 * status of that particular cell is
	 ******************************************************************/
	public void displayBoard() {
		iBoard = game.getBoard();

		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				// show O image
				if (iBoard[r][c] == CellStatus.O) {
					board[r][c].setIcon(oIcon);
				}
				// show X image
				else if (iBoard[r][c] == CellStatus.X) {
					board[r][c].setIcon(xIcon);
				}
				// show blank image
				else if (iBoard[r][c] == CellStatus.EMPTY) {
					board[r][c].setIcon(emptyIcon);
				}
			}
		}
	}

	/******************************************************************
	 * Button Listener class that implements ActionListener class to 
	 * get a response from the clicked buttons and actionPerformed 
	 * method determines which action to do based on which button was 
	 * clicked
	*******************************************************************/
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// reset button clicked
			if (resetButton == e.getSource()) {
				game.resetGame();
				resetMethod();
			}
			// undo button clicked
			if (undoButton == e.getSource()) {
				try {
					game.undo();
				} catch (NullPointerException Exception) {
					JOptionPane.showMessageDialog(null, 
							"You can only undo " + 
					"moves that have been played");
				}
			}
			// game in progress
			if (game.getGameStatus() == GameStatus.IN_PROGRESS) {
				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < boardSize; c++) {
						if (board[r][c] == e.getSource() && 
								game.getOK(r, c)) {
							game.select(r, c);
							game.getGameStatus();
						} else if (game.getGameStatus() == 
								GameStatus.IN_PROGRESS) {
							game.move();
							game.getGameStatus();
						}
						displayBoard();
					}
				}
			}

			// O Wins
			if (game.getGameStatus() == GameStatus.O_WON) {
				int result = JOptionPane.showConfirmDialog(null, 
						"O " + 
				"won and X lost!\nDo you want to continue?",
						null, JOptionPane.YES_NO_OPTION);
				if (JOptionPane.YES_OPTION == result) {
					game.resetGame();
				}
				if (JOptionPane.NO_OPTION == result) {
					System.exit(0);
				}
				displayBoard();
			}

			// X Wins
			if (game.getGameStatus() == GameStatus.X_WON) {
				int result = JOptionPane.showConfirmDialog(null, 
						"X " + 
				"won and O lost!\nDo you want to continue?",
						null, JOptionPane.YES_NO_OPTION);
				if (JOptionPane.YES_OPTION == result) {
					game.resetGame();
				}
				if (JOptionPane.NO_OPTION == result) {
					System.exit(0);
				}
				displayBoard();
			}

			// Cats Game
			if (game.getGameStatus() == GameStatus.CATS) {
				int result = JOptionPane.showConfirmDialog(null, 
						"It's " 
				+ "a Tie!\nDo you want to continue?", null,
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.YES_OPTION == result) {
					game.resetGame();
				}
				if (JOptionPane.NO_OPTION == result) {
					System.exit(0);
				}
				displayBoard();
			}
			// quit game
			if (quitButton == e.getSource()) {
				System.exit(0);
			}

		}
	}
}
