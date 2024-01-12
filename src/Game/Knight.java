package Game;

public class Knight extends Piece{
	final int dimensionx = 46;
	final int dimensiony = 52;
	public Knight(int x, int y, boolean colour, Board b) {
		super(x, y, colour, b);
		type = Type.KNIGHT;
		if (colour == paintBoard.WHITE) {
			image = loadImage("Assets/white_knight.jpg");
		}
		else
			image = loadImage("Assets/black_knight.jpg");
	}
	@Override
	public boolean isLegalMove(int xNew, int yNew) {
		return (Math.abs(yNew - yOld)==2&&(Math.abs(xNew - xOld)==1) ||
				Math.abs(xNew - xOld)==2&&(Math.abs(yNew - yOld)==1)) && super.isLegalMove(xNew, yNew);
	}
	public int getDimensionx() {
		return dimensionx;
	}


	public int getDimensiony() {
		return dimensiony;
	}
}
