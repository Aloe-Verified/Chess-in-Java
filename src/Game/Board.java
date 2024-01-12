	package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Board {
	final int sqrcnt = 64;
	final int width = 8;
	public static final int sqr_size = 64;
	public void draw(Graphics g) {
		boolean colour = true;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				g.setColor(colour == true ? new Color(112,102,119) : new Color(204,183,174));
				g.fillRect(x * sqrcnt, y * sqrcnt, sqrcnt, sqrcnt);
				colour = !colour;
			}
			colour = !colour;
		}
	}

	Piece[][] board;

	public Board() {
		board = new Piece[width][width];
	}

	public void set(Piece p	, int x, int y) {
		board[x][y] = p;
	}

	public Piece get(int x, int y) {
		
		try {
			return board[x][y];
		}

		catch (Exception e) {
			throw e;
		}

	}

}
