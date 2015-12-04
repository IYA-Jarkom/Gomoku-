package algorithm;



import java.awt.Point;
import java.io.Serializable;
import java.util.Arrays;

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
        if ((p.getX()-i) >= 0) {
            while (value[(int) p.getX() - i][(int) p.getY()] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if ((p.getX()-i) < 0) {
                    break;
                }
            }
            i = 1;
        }
        
        if ((p.getX()+i) < 20) {
            while (value[(int) p.getX() + i][(int) p.getY()] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if ((p.getX()+i) >= 20) {
                    break;
                }
            }
        }
        if (sum >= 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        //CHECK VERTICAL
        
        sum=1;
        i=1;
        if ((p.getY()-i) >= 0) {
            while (value[(int) p.getX()][(int) p.getY()-i] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if ((p.getY()-i) < 0) {
                    break;
                }
            }
            i = 1;
        }
        
        if ((p.getY()+i) < 20) {
            while (value[(int) p.getX()][(int) p.getY()+i] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if ((p.getY()+i) >= 20) {
                    break;
                }
            }
        }
        if (sum >= 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        //CHECK DIAGONAL KIRI
        
        sum=1;
        i=1;
        if (((p.getX()-i) >= 0) && ((p.getY()-i) >= 0)) {
            while (value[(int) p.getX()-i][(int) p.getY()-i] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if (((p.getX()-i) < 0) || ((p.getY()-i) < 0)) {
                    break;
                }
            }
            i = 1;
        }
        
        if (((p.getX()+i) < 20) && ((p.getY()+i) < 20)) {
            while (value[(int) p.getX()+i][(int) p.getY()+i] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if (((p.getX()+i) >= 20) || ((p.getY()+i) >= 20)) {
                    break;
                }
            }
        }
        if (sum >= 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        //CHECK DIAGONAL KANAN
        
        sum=1;
        i=1;
        if (((p.getX()-i) >= 0) && ((p.getY()+i) < 20)) {
            while (value[(int) p.getX()-i][(int) p.getY()+i] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if (((p.getX()-i) < 0) || ((p.getY()+i) >= 20)) {
                    break;
                }
            }
            i = 1;
        }
        
        if (((p.getX()+i) < 20) && ((p.getY()-i) >= 0)) {
            while (value[(int) p.getX()+i][(int) p.getY()-i] == value[(int) p.getX()][(int) p.getY()]) {
                sum++;
                i++;
                if (((p.getX()+i) >= 20) || ((p.getY()-i) < 0)) {
                    break;
                }
            }
        }
        if (sum >= 5) {
            return value[(int) p.getX()][(int) p.getY()];
        }
        
        return -1;
    }

    public void display() {
        System.out.println();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(value[i][j]+"\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}