package Forms;

import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Player.Computer;
import AllThoseTerritories.Player.Human;
import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import static Main.IOHelper.createPlayingFieldsFromFile;

/**
 * Created by Thomas on 01/01/2016.
 */
public class MainMenuScreen extends JFrame {
    private JButton startButton = new JButton("Start Game");
    private JButton exitButton = new JButton("Exit");
    private JComboBox mapList = new JComboBox();
    private JLabel mapSelectedText = new JLabel();
    private ColorChooserButton player1ColorButton = new ColorChooserButton(new Color(0, 200, 0), "Choose a color for Player1");
    private ColorChooserButton player2ColorButton = new ColorChooserButton(new Color(200, 0, 0), "Choose a color for Player2");
    private JCheckBox isPlayer1ABot = new JCheckBox();
    private JCheckBox isPlayer2ABot = new JCheckBox();

    public MainMenuScreen() {
        super("AllThoseTerritories");
        initFrame();
        initComponents();
        initListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initFrame() {
        setSize(Main.DEFAULT_FRAME_SIZE);
        setResizable(false);
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/Sprites/ATT.png"))));
        setLayout(null);
    }

    private void initComponents() {
        loadMapNames();

        mapSelectedText.setText("<html><font color='red'>Selected Map:</font></html>"); // using html code for color.

        player1ColorButton.setText("Choose a color for Player1");
        player2ColorButton.setText("Choose a color for Player2");

        isPlayer1ABot.setText("Player1 is a bot");
        isPlayer2ABot.setText("Player2 is a bot");

        mapList.setBounds(500, 50, 300, 35);

        player1ColorButton.setBounds(500, 100, 250, 35);
        player2ColorButton.setBounds(500, 150, 250, 35);

        mapSelectedText.setBounds(400, 50, 300, 35);

        startButton.setBounds(50, 50, 250, 50);
        isPlayer1ABot.setBounds(50, 150, 250, 50);
        isPlayer2ABot.setBounds(50, 250, 250, 50);
        exitButton.setBounds(50, 570, 100, 50);
        add(startButton);
        add(exitButton);
        add(mapList);
        add(mapSelectedText);
        add(player1ColorButton);
        add(player2ColorButton);
        add(isPlayer1ABot);
        add(isPlayer2ABot);
    }

    private void loadMapNames() {
        File[] files = new File(getClass().getClassLoader().getResource("").getFile() + "resources/Maps/").listFiles();
        for (File f : files) {
            // Checks if the file has the extension ".map".
            if (f.getName().substring(f.getName().lastIndexOf(".") + 1).equals("map")) {
                mapList.addItem(f.getName().substring(0, f.getName().lastIndexOf(".")));
            }
        }
    }

    private void initListeners() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == exitButton) {
                    Main.setCurrentFrame(null); // Full exit.
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    // Load the continents.
                    List<Kontinent> continents = createPlayingFieldsFromFile(getClass().getClassLoader().getResource("").getFile() + "resources/Maps/" + mapList.getSelectedItem() + ".map");

                    GameScreen gameFrame = new GameScreen(
                            isPlayer1ABot.isSelected() ? new Computer(player1ColorButton.MyColor, true) : new Human(player1ColorButton.MyColor, true),
                            isPlayer2ABot.isSelected() ? new Computer(player2ColorButton.MyColor, false) : new Human(player2ColorButton.MyColor, false),
                            continents
                    );

                    Main.setCurrentFrame(gameFrame);
                }
            }
        });
    }
}
