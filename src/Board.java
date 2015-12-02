import java.awt.Point;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yoga
 */
public class Board implements Serializable{

    public int[][] value;

    public Board() {
        value = new int[20][20];
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

    public int[][] getVal() {
        return value;
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
