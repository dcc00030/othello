/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package othello.Utils;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Heuristica {
    
    public static int h2(Tablero tablero,int playerColor)
    {
        //da un valor a cada una de las posiciones del tablero
        //dandole mayor valoracion a las esquinas del tablero
        int[][] matriz = {{6,-4,5,3,3,5,-4,6},{5,-3,4,2,2,4,-3,5},{4,-2,3,1,1,3,-2,4},{3,-1,2,0,0,2,-1,3},{3,-1,2,0,0,2,-1,3},{4,-2,3,1,1,3,-2,4},{5,-3,4,2,2,4,-3,5},{6,-4,5,3,3,5,-4,6}};
        //usamos el mismo metodo anterior para calcular los puntos que tenemos
        //contando la posicion de las fichas
        int puntuacion = Puntos(playerColor,tablero) -Puntos(-playerColor,tablero);
        ArrayList<Casilla> posiblesMovimientos = tablero.generarMovimiento(playerColor);
        puntuacion = posiblesMovimientos.size();
        //y aqui usamos la puntuacion de los posibles movimientos junto con el valor
        //introducido en la matriz creada por nosotros
        Casilla [][]matrizAux = tablero.getMatrizTablero();
        for(int  i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(matrizAux[i][j].obtenerColorFicha()== playerColor){
                    puntuacion= puntuacion + matriz[i][j];
                }
            }
        }
        return puntuacion;
    }
/*

    //Una heuristica posible a usar
    public static int h2(Tablero tablero,int playerColor)
    {
        int score = Puntos(playerColor, tablero) - Puntos(-playerColor, tablero);

        // If the game is over
        if (tablero.EsFinalDeJuego())
        {
            // if player has won
            if (score > 0)
                return 100;
            // if player has lost (or tied)
            else
                return -100;
        }

        // if game isn't over, return relative advatage
        return score;
    }
*/
    public static int Puntos(int playerColor, Tablero tablero)
    {
        int points = 0;

        for (int x = 0; x < Tablero.CANTIDAD_FILAS_DEFECTO; x++)
            for (int y = 0; y < Tablero.CANTIDAD_COLUMNAS_DEFECTO; y++)
                if (tablero.getMatrizTablero()[x][y].obtenerColorFicha() == playerColor)
                    points++;

        return points;
    }

    


}
