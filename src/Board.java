

import java.awt.Point;
import java.io.Serializable;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Board implements Serializable{

    public int[][] value;

    public Board() {
        value = new int[20][20];
        for (int i = 0; i < 20; i++) {
            Arrays.fill(value[i], -1);
        }
    }

    public Board(Board B) {
        value = new int[20][20];
        for (int i = 0; i < 20; i++) {
            System.arraycopy(B.value[i], 0, value[i], 0, 20);
        }
    }

    public void setBoard(Board B) {
        for (int i = 0; i < 20; i++) {
            System.arraycopy(B.value[i], 0, value[i], 0, 20);
        }
    }
    
    public void setBoardElement(Point position, int element) {
        value[(int)position.getX()][(int)position.getY()] = element;
    }

    public int[][] getVal() {
        return value;
    }
    
    public int getBoardElement(Point position) {
        return value[(int)position.getX()][(int)position.getY()];
    }

    public int checkWinner(Point p) {
        int sum = 1;
        int i = 1;
        
        //CHECK HORIZONTAL
        while (value[(int) p.getX() - i][(int) p.getY()] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        i = 1;
        while (value[(int) p.getX() + i][(int) p.getY()] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        if (sum == 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        //CHECK VERTICAL
        
        sum=1;
        i=1;
        while (value[(int) p.getX()][(int) p.getY()-i] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        i = 1;
        while (value[(int) p.getX()][(int) p.getY()+i] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        if (sum == 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        //CHECK DIAGONAL KIRI
        
        sum=1;
        i=1;
        while (value[(int) p.getX()-i][(int) p.getY()-i] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        i = 1;
        while (value[(int) p.getX()+i][(int) p.getY()+i] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        if (sum == 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        //CHECK DIAGONAL KANAN
        
        sum=1;
        i=1;
        while (value[(int) p.getX()-i][(int) p.getY()+i] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        i = 1;
        while (value[(int) p.getX()+i][(int) p.getY()-i] == value[(int) p.getX()][(int) p.getY()]) {
            sum++;
            i++;
        }
        if (sum == 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        return 0;
    }

}