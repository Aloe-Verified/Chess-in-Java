package Game;

public class Rook extends Piece {
	final int dimensionx = 47;
	final int dimensiony = 52;
	public Type type;
	public Rook(int x, int y, boolean colour, Board board) {
		super(x, y, colour, board);
		type = Type.ROOK;
				
		if (colour == paintBoard.WHITE) {
			image = loadImage("Assets/white_rook.jpg");
		} else
			image = loadImage("Assets/black_rook.jpg");
	}

	@Override
	public boolean isLegalMove(int xNew, int yNew) {
		if (super.isLegalMove(xNew, yNew) && super.isStraightMove(xNew, yNew)&&!super.isIntercepted(xNew, yNew))
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
