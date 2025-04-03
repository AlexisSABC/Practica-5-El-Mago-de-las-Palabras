package homework;

import java.util.HashSet;
import java.util.Random;

public class SetdeLetras {
    //Guardar set de letras
    HashSet<String> letterSet;

    //Inicializar HasSet
    public SetdeLetras(){
        letterSet = new HashSet<>();
    }

    //Generar y devolver Set de Letras
    public HashSet<String> generateSet(int mount){
        //Inicializar elementos
        String alfabet = "abcdefghijklmnñopqrstuvwxyz";
        int letterIndex = 0;
        String letter;
        Random generator = new Random();

        //Controlar 10 letras
        while(letterSet.size() != mount){
            letterIndex = generator.nextInt(alfabet.length());
            letter = String.valueOf(alfabet.charAt(letterIndex));
            letterSet.add(letter);
        }

        return letterSet;
    }
}
