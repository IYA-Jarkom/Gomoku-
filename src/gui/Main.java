/*
 * Test1
 * Each line should be prefixed with  * 
 test2
 */
package gui;

import gui.element.*;
import gui.page.*;
import gui.window.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 * 
 * Kelas utama program dengan Graphical User Interface
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Main extends JFrame{
    // Atribut
    private JLayeredPane layers;
    private HomePage homePage;
    private MenuPage menuPage;
    private RoomPage roomPage;
    private EmptyWindow emptyWindow;
    private String nickname;
    private int character;
    
    // Konstruktor
    public Main() {
        // Inisiasi
        setTitle("WAL");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        layers = new JLayeredPane();
        layers.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        layers.setOpaque(false);
        layers.setLayout(null);
        homeController();
        setVisible(true);
    }
    
    // Method
    public void homeController() {
        layers = new JLayeredPane();
        homePage = new HomePage();
        layers.add(homePage, new Integer(0));
        add(layers);
        setContentPane(layers);
        // Mendeteksi tombol tombol ok pada home ditekan
        homePage.getYesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == homePage.getYesButton()) {
                    nickname = homePage.getNickname();
                    if (nickname.isEmpty()) {
                        emptyWindow = new EmptyWindow("Please insert a valid nickname");
                        homePage.setNickname(nickname);
                        layers.add(emptyWindow, new Integer(1));
                        setContentPane(layers);
                        backFromEmptyWindow(emptyWindow);
                    } else {
                        menuController();
                        invalidate();
                        validate();
                    }
                }
            }
        });
    }
    public void menuController() {
        layers = new JLayeredPane();
        menuPage = new MenuPage(nickname);
        layers.add(menuPage, new Integer(0));
        setContentPane(layers);
        // Mendeteksi tombol back ditekan
        menuPage.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == menuPage.getBackButton()) {
                    homeController();
                    invalidate();
                    validate();
                }
            }
        });
    }
    
    // Prosedur kecil
    public void backFromEmptyWindow(EmptyWindow messagePage) {
        messagePage.getYesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == messagePage.getYesButton()) {
                    layers.remove(layers.getIndexOf(messagePage));
                    setContentPane(layers);
                    invalidate();
                    validate();
                }
            }
        });
    }
    
    // Program utama
    public static void main (String[] args) {
        Main mainFrame = new Main();
    }
}
