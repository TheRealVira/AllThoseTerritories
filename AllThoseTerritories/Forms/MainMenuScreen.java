package Forms;

import AllThoseTerritories.Player.Computer;
import AllThoseTerritories.Player.Human;
import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static Main.IOHelper.CreatePlayingFieldsFromFile;

/**
 * Created by Thomas on 01/01/2016.
 */
public class MainMenuScreen extends JFrame{
    private JButton StartGame=new JButton("Start Game");
    private JButton Exit=new JButton("EXIT");
    private JComboBox MapList=new JComboBox();
    private JLabel MapSelectedText=new JLabel();
    private ColorChooserButton Player1Color=new ColorChooserButton(new Color(0,200,0),"Coose a color for Player1");
    private ColorChooserButton Player2Color=new ColorChooserButton(new Color(200,0,0),"Choose a color for Player2");
    private JCheckBox IsPlayer1ABot=new JCheckBox();
    private JCheckBox IsPlayer2ABot=new JCheckBox();

    public MainMenuScreen() {
        super("AllThoseTerritories");
        setSize(1250,680); // We have to add 30 px to spacing issues with swing...
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/Sprites/ATT.png"))));
        setLayout(null);

        for (File f:
             new File(getClass().getClassLoader().getResource("").getFile()+"resources/Maps/").listFiles()) {
            if(f.getName().substring(f.getName().lastIndexOf(".")+1).equals("map")) {
                MapList.addItem(f.getName().substring(0,f.getName().lastIndexOf(".")));
            }
        }

        MapSelectedText.setText("<html><font color='red'>Selected Map:</font></html>"); // here some hmtl code to get a red text color
        Player1Color.setText("Choose a color for Player1");
        Player2Color.setText("Choose a color for Player2");
        IsPlayer1ABot.setText("Player1 is a bot");
        IsPlayer2ABot.setText("Player2 is a bot");
        MapList.setBounds(500,50,300,35);
        Player1Color.setBounds(500,100,250,35);
        Player2Color.setBounds(500,150,250,35);
        MapSelectedText.setBounds(400,50,300,35);
        StartGame.setBounds(50,50,250,50);
        IsPlayer1ABot.setBounds(50,150,250,50);
        IsPlayer2ABot.setBounds(50,250,250,50);
        Exit.setBounds(50,570,100,50);
        add(StartGame);
        add(Exit);
        add(MapList);
        add(MapSelectedText);
        add(Player1Color);
        add(Player2Color);
        add(IsPlayer1ABot);
        add(IsPlayer2ABot);

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==Exit){
                    Main.SetCurrentFrame(null);
                }
            }
        });

        StartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==StartGame){
                    Main.SetCurrentFrame(
                            new GameScreen(
                                    IsPlayer1ABot.isSelected()?new Computer(Player1Color.MyColor):new Human(Player1Color.MyColor),
                                    IsPlayer2ABot.isSelected()?new Computer(Player2Color.MyColor):new Human(Player2Color.MyColor),
                                    CreatePlayingFieldsFromFile(
                                            getClass().getClassLoader().getResource("").getFile()+"resources/Maps/"+MapList.getSelectedItem()+".map")));
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
