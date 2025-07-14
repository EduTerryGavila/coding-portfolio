package geometria;

public class PuntoInmutable {
		private final int x;
		private final int y;
		
		public PuntoInmutable(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public PuntoInmutable() {
			this(0,0);
		}
		
		public PuntoInmutable(PuntoInmutable puntoInmutable) {
			x = puntoInmutable.getX();
			y = puntoInmutable.getY();
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public PuntoInmutable desplazar(int dx, int dy) {
			return new PuntoInmutable(x+dx,y+dy);
		}
	}


