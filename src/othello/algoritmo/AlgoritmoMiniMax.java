/*
 */
package othello.algoritmo;

import othello.Utils.Casilla;
import othello.Utils.Heuristica;
import othello.Utils.Tablero;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static othello.Utils.Casilla.FICHA_BLANCA;

/**
 *
 * @author gusamasan
 */
public class AlgoritmoMiniMax extends Algoritmo {
// ----------------------------------------------------------------------

// ----------------------------------------------------------------------
    /**
     * Constructores *************************************************
     */
    private int playerColor;

    public AlgoritmoMiniMax() {

    }

    /**
     * ****************************************************************
     */
    @Override
    public Tablero obtenerNuevaConfiguracionTablero(Tablero tablero, short turno) {

        System.out.println("analizando siguiente jugada con MINIMAX");
        this.playerColor = turno;
        Tablero tableroJugada = tablero.copiarTablero();
        try {
            miniMax(tableroJugada, this.getProfundidad(), playerColor);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (tableroJugada);
    }

    /**
     *
     * Éste es el método que tenemos que implementar.
     *
     * Algoritmo AlfaBeta para determinar cuál es el siguiente mejor movimiento
     *
     * @param tablero Configuración actual del tablero
     * @param prof Profundidad de búsqueda
     * @param jugadorActual Nos indica a qué jugador (FICHA_BLANCA ó
     * FICHA_NEGRA) le toca
     * @return
     */
    public int miniMax(Tablero tablero, int prof, int jugadorActual) {

        //Nos pasan un tablero y el turno.
        //Calculamos la mejor jugada para nuestro turno
        ArrayList<Casilla> casillas = tablero.generarMovimiento(jugadorActual); //Posibles casillas donde poner una ficha        
        ArrayList<Integer> valor = new ArrayList<Integer>(); //Valor del tablero resultado de poner la ficha de la posicion i

        Tablero copia = tablero.copiarTablero();

        //Si no es nodo hoja o maxima profundidad, calculamos siguiente nivel
        if (prof > 0) {
            for (int i = 0; i < casillas.size(); i++) {

                if (jugadorActual == FICHA_BLANCA) {
                    casillas.get(i).asignarFichaBlanca();
                } else {
                    casillas.get(i).asignarFichaNegra();
                }

                copia.ponerFicha(casillas.get(i));

                valor.add(miniMax(copia, prof-1, (-1)*jugadorActual));

                copia = tablero.copiarTablero();
                
            }
        }

        //Si hemos vuelto a la llamada inicial
        if (prof == 5) {
            
            Casilla mejorCasilla = casillas.get(0).copiarCasilla();
            int max = valor.get(0);
           
            if (jugadorActual == FICHA_BLANCA) {
                for (int i = 0; i < valor.size(); i++) {
                    if (valor.get(i) > max) {
                        max = valor.get(i);
                        mejorCasilla = casillas.get(i).copiarCasilla();
                    }
                }
            } else {
                for (int i = 0; i < valor.size(); i++) {
                    if (valor.get(i) < max) {
                        max = valor.get(i);
                        mejorCasilla = casillas.get(i).copiarCasilla();
                    }
                }
            }

            tablero.ponerFicha(mejorCasilla);
        }
        
        return Heuristica.h2(tablero,playerColor);

    }
}