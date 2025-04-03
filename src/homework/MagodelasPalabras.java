package homework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class MagodelasPalabras {
    //Atributos
    private HashMap<String, Integer> wordBank; //Banco con todas las palabras disponibles
    private HashMap<Integer, Integer> playerPoints; //Puntos de los 2 a 4 jugadores
    private HashSet<String> foundWords; //Guarda las palabras ya usadas
    private HashSet<String> lettersSet; //Set de letras usables por ronda

    private int playersAmount; //Cantidad de jugadores en juego
    private boolean gameMode; //Modo de juego elegido
    private Scanner reader; //Leer por teclado

    Iterator<String> foundWordsIterator;
    Iterator<String> lettersIterator;

    //Iniciarlizar juego
    public MagodelasPalabras(){
        //Inicializar Atributos
        playersAmount = 0;

        playerPoints = new HashMap<>();
        foundWords = new HashSet<>();
        reader = new Scanner(System.in);
        lettersSet = new HashSet<>();

        foundWordsIterator = foundWords.iterator();

        //Crear banco de palabras
        BancodePalabras bank = new BancodePalabras();
        wordBank = bank.getBank();

        //Pedir numero de jugadores
        playersAmount = setPlayersAmount();

        //Crear jugadores
        for(int i = 1; i <= playersAmount; i++){
            playerPoints.put(i, 0);
        }
        //Pedir modo de juego
        gameMode = setGameMode();

        //Jugar
        wordsWizardGame(gameMode);
    }

    //Pedir numero de jugadores
    private int setPlayersAmount(){
        int players = 0;

        System.out.println("+) Ingresa número de jugadores (entre 2 y 4):");
        do{
            players = reader.nextInt();

            if((players < 2) || (players > 4)){
                System.err.println("Número de jugadores invalido, intenta de nuevo: ");
            }
        }while((players < 2) || (players > 4));

        return players;
    }

    //Pedir modo de juego
    private boolean setGameMode(){
        int mode = 0;

        System.out.println("Tipo de puntuacion");
        System.out.println("   Las vocales otorgan 5 puntos, mientras que las constantes otorgan 3, si y solo si es una palabra valida y se cumplio con las 10 letras ofrecidas, por su contraparte el jugador pierde 5 puntos al ingresar una palabra no valida");
        System.out.println("Tipos de Modo ");
        System.out.println("   Regular Los jugadores podran ingresar tantas palabras como quieran, mientras cumplan con las letras establecidas, ellos mismos pasan turno");
        System.out.println("   Experto Los jugadores solo podran poner una palabra en su turno, tambien contaran con solo 7 letras para crear la palabra");

        System.out.println("+) Ingresa Modo de juego: ");
        System.out.println("   1. Regular ().");
        System.out.println("   2. Experto ().");
        do{
            mode = reader.nextInt();

            if((mode != 1) && (mode != 2)){
                System.err.println("Modo de juego invalido, intenta de nuevo.");
            }
        }while((mode != 1) && (mode != 2));

        if(mode == 1){
            return false;
        }else{
            return true;
        }
    }

    //Juego completo
    public void wordsWizardGame(boolean gameMode){
        boolean gameRule = gameMode; //si es falso, es solo una palabra por ronda, si es true son hasta que pase turno
        boolean skipTurn;
        int playerAction=1; //guarda la accion que el jugador desea realizar
        String word; //guarda la palabra del jugador
        //Control de rondas
        for(int i = 1; i <= 3; i++){
            //Control de jugadores
            for(int playerTurn = 1; playerTurn <= playersAmount; playerTurn++){
                //Generar set de palabras al inicio del turno del jugador
                SetdeLetras set = new SetdeLetras();
                if(gameRule){ //si es modo regular crea un set de 10 letras, si es experto lo crea de 7
                    lettersSet = set.generateSet(10);
                }else {
                    lettersSet = set.generateSet(7);
                }
                if(gameRule) { //dependiendo el modo de juego obligamos al jugador a pasar de turno o que el decida
                    skipTurn = false;
                }else{
                    skipTurn = true;
                }
                do{ //Se sigue jugando el turno del jugador hasta que pase turno
                    //Mostrar turno de jugador
                    System.out.println("+) Turno de Jugador " + playerTurn + ": ");
                    System.out.println("   -) Letras disponibles:");

                    //Mostrar letras de turno
                    lettersIterator = lettersSet.iterator();
                    while (lettersIterator.hasNext()) {
                        System.out.println(lettersIterator.next());
                    }

                    if(gameRule){ //si es de mas de una ronda, entonces le pregunta al jugador que desea realizar
                        System.out.println("Que desea realizar?");
                        System.out.println("[1] Ingresar una palabra ");
                        System.out.println("[2] Pasar Turno ");
                        do {
                            playerAction = reader.nextInt();

                            if ((playerAction != 1) && (playerAction != 2)) {
                                System.err.println("Modo de juego invalido, intenta de nuevo.");
                            }
                        } while ((playerAction != 1) && (playerAction != 2));
                    }
                    switch(playerAction){
                        case 1:
                            System.out.println("Ingrese la palabra: ");
                            word = reader.nextLine();
                            //faltaria verificar que la palabra cumple con las 10 letras
                            // verificar que la palabra no haya sido puesta antes
                            givePoints(word, playerTurn); //le da los puntos del valor de la palabra al jugador
                            break;
                        case 2:
                            skipTurn = true;
                            break;
                        default:
                    }


                }while(!skipTurn);
            }
            foundWords.clear();
        }
    }

    //Metodo para darle los puntos de la palabra al jugador
    public void givePoints(String word, int playerTurn){
        if(wordBank.containsKey(word)) { //si el banco de palabras tiene la palabra entonces obtiene su puntuacion y la suma al jugador
            Integer points = wordBank.get(word);
            playerPoints.put(playerTurn, playerPoints.getOrDefault(playerTurn, 0) + points); //obtiene los puntos del jugador y los guarda con los nuevos sumados
            System.out.println("Puntos obtenidos por la palabra: " + points);
            foundWords.add(word);
        }else{
            playerPoints.put(playerTurn, playerPoints.getOrDefault(playerTurn, 0) - 5); //le restamos los 5 puntos
            System.out.println("La palabra no existe ");
        }
    }
}
