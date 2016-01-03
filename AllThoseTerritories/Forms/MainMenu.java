package Forms;

import AllThoseTerritories.Player.Computer;
import AllThoseTerritories.Player.Human;
import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static Main.IOHelper.CreatePlayingFieldsFromFile;

/**
 * Created by Thomas on 01/01/2016.
 */
public class MainMenu extends JFrame{
    private JButton StartSPGame=new JButton("Start single Player Game");
    private JButton StartMPGame=new JButton("Start multy Player Game");
    private JButton Exit=new JButton("EXIT");
    private JComboBox MapList=new JComboBox();
    private JLabel MapSelectedText=new JLabel();

    public MainMenu() {
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
        MapList.setBounds(500,50,300,35);
        MapSelectedText.setBounds(400,50,300,35);
        StartMPGame.setBounds(50,50,250,50);
        StartSPGame.setBounds(50,150,250,50);
        Exit.setBounds(50,570,100,50);
        add(StartMPGame);
        add(StartSPGame);
        add(Exit);
        add(MapList);
        add(MapSelectedText);

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==Exit){
                    Main.SetCurrentFrame(null);
                }
            }
        });

        StartMPGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==StartMPGame){
                    Main.SetCurrentFrame(
                            new Game(
                                    new Human(),
                                    new Human(),
                                    CreatePlayingFieldsFromFile(
                                            getClass().getClassLoader().getResource("").getFile()+"resources/Maps/"+MapList.getSelectedItem()+".map")));
                }
            }
        });

        StartSPGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==StartSPGame){
                    Main.SetCurrentFrame(
                            new Game(
                                    new Human(),
                                    new Computer(),
                                    CreatePlayingFieldsFromFile(
                                            getClass().getClassLoader().getResource("").getFile()+"resources/Maps/"+MapList.getSelectedItem()+".map")));
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
