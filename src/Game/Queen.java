package Game;

public class Queen extends Piece {
	final int dimensionx = 53;
	final int dimensiony = 46;
	public Queen(int x, int y, boolean colour, Board b) {
		super(x, y, colour, b);
		type = Type.QUEEN;
		if (colour == paintBoard.WHITE) {
			image = loadImage("Assets/white_queen.jpg");
		}
		else
			image = loadImage("Assets/black_queen.jpg");
	}

	@Override
	public boolean isLegalMove(int xNew, int yNew) {
		if (super.isLegalMove(xNew, yNew) && (super.isDiagonalMove(xNew, yNew)||super.isStraightMove(xNew, yNew))&&!super.isIntercepted(xNew, yNew))
			return true;
		return false;
	}
	public int getDimensionx() {
		return dimensionx;
	}


	public int getDimensiony() {
		return dimensiony;
	}
}
