package homework;

public class PalabraUsada {
    //Atributos
    private String word;
    private int points;
    private int playerID;

    //Establecer datos de la palabra
    public PalabraUsada(String word, int points, int playerID) {
        this.word = word;
        this.points = points;
        this.playerID = playerID;
    }

    //Regresar palabra
    public String getWord() {
        return word;
    }

    //Regresar puntos
    public int getPoints() {
        return points;
    }

    //Regresar ID de jugador
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public String toString() {
        return word + " (" + points + " puntos para Jugador " + playerID + " ).";
    }
}
