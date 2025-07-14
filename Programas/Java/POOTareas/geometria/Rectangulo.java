package geometria;

public class Rectangulo {
	private int ladoX;
	private int ladoY;
	private Punto verticeII;
	private Punto verticeSI;
	private Punto verticeSD;
	private Punto verticeID;
	private int perimetro;
	
	public Rectangulo(Punto verticeII, int ladoX, int ladoY) {
		this.ladoX = ladoX;
		this.ladoY = ladoY;
		this.verticeII = new Punto(verticeII.getX(),verticeII.getY());
		this.verticeSI = new Punto(verticeII.getX(),verticeII.getY()+ladoY);
		this.verticeID = new Punto(verticeII.getX()+ladoX,verticeII.getY());
		this.verticeSD = new Punto(verticeII.getX()+ladoX,verticeII.getY()+ladoY); 
	}
	
	public Rectangulo(Punto verticeII, Punto verticeSD) {
		this.verticeII = new Punto(verticeII.getX(),verticeII.getY());
		this.verticeSD = new Punto(verticeSD.getX(),verticeSD.getY());
		this.ladoX = verticeSD.getX() - verticeII.getX();
		this.ladoY = verticeSD.getY() - verticeII.getY();
		this.verticeSI = new Punto(verticeII.getX(),verticeII.getY()+ladoY);
		this.verticeID = new Punto(verticeII.getX()+ladoX,verticeII.getY());	
	}
	
	public Punto getVerticeII() {
		return verticeII;
	}
	
	public Punto getVerticeSI() {
		return verticeSI;
	}
	
	public Punto getVerticeSD() {
		return verticeSD;
	}
	
	public Punto getVerticeID() {
		return verticeID;
	}
	
	public int getPerimetro() {
		perimetro = ladoX*2 + ladoY*2;
		return perimetro;
	}
	
	public void desplazar(int x, int y) {
		this.verticeII = new Punto(verticeII.getX()+x,verticeII.getY()+y);
		this.verticeSI = new Punto(verticeSI.getX()+x,verticeSI.getY()+y);
		this.verticeSD = new Punto(verticeSD.getX()+x,verticeSD.getY()+y);
		this.verticeID = new Punto(verticeID.getX()+x,verticeID.getY()+y);

	}
	
	public void escalar(double tam) {
		double resul = tam/100;
		ladoX = (int)(resul*ladoX);
		ladoY = (int)(resul*ladoY);
		this.verticeII = new Punto(verticeII.getX(),verticeII.getY());
		this.verticeSI = new Punto(verticeII.getX(),verticeII.getY()+ladoY);
		this.verticeID = new Punto(verticeII.getX()+ladoX,verticeII.getY());
		this.verticeSD = new Punto(verticeII.getX()+ladoX,verticeII.getY()+ladoY); 
	}
	
	@Override
	public String toString() {
        return "Rectangulo [verticeII=(" + verticeII.getX() + ", " + verticeII.getY() +
               "), verticeSI=(" + verticeSI.getX() + ", " + verticeSI.getY() +
               "), verticeSD=(" + verticeSD.getX() + ", " + verticeSD.getY() +
               "), verticeID=(" + verticeID.getX() + ", " + verticeID.getY() +
               "), ladoX=" + ladoX + ", ladoY=" + ladoY + ", perimetro=" + getPerimetro() + "]";
    }
}
