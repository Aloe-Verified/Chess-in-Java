package Game;

public class Bishop extends Piece {


	
	final int dimensionx = 52;
	final int dimensiony = 52;
	public Bishop(int x, int y, boolean colour, Board b) {
		super(x, y, colour, b);
		type = Type.BISHOP;
		if (colour == paintBoard.WHITE) {
			image = loadImage("Assets/white_bishop.jpg");
		}
		else
			image = loadImage("Assets/black_bishop.jpg");
	}
	

	@Override
	public boolean isLegalMove(int xNew, int yNew) {
		if (super.isLegalMove(xNew, yNew) && super.isDiagonalMove(xNew, yNew) &&!super.isIntercepted(xNew, yNew))
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
