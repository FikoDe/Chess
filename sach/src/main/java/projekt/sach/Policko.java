package projekt.sach;

import java.util.Objects;


public class Policko{
	private int x;
	private int y;
	
	public Policko(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Policko other = (Policko) obj;
		return x == other.x && y == other.y;
	}
	
}
