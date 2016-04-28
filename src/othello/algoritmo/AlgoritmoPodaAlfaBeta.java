/*
 */

package othello.algoritmo;

import othello.Utils.Casilla;
import othello.Utils.Heuristica;
import othello.Utils.Tablero;
import java.util.ArrayList;

/**
 *
 * @author gusamasan
 */
public class AlgoritmoPodaAlfaBeta extends Algoritmo{
// ----------------------------------------------------------------------

// ----------------------------------------------------------------------

    /** Constructores **************************************************/
    private int playerColor;
    public AlgoritmoPodaAlfaBeta(){

    }
    /*******************************************************************/
    

    @Override
    public Tablero obtenerNuevaConfiguracionTablero( Tablero tablero, short turno ){

        System.out.println( "analizando siguiente jugada con ALFABETA" );
        this.playerColor=turno;
        Tablero tableroJugada=tablero.copiarTablero();
         try{
             int beta = Integer.MAX_VALUE;
             int alfa = Integer.MIN_VALUE;
             alfaBeta(tableroJugada, this.getProfundidad(), playerColor, alfa, beta);
            Thread.sleep( 1000 );
        }
        catch( Exception e ){
            e.printStackTrace();
        }

        return( tableroJugada );
    }

     /**
     *
     * Éste es el método que tenemos que implementar.
     * 
     * Algoritmo AlfaBeta para determinar cuál es el siguiente mejor movimiento
     * 
     * @param tablero
     * Configuración actual del tablero
     * @param prof
     * Profundidad de búsqueda
     * @param jugadorActual
     * Nos indica a qué jugador (FICHA_BLANCA ó FICHA_NEGRA) le toca
     * @param alfa
     * @param beta
     * Parámetros alfa y beta del algoritmo
     * @return
     */
    public int alfaBeta(Tablero tablero, int prof, int jugadorActual, int alfa, int beta)
    {        
        //nodo terminal o profundidad 0 o ese jugador no puede hacer movimientos
	if(tablero.EsFinalDeJuego() || prof == 0 || !tablero.PuedeJugar(jugadorActual)){
            return Heuristica.h2(tablero, playerColor);
        }
        //Obtener todos los movimientos posibles y crear casilla auxiliar
        //para almacenar el maximo en nodos max
        ArrayList<Casilla> posiblesMovimientos = tablero.generarMovimiento(jugadorActual);
        Casilla max = null;
        Casilla casilla = null;
        //recorrer todos los movimientos
        for(int i = 0; i < posiblesMovimientos.size(); i++){
            casilla = posiblesMovimientos.get(i);
            Tablero copiaTablero = tablero.copiarTablero();
            //Indicamos el color de la ficha a asignar
            if(jugadorActual == 1){
                casilla.asignarFichaBlanca();
            }else{
                casilla.asignarFichaNegra();
            }
            tablero.ponerFicha(casilla);
            tablero.imprimirTablero();
            
            int valorOponente = alfaBeta(copiaTablero,prof -1,-jugadorActual,alfa,beta);
            //Nodo max
            if(jugadorActual == this.playerColor){
                //si el valor actual mejora el valor de alfa actualizamos 
                //la casilla y el valor de alfa
                if(valorOponente > alfa ){
                    max =casilla;
                    alfa =valorOponente;
                }
                if(beta >= alfa){
                    return alfa;
                }
            }else{//Nodo min
                if(valorOponente < beta ){
                    //si el valor actual es inferior al valor de beta
                    //actualizamos beta y la casilla actual
                    max =casilla;
                    beta =valorOponente;
                }
                if(beta >= alfa){
                    return beta;
                }
            }
        }
        // Retornar el valor para el mejor movimiento
        if (jugadorActual == this.playerColor)
            return alfa;

        else
            return beta;
    }
}
