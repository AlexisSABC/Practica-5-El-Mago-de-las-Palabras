package homework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class MagodelasPalabras {
    //Atributos
    private HashMap<String, Integer> wordBank;
    private HashMap<Integer, Integer> playerPoints;
    private HashSet<String> foundWords;
    private HashSet<String> actualLetters;

    private int playersAmount;
    private Scanner reader;

    //Iniciarlizar juego
    public MagodelasPalabras(){
        //Inicializar Atributos
        playerPoints = new HashMap<>();
        foundWords = new HashSet<>();
        actualLetters = new HashSet<>();
        playersAmount = 0;
        reader = new Scanner(System.in);

        //Crear banco de palabras
        BancodePalabras bank = new BancodePalabras();
        wordBank = bank.getBank();

        //Pedir numero de jugadores
        playersAmount = setPlayersAmount();
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
}
