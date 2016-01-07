package Forms;

import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Player.Computer;
import AllThoseTerritories.Player.Player;
import AllThoseTerritories.Territorium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

import static Main.Tools.GetCursorLocation;
import static Main.Tools.HexColorCode;

/**
 * Created by Thomas on 03/01/2016.
 */
public class GameScreen extends JFrame{
    private JPanel Map=new JPanel();

    private JFrame This=this;
    private List<Kontinent>Continents;
    private Player Player1,Player2;
    public JLabel Console=new JLabel();
    public JLabel Suggestions=new JLabel();
    public JLabel WhosTurn=new JLabel();
    public JLabel Reinforcement=new JLabel();
    private JButton NextRound=new JButton();
    private JButton CancelSelection=new JButton();

    private static final int DELAY=60;
    private boolean Player1sTurn=true;

    private Random Rand;

    private Timer PcTimer, DrawTimer;

    private JLabel Background;

    /*
    0=Exit
    1=Landerwerb
    2=Get Bonus
    3=Select Terretorium
    4=Select second Terretorium (and do stuff)
     */
    private int StatesOfPlaying=1;

    public int GetGameState(){
        return this.StatesOfPlaying;
    }

    public GameScreen(Player player1, Player player2, List<Kontinent> continents){
        super("AllThoseTerritories");
        setLayout(null);
        setSize(1250,680); // We have to add 30 px to spacing issues with swing...
        setResizable(false);

        this.Map =new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Background.paint(g);

                if (continents != null) {
                    for (Kontinent c :
                            Continents) {
                        if (c != null) {
                            c.DrawConnections(g, new Dimension(getWidth(),getHeight()));
                        }
                    }

                    for (Kontinent c :
                            Continents) {
                        if (c != null) {
                            c.Draw(g, This, Player1.Color, Player2.Color);
                        }
                    }
                }

                if(Player1sTurn?Player1.Verstärkung.Count==0:Player2.Verstärkung.Count==0) {
                    NextRound.setVisible(true);
                    NextRound.updateUI();
                }
                else{
                    NextRound.setVisible(false);
                }

                WhosTurn.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+(Player1sTurn?"1st Players turn":"2nd Players turn")+"</span> <span style=\"color: #000000; background-color: "+(Player1sTurn?HexColorCode(Player1.Color):HexColorCode(Player2.Color))+"\">[___]</span></html>");
                Reinforcement.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+"Reinforement:  "+(Player1sTurn?Player1.Verstärkung.Count:Player2.Verstärkung.Count)+"</span></html>");
                WhosTurn.updateUI();
                Reinforcement.updateUI();
                CancelSelection.updateUI();

                Territorium testTerr=GetTerritoriumFromPosition(GetCursorLocation(This));
                if(testTerr!=null){
                    if(testTerr.Occupation.State==null){
                        Suggestions.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+"Claim "+testTerr.Name+"</span></html>");
                    }else if(StatesOfPlaying==3) {
                        Suggestions.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+"Select " + testTerr.Name+"</span></html>");
                    }else if(StatesOfPlaying==4) {
                        if (testTerr.Occupation.State == Player1sTurn) {
                            Suggestions.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+"Move some reinformance to " + testTerr.Name+"</span></html>");
                        } else {
                            Suggestions.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+"Atack " + testTerr.Name+"</span></html>");
                        }
                    }
                }
                else{
                    Suggestions.setText("");
                }

                Suggestions.updateUI();
            }
        };

        this.Map.setBounds(0,0,getWidth(),getHeight());
        this.Map.setVisible(true);
        this.Map.setLayout(null);

        this.Continents=continents;
        this.Player1=player1;
        this.Player2=player2;
        this.Console.setBounds(100,this.Map.getHeight()-75,this.Map.getWidth()-200,35);
        this.Suggestions.setBounds(100,this.Map.getHeight()-105,this.Map.getWidth()-200,35);

        this.NextRound.setBounds(this.Map.getWidth()-185,getHeight()-85,150,35);
        this.NextRound.setText("Next Round");
        this.NextRound.setVisible(false);

        this.CancelSelection.setBounds(this.Map.getWidth()-185,getHeight()-120,150,35);
        this.CancelSelection.setText("Cancel selection");
        this.CancelSelection.setVisible(false);

        this.WhosTurn.setBounds(getWidth()/2-35,10,150,35);
        this.Reinforcement.setBounds(getWidth()/2-35,45,150,35);

        this.Background=new JLabel();
        this.Background.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/Sprites/GameBackground.png")));
        this.Background.setBounds(0,0,getWidth(),getHeight());

        this.Map.add(this.NextRound);
        this.Map.add(this.Console);
        this.Map.add(this.WhosTurn);
        this.Map.add(this.Reinforcement);
        this.Map.add(this.Suggestions);
        this.Map.add(this.CancelSelection);

        add(this.Map);

        Rand=new Random();

        this.Map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!((Player1sTurn && Player1.getClass() == Computer.class) || (!Player1sTurn && Player2.getClass() == Computer.class))) {
                    Territorium testTerr = GetTerritoriumFromPosition(GetCursorLocation(This));
                    int selectedTerCount = 0;
                    if (StatesOfPlaying == 1) {
                        if (Continents != null) {
                            for (Kontinent kon :
                                    Continents) {
                                selectedTerCount += kon.GetCountOfOwnedTerritories(Player1sTurn);
                            }
                        }
                    }

                    if (testTerr != null) {
                        String bevor = Console.getText();
                        if (Player1sTurn) {
                            Console.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">" + Player1.Update(StatesOfPlaying, testTerr, Rand) + "</span></html>");
                        } else {
                            Console.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">" + Player2.Update(StatesOfPlaying, testTerr, Rand) + "</span></html>");
                        }

                        if (!bevor.equals(Console.getText())) {
                            if (StatesOfPlaying == 1) {
                                CheckIfAllContinentsAreSet();

                                int check = 0;
                                if (Continents != null) {
                                    for (Kontinent kon :
                                            Continents) {
                                        check += kon.GetCountOfOwnedTerritories(Player1sTurn);
                                    }
                                }

                                if (check != selectedTerCount) {
                                    NextRound.setVisible(true);
                                }
                            } else if (StatesOfPlaying == 3) {
                                CancelSelection.setVisible(true);
                                StatesOfPlaying++;
                            } else if (StatesOfPlaying == 4) {
                                StatesOfPlaying = 3;
                                CancelSelection.setVisible(false);
                            }
                        }
                    }
                }
            }
        });

        this.NextRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==NextRound){
                    NextRound();
                }
            }
        });

        Thread drawThread=new Thread(){
            public void run() {
                super.run();
                DrawTimer=new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Map.repaint();
                    }
                });

                DrawTimer.start();
            }
        };
        drawThread.start();

        Thread pcPlayerThread=new Thread(){
            @Override
            public void run() {
                super.run();
                PcTimer =new Timer(100,new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Boolean winner=Winner();
                        if(winner!=null){
                            Main.Main.SetCurrentFrame(new WinnerScreen(winner));
                            PcTimer.stop();
                            DrawTimer.stop();
                        }
                        else {
                            if ((Player1sTurn && Player1.getClass() == Computer.class)||(!Player1sTurn && Player2.getClass() == Computer.class)) {
                                UpdateComputer();
                            }
                        }
                    }
                });

                PcTimer.start();
            }
        };
        pcPlayerThread.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                PcTimer.stop();
                DrawTimer.stop();
            }
        });

        this.CancelSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==CancelSelection){
                    Player1.LastSelected=null;
                    Player2.LastSelected=null;
                    StatesOfPlaying=3;
                    CancelSelection.setVisible(false);
                    Console.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">Canceled moves</span></html>");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Territorium GetTerritoriumFromPosition(Point point){
        if(this.Continents==null){
            return null;
        }

        for (Kontinent k :
                this.Continents) {
            Territorium testTerr = k.GetTerritoriumFromPosition(point, this);
            if(testTerr!=null){
                return testTerr;
            }
        }

        return null;
    }

    private void CheckIfAllContinentsAreSet() {
        if (this.Continents != null) {
            for (Kontinent k :
                    this.Continents) {
                if (!k.AllTerritoriesAreSet()) {
                    return;
                }
            }
            this.StatesOfPlaying++;
            this.CancelSelection.setVisible(false);
        }
    }

    public int GetBonus(boolean player1){
        if(this.Continents==null){
            return 0;
        }

        int toRet=0;
        for (Kontinent con :
                this.Continents) {

            toRet+=(con.OwnedBy(player1)?con.GetBonus():0)+con.GetCountOfOwnedTerritories(player1)/3+(con.OwnedBy(player1)?con.GetBonus():0);
        }

        return toRet<3?3:toRet;
    }

    private Boolean Winner(){
        Boolean owner=this.Continents.get(0).GetTheOwner();
        if(owner==null){
            return null;
        }

        for (int i = 1; i < this.Continents.size(); i++) {
            if(owner!=this.Continents.get(i).OwnedBy(true)||(!this.Continents.get(i).OwnedBy(false)&&!this.Continents.get(i).OwnedBy(true))){
                return null;
            }
        }

        return owner;
    }

    private int SecureCounter=0;

    private void UpdateComputer() {
        /*try {
            Thread.sleep(500); // so the viewer sees whats going on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Territorium nextTarget = null;
        SecureCounter++;
        if (this.StatesOfPlaying == 1) {
            nextTarget = GetFirstNotOwnedTerretorium();
            if (nextTarget == null) {
                this.StatesOfPlaying++;
            }
        }
        if ((this.StatesOfPlaying == 3 || this.StatesOfPlaying == 4) && (this.Player1sTurn ? this.Player1.Verstärkung.Count > 0 : this.Player2.Verstärkung.Count > 0)) {
            nextTarget = GetHeaviestTerritory();
        } else if ((this.StatesOfPlaying == 3)) {
            nextTarget = GetHeaviestTerritory();
        } else if (this.StatesOfPlaying == 4) {
            if (Player1sTurn && Player1.LastSelected != null&&Player1.LastSelected.Neighbours.size()!=0) {
                for (Territorium neighb :
                        Player1.LastSelected.Neighbours) {
                    if (neighb.Occupation.State != true && neighb.Occupation.Count < Player1.LastSelected.Occupation.Count) {
                        nextTarget = neighb;
                        break;
                    }
                }
            } else if (!Player1sTurn && Player2.LastSelected != null&&Player2.LastSelected.Neighbours.size()!=0) {
                for (Territorium neighb :
                        Player2.LastSelected.Neighbours) {
                    if (neighb.Occupation.State != false && neighb.Occupation.Count < Player2.LastSelected.Occupation.Count) {
                        nextTarget = neighb;
                        break;
                    }
                }
            }

            if(nextTarget==null) { // Well be basically don't have any enemies left around us.
                // We now have to move our "big group" to another country (which should have enemies as neighbors)
                // And bet we don't get into an endless loop 0-0

                if (Player1sTurn && Player1.LastSelected != null&&Player1.LastSelected.Neighbours.size()!=0&&Player1.LastSelected.Occupation.Count>1) {
                    //nextTarget=Player1.LastSelected.Neighbours.get(0);
                    nextTarget=Player1.LastSelected.GetNextStepToEnemie(true,Player1.LastSelected.Occupation.Count);
                } else if (!Player1sTurn && Player2.LastSelected != null&&Player2.LastSelected.Neighbours.size()!=0&&Player2.LastSelected.Occupation.Count>1) {
                    //nextTarget=Player2.LastSelected.Neighbours.get(0);
                    nextTarget=Player2.LastSelected.GetNextStepToEnemie(false,Player2.LastSelected.Occupation.Count);
                }
            }
        }

        if (nextTarget == null||SecureCounter>15) {
            NextRound();
            SecureCounter=0;
            return;
        }

        if (Player1sTurn) {
            Console.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">" + Player1.Update(StatesOfPlaying, nextTarget, Rand) + "</span></html>");
        } else {
            Console.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">" + Player2.Update(StatesOfPlaying, nextTarget, Rand) + "</span></html>");
        }

        if(StatesOfPlaying==1) {
            CheckIfAllContinentsAreSet();
        }

        if(StatesOfPlaying==4){
            StatesOfPlaying=3;
        }
        else if(StatesOfPlaying==3){
            StatesOfPlaying=4;
        }

        if (this.StatesOfPlaying == 1) {
            NextRound();
        }
    }

    private Territorium GetHeaviestTerritory(){
        if(this.Continents==null||this.Continents.size()==0){
            return null;
        }

        Territorium toRet=null;
        for (Kontinent kon :
                this.Continents) {
            Territorium testTerr=kon.GetHeaviestTerritory(Player1sTurn);
            if(testTerr!=null) {
                toRet = (toRet == null || toRet.Occupation.Count < testTerr.Occupation.Count) ? testTerr : toRet;
            }
        }

        return toRet;
    }

    private Territorium GetFirstNotOwnedTerretorium(){
        for (Kontinent kon :
                this.Continents) {
            Territorium toRet=kon.GetFirstNotOwnedTerretorium();
            if(toRet!=null){
                return toRet;
            }
        }

        return null;
    }

    private void NextRound(){
        StatesOfPlaying=StatesOfPlaying>1?2:1;
        Player1sTurn=!Player1sTurn;

        if(StatesOfPlaying==1||((Player1sTurn && Player1.getClass() == Computer.class)||(!Player1sTurn && Player2.getClass() == Computer.class))) {
            NextRound.setVisible(false);
        }
        else{
            NextRound.setVisible(true);
        }

        Player1.LastSelected=null;
        Player2.LastSelected=null;

        if(StatesOfPlaying==2) {
            if (Player1sTurn) {
                Player1.AddBonus(GetBonus(Player1sTurn));
            } else {
                Player2.AddBonus(GetBonus(Player1sTurn));
            }

            StatesOfPlaying++;
        }
    }
}
