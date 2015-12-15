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

import algorithm.Player;
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

import static algorithm.Client2.*;
import static java.lang.Thread.sleep;

/**
 * Kelas utama program dengan Graphical User Interface
 */
public class Main extends JFrame {
    public final static int INTERVAL = 1000;

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
    private static Frame chatBox;

    // Atribut penggambaran
    private ArrayList<String> characterFileNames;
    private Map highScores;
    private Map roomList;
    private ArrayList<Label> roomLabels;
    private PlayersDetail playersDetail;
    private Label[][] boardLabels;
    private boolean isConnect;
    private boolean isGameFinish;
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
        roomList = new TreeMap();
        highScores = new TreeMap();
        playersDetail = new PlayersDetail();
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
                                emptyWindow = new EmptyWindow("Sorry, try another nickname.");
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
//        if (!room.isGameStart()) {
            // Kirim perintah untuk mendapatkan daftar pemain
            sendToServer("get-players");
            sleep(100);

            // Isi daftar pemain
            playersDetail = new PlayersDetail();
            String masterNickname = room.getMaster().getNickName();
            if (!nickname.equals(masterNickname)) {
                // Bukan player master
                roomPage.getStartButton().setVisible(false);
            }
            for (int i = 0; i < room.getPlayers().size(); i++) {
                Player player = room.getPlayer(i);
                boolean isMaster = false;
                if (player.getNickName().equals(masterNickname)) {
                    isMaster = true;
                }
                // Tambah player baru
                boolean isTurn = false;
                if (player.getNickName().equals(room.turn().getNickName())) {
                    isTurn = true;
                }
                playersDetail.add(player.getCharacter(), player.getNickName(), isTurn, isMaster);
            }
            // Menampilkan tombol start game bila pemain adalah master room
            if (nickname.equals(room.getMaster())) {
                roomPage.getStartButton().setVisible(true);
            }

            layers.removeAll();
            layers.repaint();
            layers.revalidate();

            layers = new JLayeredPane();
            roomPage = new RoomPage(roomName, characterFileNames, playersDetail, nickname);
            layers.add(roomPage, new Integer(0));
            layers.repaint();
            layers.revalidate();
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
            startGameHandler();
//        } else {
            boardHandler();
            roomPage.refreshBoard();
            roomPage.repaint();
            roomPage.revalidate();
//        }
    }
    // Auto refresh player setiap interval tertentu
    public void playersAutoRefresher() throws Exception {
        // Kirim perintah untuk mendapatkan daftar pemain
        sendToServer("get-players");
        sleep(100);

        // Isi daftar pemain
        playersDetail = new PlayersDetail();
        String masterNickname = room.getMaster().getNickName();
        for (int i = 0; i < room.getPlayers().size(); i++) {
            Player player = room.getPlayer(i);
            boolean isMaster = false;
            if (player.getNickName().equals(masterNickname)) {
                isMaster = true;
            }
            boolean isTurn = false;
            System.out.println("---turn: " + player.getNickName());
            if (player.getNickName().equals(room.turn().getNickName())) {
                isTurn = true;
            }
            // Tambah player baru
            playersDetail.add(player.getCharacter(), player.getNickName(), isTurn, isMaster);
        }
        // Menampilkan tombol start game bila pemain adalah master room
        if (nickname.equals(room.getMaster())) {
            roomPage.getStartButton().setVisible(true);
        }

        layers = new JLayeredPane();
        roomPage = new RoomPage(roomName, characterFileNames, playersDetail, nickname);
        layers.add(roomPage, new Integer(0));
        setContentPane(layers);

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    roomController();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean isGameStart = room.isGameStart();
                if (!isGameStart) {
                    //Refresh player panel
                } else {
                    // Refresh board panel
                    // Kirim perintah update board lokal
                    try {
                        sendToServer("get-board");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (isGameFinish) {
                    try {
                        roomController();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isGameStart = room.isGameStart();
                    if (!isGameStart) {
                        //Refresh player panel
                    } else {
                        // Refresh board panel
                        // Kirim perintah update board lokal
                        try {
                            sendToServer("get-board");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    ((Timer)evt.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    // Memeriksa daftar pemain pada room saat ini
    private void refreshPlayersDetail() throws Exception {
        sendToServer("get-players");
        sleep(100);
        PlayersDetail roomPlayersDetail = roomPage.getPlayersDetail();
        String masterNickname = room.getMaster().getNickName();
        for (int i=0; i<room.getPlayers().size(); i++) {
            Player player = room.getPlayer(i);
            boolean isMaster = false;
            if (player.getNickName().equals(masterNickname)) {
                isMaster = true;
            }

            if (i > roomPlayersDetail.size()-1) {
                // Tambah player baru
                roomPlayersDetail.add(player.getCharacter(), player.getNickName(), false, isMaster);
            } else {
                // Periksa perubahan pada player
                boolean isChanged = roomPlayersDetail.getCharacterSign(i)!=player.getCharacter() && !roomPlayersDetail.getPlayerName(i).equals(player.getNickName()) && (roomPlayersDetail.getIsMaster(i)!=isMaster);
                if (isChanged) {
                    roomPlayersDetail.set(i, player.getCharacter(), player.getNickName(), false, isMaster);
                }
            }
        }
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
                                // Simpan nama host baru, tutup window
                                layers.remove(layers.getIndexOf(hostNameWindow));
                                homeController();
                                invalidate();
                                validate();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                isConnect = false;
                                // Pesan error
                                emptyWindow = new EmptyWindow("We can not connect to you :(");
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
                roomName = labelValue;
                // Kirim perintah gabung room ke server
                sendToServer("join-room " + roomName);
                sleep(100);
                if (Client2.command[0].equals("success-room")) {
                    // Player berhasil join ke room
                    chooseRoleHandler();
                    invalidate();
                    validate();
                } else if (Client2.command[0].equals("fail-room")) {
                    emptyWindow = new EmptyWindow("Sorry, you can not join right now.");
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
                            characterSign = 6;
                            layers.remove(layers.getIndexOf(roleWindow));
                            playersAutoRefresher();
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
                    playersAutoRefresher();
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
                    // Kirim perintah untuk memulai game
                    try {
                        sendToServer("start-game");
                        sleep(100);
                        System.out.println("Tes: " + Client2.command[0]);
                        if (Client2.command[0].equals("fail")) {
                            // Jumlah pemain < 3 orang
                            emptyWindow = new EmptyWindow("Waiting for 3 players...");
                            layers.add(emptyWindow, new Integer(layers.highestLayer()+1));
                            setContentPane(layers);
                            backFromEmptyWindow(emptyWindow);
                        } else {
                            // Game berhasil dimulai, set turn menjadi master
                            isGameFinish = false;
                            emptyWindow = new EmptyWindow("Game start.");
                            layers.add(emptyWindow, new Integer(layers.highestLayer()+1));
                            setContentPane(layers);
                            backFromEmptyWindow(emptyWindow);
                            playersAutoRefresher();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    // Mendeteksi label pada board ditekan
    public void boardHandler() {
        System.out.println("masukkk");
        boardLabels = new Label[20][20];
        boardLabels = roomPage.getBoard();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                boardLabels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            onMouseClickedBoard(e, boardLabels);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private boolean isPopUp = false;
    private int iBefore = -2;
    private int jBefore = -2;
    public void onMouseClickedBoard(MouseEvent e, Label[][] boardLabels) throws Exception {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (e.getSource() == boardLabels[i][j]) {
                    // Kirim perintah isi board ke server
                    sendToServer("update-board " + i + " " + j);
                    if (iBefore==i && jBefore==j) {
                        isPopUp = true;
                    } else {
                        isPopUp = false;
                        iBefore = i;
                        jBefore = j;
                    }
                    System.out.println("aaaaaaaaaaaaaaaaaaaa");
                    if (Client2.command[0].equals("fail")) {
                        System.out.println("-------------faaaaaaaaaillllllll");
//                        if (!isPopUp) {
                            emptyWindow = new EmptyWindow("It is not your turn.");
                            backFromEmptyWindow(emptyWindow);
                            layers.add(emptyWindow, new Integer(1));
                            setContentPane(layers);
//                        }
                    } else if (Client2.command[0].equals("win-game")) {
                        System.out.println("-----------winnnnnnnnnn");
                        isGameFinish = true;
                        if (Client2.command[1].equals(nickname)) {
                            // Pemain menang
                            playerWinWindow = new PlayerWinWindow();
                            layers.add(emptyWindow, new Integer(1));
                            setContentPane(layers);
                            backFromPlayerWinWindow(playerWinWindow);
                        } else {
                            // Pemain lain menang
                            otherWinWindow = new OtherWinWindow(Client2.command[1]);
                            layers.add(emptyWindow, new Integer(1));
                            setContentPane(layers);
                            backFromOtherWinWindow(otherWinWindow);
                        }
                    } else if (Client2.command[0].equals("turn")) {
                        /// TODO ini apa
                    }
                }
            }
        }
    }

    // Auto refresh room setiap interval tertentu
    public void boardAutoRefresher() {
        // Timer
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Refresh the panel
                roomPage.refreshBoard();
                roomPage.revalidate();

                if (isGameFinish) {
                    ((Timer)evt.getSource()).stop();
                }
            }
        });
        timer.start();
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
    public void backFromPlayerWinWindow(PlayerWinWindow messagePage) {
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
    public void backFromOtherWinWindow(OtherWinWindow messagePage) {
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
//        chatBox = new JFrame("Chat Box");
//        chatBox.setSize(300,400);
//        chatBox.setLocationRelativeTo(mainFrame);
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
//        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
//        int x = (int) rect.getMaxX() - mainFrame.getWidth();
//        int y = (int) rect.getMaxY() - mainFrame.getHeight();
//        chatBox.setLocation(x, y);
//        chatBox.pack();
//        chatBox.setVisible(true);
    }
}
