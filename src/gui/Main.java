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

import com.sun.org.apache.xpath.internal.SourceTree;
import algorithm.Client2;
import gui.data.PlayersDetail;
import gui.element.ImageButton;
import gui.element.Label;
import gui.page.HighScorePage;
import gui.page.HomePage;
import gui.page.MenuPage;
import gui.page.RoomPage;
import gui.window.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static algorithm.Client2.clientSocket;
import static algorithm.Client2.room;
import static algorithm.Client2.sendToServer;
import static java.lang.Thread.sleep;

/**
 * Kelas utama program dengan Graphical User Interface
 */
public class Main extends JFrame {
    // Atribut GUI
    private JLayeredPane layers;
    private HomePage homePage;
    private MenuPage menuPage;
    private HighScorePage highScorePage;
    private RoomPage roomPage;
    private NewRoomWindow newRoomWindow;
    private RoleWindow roleWindow;
    private CharacterWindow characterWindow;
    private OtherWinWindow otherWinWindow;
    private PlayerWinWindow playerWinWindow;
    private HostNameWindow hostNameWindow;
    private EmptyWindow emptyWindow;

    // Atribut penggambaran
    private ArrayList<String> characterFileNames;
    private Map highScores;
    private Map roomList;
    private ArrayList<Label> roomLabels;
    private PlayersDetail playersDetail;
    private int[][] boardValue;
    private Label[][] boardLabels;
    private boolean isConnect;
    private boolean isGameStart;
    private String nickname;
    private String newRoomName;
    private String roomName;
    private String role;
    private int characterSign;

    // Konstruktor
    public Main() {
        // Info file character
        characterFileNames = new ArrayList<>();
        characterFileNames.add("penguin.png");
        characterFileNames.add("deer.png");
        characterFileNames.add("bird.png");
        characterFileNames.add("dog.png");
        characterFileNames.add("duck.png");
        characterFileNames.add("rabbit.png");
        characterFileNames.add("spectator.png");

        // Inisiasi nilai
        isConnect = false;
        isGameStart = false;
        roomList = new TreeMap();
        highScores = new TreeMap();
        playersDetail = new PlayersDetail();
        boardValue = new int[20][20];
        // TODO inisiasi nilai
        highScores.put("birdie", 100);
        highScores.put("lala", 200);
        highScores.put("doggie", 50);
        highScores.put("birdie2", 100);
        highScores.put("lala3", 200);
        highScores.put("doggie4", 50);
        highScores.put("birdie6", 100);
        highScores.put("lala8", 200);
        highScores.put("doggie9", 50);
        highScores.put("doggie1", 50);
        highScores.putAll(highScores);
        playersDetail.add(2,"birdie", false, true);
        playersDetail.add(3,"doggie", true, false);
        playersDetail.add(1,"test", false, true);
        playersDetail.add(2,"birdie", false, true);
        playersDetail.add(3,"doggie", true, false);
        playersDetail.add(1,"test", false, true);
        playersDetail.add(2,"birdie", false, true);
        playersDetail.add(3,"doggie", true, false);
        playersDetail.add(1,"test", false, true);
        playersDetail.add(2,"birdie", false, true);
        for (int i=0; i<20; i++) {
            for (int j=0; j<20; j++) {
                boardValue[i][j] = -1;
            }
        }
        roomList.put("Room Satu", 2);
        roomList.put("Room Dua", 1);
        roomList.put("Room Tiga", 5);
        roomList.put("Room Lima", 2);

        // Inisiasi frame
        setTitle("WAL");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

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
                        try {
                            sendToServer("add-user " + nickname);
                            sleep(100);
                            if (Client2.command[0].equals("success")) {
                                // Penambahan pengguna berhasil
                                menuController();
                                invalidate();
                                validate();
                            } else if (Client2.command[0].equals("fail")) {
                                emptyWindow = new EmptyWindow("Sorry, the nickname has been existed.");
                                layers.add(emptyWindow, new Integer(1));
                                setContentPane(layers);
                                backFromEmptyWindow(emptyWindow);
                            }
                            sendToServer("get-room");
                            sleep(100);

                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    // Menangani tampilan dan aksi pada page Menu
    public void menuController() {
        // Kirim perintah meminta list player ke server
        try {
            sendToServer("get-room");
            sleep(100);
            if (Client2.command[0].equals("list-of-room")) {
                roomList = Client2.stringToMap(Client2.command);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

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
        // Mendeteksi tombol high scores ditekan
        menuPage.getHighScoreButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == menuPage.getHighScoreButton()) {
                    // Kirim perintah ke server untuk mendapatkan highscore
                    try {
                        sendToServer("get-highscore");
                        sleep(100);
                        if (Client2.command[0].equals("highscore")) {
                            highScores = Client2.stringToMap(Client2.command);
                            highScoreController();
                            invalidate();
                            validate();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        newRoomHandler();
        chooseRoomHandler();
    }

    // Menangani tampilan dan aksi pada page highscore
    public void highScoreController() {
        layers = new JLayeredPane();
        highScorePage = new HighScorePage(highScores);
        layers.add(highScorePage, new Integer(0));
        setContentPane(layers);
        // Mendeteksi tombol back pada highscore ditekan
        highScorePage.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == highScorePage.getBackButton()) {
                        menuController();
                        invalidate();
                        validate();
                }
            }
        });
    }

    // Menangani tampilan dan aksi pada page Room
    public void roomController() throws Exception {
        // Daftar pemain pada room
        room.getPlayers();
        // Jumlah pemain aktif pada room
        int playerNum = Client2.room.countPlayers();
        if (playerNum > 3) {
            roomPage.getStartButton().setVisible(false);
        }

        layers = new JLayeredPane();
        roomPage = new RoomPage(roomName, characterFileNames, playersDetail, nickname, boardValue);
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
        boardHandler();
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
                            try {
                                clientSocket = new Socket(hostName, 2000);
                                Thread t = new Thread(new Client2.StringGetter());
                                t.start();
                                isConnect = true;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                isConnect = false;
                            }
                            if (isConnect) {
                                // Simpan nama host baru, tutup window
                                layers.remove(layers.getIndexOf(hostNameWindow));
                                homeController();
                                invalidate();
                                validate();
                            } else {
                                emptyWindow = new EmptyWindow("We can not connect to you right now :(");
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
                                newRoomName = newRoomWindow.getNewRoomName();
                                if (newRoomName.isEmpty()) {
                                    emptyWindow = new EmptyWindow("Please insert a valid room name.");
                                    layers.add(emptyWindow, new Integer(2));
                                    setContentPane(layers);
                                    backFromEmptyWindow(emptyWindow);
                                } else {
                                    // Kirim perintah membuat room baru ke server
                                    try {
                                        sendToServer("create-room " + newRoomName);
                                        sleep(100);
                                        if (Client2.command[0].equals("success-room")) {
                                            // Room berhasil dibuat, pilih character
                                            roomName = newRoomName;
                                            closeWindow(newRoomWindow);
                                            chooseCharacterHandler();
                                            invalidate();
                                            validate();
                                        } else if (Client2.command[0].equals("fail-room")) {
                                            emptyWindow = new EmptyWindow("Sorry, the room name has been existed.");
                                            layers.add(emptyWindow, new Integer(2));
                                            setContentPane(layers);
                                            backFromEmptyWindow(emptyWindow);
                                        }
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
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

    // Mendeteksi salah satu room dipilih
    public void chooseRoomHandler() {
        roomLabels = new ArrayList<>();
        roomLabels = menuPage.getRooms();
        for (int i = 0; i < roomLabels.size(); i++) {
            roomLabels.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        onMouseClickedRooms(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }
    private void onMouseClickedRooms(MouseEvent e) throws Exception {
        for (int i = 0; i < roomLabels.size(); i++) {
            if (e.getSource() == roomLabels.get(i)) {
                String labelValue = roomLabels.get(i).getText();
                roomName = labelValue.substring(3);
                // Kirim perintah gabung room ke server
                sendToServer("join-room " + roomName);
                sleep(100);
                if (Client2.command[0].equals("success-room")) {
                    // Player berhasil join ke room
                    chooseRoleHandler();
                    invalidate();
                    validate();
                } else if (Client2.command[0].equals("fail-room")) {
                    emptyWindow = new EmptyWindow("Sorry, the game has been started.");
                    layers.add(emptyWindow, new Integer(1));
                    setContentPane(layers);
                    backFromEmptyWindow(emptyWindow);
                }
            }
        }
    }

    // Mendeteksi salah satu tombol role dipilih
    public void chooseRoleHandler() {
        roleWindow = new RoleWindow();
        layers.add(roleWindow, new Integer(layers.highestLayer()+1));
        setContentPane(layers);
        // Mendeteksi tombol role player ditekan
        roleWindow.getPlayerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roleWindow.getPlayerButton()) {
                    role = "player";
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
                    role = "spectator";
                    // Kirim perintah bergabung ke room ke server sebagai spectator
                    try {
                        sendToServer("join-room " + roomName + " " + role);
                        sleep(100);
                        if (Client2.command[0].equals("success")) {
                            // Player berhasil bergabung ke room sebagai spectator
                            layers.remove(layers.getIndexOf(roleWindow));
                            roomController();
                            invalidate();
                            validate();
                        } else if (Client2.command[0].equals("fail")) {
                            emptyWindow = new EmptyWindow("Sorry, the game has been started.");
                            layers.add(emptyWindow,  new Integer(layers.highestLayer()+1));
                            setContentPane(layers);
                            backFromEmptyWindow(emptyWindow);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        closeWindow(roleWindow);
    }

    // Mendeteksi pilihan character ditekan
    public void chooseCharacterHandler() {
        characterWindow = new CharacterWindow();
        layers.add(characterWindow, new Integer(layers.highestLayer()+1));
        setContentPane(layers);
        // Mendeteksi pilihan character ditekan
        for (int i = 0; i < characterWindow.getCharacters().size(); i++) {
            characterWindow.getCharacters().get(i).getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        onMouseClickedCharacter(e, characterWindow.getCharacters());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }
    private void onMouseClickedCharacter(ActionEvent e, ArrayList<ImageButton> characters) throws Exception {
        for (int i = 0; i < characters.size(); i++) {
            if (e.getSource() == characters.get(i).getButton()) {
                characterSign = i;
                // Kirim perintah masuk ke room ke server
                if (roomName.equals(newRoomName)) {
                    // Pemain baru membuat room baru
                    sendToServer("create-room " + roomName + " " + characterSign);
                } else {
                    // Pemain berasal dari memilih room
                    sendToServer("join-room " + roomName + " " + role + " " + characterSign);
                }
                sleep(100);
                if (Client2.command[0].equals("success")) {
                    // Pemain pindah ke room
                    roomController();
                    invalidate();
                    validate();
                } else if (Client2.command[0].equals("fail-character")) {
                    emptyWindow = new EmptyWindow("Sorry, the character has been used.");
                    layers.add(emptyWindow, new Integer(layers.highestLayer()+1));
                    setContentPane(layers);
                    backFromEmptyWindow(emptyWindow);
                }
            }
        }
    }

    // Mendeteksi game telah dimulai (sudah ada 3 orang)
    public void startGameHandler() {
        roomPage.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == roomPage.getStartButton()) {
                    if (isGameStart) {
                        emptyWindow = new EmptyWindow("Game start.");
                        layers.add(emptyWindow, new Integer(layers.highestLayer()+1));
                        setContentPane(layers);
                        backFromEmptyWindow(emptyWindow);
                    }
                }
            }
        });
    }

    // Mendeteksi label pada board ditekan
    public void boardHandler() {
        boardLabels = new Label[20][20];
        boardLabels = roomPage.getBoard();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                boardLabels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        onMouseClickedBoard(e, boardLabels);
                    }
                });
            }
        }
    }

    public void onMouseClickedBoard(MouseEvent e, Label[][] boardLabels) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (e.getSource() == boardLabels[i][j]) {
                    boardValue[i][j] = characterSign;
                    roomPage.setBoardValue(i,j, characterSign);
                    // TODO board sudah terisi/belum, apakah client menang, ganti giliran
//                    if (true) {
//                        emptyWindow = new EmptyWindow("You win.");
//                        backFromEmptyWindow(emptyWindow);
//                        layers.add(emptyWindow, new Integer(1));
//                        setContentPane(layers);
//                    }
                }
            }
        }
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

    // Program utama
    public static void main(String[] args) {
        Main mainFrame = new Main();
    }
}
