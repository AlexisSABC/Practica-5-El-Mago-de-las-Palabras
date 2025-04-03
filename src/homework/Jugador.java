package homework;

public class Jugador {
    private int points;

    public Jugador(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void minusFivePoints(){ //si la palabra no existe entonces se le quitan 5 puntos
        this.points=this.points-5;
    }
}
