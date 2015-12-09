/*
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
package gui;

import gui.element.*;
import gui.page.*;
import gui.window.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Kelas utama program dengan Graphical User Interface
 */
public class Main extends JFrame{
    // Atribut GUI
    private JLayeredPane layers;
    private HomePage homePage;
    private MenuPage menuPage;
    private RoomPage roomPage;
    private NewRoomWindow newRoomWindow;
    private RoleWindow roleWindow;
    private CharacterWindow characterWindow;
    private OtherWinWindow otherWinWindow;
    private PlayerWinWindow playerWinWindow;
    private HostNameWindow hostNameWindow;
    private EmptyWindow emptyWindow;

    // Atribut penggambaran
    private boolean isConnect;
    private boolean isGameStart;
    private String nickname;
    private String roomName;
    private Map roomList;

    // Konstruktor
    public Main() {
        // Inisiasi nilai
        isConnect = false;
        isGameStart = false;
        roomList = new HashMap();

        // Inisiasi frame
        setTitle("WAL");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        layers = new JLayeredPane();
        layers.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        layers.setOpaque(false);
        layers.setLayout(null);
        hostNameHandler();
        setVisible(true);
    }
    
    // Method
    // Menangani tampilan dan aksi pada page Home
    public void homeController() {
        layers = new JLayeredPane();
        homePage = new HomePage();
        layers.add(homePage, new Integer(0));
        setContentPane(layers);
        // Mendeteksi tombol tombol ok pada home ditekan
        homePage.getYesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == homePage.getYesButton()) {
                    nickname = homePage.getNickname();
                    if (nickname.isEmpty()) { /// Dan nama belum ada sebelumnya
                        emptyWindow = new EmptyWindow("Please insert a valid nickname.");
                        layers.add(emptyWindow, new Integer(1));
                        setContentPane(layers);
                        backFromEmptyWindow(emptyWindow);
                    } else {
                        homePage.setNickname(nickname);
                        menuController();
                        invalidate();
                        validate();
                    }
                }
            }
        });
    }

    // Menangani tampilan dan aksi pada page Menu
    public void menuController() {
        layers = new JLayeredPane();
        menuPage = new MenuPage(nickname, roomList);
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
        newRoomHandler();
        //chooseRoomHandler();
    }

    // Menangani tampilan dan aksi pada page Room
    public void roomController() { // Kirim nama room, daftar pengguna dan masternya
        layers = new JLayeredPane();
        roomPage = new RoomPage(roomName);
        layers.add(roomPage, new Integer(0));
        setContentPane(layers);
        // Mendeteksi tombol back ditekan
        roomPage.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roomPage.getBackButton()) {
                    menuController();
                    invalidate();
                    validate();
                }
            }
        });
    }
    
    // Prosedur lainnya
    // Meminta host name saat pengguna pertama kali masuk
    private void hostNameHandler() {
        homePage = new HomePage();
        layers.add(homePage, new Integer(0));
        hostNameWindow = new HostNameWindow();
        layers.add(hostNameWindow, new Integer(1));
        add(layers);
        if (!isConnect) {
            // Memeriksa pengguna menekan tombol check
            hostNameWindow.getYesButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == hostNameWindow.getYesButton()) {
                        // Memeriksa pengguna menekan tombol check
                        String hostName = hostNameWindow.getHostName();
                        if (hostName.isEmpty()) {
                            emptyWindow = new EmptyWindow("Please insert a valid host name.");
                            layers.add(emptyWindow, new Integer(2));
                            setContentPane(layers);
                            backFromEmptyWindow(emptyWindow);
                        } else {
                            /// isConnect di set tergantung connect atau ngga ke servernya
                            isConnect = true;
                            if (isConnect) {
                                // Simpan nama host baru, tutup window
                                layers.remove(layers.getIndexOf(hostNameWindow));
                                homeController();
                                invalidate();
                                validate();
                            } else {
                                emptyWindow = new EmptyWindow("Please try again.");
                                layers.add(emptyWindow, new Integer(2));
                                setContentPane(layers);
                                backFromEmptyWindow(emptyWindow);
                            }
                        }
                    }
                }
            });
        }
    }

    // Mendeteksi tombol add new room ditekan
    private void newRoomHandler() {
        menuPage.getNewRoomButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == menuPage.getNewRoomButton()) {
                    newRoomWindow = new NewRoomWindow();
                    layers.add(newRoomWindow, new Integer(1));
                    setContentPane(layers);
                    // Memeriksa pengguna menekan tombol check
                    newRoomWindow.getYesButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent eOk) {
                            if (eOk.getSource() == newRoomWindow.getYesButton()) {
                                String newRoomName = newRoomWindow.getNewRoomName();
                                if (newRoomName.isEmpty()) {
                                    emptyWindow = new EmptyWindow("Please insert a valid room name.");
                                    layers.add(emptyWindow, new Integer(2));
                                    setContentPane(layers);
                                    backFromEmptyWindow(emptyWindow);
                                } else {
                                    // Simpan nama room baru, tutup window
                                    roomName = newRoomName;
                                    roomList.put(roomName, 1);
                                    /// Kirim ke server, pindah masuk ke room tersebut
                                    closeWindow(newRoomWindow);
                                    chooseRoleHandler();
                                    invalidate();
                                    validate();
                                }
                            }
                        }
                    });
                    // Memeriksa pengguna menekan tombol close
                    newRoomWindow.getNoButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent eNo) {
                            if (eNo.getSource() == newRoomWindow.getNoButton()) {
                                // Tutup window
                                closeWindow(newRoomWindow);
                                menuController();
                                invalidate();
                                validate();
                            }
                        }
                    });
                }
            }
        });
    }
    // Mendeteksi tombol room ditekan
    public void chooseRoomHandler() {
//        for (int i = 0; i < roomLabels.size(); i++) {
//            roomLabels.get(i).addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    onMouseClickedRooms(e);
//                }
//            });
//        }
    }
    private void onMouseClickedRooms(MouseEvent e) {
//        for (int i = 0; i < roomLabels.size(); i++) {
//            if (e.getSource() == roomLabels.get(i)) {
//                String labelValue = roomLabels.get(i).getText();
//                roomNumber = Integer.parseInt(labelValue.substring(0, 1));
//                roomName = roomList.
//                roomController();
//                roomPage = new RoomPage();
//                invalidate();
//                validate();
//            }
//        }
    }
    // Mendeteksi tombol role ditekan
    public void chooseRoleHandler() {
        roleWindow = new RoleWindow();
        layers.add(roleWindow, new Integer(2));
        setContentPane(layers);
        // Mendeteksi tombol role player ditekan
        roleWindow.getPlayerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roleWindow.getPlayerButton()) {
                    layers.remove(layers.getIndexOf(roleWindow));
                    chooseCharacterHandler();
                    invalidate();
                    validate();
                }
            }
        });
        // Mendeteksi tombol role spectator ditekan
        roleWindow.getSpectatorButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roleWindow.getSpectatorButton()) {
                    layers.remove(layers.getIndexOf(roleWindow));
                    setContentPane(layers);
                    roomController();
                    invalidate();
                    validate();
                }
            }
        });
    }
    // Mendeteksi pilihan character ditekan
    public void chooseCharacterHandler() {
        characterWindow = new CharacterWindow();
        layers.add(characterWindow, new Integer(3));
        setContentPane(layers);

    }


    // Mendeteksi game telah dimulai (sudah ada 3 orang)
    public void startGameHandler() {
        roomPage.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roomPage.getStartButton()) {
                    if (isGameStart) {
                        emptyWindow = new EmptyWindow("Game start.");
                        layers.add(emptyWindow, new Integer(1));
                        setContentPane(layers);
                        backFromEmptyWindow(emptyWindow);
                    }
                }
            }
        });
    }
    // Mendeteksi label pada board ditekan
    public void boardHandler() {
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 20; j++) {
//                board[i][j].addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        onMouseClickedBoard(e);
//                    }
//                });
//            }
//        }
    }
    public void onMouseClickedBoard(MouseEvent e) {
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 20; j++) {
//                if (e.getSource() == board[i][j]) {
//                    String labelValue = board[i][j].getText();
//                    // Player memilih posisi board diinginkan
//                    Point position = new Point(x,y);
//
//                    // Posisi board belum terisi
//                    x = i;
//                    y = j;
//                    board[x][y].setText("*");
//
//                    // Mengecek apakah client menang
//                    if ((boolean) objectFromServer.readObject()) {
//                        emptyWindow = new EmptyWindow("You win.");
//                        backFromEmptyWindow(emptyWindow);
//                        layers.add(emptyWindow, new Integer(1));
//                        setContentPane(layers);
//                    }
//
//                    isPlayerTurn = false;
//                    // Else: Board sudeh terisi
//                }
//            }
//        }
    }
    public void play() {
        // Game selesai
        emptyWindow = new EmptyWindow("Game is finished.");
        layers.add(emptyWindow, new Integer(1));
        setContentPane(layers);
        backFromEmptyWindow(emptyWindow);
    }

    // Mengembalikan ke window sebelumnya apabila tombol check ditekan
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
    // Mengembalikan ke window sebelumnya apabila tombol close ditekan
    public void closeWindow(CharacterWindow window) {
        window.getNoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == window.getNoButton()) {
                    layers.remove(layers.getIndexOf(window));
                    setContentPane(layers);
                    invalidate();
                    validate();
                }
            }
        });
    }
    public void closeWindow(RoleWindow window) {
        window.getNoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == window.getNoButton()) {
                    layers.remove(layers.getIndexOf(window));
                    setContentPane(layers);
                    invalidate();
                    validate();
                }
            }
        });
    }
    public void closeWindow(NewRoomWindow window) {
        window.getNoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == window.getNoButton()) {
                    layers.remove(layers.getIndexOf(window));
                    setContentPane(layers);
                    invalidate();
                    validate();
                }
            }
        });
    }
    public void closeWindow(HostNameWindow window) {

    }
    // Program utama
    public static void main (String[] args) {
        Main mainFrame = new Main();
    }
}
