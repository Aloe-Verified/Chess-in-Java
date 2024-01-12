package Game;

public class Pawn extends Piece {

	final int dimensionx = 42;
	final int dimensiony = 52;
	
	public Pawn(int x, int y, boolean colour, Board b) {
		super(x, y, colour, b);
		type = Type.PAWN;
		if (colour == paintBoard.WHITE) {
			image = loadImage("Assets/white_pawn.jpg");
		} else
			image = loadImage("Assets/black_pawn.jpg");
	}

	@Override
	public boolean isLegalMove(int xNew, int yNew) {
		int delta = (this.getColour() == paintBoard.WHITE) ? -1 : 1;

		if (super.isLegalMove(xNew, yNew)) {
			if (xNew == xOld && yNew == yOld + delta) {
				if (board.get(xNew, yNew) != null)
					return false;
				return true;

			}

			if (xNew == xOld && yNew == yOld + 2 * delta && !moved && !super.isIntercepted(xNew, yNew)) {
				if (board.get(xNew, yNew) != null)
					return false;
				return true;
			}
			if (Math.abs(xNew - xOld) == 1 && yNew == yOld + delta && board.get(xNew, yNew) != null)
				return true;
			
			if(Math.abs(xNew - xOld) == 1 && yNew == yOld + delta) { 
				Piece p = board.get(xNew, yNew-delta);
				if(p!=null&&p.enpassant_flag==true) {
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
