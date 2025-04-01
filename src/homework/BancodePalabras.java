package homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BancodePalabras {
    //Guardar todo el banco
    HashMap<Integer, String> bank = new HashMap<>();

    //Inicializar banco de palabras
    public BancodePalabras(){
        saveAllBank();

        //Imprimir
        bank.forEach((clave, valor) -> System.out.println("Clave: " + clave + "; Valor: " + valor));
    }

    //Guardar todo el banco de palabras
    private void saveAllBank(){
        try(BufferedReader reader = new BufferedReader(new FileReader("src/homework/Banco_de_Palabras.csv"))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(","); // Divide la línea en columnas
                if (datos.length >= 2) {
                    try {
                        int clave = Integer.parseInt(datos[0].trim()); // Convertir la clave numérica
                        String valor = datos[1].trim(); // Obtener el valor como String
                        bank.put(clave, valor); // Insertar en el HashMap
                    } catch (NumberFormatException e) {
                        System.err.println("Clave no válida: " + datos[0]);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
