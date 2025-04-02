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
    private int gameMode; //Modo de juego elegido
    private Scanner reader; //Leer por teclado

    Iterator<String> foundWordsIterator;
    Iterator<String> lettersIterator;

    //Iniciarlizar juego
    public MagodelasPalabras(){
        //Inicializar Atributos
        playersAmount = 0;
        gameMode = 0;

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
        wordsWizardGame();
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
    private int setGameMode(){
        int mode = 0;

        System.out.println("+) Ingresa Modo de juego: ");
        System.out.println("   1. Regular ().");
        System.out.println("   2. Experto ().");
        do{
            mode = reader.nextInt();

            if((mode != 1) && (mode != 2)){
                System.err.println("Modo de juego invalido, intenta de nuevo.");
            }
        }while((mode != 1) && (mode != 2));

        return mode;
    }

    //Juego completo
    public void wordsWizardGame(){
        //Control de turnos
        for(int i = 1; i <= 3; i++){
            //Generar set de palabras
            SetdeLetras set = new SetdeLetras();
            lettersSet = set.generateSet();

            //Control de jugadores
            for(int playerTurn = 1; playerTurn <= playersAmount; playerTurn++){
                //Mostrar turno de jugador
                System.out.println("+) Turno de Jugador " + playerTurn +": ");
                System.out.println("   -) Letras disponibles:");

                //Mostrar letras de turno
                lettersIterator = lettersSet.iterator();
                while(lettersIterator.hasNext()){
                    System.out.println(lettersIterator.next());
                }
            }
        }
    }
}
