package Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MyFrame extends JFrame {

	MyFrame() throws IOException{

		paintBoard squares = new paintBoard();
		BufferedImage im = ImageIO.read(new File ("Assets/Donkey_Kong_character.png"));
		this.setIconImage(im);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(squares);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		squares.launchGame();
	}



	
		
	

}
