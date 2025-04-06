package homework;

import java.util.*;

public class MagodelasPalabras {
    //Atributos
    private HashMap<String, Integer> wordBank; //Banco con todas las palabras disponibles
    private HashSet<String> foundWords; //Guarda las palabras ya usadas
    private HashSet<String> lettersSet; //Set de letras usables por ronda
    private ArrayList<PalabraUsada> foundWordsData; //Guardar palabra, puntaje y jugador que lo ingreso

    private int playersAmount; //Cantidad de jugadores en juego
    private HashMap<Integer, Integer> playerPoints; //Puntos de los 2 a 4 jugadores

    private Scanner reader; //Leer por teclado

    //Iniciarlizar juego
    public MagodelasPalabras(){
        //Inicializar Atributos
        playersAmount = 0;

        playerPoints = new HashMap<>();
        foundWords = new HashSet<>();
        lettersSet = new HashSet<>();
        foundWordsData = new ArrayList<>();

        reader = new Scanner(System.in);

        //Crear banco de palabras
        BancodePalabras bank = new BancodePalabras();
        wordBank = bank.getBank();

        //Pedir numero de jugadores
        playersAmount = setPlayersAmount();
        System.out.println("\n");

        //Crear jugadores
        for(int i = 1; i <= playersAmount; i++){
            playerPoints.put(i, 0);
        }

        //Pedir modo de juego
        boolean gameMode = setGameMode();
        System.out.println("\n");

        //Jugar
        wordsWizardGame(gameMode);

        //Mostrar estadisticas
        showGameResults();
    }

    //Pedir numero de jugadores
    public int setPlayersAmount(){
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
    public boolean setGameMode(){
        int mode = 0;

        System.out.println("+) Reglas de puntuación: ");
        System.out.println("   -) Las vocales otorgan 5 puntos.");
        System.out.println("   -) Las consonantes otorgan 3 puntos.");
        System.out.println("   -) Se agregan los puntos si la palabra ingresada es una palabra valida y cumple con las letras ofrecidas.");
        System.out.println("   -) Por su contraparte, el jugador pierde 5 puntos al ingresar una palabra no valida.");
        System.out.println("+) Modos de juego: ");
        System.out.println("   -) Regular: Los jugadores podran ingresar tantas palabras como quieran  en un turno.");
        System.out.println("               Ellos mismos pasan turno.");
        System.out.println("               A cada jugador se le da un set de 10 palabras.");
        System.out.println("               Hay 3 vocales garantizadas.");
        System.out.println("   -) Experto: Los jugadores solo podran poner una palabra en su turno.");
        System.out.println("               Cada jugador contara con un set de 7 letras para crear la palabra.");
        System.out.println("               Hay 2 vocales garantizadas.");

        System.out.println("\n");

        System.out.println("+) Ingresa Modo de juego: ");
        System.out.println("   1. Regular.");
        System.out.println("   2. Experto.");
        do{
            mode = reader.nextInt();

            if((mode != 1) && (mode != 2)){
                System.err.println("Modo de juego invalido, intenta de nuevo.");
            }
        }while((mode != 1) && (mode != 2));

        if(mode == 1){
            return true;
        }else{
            return false;
        }
    }

    //Juego completo
    public void wordsWizardGame(boolean gameMode){
        boolean gameRule = gameMode; //si es falso, es solo una palabra por ronda, si es true son hasta que pase turno
        boolean skipTurn;
        int skipCounter = 0; //Cuenta cuantos jugadores han pasado turno
        int playerTurn = 0;
        int playerAction=1; //guarda la accion que el jugador desea realizar
        String word = ""; //guarda la palabra del jugador

        //Control de rondas
        for(int i = 1; i <= 3; i++){
            //Reiniciar vairables y listas
            playerTurn = 0;
            skipCounter = 0;
            foundWords.clear();
            foundWordsData.clear();

            //Titulo de ronda
            System.out.println("( -------------------- RONDA " + i + " -------------------- )");

            //Control de jugadores
            do{
                //Incrementar control de jugadores
                if(playerTurn < playersAmount){
                    playerTurn++;
                }else{
                    playerTurn = 1;
                }

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

                //Se sigue jugando el turno del jugador hasta que pase turno
                do{
                    //Mostrar Puntos de jugadores
                    System.out.println("+) Puntos de jugadores: ");
                    playerPoints.forEach((k, v) -> {
                        System.out.println("   -) Jugador " + k + ": " + v + " Puntos.");
                    });

                    //Mostrar letras usadas
                    Iterator<PalabraUsada> foundWordsDataIterator = foundWordsData.iterator();
                    System.out.println("+) Palabras usadas: ");
                    while(foundWordsDataIterator.hasNext()){
                        System.out.println("   -) " + foundWordsDataIterator.next().toString());
                    }
                    System.out.println("\n");

                    //Mostrar turno de jugador
                    System.out.println("+) Turno de Jugador " + playerTurn + " (Puntos = " + playerPoints.get(playerTurn) + "): ");
                    System.out.println("   -) Letras disponibles:");

                    //Mostrar letras de turno
                    Iterator<String> lettersIterator = lettersSet.iterator();
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

                    reader = new Scanner(System.in);
                    switch(playerAction){
                        case 1:
                            //Incrementar si esta elegido el modo experto
                            if(gameRule == false){
                                skipCounter++;
                            }

                            //Ingresar palabras
                            System.out.println("Ingrese la palabra: ");
                            word = reader.nextLine();

                            //Verificar que la palabra ingresada tenga las palabras del HashSet
                            if(verifyLetters(word)){
                                //Encontrar y dar los puntos del valor de la palabra al jugador si no ha sido colocado
                                findWord(word, playerTurn);
                            }else{
                                //Mensaje de letras no permitidad
                                System.out.println("-) La palabra tiene letras no asignadas, -5 Puntos.");

                                //Eliminar 5 puntos
                                reduceFivePoints(playerTurn);
                            }
                            break;
                        case 2:
                            skipTurn = true;
                            skipCounter++;
                            break;
                        default:
                            break;
                    }

                    System.out.println("\n");
                }while(!skipTurn);

            }while(skipCounter != playersAmount);
        }
    }

    //Metodo para darle los puntos de la palabra al jugador
    public void findWord(String word, int playerTurn){
        //Verificar si ya ha sido ingresado antes
        if(foundWords.contains(word)){
            System.out.println("+) " + word + " ya ha sido usada antes.");

        }else{
            if(wordBank.containsKey(word)) { //si el banco de palabras tiene la palabra entonces obtiene su puntuacion y la suma al jugador
                //Guardar puntos
                Integer points = wordBank.get(word);

                //Actualizar los puntos
                playerPoints.put(playerTurn, playerPoints.get(playerTurn) + points);

                //Mensaje
                System.out.println("Puntos obtenidos por la palabra: " + points);

                //Agregar datos de la palabra
                PalabraUsada usedWord = new PalabraUsada(word, points, playerTurn);
                foundWordsData.add(usedWord);
            }else{
                //Eliminar 5 puntos
                reduceFivePoints(playerTurn);

                //Mensaje
                System.out.println("La palabra no existe, -5 puntos");

                //Agregar palabra usada
                PalabraUsada usedWord = new PalabraUsada(word, -5, playerTurn);
                foundWordsData.add(usedWord);
            }
        }

        //Establecer que la palabra ya se uso
        foundWords.add(word);
    }

    //Eliminar 5 puntos de jugador
    public void reduceFivePoints(int playerTurn){
        for(int i = 1; i <= 5; i++){
            if(playerPoints.get(playerTurn) != 0){
                playerPoints.put(playerTurn, playerPoints.get(playerTurn) - 1);
            }
        }
    }

    //Verifiar que las letras de la palabra ingresada esten en el HashSet
    public boolean verifyLetters(String word){
        boolean existLetters = true;

        //Convertir letra a arreglo
        String[] wordArray = word.split("");
        int booleanCounter[] = {0};

        //Crear arreglo de control
        boolean[] wordsControl = new boolean[wordArray.length];
        Arrays.fill(wordsControl, false);

        //Buscar las letras validas
        Iterator<String> lettersIterator = lettersSet.iterator(); //Iterador
        while(lettersIterator.hasNext()){
            String letter = lettersIterator.next();
            for(int i = 0; i < wordArray.length; i++){
                if(letter.equals(wordArray[i])){
                    wordsControl[i] = true;
                }
            }
        }

        //Lambda para contar las letras validas
        for(int i = 0; i < wordsControl.length; i++){
            if(wordsControl[i]){
                booleanCounter[0]++;
            }
        }

        //Definir si la palabra tiene todas sus letras validas
        if(booleanCounter[0] == wordArray.length){
            existLetters = true;
        }else{
            existLetters = false;
        }

        return existLetters;
    }

    //Mostrar resultados de juego
    public void showGameResults(){
        //Control de puntaje mas alto
        int highestPoints[] = {playerPoints.get(1)};
        int tiePlayers[] = {0};

        //Obtener el puntaje mas alto
        playerPoints.forEach((k, v) -> {
            if(v >= highestPoints[0]){
                highestPoints[0] = v;
            }
        });

        //Determinar si existe empate
        playerPoints.forEach((k, v) -> {
            if(v == highestPoints[0]){
                tiePlayers[0]++;
            }
        });

        //Mostrar resultados
        System.out.println("+) Resultados de los Jugadores: ");
        playerPoints.forEach((k, v) -> {
            if(v == highestPoints[0]){
                if(tiePlayers[0] > 1){
                    System.out.println("   -) Jugador " + k + ": " + v + " Puntos (EMPATE).");
                }else{
                    System.out.println("   -) Jugador " + k + ": " + v + " Puntos (¡¡¡Ganador!!!).");
                }
            }else{
                System.out.println("   -) Jugador " + k + ": " + v + " Puntos.");
            }
        });

    }
}