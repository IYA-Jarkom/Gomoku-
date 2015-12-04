/*
 * Test1
 * Each line should be prefixed with  * 
 test2
 */
package gui;

import algorithm.Client;
import static algorithm.Client.clientSocket;
import static algorithm.Client.listRoom;
import static algorithm.Client.objectFromServer;
import static algorithm.Client.objectToServer;
import algorithm.Room;
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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private NewRoomWindow newRoomWindow;
    private RoleWindow roleWindow;
    private CharacterWindow characterWindow;
    private OtherWinWindow otherWinWindow;
    private PlayerWinWindow playerWinWindow;
    private HostNameWindow hostNameWindow;
    private EmptyWindow emptyWindow;
    private String nickname;
    private String roomName;
    private boolean isConnect;
    private ArrayList<Label> roomLabels;
    private Thread recListRoom;
    private boolean isGameStart;
    private int clientRoom;
    private int character;
    private int roomNumber;
    private boolean isPlayerTurn;
    private Label[][] board;
    private int x;
    private int y;
    
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
        isConnect = false;
        roomName = "Ruanganku";
        hostNameHandler();
//        homeController();
        //nickname = "Default";
        //menuController();
//        roomController("Roomku");
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
                    if (nickname.isEmpty()) {
                        emptyWindow = new EmptyWindow("Please insert a valid nickname.");
                        layers.add(emptyWindow, new Integer(1));
                        setContentPane(layers);
                        backFromEmptyWindow(emptyWindow);
                    } else {
                        try {
                            objectToServer.writeObject(nickname);
                            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
                            homePage.setNickname(nickname);
                            menuController();
                            invalidate();
                            validate();
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
    // Menangani tampilan dan aksi pada page Menu
    public void menuController() {
        try {
            //TERIMA LISTROOM DARI SERVER
            listRoom = new ArrayList<Room>((ArrayList<Room>) objectFromServer.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        recListRoom = new Thread(new Client.RecListRoom());
        recListRoom.start();
                
        layers = new JLayeredPane();
        menuPage = new MenuPage(nickname, listRoom);
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
        chooseRoomHandler();
    }
    // Menangani tampilan dan aksi pada page Room
    public void roomController() { // Kirim nama room, daftar pengguna dan masternya
        layers = new JLayeredPane();
        roomPage = new RoomPage();
        layers.add(roomPage, new Integer(0));
        setContentPane(layers);
        isGameStart = false;
        try {
            String playerName = (String) objectFromServer.readObject();
            clientRoom = (Integer)objectFromServer.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                            layers.add(emptyWindow, new Integer(1));
                            setContentPane(layers);
                            backFromEmptyWindow(emptyWindow);
                        } else {
                            // Simpan nama host baru, tutup window
                            isConnect = true;
                            hostName = hostNameWindow.getHostName();
                            try {
                                clientSocket = new Socket(hostName, 2000);
                                objectToServer = new ObjectOutputStream(clientSocket.getOutputStream());
                                layers.remove(layers.getIndexOf(hostNameWindow));
                                setContentPane(layers);
                                invalidate();
                                validate();
                            } catch (IOException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
                                    try {
                                        // Simpan nama room baru, tutup window
                                        objectToServer.writeObject(-1);
                                        String roomName = newRoomWindow.getNewRoomName();
                                        objectToServer.writeObject(roomName);
                                        recListRoom.stop();
                                        closeWindow(newRoomWindow);
                                        menuController();
                                        invalidate();
                                        validate();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
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
        for (int i = 0; i < roomLabels.size(); i++) {
            roomLabels.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onMouseClickedRooms(e);
                }
            });
        }
    }
    private void onMouseClickedRooms(MouseEvent e) {
        for (int i = 0; i < roomLabels.size(); i++) {
            if (e.getSource() == roomLabels.get(i)) {
                String labelValue = roomLabels.get(i).getText();
                try {
                    roomNumber = Integer.parseInt(labelValue.substring(0, 1));
                    objectToServer.writeObject(roomNumber);
                    recListRoom.stop();
                    roomController();
                    roomPage = new RoomPage();
                    invalidate();
                    validate();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    // Mendeteksi tombol role ditekan
    public void chooseRoleHandler() {
        roomPage = new RoomPage();
        layers.add(roomPage, new Integer(0));
        roleWindow = new RoleWindow();
        layers.add(roleWindow, new Integer(1));
        setContentPane(layers);
        // Mendeteksi tombol role player ditekan
        roleWindow.getPlayerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roleWindow.getPlayerButton()) {
                    layers.remove(layers.getIndexOf(roleWindow));
                    characterWindow = new CharacterWindow();
                    layers.add(characterWindow, new Integer(1));
                    setContentPane(layers);
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
                    roomPage = new RoomPage();
                    characterWindow = new CharacterWindow();
                    layers.add(characterWindow, new Integer(1));
                    setContentPane(layers);
                    invalidate();
                    validate();
                }
            }
        });
        closeWindow(roleWindow);
    }
    // Mendeteksi pilihan character ditekan
    public void chooseCharacterHandler() {
        
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
    // Mendeteksi game telah dimulai (sudah ada 3 orang)
    public void startGameHandler() {
        roomPage.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roomPage.getStartButton()) {
                    try {
                        // Master player kirim start
                        objectToServer.writeObject(true);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        isGameStart = (boolean) objectFromServer.readObject();
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                board[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        onMouseClickedBoard(e);
                    }
                });
            }
        }
    }
    public void onMouseClickedBoard(MouseEvent e) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (e.getSource() == board[i][j]) {
                    String labelValue = board[i][j].getText();
                    // Player memilih posisi board diinginkan
                    Point position = new Point(x,y);

                    try {
                        // Mengirim posisi board ke server
                        objectToServer.writeObject(position);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        if ((boolean) objectFromServer.readObject()) {  // Posisi board belum terisi
                            x = i;
                            y = j;
                            board[x][y].setText("*");
                            
                            // Mengecek apakah client menang
                            if ((boolean) objectFromServer.readObject()) {
                                emptyWindow = new EmptyWindow("You win.");
                                backFromEmptyWindow(emptyWindow);
                                layers.add(emptyWindow, new Integer(1));
                                setContentPane(layers);
                            }
                            
                            isPlayerTurn = false;
                        } else {
                            // Board sudeh terisi
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    public void play() throws IOException {
        while (true) {
            do {
                if (roomNumber < 0) {  // Player adalah master di room
                    startGameHandler();
                } else {    // Player bukan master di room
                    emptyWindow = new EmptyWindow("Wait for the Master to start the game.");
                    layers.add(emptyWindow, new Integer(1));
                    setContentPane(layers);
                    try {
                        isGameStart = (boolean) objectFromServer.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    backFromEmptyWindow(emptyWindow);
                    // Start
                    emptyWindow = new EmptyWindow("Game start.");
                    layers.add(emptyWindow, new Integer(1));
                    setContentPane(layers);
                    try {
                        isGameStart = (boolean) objectFromServer.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    backFromEmptyWindow(emptyWindow);
                }
            } while (!isGameStart);

            if (isGameStart) {
                // Game dimulai
                isPlayerTurn = false;
                try {
                    listRoom = new ArrayList<Room>(new ArrayList((ArrayList<Room>) objectFromServer.readObject()));
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                do {
                    isPlayerTurn = objectFromServer.readBoolean();
                    System.out.println(isPlayerTurn);
                    if (isPlayerTurn) {
                        boardHandler();
                    }
                    try {
                        isGameStart = (boolean) objectFromServer.readObject();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } while (isGameStart);
                // Game selesai
                emptyWindow = new EmptyWindow("Game is finished.");
                layers.add(emptyWindow, new Integer(1));
                setContentPane(layers);
                backFromEmptyWindow(emptyWindow);
            }
        }
    }
    
    // Program utama
    public static void main (String[] args) {
        Main mainFrame = new Main();
    }
}
