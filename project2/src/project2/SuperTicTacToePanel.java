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

public class SuperTicTacToePanel extends JPanel {

	private JButton[][] board;
	// private CellStatus iCell;
	private CellStatus[][] iBoard;

	private JLabel xWon;
	private JLabel oWon;

	private JButton quitButton;
	private JButton undoButton;

	private SuperTicTacToeGame game;

	private ImageIcon xIcon;
	private ImageIcon oIcon;
	private ImageIcon emptyIcon;

	protected int boardSize;
	protected int inputSize;
	public int first;
	
	public boolean humanFirst;
	
	private int connections;

	SuperTicTacToePanel() {

		inputSizeMsgDialogue();
		inputConnectionsMsgDialogue();
		inputWhoGoesFirst();
		
		boardSize = inputSize;

		game = new SuperTicTacToeGame(boardSize, connections, humanFirst);

		// gameItem = pgameItem;
		// quitItem = pquitItem;

		

		// create game listeners
		ButtonListener listener = new ButtonListener();

		JPanel center = new JPanel();
		JPanel right = new JPanel();

		// instantiating quit button
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);

		// instantiating the undo button
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);

		// instantiating images
		emptyIcon = new ImageIcon("src/project2/ticTacToeBlank.jpeg");
		xIcon = new ImageIcon("src/project2/ticTacToeX.jpeg");
		oIcon = new ImageIcon("src/project2/ticTacToeO.jpeg");

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
		right.add(quitButton);
		right.add(undoButton);

		displayBoard();

		add(new JLabel("Super TicTacToe"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(right, BorderLayout.EAST);

	}

	public void inputSizeMsgDialogue() {
		inputSize = Integer.parseInt(
				JOptionPane.showInputDialog(null,"How big would you like this board?\n Choose a number between 3-9"));
	}
	
	public void inputConnectionsMsgDialogue() {
		connections = Integer.parseInt(
				JOptionPane.showInputDialog(null,"Enter number of Xs or Os in a row needed to win"));
	}
	
	public void inputWhoGoesFirst() {
		first = JOptionPane.showConfirmDialog(null, "Would you like to go first?", "Yes or No", JOptionPane.YES_NO_OPTION);
	if (first == 0) {
		humanFirst = true;
		}
	else 
		humanFirst = false;
	}
	
	// resizing the images
	// private BufferedImage fitImage(Image img, int w, int h) {
	// int width = img.getWidth(this);
	// int height = img.getHeight(this);
	// BufferedImage resizedimage;
	//
	// if (width > w && height > h) {
	// resizedimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	// Graphics2D g2 = resizedimage.createGraphics();
	// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	// g2.drawImage(img, 0, 0, w, h, null);
	// g2.dispose();
	// } else {
	// resizedimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	// Graphics2D g2 = resizedimage.createGraphics();
	// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	// g2.drawImage(img, 0, 0, width, height, null);
	// g2.dispose();
	// }
	// return resizedimage;
	// }

	public void displayBoard() {
		// System.out.println(GameStatus.O_WON);
		iBoard = game.getBoard();

		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {

				/*
				 * TODO 2: (continued) Change these lines of code to set the Icon for each
				 * JButton
				 */
				// board[r][c].setIcon(emptyIcon);

				if (iBoard[r][c] == CellStatus.O) {
					board[r][c].setIcon(oIcon);
				}

				else if (iBoard[r][c] == CellStatus.X) {
					board[r][c].setIcon(xIcon);
				}

				else if (iBoard[r][c] == CellStatus.EMPTY) {
					board[r][c].setIcon(emptyIcon);
				}
			}
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (quitButton == e.getSource()) {
				System.exit(0);
			}

			if (game.getGameStatus() == GameStatus.IN_PROGRESS) {
				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < boardSize; c++) {
						if (board[r][c] == e.getSource() && game.getOK(r, c)) {
							game.select(r, c);
							game.getGameStatus();
						}
						else if (game.getGameStatus() == GameStatus.IN_PROGRESS){
						game.move();
						game.getGameStatus();
						}
						displayBoard();
					}
				}
			}

			// O Wins
			if (game.getGameStatus() == GameStatus.O_WON) {
				// JOptionPane.showMessageDialog(null, "O won and X lost!\n The game will
				// reset");
				int result = JOptionPane.showConfirmDialog(null, "O won and X lost!\nDo you want to continue?", null,
						JOptionPane.YES_NO_OPTION);
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
				int result = JOptionPane.showConfirmDialog(null, "X won and O lost!\nDo you want to continue?", null,
						JOptionPane.YES_NO_OPTION);
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
				int result = JOptionPane.showConfirmDialog(null, "It's a Tie!\nDo you want to continue?", null,
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.YES_OPTION == result) {
					game.resetGame();
				}
				if (JOptionPane.NO_OPTION == result) {
					System.exit(0);
				}
				displayBoard();
			}
		}
	}
}
