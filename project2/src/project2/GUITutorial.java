package project2;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUITutorial {
  private JFrame     frame;
  private JPanel     panel;
  private JButton    btnSubmit;
  private JLabel     lbResult;
  private JTextField tfInput;

  // Constructor
  public GUITutorial() {
    // Create the textfield to read input
    tfInput = new JTextField( 30 );
    tfInput.setBounds( 10, 10, 240, 20 );

    // Create the button to submit the value
    btnSubmit = new JButton( "Submit" );
    btnSubmit.addActionListener( new btnSubmitAction( this ) );
    btnSubmit.setBounds( 260, 10, 100, 20 );

    // Create the label to display the result
    lbResult = new JLabel( "Enter input and then press Submit" );
    lbResult.setBounds( 10, 40, 320, 20 );

    // Create the panel to hold the button, label, and textfield
    panel = new JPanel( null );
    panel.add( btnSubmit );
    panel.add( lbResult );
    panel.add( tfInput );
    panel.setPreferredSize( new Dimension(370, 70) );

    // Create the frame which is a window
    frame = new JFrame( "GUITutorial" );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.getContentPane().add( panel );
    frame.pack();
    frame.setVisible( true );
  }

  public String getTextFieldInput() {
    return tfInput.getText();
  }

  public void setTextLabelResult( String s ) {
    lbResult.setText( s );
  }

  public static void main( String[] args ) {
    new GUITutorial();
  }
}

class btnSubmitAction implements ActionListener {
  private GUITutorial g;

  public btnSubmitAction( GUITutorial g ) {
    this.g = g;
  }

  @Override
  public void actionPerformed( ActionEvent e ) {
    String s = g.getTextFieldInput();
    g.setTextLabelResult( "You have entered: " + s );
  }
}