package javattresenraya;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Alexander Sotelo
 */
public class GuardarArchivo {
    private String ruta = "";
    private String archivo = "";

    public GuardarArchivo() {
        
    }
    
    public GuardarArchivo(String ruta, String archivo){
        this.ruta = ruta;
        this.archivo = archivo;
    }
    
    public void GuardarArchivo(int nX, int nO){
        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta + archivo,false)); /*El "true" nos indica que 
            escribira a partir de la ultima liena existente en el archivo, si fuera "false" el programa reescribiria todo el archivo*/
            
            //Escrito en un archivo las variables que recive el metodo
            escritor.write(String.valueOf(nX) + "," + String.valueOf(nO));
            
            //Cerramos la memoria en buffer
            escritor.close();
        }catch(IOException e){
            //Le imprimo el error al usuario (SI ES QUE HAY UNO)
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
