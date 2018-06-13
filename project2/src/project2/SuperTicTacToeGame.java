package project2;

import java.awt.*;
import java.math.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**********************************************************************
 * This is the class that makes the Graphical User Interface for the 
 * Super tic tac toe game.
 *
 * @author Kelly Hancox and Isfar Baset
 * @version June 13, 2018
 *********************************************************************/
public class SuperTicTacToeGame {

	/** Status of cells down and acrosss board */
	private CellStatus[][] board;

	/** Status of game */
	private GameStatus status;

	/** Size of board */
	private int size;

	/** Consecutive X's or O's */
	private int connections;

	/** O's turn */
	private boolean isOTurn = true;

	/** Keeping count of turn */
	private int count;

	/** Empty spaces on board */
	private ArrayList<Point> emptySpaces;

	/** X's on the board */
	private ArrayList<Point> xSpaces;

	/** O's on the board */
	private ArrayList<Point> oSpaces;

	/** points of X's and O's on the board */
	private ArrayList<Point> pointList;

	/*****************************************************************
	 * Constructor creates a game of tic tac toe between human vs. 
	 * computer
	 * 
	 * @param size the length of each side, the connection of X's 
	 * or O's needed to win and if the human goes first or not
	 *****************************************************************/
	public SuperTicTacToeGame(int size, int connections, 
			boolean humanFirst) {

		if (size < 3 || size > 9 || connections < 2 || 
				connections > size) {
			throw new IllegalArgumentException();
		}

		else {
			this.size = size;

			this.connections = connections - 1;
			board = new CellStatus[size][size];

			emptySpaces = new ArrayList<Point>();
			xSpaces = new ArrayList<Point>();
			oSpaces = new ArrayList<Point>();

			pointList = new ArrayList<Point>();

			if (humanFirst) {
				isOTurn = true;
			}
			if (!humanFirst) {
				isOTurn = false;
			}
			resetGame();
		}

	}

	/*****************************************************************
	 * creates board
	 *****************************************************************/
	public CellStatus[][] getBoard() {
		return board;
	}

	/*****************************************************************
	 * Selects a button based on who's turn it is
	 * 
	 * @param row
	 *            and column on the board
	 *****************************************************************/
	public void select(int row, int col) {
		OTurn();

		// if it was X's turn
		if (!OTurn()) {
			board[row][col] = CellStatus.X;
			isOTurn = true;
		}

		// if it was 0's turn
		else if (OTurn()) {
			board[row][col] = CellStatus.O;
			isOTurn = false;
		}

		pointList.add(new Point(row, col));
	}

	/*****************************************************************
	 * lets user or the computer make a appropiate move
	 *****************************************************************/
	public void move() {

		while (isOTurn == false) {

			int selected[] = findWinningMove();
			if (selected[0] != -1 && selected[1] != -1) {
				select(selected[0], selected[1]);
				selected = null;
				break;
			}

			else {
				int selected2[] = findOpponentWinningMove();
				if (selected2[0] != -1 && selected2[1] != -1) {
					select(selected2[0], selected2[1]);
					break;
				}

				else {
					getEmptySpaces();
					if (emptySpaces.size() > 0) {
						select(emptySpaces.get(0).x, 
								emptySpaces.get(0).y);
						break;
					}
				}
			}
		}
		isOTurn = true;
	}

	/*****************************************************************
	 * Computer tries to find a way to win
	 *****************************************************************/
	public int[] findWinningMove() {
		int row = -1;
		int c = -1;
		int xCount = 0;

		for (int r = 0; r < size; r++) {
			for (int column = 0; column < size * 2; column++) {

				if (board[r][column % size] == CellStatus.X) {
					if (board[r][(column + 1) % size] == 
							CellStatus.X) {
						xCount++;

						if (xCount >= connections - 1) {
							if (board[r][(column + 2) % size] ==
									CellStatus.EMPTY) {
								row = r;
								c = (column + 2) % size;
								break;
							}
						}
					}
				}
			}
			xCount = 0;
		}

		for (int column = 0; column < size; column++) {
			for (int r = 0; r < size * 2; r++) {

				if (board[r % size][column] == CellStatus.X) {
					if (board[(r + 1) % size][column] == 
							CellStatus.X)
						xCount++;

					if (xCount >= connections - 1) {
						if (board[(r + 2) % size][column] == 
								CellStatus.EMPTY) {
							row = (r + 2) % size;
							c = column;
							break;
						}
					}
				}
			}
			xCount = 0;
		}
		return new int[] { row, c };
	}

	/*****************************************************************
	 * Computer checks if human is almost winning
	 *****************************************************************/
	public int[] findOpponentWinningMove() {
		int row = -1;
		int c = -1;
		int oCount = 0;

		for (int r = 0; r < size; r++) {
			for (int column = 0; column < size * 2; column++) {

				if (board[r][column % size] == CellStatus.O) {
					if (board[r][(column + 1) % size] == 
							CellStatus.O) 
					{
						oCount++;

						if (oCount >= connections - 1) {
							if (board[r][(column + 2) % size] == 
									CellStatus.EMPTY) {
								row = r;
								c = (column + 2) % size;
								break;
							}
						}
					}
				}
			}
			oCount = 0;
		}

		for (int column = 0; column < size; column++) {
			for (int r = 0; r < size * 2; r++) {

				if (board[r % size][column] == CellStatus.O) {
					if (board[(r + 1) % size][column] == 
							CellStatus.O) {
						oCount++;

						if (oCount >= connections - 1) {
							if (board[(r + 2) % size][column] == 
									CellStatus.EMPTY) {
								row = (r + 2) % size;
								c = column;
								break;
							}
						}
					}
				}
			}
			oCount = 0;
		}

		return new int[] { row, c };
	}

	/*****************************************************************
	 * Finds empty spaces on the board
	 *****************************************************************/
	public void getEmptySpaces() {
		emptySpaces.clear();

		for (int r = 0; r < size; r++)
			for (int column = 0; column < size; column++)
				if (board[r][column] == CellStatus.EMPTY) {
					emptySpaces.add(new Point(r, column));
				}
	}

	/*****************************************************************
	 * Undoes the last move
	 *****************************************************************/
	public void undo() {

		if (pointList.size() > 0) {

			int row = pointList.get(pointList.size() - 1).x;
			int col = pointList.get(pointList.size() - 1).y;

			int row2 = pointList.get(pointList.size() - 2).x;
			int col2 = pointList.get(pointList.size() - 2).y;

			board[row][col] = CellStatus.EMPTY;
			board[row2][col2] = CellStatus.EMPTY;
			pointList.remove(pointList.size() - 1);
			pointList.remove(pointList.size() - 1);
		}

		else {
			throw new NullPointerException();
		}
	}

	/*****************************************************************
	 * Determines if it's O's turn
	 *****************************************************************/
	public boolean OTurn() {
		return isOTurn;
	}

	/*****************************************************************
	 * Determines if a particular cell on board is empty
	 *****************************************************************/
	public boolean getOK(int r, int c) {
		return board[r][c] == CellStatus.EMPTY;
	}

	/*****************************************************************
	 * Determines the status of the game
	 *****************************************************************/
	public GameStatus getGameStatus() {

		int oVerticalCount = 0;
		int oHorizontalCount = 0;
		int xHorizontalCount = 0;
		int xVerticalCount = 0;
		int totalBoard = size * size;
		int totalCount = 0;

		// Horizontal win
		for (int r = 0; r < size; r++) {
			for (int column = 0; column < size * 2; column++) {

				if (board[r][column % size] == CellStatus.O) {
					if (board[r][(column + 1) % size] == 
							CellStatus.O) {
						oHorizontalCount++;
						if (oHorizontalCount == connections) {
							status = GameStatus.O_WON;
						}
					}
					if (board[r][(column + 1) % size] != 
							CellStatus.O) {
						oHorizontalCount = 0;
					}
				}

				if (board[r][column % size] == CellStatus.X) {
					if (board[r][(column + 1) % size] == 
							CellStatus.X) {
						xHorizontalCount++;
						if (xHorizontalCount == connections) {
							status = GameStatus.X_WON;
						}
					}
					if (board[r][(column + 1) % size] != 
							CellStatus.X) {
						xHorizontalCount = 0;
					}
				}
			}
			oHorizontalCount = 0;
			xHorizontalCount = 0;
		}

		// Vertical win
		for (int column = 0; column < size; column++) {
			for (int r = 0; r < size * 2; r++) {

				if (board[r % size][column] == CellStatus.O) {
					if (board[(r + 1) % size][column] == 
							CellStatus.O) {
						oVerticalCount++;
						if (oVerticalCount == connections) {
							status = GameStatus.O_WON;
						}
					}
					if (board[(r + 1) % size][column] != 
							CellStatus.O) {
						oVerticalCount = 0;
					}
				}

				if (board[r % size][column] == CellStatus.X) {
					if (board[(r + 1) % size][column] == 
							CellStatus.X) {
						xVerticalCount++;
						if (xVerticalCount == connections) {
							status = GameStatus.X_WON;
						}
					}
					if (board[(r + 1) % size][column] != 
							CellStatus.X) {
						xVerticalCount = 0;
					}
				}
			}
			oVerticalCount = 0;
			xVerticalCount = 0;
		}

		// Cats game
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++) {

				if (board[r][c] == CellStatus.O || board[r][c] == 
						CellStatus.X)
					totalCount++;
				if (totalCount == totalBoard) {
					if (status != GameStatus.X_WON && status != 
							GameStatus.O_WON) {
						status = GameStatus.CATS;
					}
				}
			}

		return status;
	}
	
	/*****************************************************************
	 * When the game is reset 
	 *****************************************************************/
	public void resetGame() {
		if (size < 3 || size > 9 || connections < 2 || 
				connections > size) {
			throw new IllegalArgumentException();
		}

		else {

			for (int r = 0; r < size; r++) {
				for (int c = 0; c < size; c++) {
					board[r][c] = CellStatus.EMPTY;
				}
			}

			if (!OTurn()) {
				move();
				isOTurn = true;
			}

			status = GameStatus.IN_PROGRESS;
		}
	}

}
