
package Model;

import com.LibConexion.Conexion;
import com.LibConexion.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Logica {
    
    Connection connection = Conexion.getConnection();   
    
    static Query query = new Query();
            
    private final ArrayList<Integer>numsUsados = new ArrayList<>();
    
    static String[] pregunta = new String[6];
    static int count = 0;
    static int numPreguntas = 0;
    
    /**
     * Método que genera un numero aleatorio nuevo sin repetirse
     * @param bound
     * @return numero aleatorio
     */
    public int numero_random(int bound){
        
        int numRandom;    
         
        // Una vez no queden preguntas retorna -1
        if (count<bound){
            count++;
            System.out.println(count);
        }else{
            return -1;
        }
        
        // Genera numeros aleatorios hasta no haya ninguno que coincida con los del array
        do{
                        
            // Numero aleatorio entre -1 y @bound
            numRandom = ThreadLocalRandom.current().nextInt(bound);
            
        } while (numsUsados.contains(numRandom));
        
        // Añadir el número al ArrayList para que no se use más
        numsUsados.add(numRandom);
        
        return numRandom;
        
    }

    /**
     * Método que dado una conexion y una tabla genera una fila aleatoria de esa tabla de la BBDD
     * @param connection
     * @param tabla
     * @return array con una fila
     */
    public String[] preguntaRandom(Connection connection, String tabla){
        
        // Selecciona todas las filas de una tabla
        String[][] preguntas = query.SelectAllFromTable(connection, tabla);
        
        // Numero de preguntas
        numPreguntas = preguntas.length;
        System.out.println(numPreguntas);
        
        // Selecciona una fila aleatoriamente
        int ID = numero_random(preguntas.length);
        
        if (ID == -1){
            pregunta[0] = "";
            return pregunta;
        }
        
        // Guarda esa fila en un array
        pregunta = preguntas[ID];
                
        return pregunta;
        
    }
    
    public int getNumPreguntas(){   
        return numPreguntas;
        
    }
        
}