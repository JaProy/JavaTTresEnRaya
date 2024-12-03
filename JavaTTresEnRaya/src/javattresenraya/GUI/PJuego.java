package javattresenraya.GUI;

import javattresenraya.GuardarArchivo;
import javattresenraya.LeerArchivo;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Alexander Sotelo
 */
public class PJuego extends javax.swing.JFrame {

    public PJuego() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        rC();
        
        asignarCM();
        leerDatos();
    }
    
    //Declaro variables que utilizare
    private boolean p = true; //Variable para sqaber si el juego puede continuar
    private int cC = 1; //Contador que lleva la guia del juego, por defecto es 1 = X, 2 = O
    private int[][] mJ = new int[3][3]; //Matriz donde llevare registro del juego
    private int nDJ = 0; //Variable donde almazeno el numero de matriz que hizo el gane
    private javax.swing.JLabel[][] casillasM = new JLabel[3][3]; //Variable donde almazeno referencia a los JLabel
    private int sX = 0, sO = 0; //Declaro variables donde almacenare el puntaje de cada jugador
    private String ruta = "C:/Alex/Aer/", archivo = "TER1.lqn"; //Declaro variables para almazenar y obtener datos de un archivo
    private List<Integer> lista = new ArrayList<>(); //ArrayList para almazenar los valores obtenidos de la clase LeerArchivo
    
    //Declaro un objeto de la clase guardar archivo que utilizare despues
    GuardarArchivo guardarA = new GuardarArchivo(ruta,archivo);
    
    //Declaro un objeto de la clase leer archivo que utilizare despues
    LeerArchivo leerA = new LeerArchivo(ruta,archivo);
    
    //Metodo leerDatos, se encarga de leer el lqn y aplicar sus datos
    private void leerDatos(){
        //Leo los datos del archivo y los incluyo en la lista
        leerA.leer_archivo(lista);
        
        //Compruebo si el tamaño de la lista es mayor a 0 para poder seguir con el codigo
        if(lista.size() > 0){
            //Paso los datos obtenidos de la lista y los ajusto en las variables que controlan el score
            sX = lista.get(0);
            sO = lista.get(1);

            //Actualizo los JLabel que muestran el Score
            this.ScoreX.setText(String.valueOf(sX));
            this.ScoreO.setText(String.valueOf(sO));
        }
        
    }
    
    //Metodo para asignar los valores al vector casillasM
    private void asignarCM(){
        casillasM[0][0] = this.C0;
        casillasM[0][1] = this.C1;
        casillasM[0][2] = this.C2;
        casillasM[1][0] = this.C3;
        casillasM[1][1] = this.C4;
        casillasM[1][2] = this.C5;
        casillasM[2][0] = this.C6;
        casillasM[2][1] = this.C7;
        casillasM[2][2] = this.C8;
    }
    
    //Declaro las matrizes de los posibles juegos...
    //Posibles juegos en 'X'
    private static int[][] mj1 = {
        {1,1,1},
        {0,0,0},
        {0,0,0}
    };
    private static int[][] mj2 = {
        {0,0,0},
        {1,1,1},
        {0,0,0}
    };
    private static int[][] mj3 = {
        {0,0,0},
        {0,0,0},
        {1,1,1}
    };
    private static int[][] mj4 = {
        {1,0,0},
        {1,0,0},
        {1,0,0}
    };
    private static int[][] mj5 = {
        {0,1,0},
        {0,1,0},
        {0,1,0}
    };
    private static int[][] mj6 = {
        {0,0,1},
        {0,0,1},
        {0,0,1}
    };
    private static int[][] mj7 = {
        {1,0,0},
        {0,1,0},
        {0,0,1}
    };
    private static int[][] mj8 = {
        {0,0,1},
        {0,1,0},
        {1,0,0}
    };
    
    //Lista de todas las combinaciones ganadoras
    int[][][] combinacionesG = {mj1, mj2, mj3, mj4, mj5, mj6, mj7, mj8};
    
    
    
    //Funcion para borrar todos los textos de los leabel-texto dentro de la cuadricula
    private void rC(){
        this.C0.setText("");
        this.C1.setText("");
        this.C2.setText("");
        this.C3.setText("");
        this.C4.setText("");
        this.C5.setText("");
        this.C6.setText("");
        this.C7.setText("");
        this.C8.setText("");
        this.ScoreX.setText(String.valueOf(sX));
        this.ScoreO.setText(String.valueOf(sO));
    }
    
    private String updateCass(int x, int y) throws InterruptedException{
        String s = "", t = "";
        //Compruebo si existen combinaciones dispinibles
        if(p){
            //El algoritmo para saber si se llenaron las casillas VA AQUI!
            sM(x,y,cC);
            if(cC == 1){
               s = "X";
               t = "O";
               cC = 2;
            }else{
                if(cC == 2){
                    s = "O";
                    t = "X";
                    cC = 1;
                }
            }
            this.TurnOf.setText(t);
            p = cD();
        }
        
        switch(vV()){
            case ""->{
                //Creo un nuevo hilo donde ejecuto la tarea de comprobar si hay jugadas disponibles
                new Thread(() -> {
                    try {
                        jD();
                    } catch (InterruptedException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                }).start();
            }
            case "O"->{
                //Creo un nuevo hilo donde ejecuto la tarea de victoria
                new Thread(() -> {
                    try {
                        sO++;
                        this.ScoreO.setText(String.valueOf(sO));
                        victoria();
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                }).start();
            }
            case "X"->{
                //Creo un nuevo hilo donde ejecuto la tarea de victoria
                new Thread(() -> {
                    try {
                        sX++;
                        this.ScoreX.setText(String.valueOf(sX));
                        victoria();
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                }).start();
            }
            default->{
                System.out.println("Ocurrio un ERROR!");
            }
        }
        
        return s;
    }
    
    private void victoria() throws InterruptedException{
        p = false;
        int[][] matrizG = combinacionesG[nDJ];
        
        //this.C0.setBackground(Color.green);
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(matrizG[i][j] == 1){
                    this.casillasM[i][j].setBackground(Color.green);
                }
            }
        }
        
        guardarA.GuardarArchivo(sX, sO);
        
        Thread.sleep(1500);
        
        this.dispose();
            
        PMenu1 menuS = new PMenu1("Ha ganado el jugador: " + vV(),"Datos guardados CORRECTAMENTE!");
        menuS.setVisible(true);
    }
    
    private void jD() throws InterruptedException{
        if(!p){
            //System.out.println("NO HAY JUEGOS DISPONIBLES!!!");
            
            Thread.sleep(1000);
            
            this.dispose();
            
            PMenu1 menuS = new PMenu1("Ningun jugador ha ganado!!!","");
            menuS.setVisible(true);
        }
    }
    
    private void sM(int x, int y, int cC){
        mJ[x][y] = cC; //Ajustamos el valor dado en la coordenada dada
        //Imprimimos la matriz (modo de prueba)
        /*for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(mJ[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");*/
    }
    
    //Funcion para comprobar si todavia hay juegos disponibles
    private boolean cD(){
        int m = 0;
        boolean p = true; 
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(mJ[i][j] == 0){
                    m = 1;
                }
            }
            //System.out.println(m);
        }
        if(m == 0){
            p = false;
        }else{
            if(m == 1){
                p = true;
            }
        }
        return p;
    }
    
    
    
    private String vV() {
        //Declaro variables que nesesitare
        int c1 = 0,c2 = 0;
        
        //Compruebo si hay una victoria de "X" (1 en mJ)
        for (int[][] combinacion : combinacionesG) {
            if (comprobarCombinacion(mJ, combinacion, 1)) {
                //Almazeno el numero de matriz que hizo la cimbinacion ganadora
                nDJ = c1;
                return "X"; //Si el jugador x ha ganado, devuelvo una X
            }
            c1++;
        }

        // Comprobar si hay una victoria de "O" (2 en mJ)
        for (int[][] combinacion : combinacionesG) {
            if (comprobarCombinacion(mJ, combinacion, 2)) {
                //Almazeno el numero de matriz que hizo la cimbinacion ganadora
                nDJ = c2;
                return "O"; //Si el jugador o ha ganado, devuelvo una O
            }
            c2++;
        }

        return ""; //No hay ninguna victoria
    }

    private boolean comprobarCombinacion(int[][] mJ, int[][] combinacion, int valor) {
        //Algoritmo para recorrer la matriz
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (combinacion[i][j] == 1 && mJ[i][j] != valor) {
                    return false; //Si una combinacion no coincide, entonces devuelvo un FALSE
                }
            }
        }
        return true; //Si todas las combinaciones coinciden, devuelto un TRUE
    }

    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Base = new javax.swing.JPanel();
        Section_Score = new javax.swing.JPanel();
        Score = new javax.swing.JLabel();
        TurnOf = new javax.swing.JLabel();
        TurnTXT = new javax.swing.JLabel();
        ScoreOTXT = new javax.swing.JLabel();
        ScoreXTXT = new javax.swing.JLabel();
        ScoreO = new javax.swing.JLabel();
        ScoreX = new javax.swing.JLabel();
        Section_Game = new javax.swing.JPanel();
        Game_B = new javax.swing.JPanel();
        C2 = new javax.swing.JLabel();
        C1 = new javax.swing.JLabel();
        C0 = new javax.swing.JLabel();
        C5 = new javax.swing.JLabel();
        C4 = new javax.swing.JLabel();
        C3 = new javax.swing.JLabel();
        C8 = new javax.swing.JLabel();
        C7 = new javax.swing.JLabel();
        C6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Score.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Score.setText("Score:");

        TurnOf.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        TurnOf.setText("X");

        TurnTXT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        TurnTXT.setText("Turno de:");

        ScoreOTXT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ScoreOTXT.setText("O =");

        ScoreXTXT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ScoreXTXT.setText("X =");

        ScoreO.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ScoreO.setText("000");

        ScoreX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ScoreX.setText("000");

        javax.swing.GroupLayout Section_ScoreLayout = new javax.swing.GroupLayout(Section_Score);
        Section_Score.setLayout(Section_ScoreLayout);
        Section_ScoreLayout.setHorizontalGroup(
            Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Section_ScoreLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Score)
                    .addGroup(Section_ScoreLayout.createSequentialGroup()
                        .addGroup(Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ScoreXTXT)
                            .addComponent(ScoreOTXT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ScoreO)
                            .addComponent(ScoreX))))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Section_ScoreLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TurnOf)
                .addGap(24, 24, 24))
            .addGroup(Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Section_ScoreLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(TurnTXT)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        Section_ScoreLayout.setVerticalGroup(
            Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Section_ScoreLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(TurnOf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Score)
                .addGap(20, 20, 20)
                .addGroup(Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ScoreXTXT)
                    .addComponent(ScoreX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ScoreOTXT)
                    .addComponent(ScoreO))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Section_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Section_ScoreLayout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(TurnTXT)
                    .addContainerGap(224, Short.MAX_VALUE)))
        );

        Game_B.setBackground(new java.awt.Color(0, 0, 51));

        C2.setBackground(new java.awt.Color(255, 255, 255));
        C2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C2.setForeground(new java.awt.Color(0, 0, 0));
        C2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C2.setText("X");
        C2.setOpaque(true);
        C2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C2MouseClicked(evt);
            }
        });

        C1.setBackground(new java.awt.Color(255, 255, 255));
        C1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C1.setForeground(new java.awt.Color(0, 0, 0));
        C1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C1.setText("X");
        C1.setOpaque(true);
        C1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C1MouseClicked(evt);
            }
        });

        C0.setBackground(new java.awt.Color(255, 255, 255));
        C0.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C0.setForeground(new java.awt.Color(0, 0, 0));
        C0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C0.setText("X");
        C0.setOpaque(true);
        C0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C0MouseClicked(evt);
            }
        });

        C5.setBackground(new java.awt.Color(255, 255, 255));
        C5.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C5.setForeground(new java.awt.Color(0, 0, 0));
        C5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C5.setText("X");
        C5.setOpaque(true);
        C5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C5MouseClicked(evt);
            }
        });

        C4.setBackground(new java.awt.Color(255, 255, 255));
        C4.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C4.setForeground(new java.awt.Color(0, 0, 0));
        C4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C4.setText("X");
        C4.setOpaque(true);
        C4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C4MouseClicked(evt);
            }
        });

        C3.setBackground(new java.awt.Color(255, 255, 255));
        C3.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C3.setForeground(new java.awt.Color(0, 0, 0));
        C3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C3.setText("X");
        C3.setOpaque(true);
        C3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C3MouseClicked(evt);
            }
        });

        C8.setBackground(new java.awt.Color(255, 255, 255));
        C8.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C8.setForeground(new java.awt.Color(0, 0, 0));
        C8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C8.setText("X");
        C8.setOpaque(true);
        C8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C8MouseClicked(evt);
            }
        });

        C7.setBackground(new java.awt.Color(255, 255, 255));
        C7.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C7.setForeground(new java.awt.Color(0, 0, 0));
        C7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C7.setText("X");
        C7.setOpaque(true);
        C7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C7MouseClicked(evt);
            }
        });

        C6.setBackground(new java.awt.Color(255, 255, 255));
        C6.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        C6.setForeground(new java.awt.Color(0, 0, 0));
        C6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        C6.setText("X");
        C6.setOpaque(true);
        C6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                C6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Game_BLayout = new javax.swing.GroupLayout(Game_B);
        Game_B.setLayout(Game_BLayout);
        Game_BLayout.setHorizontalGroup(
            Game_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Game_BLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Game_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Game_BLayout.createSequentialGroup()
                        .addComponent(C0, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Game_BLayout.createSequentialGroup()
                        .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Game_BLayout.createSequentialGroup()
                        .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Game_BLayout.setVerticalGroup(
            Game_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Game_BLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Game_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C0, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Game_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Game_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(C7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Section_GameLayout = new javax.swing.GroupLayout(Section_Game);
        Section_Game.setLayout(Section_GameLayout);
        Section_GameLayout.setHorizontalGroup(
            Section_GameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Section_GameLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(Game_B, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        Section_GameLayout.setVerticalGroup(
            Section_GameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Section_GameLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(Game_B, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout BaseLayout = new javax.swing.GroupLayout(Base);
        Base.setLayout(BaseLayout);
        BaseLayout.setHorizontalGroup(
            BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BaseLayout.createSequentialGroup()
                .addComponent(Section_Score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Section_Game, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BaseLayout.setVerticalGroup(
            BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Section_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Section_Game, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void C0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C0MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C0.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C0.setText(updateCass(0,0));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C0MouseClicked

    private void C1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C1MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C1.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C1.setText(updateCass(0,1));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C1MouseClicked

    private void C2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C2MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C2.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C2.setText(updateCass(0,2));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C2MouseClicked

    private void C3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C3MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C3.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C3.setText(updateCass(1,0));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C3MouseClicked

    private void C4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C4MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C4.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C4.setText(updateCass(1,1));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C4MouseClicked

    private void C5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C5MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C5.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C5.setText(updateCass(1,2));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C5MouseClicked

    private void C6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C6MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C6.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C6.setText(updateCass(2,0));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C6MouseClicked

    private void C7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C7MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C7.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C7.setText(updateCass(2,1));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C7MouseClicked

    private void C8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_C8MouseClicked
        //Comprobamos si la casilla esta vacia
        if(this.C8.getText().equals("") && p){
            try {
                //Si esta vacia ejecutamos la logica de actualizacion de casilla
                this.C8.setText(updateCass(2,2));
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_C8MouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Base;
    private javax.swing.JLabel C0;
    private javax.swing.JLabel C1;
    private javax.swing.JLabel C2;
    private javax.swing.JLabel C3;
    private javax.swing.JLabel C4;
    private javax.swing.JLabel C5;
    private javax.swing.JLabel C6;
    private javax.swing.JLabel C7;
    private javax.swing.JLabel C8;
    private javax.swing.JPanel Game_B;
    private javax.swing.JLabel Score;
    private javax.swing.JLabel ScoreO;
    private javax.swing.JLabel ScoreOTXT;
    private javax.swing.JLabel ScoreX;
    private javax.swing.JLabel ScoreXTXT;
    private javax.swing.JPanel Section_Game;
    private javax.swing.JPanel Section_Score;
    private javax.swing.JLabel TurnOf;
    private javax.swing.JLabel TurnTXT;
    // End of variables declaration//GEN-END:variables
}
