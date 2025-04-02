package homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BancodePalabras {
    //Guardar todo el banco
    HashMap<String, Integer> bank;

    //Generar Banco de palabras
    public BancodePalabras() {
        bank = new HashMap<>();
        generateBank();
    }

    //Proceso de creacion de banco
    private void generateBank(){
        String word; //Almacenar palabra temporal
        int wordPoints; //Calcula los puntos de las palabras

        try (BufferedReader fileData = new BufferedReader(new FileReader("src/homework/Banco_de_Palabras.txt"))) {
            while ((word = fileData.readLine()) != null) {
                //Reiniciar acumulador de puntos
                wordPoints = 0;

                //Convertir palabra a array
                String[] wordArray = word.split("");

                //Generar los puntos de la palabra
                for (int i = 0; i < wordArray.length; i++) {
                    if(wordArray[i].equals("a") || wordArray[i].equals("e") || wordArray[i].equals("i") || wordArray[i].equals("o") || wordArray[i].equals("u")){
                        wordPoints+= 5;
                    }else{
                        wordPoints+= 3;
                    }
                }

                //Insertar palabras (Clave) y puntaje de palabras
                bank.put(word.toLowerCase(), wordPoints);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo");
        }
    }

    //Regresar HashMap de Banco de Palabras
    public HashMap<String, Integer> getBank() {
        return bank;
    }
}
