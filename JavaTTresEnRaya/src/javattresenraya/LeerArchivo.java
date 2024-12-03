package javattresenraya;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Alexander Sotelo
 */
public class LeerArchivo {
    private String ruta = "";
    private String archivo = "";

    public LeerArchivo() {
    
    }
    
    public LeerArchivo(String ruta, String archivo){
        this.archivo = archivo;
        this.ruta = ruta;
    }
    
    public void leer_archivo(List<Integer> lista){
        try{
            BufferedReader lector = new BufferedReader(new FileReader(ruta + archivo));
            String linea = "";
            while((linea = lector.readLine()) != null){
                String bloques[] = linea.split(",");
                if(bloques.length == 2){
                    int nX = Integer.parseInt(bloques[0]);
                    lista.add(nX);
                    int nO = Integer.parseInt(bloques[1]);
                    lista.add(nO);
                }
            }
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
