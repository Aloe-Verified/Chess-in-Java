package Game;

public class King extends Piece {
	final int dimensionx = 52;
	final int dimensiony = 52;
	public King(int x, int y, boolean colour, Board b) {
		super(x, y, colour, b);
		type = Type.KING;
		// TODO Auto-generated constructor stub
		if (colour == paintBoard.WHITE) {
			image = loadImage("Assets/white_king.jpg");
		} else
			image = loadImage("Assets/black_king.jpg");
	}

	@Override
	public boolean isLegalMove(int xNew, int yNew) {
		if (Math.abs(xNew - xOld) <= 1 && Math.abs(yNew - yOld) <= 1 && !isSameSquare(xNew, yNew)
				&& super.isLegalMove(xNew, yNew))
			return true;

		else if (!moved) {
			if ((xNew == xOld + 2) && (yNew == yOld) && (!super.isIntercepted(xNew, yNew))) {
				Piece p = (board.get(xOld + 3, yOld));
				if (p != null && p.moved == false) {
					paintBoard.castlingP = p;
					return true;
				}

			}
			if((xNew == xOld - 2) && (yOld == yNew) &&	!super.isIntercepted(xNew, yNew)) {
				Piece p = (board.get(xOld - 4, yNew));
				if(!p.moved&&board.get(xOld - 3, yNew)==null) {
					paintBoard.castlingP = p;
					return true;
				}
			}
		}
		return false;
	}

	public int getDimensionx() {
		return dimensionx;
	}

	public int getDimensiony() {
		return dimensiony;
	}

}
