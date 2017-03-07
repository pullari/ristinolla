/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ristinolla;

import java.util.ArrayList;

/**
 *
 * @author samrouvi
 */
public class Ristinolla {

    /**
     * @param args the command line arguments
     */
    public static boolean gameOver = false;
    
    public static void main(String[] args) {
        
        int[][] kentta = new int[3][3];
        
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                kentta[i][j] = 0;
            }
        }
        
        kentta[0][0] = -1;
        kentta[0][2] = -1;
        kentta[2][1] = -1;
        
        kentta[0][1] = 1;
        kentta[1][0] = 1;
        kentta[1][2] = 1;
        
        while(!gameOver)
        {
            ArrayList<int[][]> lapsety = lapset(kentta, 1);
            for(int[][] tila : lapsety)
            {
                if(minAbValue(tila) == -1){    
                    kentta = tila; 
                    break;
                }else{
                    for(int i = 0; i < 3; i++)
                    {
                        if(kentta[0][i] == 0){
                            kentta[0][i] = -1;
                            break;
                        }
                        if(kentta[1][i] == 0){
                            kentta[1][i] = -1;
                            break;
                        }
                        if(kentta[2][i] == 0){
                            kentta[2][i] = -1;
                            break;
                        }
                    } 
                }
            }
            
            ArrayList<int[][]> lapsetx = lapset(kentta, 1);
            for(int[][] tila : lapsetx)
            {
                if(abValue(tila) == 1){    
                    kentta = tila; 
                    break;
                }else{
                    for(int i = 0; i < 3; i++)
                    {
                        if(kentta[0][i] == 0){
                            kentta[0][i] = 1;
                            break;
                        }
                        if(kentta[1][i] == 0){
                            kentta[1][i] = 1;
                            break;
                        }
                        if(kentta[2][i] == 0){
                            kentta[2][i] = 1;
                            break;
                        }
                    } 
                }
            }
            printTila(kentta);
        }
        
    }
    
    public static void printTila(int[][] tila){
    
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                System.out.print(" | " + tila[i][j] + " | ");
            }
            System.out.println("– – –");
        }
    }
    
    
    public static int abValue(int[][] tila)
    {
        return maxValue(tila, -1, +1);
    }
    
    public static int minAbValue(int[][] tila)
    {
        return minValue(tila, -1, +1);
    }
    
    public static int tarkastaLoppuTila(int[][] kentta)
    {
        for(int i = 0; i < 3; i++)
        {
            if(kentta[i][0] != 0 && kentta[i][0] == kentta[i][1] && kentta[i][0] == kentta[i][2])
            {
                gameOver = true;
                return kentta[i][0];
            }
        }
        
        for(int i = 0; i < 3; i++)
        {
            if(kentta[0][i] != 0 && kentta[0][i] == kentta[1][i] && kentta[0][i] == kentta[2][i])
            {
                gameOver = true;
                return kentta[0][i];
            }
        }

        if(kentta[0][0] != 0 && kentta[0][1] == kentta[1][1] && kentta[0][1] == kentta[2][2])
        {
            gameOver = true;
            return kentta[0][0];
        }
        
        if(kentta[0][0] != 0 && kentta[0][1] == kentta[1][1] && kentta[0][1] == kentta[2][2])
        {
            gameOver = true;
            return kentta[0][0];
        }

        return 0;
    }
    
    public static ArrayList<int[][]> lapset(int[][] solmu, int vuoro)
    {
        ArrayList<int[][]> palauta = new ArrayList<>();
    
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3;j++)
            {
                if(solmu[i][j] == 0)
                {
                    int[][] add = solmu;
                    add[i][j] = vuoro;
                    palauta.add(add);
                }
            }
        }
        return palauta;
    }
    
    public static int maxValue(int[][] solmu, int alpha, int beta)
    {
        int test = tarkastaLoppuTila(solmu);
        
        if(test != 0)return test;
        int v = Integer.MIN_VALUE;
        
        ArrayList<int[][]> lapset = lapset(solmu, 1);
        
        for(int[][] tila : lapset)
        {
            v = Math.max(v, minValue(tila, alpha, beta));
            if(v >= beta) return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }
    
    public static int minValue(int[][] solmu, int alpha, int beta) {
        int test = tarkastaLoppuTila(solmu);
        
        if(test != 0)return test;
        int v = Integer.MAX_VALUE;
        
        ArrayList<int[][]> lapset = lapset(solmu, -1);
        
        for(int[][] tila : lapset)
        {
            v = Math.min(v, maxValue(tila, alpha, beta));
            if(v <= alpha) return v;
            beta = Math.max(beta, v);
        }
        return v;
    }
    
    
 /*   ALPHA-BETA-ARVO(Solmu):
    return(MAX-ARVO(Solmu,-1,+1))
    MAX-ARVO(Solmu,alpha,beta):
    if LOPPUTILA(Solmu) return(ARVO(Solmu))
    v=-Inf
    for each Lapsi in LAPSET(Solmu,’X’)
    v=MAX(v,MIN-ARVO(Lapsi,alpha,beta))
    if v>=beta return(v)
    alpha=MAX(alpha,v)
    return(v)
    MIN-ARVO(Solmu,alpha,beta):
    if LOPPUTILA(Solmu) return(ARVO(Solmu))
    v=+Inf
    for each Lapsi in LAPSET(Solmu,’O’)
    v=MIN(v,MAX-ARVO(Lapsi,alpha,beta))
    if v<=alpha return(v)
    beta=MIN(beta,v)
    return(v)
    missä LAPSET(Solmu,’X’) ja LAPSET(Solmu,’O’) palauttavat tilanteessa Solmu kunkin
    pelaajan vuorolla mahdolliset seuraavat pelitilanteet. Testaa algoritmia ratkaisemalla tehtävä 3.
 */
}


