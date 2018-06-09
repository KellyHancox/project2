package project2;

import javax.swing.*;

public class SuperTicTacToe {

	public static void main(String[] args) {

		JMenuBar menus;
		JMenu fileMenu;
		JMenuItem quitItem;
		JMenuItem gameItem;

		JFrame frame = new JFrame("Super Tic Tac Toe!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("File");
		quitItem = new JMenuItem("quit");
		gameItem = new JMenuItem("new game");

		fileMenu.add(gameItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);

		SuperTicTacToePanel panel = new SuperTicTacToePanel();
		frame.getContentPane().add(panel);

		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}

		

	
	

