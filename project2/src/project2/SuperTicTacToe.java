package project2;

/**********************************************************************
* This is the class that has the mein method for running the super tic 
* tac toe between human vs. computer 
*
* @author Kelly Hancox and Isfar Baset
* @version June 13, 2018
**********************************************************************/
import javax.swing.*;

public class SuperTicTacToe {

	public static void main(String[] args) {

		try {
			JFrame frame = new JFrame("Super Tic Tac Toe!");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			SuperTicTacToePanel panel = new SuperTicTacToePanel();

			frame.getContentPane().add(panel);

			frame.setSize(800, 800);
			frame.setVisible(true);
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, 
					"Oops something went" + 
			" wrong. Please try again.");
		}

	}
}
