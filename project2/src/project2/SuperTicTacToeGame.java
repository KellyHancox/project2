package project2;

import java.awt.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SuperTicTacToeGame {
	private CellStatus[][] board;
	private GameStatus status;
	private int size;
	private CellStatus turn;
	private int connections; // used in to do 4.
	private boolean inProgress = false;

	private boolean isOTurn = true;
	private int count;

	public SuperTicTacToeGame(int size, int connections, boolean humanFirst) {

		status = GameStatus.IN_PROGRESS;

		this.size = size;
		this.connections = connections;
		board = new CellStatus[size][size];

		if (humanFirst) {
			isOTurn = true;
			count = 0;
		}
		if (!humanFirst) {
			isOTurn = false;
			count = 1;
		}

		resetGame();
		// turn = CellStatus.X;
		//gameMethod();

	}

	public CellStatus[][] getBoard() {
		return board;
	}

	public void select(int row, int col) {
		// if it was X's turn
		if (!OTurn()) {
			board[row][col] = CellStatus.X;
			isOTurn = true;

			getGameStatus();
		}
		
		// if it was 0's turn
		else if (OTurn()) {
			board[row][col] = CellStatus.O;
			isOTurn = false;

			getGameStatus();
					//count++;
		}
	}
	
	public void gameMethod() {
		while(isOTurn == false) {
			move();
		}
	}
	
	public void move() {
			//for (int r = 0; r < size; r++) {
				//for (int c = 0; c < size; c++) {
		while(!OTurn()) {
					if(board[0][0] == CellStatus.EMPTY) {
							//board[r][c] = CellStatus.X;
							//if(count%2 == 0)
							select(0,0);
							//isOTurn = true;
							//count++;
							//getGameStatus();
						}
							else if (board[0][1] == CellStatus.EMPTY) {
								select(0,1);
							}
							else if (board[0][2] == CellStatus.EMPTY) {
								select(0,2);
							}
							else if (board[1][0] == CellStatus.EMPTY) {
								select(1,0);
							}
							else if (board[1][1] == CellStatus.EMPTY) {
								select(1,1);
							}
							else if (board[1][2] == CellStatus.EMPTY) {
								select(1,2);
							}
							else if (board[2][0] == CellStatus.EMPTY) {
								select(2,0);
							}
							else if (board[2][1] == CellStatus.EMPTY) {
								select(2,1);
							}
							else if (board[2][2] == CellStatus.EMPTY) {
								select(2,2);
							}
		}
		}
	
//
//	public void trackMove() {
//		int count = 0;
//		move();
//		count++;
//	}

	public boolean OTurn() {
		return isOTurn;
	}
	
	/*
	 * TO DO 5, if you have the time...
	 *
	 * Make an Undo feature.
	 */

	public boolean undo() {
		return false;
	}
	
	public void resetGame() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				board[r][c] = CellStatus.EMPTY;
			}
		}
		status = GameStatus.IN_PROGRESS;
	}

	public boolean getOK(int r, int c) {
		return board[r][c] == CellStatus.EMPTY;
	}

	public GameStatus getGameStatus() {

		// int oCount = 0;
		// int xCount = 0;
		// int winningScore = connections;
		int totalBoard = size * size;
		int totalCount = 0;

		switch (connections) {
		case 3: // if you want 3 in a row
			// Horizontal win
			for (int r = 0; r < size; r++) {
				for (int column = 0; column < size; column++) {
					if (board[r][column] == CellStatus.O && board[r][(column + 1) % size] == CellStatus.O
							&& board[r][(column + 2) % size] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[r][(column + 1) % size] == CellStatus.X
							&& board[r][(column + 2) % size] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

			// Vertical win
			for (int column = 0; column < size; column++) {
				for (int r = 0; r < size; r++) {
					if (board[r][column] == CellStatus.O && board[(r + 1) % size][column] == CellStatus.O
							&& board[(r + 2) % size][column] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[(r + 1) % size][column] == CellStatus.X
							&& board[(r + 2) % size][column] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

		case 4:
			for (int r = 0; r < size; r++) {
				for (int column = 0; column < size; column++) {
					if (board[r][column] == CellStatus.O && board[r][(column + 1) % size] == CellStatus.O
							&& board[r][(column + 2) % size] == CellStatus.O
							&& board[r][(column + 3) % size] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[r][(column + 1) % size] == CellStatus.X
							&& board[r][(column + 2) % size] == CellStatus.X
							&& board[r][(column + 3) % size] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

			// Vertical win
			for (int column = 0; column < size; column++) {
				for (int r = 0; r < size; r++) {
					if (board[r][column] == CellStatus.O && board[(r + 1) % size][column] == CellStatus.O
							&& board[(r + 2) % size][column] == CellStatus.O
							&& board[(r + 3) % size][column] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[(r + 1) % size][column] == CellStatus.X
							&& board[(r + 2) % size][column] == CellStatus.X
							&& board[(r + 3) % size][column] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

		case 5:
			for (int r = 0; r < size; r++) {
				for (int column = 0; column < size; column++) {
					if (board[r][column] == CellStatus.O && board[r][(column + 1) % size] == CellStatus.O
							&& board[r][(column + 2) % size] == CellStatus.O
							&& board[r][(column + 3) % size] == CellStatus.O
							&& board[r][(column + 4) % size] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[r][(column + 1) % size] == CellStatus.X
							&& board[r][(column + 2) % size] == CellStatus.X
							&& board[r][(column + 3) % size] == CellStatus.X
							&& board[r][(column + 4) % size] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

			// Vertical win
			for (int column = 0; column < size; column++) {
				for (int r = 0; r < size; r++) {
					if (board[r][column] == CellStatus.O && board[(r + 1) % size][column] == CellStatus.O
							&& board[(r + 2) % size][column] == CellStatus.O
							&& board[(r + 3) % size][column] == CellStatus.O
							&& board[(r + 4) % size][column] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[(r + 1) % size][column] == CellStatus.X
							&& board[(r + 2) % size][column] == CellStatus.X
							&& board[(r + 3) % size][column] == CellStatus.X
							&& board[(r + 4) % size][column] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

		case 6:
			for (int r = 0; r < size; r++) {
				for (int column = 0; column < size; column++) {
					if (board[r][column] == CellStatus.O && board[r][(column + 1) % size] == CellStatus.O
							&& board[r][(column + 2) % size] == CellStatus.O
							&& board[r][(column + 3) % size] == CellStatus.O
							&& board[r][(column + 4) % size] == CellStatus.O
							&& board[r][(column + 5) % size] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[r][(column + 1) % size] == CellStatus.X
							&& board[r][(column + 2) % size] == CellStatus.X
							&& board[r][(column + 3) % size] == CellStatus.X
							&& board[r][(column + 4) % size] == CellStatus.X
							&& board[r][(column + 5) % size] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

			// Vertical win
			for (int column = 0; column < size; column++) {
				for (int r = 0; r < size; r++) {
					if (board[r][column] == CellStatus.O && board[(r + 1) % size][column] == CellStatus.O
							&& board[(r + 2) % size][column] == CellStatus.O
							&& board[(r + 3) % size][column] == CellStatus.O
							&& board[(r + 4) % size][column] == CellStatus.O
							&& board[(r + 5) % size][column] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[(r + 1) % size][column] == CellStatus.X
							&& board[(r + 2) % size][column] == CellStatus.X
							&& board[(r + 3) % size][column] == CellStatus.X
							&& board[(r + 4) % size][column] == CellStatus.X
							&& board[(r + 5) % size][column] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

		case 7:
			for (int r = 0; r < size; r++) {
				for (int column = 0; column < size; column++) {
					if (board[r][column] == CellStatus.O && board[r][(column + 1) % size] == CellStatus.O
							&& board[r][(column + 2) % size] == CellStatus.O
							&& board[r][(column + 3) % size] == CellStatus.O
							&& board[r][(column + 4) % size] == CellStatus.O
							&& board[r][(column + 5) % size] == CellStatus.O
							&& board[r][(column + 6) % size] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[r][(column + 1) % size] == CellStatus.X
							&& board[r][(column + 2) % size] == CellStatus.X
							&& board[r][(column + 3) % size] == CellStatus.X
							&& board[r][(column + 4) % size] == CellStatus.X
							&& board[r][(column + 5) % size] == CellStatus.X
							&& board[r][(column + 6) % size] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

			// Vertical win
			for (int column = 0; column < size; column++) {
				for (int r = 0; r < size; r++) {
					if (board[r][column] == CellStatus.O && board[(r + 1) % size][column] == CellStatus.O
							&& board[(r + 2) % size][column] == CellStatus.O
							&& board[(r + 3) % size][column] == CellStatus.O
							&& board[(r + 4) % size][column] == CellStatus.O
							&& board[(r + 5) % size][column] == CellStatus.O
							&& board[(r + 6) % size][column] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[(r + 1) % size][column] == CellStatus.X
							&& board[(r + 2) % size][column] == CellStatus.X
							&& board[(r + 3) % size][column] == CellStatus.X
							&& board[(r + 4) % size][column] == CellStatus.X
							&& board[(r + 5) % size][column] == CellStatus.X
							&& board[(r + 6) % size][column] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

		case 8:
			for (int r = 0; r < size; r++) {
				for (int column = 0; column < size; column++) {
					if (board[r][column] == CellStatus.O && board[r][(column + 1) % size] == CellStatus.O
							&& board[r][(column + 2) % size] == CellStatus.O
							&& board[r][(column + 3) % size] == CellStatus.O
							&& board[r][(column + 4) % size] == CellStatus.O
							&& board[r][(column + 5) % size] == CellStatus.O
							&& board[r][(column + 6) % size] == CellStatus.O
							&& board[r][(column + 7) % size] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[r][(column + 1) % size] == CellStatus.X
							&& board[r][(column + 2) % size] == CellStatus.X
							&& board[r][(column + 3) % size] == CellStatus.X
							&& board[r][(column + 4) % size] == CellStatus.X
							&& board[r][(column + 5) % size] == CellStatus.X
							&& board[r][(column + 6) % size] == CellStatus.X
							&& board[r][(column + 7) % size] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

			// Vertical win
			for (int column = 0; column < size; column++) {
				for (int r = 0; r < size; r++) {
					if (board[r][column] == CellStatus.O && board[(r + 1) % size][column] == CellStatus.O
							&& board[(r + 2) % size][column] == CellStatus.O
							&& board[(r + 3) % size][column] == CellStatus.O
							&& board[(r + 4) % size][column] == CellStatus.O
							&& board[(r + 5) % size][column] == CellStatus.O
							&& board[(r + 6) % size][column] == CellStatus.O
							&& board[(r + 7) % size][column] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[(r + 1) % size][column] == CellStatus.X
							&& board[(r + 2) % size][column] == CellStatus.X
							&& board[(r + 3) % size][column] == CellStatus.X
							&& board[(r + 4) % size][column] == CellStatus.X
							&& board[(r + 5) % size][column] == CellStatus.X
							&& board[(r + 6) % size][column] == CellStatus.X
							&& board[(r + 7) % size][column] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

		case 9:
			for (int r = 0; r < size; r++) {
				for (int column = 0; column < size; column++) {
					if (board[r][column] == CellStatus.O && board[r][(column + 1) % size] == CellStatus.O
							&& board[r][(column + 2) % size] == CellStatus.O
							&& board[r][(column + 3) % size] == CellStatus.O
							&& board[r][(column + 4) % size] == CellStatus.O
							&& board[r][(column + 5) % size] == CellStatus.O
							&& board[r][(column + 6) % size] == CellStatus.O
							&& board[r][(column + 7) % size] == CellStatus.O
							&& board[r][(column + 8) % size] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[r][(column + 1) % size] == CellStatus.X
							&& board[r][(column + 2) % size] == CellStatus.X
							&& board[r][(column + 3) % size] == CellStatus.X
							&& board[r][(column + 4) % size] == CellStatus.X
							&& board[r][(column + 5) % size] == CellStatus.X
							&& board[r][(column + 6) % size] == CellStatus.X
							&& board[r][(column + 7) % size] == CellStatus.X
							&& board[r][(column + 8) % size] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}

			// Vertical win
			for (int column = 0; column < size; column++) {
				for (int r = 0; r < size; r++) {
					if (board[r][column] == CellStatus.O && board[(r + 1) % size][column] == CellStatus.O
							&& board[(r + 2) % size][column] == CellStatus.O
							&& board[(r + 3) % size][column] == CellStatus.O
							&& board[(r + 4) % size][column] == CellStatus.O
							&& board[(r + 5) % size][column] == CellStatus.O
							&& board[(r + 6) % size][column] == CellStatus.O
							&& board[(r + 7) % size][column] == CellStatus.O
							&& board[(r + 8) % size][column] == CellStatus.O) {
						status = GameStatus.O_WON;
					}
					if (board[r][column] == CellStatus.X && board[(r + 1) % size][column] == CellStatus.X
							&& board[(r + 2) % size][column] == CellStatus.X
							&& board[(r + 3) % size][column] == CellStatus.X
							&& board[(r + 4) % size][column] == CellStatus.X
							&& board[(r + 5) % size][column] == CellStatus.X
							&& board[(r + 6) % size][column] == CellStatus.X
							&& board[(r + 7) % size][column] == CellStatus.X
							&& board[(r + 8) % size][column] == CellStatus.X) {
						status = GameStatus.X_WON;
					}
				}
			}
		}

		// Cats game
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++) {
				if (board[r][c] == CellStatus.O || board[r][c] == CellStatus.X)
					totalCount++;
				if (totalCount == totalBoard) {
					if (status != GameStatus.X_WON && status != GameStatus.O_WON) {
						status = GameStatus.CATS;
					}
				}

			}

		return status;
	}
}
