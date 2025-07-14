package geometria;

public class CirculoInmutable {
		private static final double PI = Math.PI;
		private static final Punto ORIGEN_COORDENADAS = new Punto(0,0);
		private static final int RADIO_POR_DEFECTO = 5;
		private final Punto centro;
		private final int radio;
		
		public CirculoInmutable(Punto centro, int radio) {
			this.centro = centro;
			this.radio = radio;
		}
		
		public CirculoInmutable() {
			this(ORIGEN_COORDENADAS,RADIO_POR_DEFECTO);
		}
		
		public Punto getCentro() {
			return centro;
		}
		
		public int getRadio() {
			return radio;
		}
		
		public double getPerimetro() {
			return 2*PI*radio;
		}
		
		public CirculoInmutable desplazar(int dx, int dy) {
			Punto nuevoCentro = new Punto(dx+centro.getX(),dy+centro.getY());
			return new CirculoInmutable(nuevoCentro, this.radio);
		}
		
		public CirculoInmutable escalar(double cantidad) {
			double resultado = cantidad/100.0;
			int nuevoRadio = (int)(resultado*radio);
			return new CirculoInmutable(this.centro,nuevoRadio);
		}
	}


