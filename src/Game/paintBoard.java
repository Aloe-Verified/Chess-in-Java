package Game;

import java.util.*;
import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.*;

public class paintBoard extends JPanel implements Runnable {

	final int FPS = 60;
	final int width = 8;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();
	ArrayList<Piece> promoPieces = new ArrayList<>();
	public static final boolean WHITE = true;
	public static final boolean BLACK = false;
	boolean currentcolour = WHITE;

	boolean canMove;
	boolean validSquare;
	boolean promotion;

	Piece activeP;
	static Piece castlingP;

	public paintBoard() {
		setPreferredSize(new Dimension(600, 600));
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		set_neutral();

	}

	public void set_neutral() {
//		initialize black pieces of neutral board state
		Rook black_rook1 = new Rook(0, 0, BLACK, board);
		Knight black_knight1 = new Knight(1, 0, BLACK, board);
		Bishop black_bishop1 = new Bishop(2, 0, BLACK, board);
		Queen black_queen = new Queen(3, 0, BLACK, board);
		King black_king = new King(4, 0, BLACK, board);
		Bishop black_bishop2 = new Bishop(5, 0, BLACK, board);
		Knight black_knight2 = new Knight(6, 0, BLACK, board);
		Rook black_rook2 = new Rook(7, 0, BLACK, board);
		Pawn black_pawn1 = new Pawn(0, 1, BLACK, board);
		Pawn black_pawn2 = new Pawn(1, 1, BLACK, board);
		Pawn black_pawn3 = new Pawn(2, 1, BLACK, board);
		Pawn black_pawn4 = new Pawn(3, 1, BLACK, board);
		Pawn black_pawn5 = new Pawn(4, 1, BLACK, board);
		Pawn black_pawn6 = new Pawn(5, 1, BLACK, board);
		Pawn black_pawn7 = new Pawn(6, 1, BLACK, board);
		Pawn black_pawn8 = new Pawn(7, 1, BLACK, board);
//		white pieces
		Rook white_rook1 = new Rook(0, 7, WHITE, board);
		Knight white_knight1 = new Knight(1, 7, WHITE, board);
		Bishop white_bishop1 = new Bishop(2, 7, WHITE, board);
		Queen white_queen = new Queen(3, 7, WHITE, board);
		King white_king = new King(4, 7, WHITE, board);
		Bishop white_bishop2 = new Bishop(5, 7, WHITE, board);
		Knight white_knight2 = new Knight(6, 7, WHITE, board);
		Rook white_rook2 = new Rook(7, 7, WHITE, board);
		Pawn white_pawn1 = new Pawn(0, 6, WHITE, board);
		Pawn white_pawn2 = new Pawn(1, 6, WHITE, board);
		Pawn white_pawn3 = new Pawn(2, 6, WHITE, board);
		Pawn white_pawn4 = new Pawn(3, 6, WHITE, board);
		Pawn white_pawn5 = new Pawn(4, 6, WHITE, board);
		Pawn white_pawn6 = new Pawn(5, 6, WHITE, board);
		Pawn white_pawn7 = new Pawn(6, 6, WHITE, board);
		Pawn white_pawn8 = new Pawn(7, 6, WHITE, board);

	}

	private void update() {
		if (mouse.pressed) {

			if (activeP == null) {
				int x = mouse.x / Board.sqr_size;
				int y = mouse.y / Board.sqr_size;
				if (x >= 0 && x < 8 && y >= 0 && y < 8) {
					Piece p = board.get(x, y);
					if (p != null && p.getColour() == currentcolour) {
						activeP = p;
					}
				}
			} else {
				simulate();
			}
		}
		if (mouse.pressed == false) {
			if (activeP != null) {
				if (validSquare) {
					activeP.setBoard();
					activeP.updatePosition();
					Promote();
					if(castlingP!=null) {
						castlingP.setBoard();
						castlingP.updatePosition();
					}
					if(activeP.type==Type.PAWN) {
						int delta = (activeP.getColour()==WHITE)?-1:1;
						Piece p = board.get(activeP.x, activeP.y-delta);
						if(p!=null&&p.type==Type.PAWN&&p.enpassant_flag)board.set(null, activeP.x, activeP.y-delta);
					}

				
					changePlayer();
				} else {
					activeP.resetPosition();
				}
				activeP = null;
			}
		}

	}
	public void Promote() {
		if(activeP.type==Type.PAWN) {
			if(currentcolour == WHITE&&activeP.y==0) {
				board.set(new Queen(activeP.x,activeP.y,WHITE,board), activeP.x, activeP.y);
			}
			if(currentcolour == BLACK && activeP.y==7) {
				board.set(new Queen(activeP.x,activeP.y,BLACK,board), activeP.x, activeP.y);
			}
		}
	}
	
	private void checkCastling() {
		if(castlingP!=null) {
			if(castlingP.x == 0) {
				castlingP.x += 3;
				
				
			}
			else if(castlingP.x==7) {
				castlingP.x -= 2;
				
				
			}
		
		
			castlingP.posx = (castlingP.x * 64);
				
		}
	}

	private void changePlayer() {
		// TODO Auto-generated method stub
		currentcolour = (currentcolour == WHITE)?BLACK:WHITE;
		if(currentcolour == BLACK) {
			for(int i = 0; i < board.width; i ++) {
				for(int j = 0; j < board.width; j++) {
					if(board.get(i, j)!=null&&board.get(i, j).getColour()==BLACK) {
						board.get(i, j).enpassant_flag = false;
					}
				}
			}
		}
		if(currentcolour == WHITE) {
			for(int i = 0; i < board.width; i ++) {
				for(int j = 0; j < board.width; j++) {
					if(board.get(i, j)!=null&&board.get(i, j).getColour()==WHITE) {
						board.get(i, j).enpassant_flag = false;
					}
				}
			}
		}
		
	}

	private void simulate() {
		canMove = false;
		validSquare = false;

		if(castlingP != null) {
			castlingP.x = castlingP.xOld;
			castlingP.posx = castlingP.getPosX(castlingP.x);
			castlingP = null;
 		}
		activeP.posx = mouse.x - Board.sqr_size / 2;
		activeP.posy = mouse.y - Board.sqr_size / 2;
		activeP.x = activeP.MouseToX(activeP.posx);
		activeP.y = activeP.MouseToY(activeP.posy);

		
		if (activeP.isLegalMove(activeP.x, activeP.y)) {
			
			canMove = true;
			checkCastling();
			validSquare = true;
			
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		board.draw(g);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				Piece p = board.get(i, j);
				if (p != null) {

					p.draw(g);

				}
			}
		}
		if (activeP != null) {
			if (canMove) {
				g2d.setColor(Color.WHITE);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
				g2d.fillRect(activeP.x * Board.sqr_size, activeP.y * Board.sqr_size, Board.sqr_size, Board.sqr_size);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
		}
	}

	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override

	public void run() {
		// creating a game loop
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}

	}
}
