package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Piece {
	public Type type;
	int dimensionx = 52;
	int dimensiony = 52;
	final int sqrcnt = 64;
	final int width = 8;

	public int getDimensionx() {
		return dimensionx;
	}

	public int getDimensiony() {
		return dimensiony;
	}

	public BufferedImage image;

	int x, y, xOld, yOld, posx, posy;
	final int piece_sizex = this.getDimensionx();
	final int discrepancyx = (Board.sqr_size - piece_sizex) / 2;
	final int piece_sizey = this.getDimensiony();
	final int discrepancyy = (Board.sqr_size - piece_sizey) / 2;
	boolean colour;
	public boolean moved, enpassant_flag, enpassant_performed;
	
	Board board;

	protected Piece(int x, int y, boolean colour, Board board) {

		this.x =  x;
		this.y = y;
		posx = getPosX(x);
		posy = getPosY(y);
		this.colour = colour;
		this.board = board;
		xOld = x;
		yOld = y;
		board.set(this, x, y);
	}

	public BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return image;

	}

	public void draw(Graphics g) {
		g.drawImage(image, posx + discrepancyx, posy + discrepancyy, null);

		// PieceToImage.put()

	}

//getters and setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}




	protected boolean getColour() {
		return colour;
	}

	// position in space for GUI

	public int getPosX(int x) {
		return x * Board.sqr_size;
	}

	public int getPosY(int y) {
		return y * Board.sqr_size;
	}

	public int MouseToX(int posx) {
		return (posx + (Board.sqr_size / 2)) / Board.sqr_size;
	}

	public int MouseToY(int posy) {
		return (posy + (Board.sqr_size / 2)) / Board.sqr_size;
	}

	public void updatePosition() {
		
		posx = getPosX(x);
		posy = getPosY(y);
		xOld = MouseToX(posx);
		yOld = MouseToY(posy);
		moved = true;

		
	}
	public void setBoard() {
		board.set(this, this.getX(), this.getY());
		board.set(null, this.xOld, this.yOld);
		
		if(type == Type.PAWN) {

			if(Math.abs(y-yOld)==2) {
				enpassant_flag = true;
			}
		}
		this.xOld = this.getX();
		this.yOld = this.getY();
	}

	public void resetPosition() {
		x = xOld;
		y = yOld;
		posx = getPosX(x);
		posy = getPosY(y);
	}

	// checks whether a move is within the bounds of a 8x8 chess board
	public boolean moveWithinBounds(int xNew, int yNew) {
		if (xNew < 8 && xNew >= 0 && yNew < 8 && yNew >= 0)
			return true;
		return false;
	}

	public boolean isSameSquare(int xNew, int yNew) {
		if (Math.abs(xNew - xOld) + Math.abs(yNew - yOld) == 0) {
			return true;
		}
		return false;
	}

	// checks whether or not a move is out of boundaries and whether there's already
	// a piece there
	public boolean isLegalMove(int xNew, int yNew) {
		if (moveWithinBounds(xNew, yNew)) {
			if ((board.get(xNew, yNew) == null || board.get(xNew, yNew).getColour() != this.getColour())) {
				return true;
			}
		}
		return false;
	}

	// checking whether a move made by this piece is a legal straight move
	protected boolean isStraightMove(int xNew, int yNew) {
		if (xNew == xOld || yNew == yOld)
			return true;
		return false;
	}

	// checking whether a move made by this piece is a legal diagonal move
	protected boolean isDiagonalMove(int xNew, int yNew) {

		if (Math.abs(xNew - xOld) == Math.abs(yNew - yOld)) {
			return true;
		}
		return false;
	}

	public boolean isIntercepted(int xNew, int yNew) {
		int deltaX = 0, deltaY = 0;

		if (yNew != yOld) {
			deltaY = yNew > yOld ? 1 : -1;
		}
		if (xNew != xOld) {
			deltaX = xNew > xOld ? 1 : -1;
		}
		int x1 = xOld + deltaX, y1 = yOld + deltaY;
		// iterates through every square between the old location and the new location
		// to ensure that the piece is not jumping over anything
		while (!(x1 == xNew && y1 == yNew)) {
			if (board.get(x1, y1) != null) {
				return true;
			}
			x1 += deltaX;
			y1 += deltaY;
		}
		return false;

	}



}
