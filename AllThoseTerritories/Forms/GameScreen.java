package Forms;

import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Player.Computer;
import AllThoseTerritories.Player.Player;
import AllThoseTerritories.Territorium;
import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

import static Main.Tools.getCursorLocation;
import static Main.Tools.hexColorCode;

/**
 * Created by Thomas on 03/01/2016.
 */
public class GameScreen extends JFrame {
    private JPanel map = new JPanel();

    private JFrame This = this;
    private List<Kontinent> continents;
    private Player player1, player2;
    public JLabel consoleLabel = new JLabel();
    public JLabel suggestionLabel = new JLabel();
    public JLabel turnOwnerLabel = new JLabel();
    public JLabel reinforcementLabel = new JLabel();
    private JButton nextRoundButton = new JButton();
    private JButton cancelSelectionButton = new JButton();

    private static final int DELAY = 60;
    private boolean isPlayer1Turn = true;

    private Random rand = new Random();

    private Timer pcTimer, drawTimer;
    private JLabel backgroundLabel;

    private final static int UI_OFFSET = 105;

    /*
    0=Exit
    1=Landerwerb
    2=Get Bonus
    3=Select Territory
    4=Select second Territory (and do stuff)
     */
    public enum StateOfPlaying {
        Exit, Expansion, Reinforcing, SelectFirstTerritory, SelectSecondTerritory;
    }

    private StateOfPlaying currentState = StateOfPlaying.Expansion;

    public StateOfPlaying GetGameState() {
        return this.currentState;
    }

    public GameScreen(Player player1, Player player2, List<Kontinent> continents) {
        super("AllThoseTerritories");

        this.continents = continents;
        this.player1 = player1;
        this.player2 = player2;

        initFrame();
        initComponents();
        initListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initFrame() {
        setLayout(null);
        setSize(new Dimension(Main.DEFAULT_FRAME_SIZE.width, Main.DEFAULT_FRAME_SIZE.height + UI_OFFSET));
        setResizable(false);
    }

    private void initComponents() {
        initMapPanel();

        this.map.setBounds(0, 0, getWidth(), getHeight());
        this.map.setVisible(true);
        this.map.setLayout(null);

        this.consoleLabel.setBounds(25, this.map.getHeight() - 65, this.map.getWidth() - 200, 35);
        this.suggestionLabel.setBounds(25, this.map.getHeight() - 95, this.map.getWidth() - 200, 35);

        this.nextRoundButton.setBounds(this.map.getWidth() - 185, getHeight() - 75, 150, 35);
        this.nextRoundButton.setText("Next Round");
        this.nextRoundButton.setVisible(false);

        this.cancelSelectionButton.setBounds(this.map.getWidth() - 375, getHeight() - 75, 150, 35);
        this.cancelSelectionButton.setText("Cancel selection");
        this.cancelSelectionButton.setVisible(false);

        this.turnOwnerLabel.setBounds(getWidth() / 2 - 35, this.map.getHeight() - 65, 150, 35);
        this.reinforcementLabel.setBounds(getWidth() / 2 - 35, this.map.getHeight() - 95, 150, 35);

        this.backgroundLabel = new JLabel();
        this.backgroundLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/Sprites/GameBackground.png")));
        this.backgroundLabel.setBounds(0, 0, getWidth(), getHeight());

        this.map.add(this.nextRoundButton);
        this.map.add(this.consoleLabel);
        this.map.add(this.turnOwnerLabel);
        this.map.add(this.reinforcementLabel);
        this.map.add(this.suggestionLabel);
        this.map.add(this.cancelSelectionButton);

        add(this.map);
    }

    private void initMapPanel() {
        this.map = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                //Draw Background.
                backgroundLabel.paint(g);

                //region Draw Continents.
                if (continents != null) {
                    for (Kontinent c : continents) {
                        if (c != null) {
                            c.drawConnections(g, Main.DEFAULT_FRAME_SIZE);
                        }
                    }

                    for (Kontinent c : continents) {
                        if (c != null) {
                            c.draw(g, This, player1.color, player2.color);
                        }
                    }
                }
                //endregion

                g.setColor(Color.black);
                g.drawRect(0, 0, getWidth(), getHeight());

                //region Update Buttons and Labels
                // Set visibility of nextRoundButton and update it.
                if (isPlayer1Turn ? player1.verstärkung.count == 0 : player2.verstärkung.count == 0) {
                    if ((isPlayer1Turn ? player1.getClass() != Computer.class : player2.getClass() != Computer.class) && currentState != StateOfPlaying.Expansion) {
                        nextRoundButton.setVisible(true);
                        nextRoundButton.updateUI();
                    } else if (currentState == StateOfPlaying.Expansion) {
                        nextRoundButton.updateUI();
                    }
                } else {
                    nextRoundButton.setVisible(false);
                }

                // Set new Text of turnOwnerLabel and update it.
                String turnOwnerText =
                        "<html>" +
                                "<span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" +
                                (isPlayer1Turn ? "1st Players turn" : "2nd Players turn") + "[___]" +
                                "</span>" +
                                "</html>";
                turnOwnerLabel.setText(turnOwnerText);
                turnOwnerLabel.updateUI();

                // Set new Text of reinforcementText and update it.
                String reinforcmentText =
                        "<html>" +
                                "<span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" +
                                "Reinforement: " + (isPlayer1Turn ? player1.verstärkung.count : player2.verstärkung.count) +
                                "</span>" +
                                "</html>";
                reinforcementLabel.setText(reinforcmentText);
                reinforcementLabel.updateUI();

                cancelSelectionButton.updateUI();
                //endregion

                //region Set Suggestions
                Territorium testTerr = getTerritoriumFromPosition(getCursorLocation(This));
                if (testTerr != null) {
                    if (testTerr.occupation.state == null) {
                        suggestionLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + "Claim " + testTerr.name + "</span></html>");
                    } else if (currentState == StateOfPlaying.SelectFirstTerritory) {
                        suggestionLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + "Select " + testTerr.name + "</span></html>");
                    } else if (currentState == StateOfPlaying.SelectSecondTerritory) {
                        if (testTerr.occupation.state == isPlayer1Turn) {
                            suggestionLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + "Move some reinformance to " + testTerr.name + "</span></html>");
                        } else {
                            suggestionLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + "Attack " + testTerr.name + "</span></html>");
                        }
                    }
                } else {
                    suggestionLabel.setText("");
                }

                suggestionLabel.updateUI();
                //endregion
            }
        };
    }

    private void initListeners() {
        this.map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!((isPlayer1Turn && player1.getClass() == Computer.class) || (!isPlayer1Turn && player2.getClass() == Computer.class))) {
                    Territorium testTerr = getTerritoriumFromPosition(getCursorLocation(This));
                    int selectedTerCount = 0;
                    if (currentState == StateOfPlaying.Expansion) {
                        if (continents != null) {
                            for (Kontinent kon :
                                    continents) {
                                selectedTerCount += kon.getCountOfOwnedTerritories(isPlayer1Turn);
                            }
                        }
                    }

                    if (testTerr != null) {
                        String before = consoleLabel.getText();
                        if (isPlayer1Turn) {
                            consoleLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + player1.update(currentState, testTerr, rand) + "</span></html>");
                        } else {
                            consoleLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + player2.update(currentState, testTerr, rand) + "</span></html>");
                        }

                        if (!before.equals(consoleLabel.getText())) {
                            if (currentState == StateOfPlaying.Expansion) {
                                checkIfAllcontinentsAreSet();

                                int check = 0;
                                if (continents != null) {
                                    for (Kontinent kon :
                                            continents) {
                                        check += kon.getCountOfOwnedTerritories(isPlayer1Turn);
                                    }
                                }

                                if (check != selectedTerCount) {
                                    nextRoundButton.setVisible(true);
                                }
                            } else if (currentState == StateOfPlaying.SelectFirstTerritory) {
                                cancelSelectionButton.setVisible(true);
                                currentState = StateOfPlaying.SelectSecondTerritory;
                            } else if (currentState == StateOfPlaying.SelectSecondTerritory) {
                                currentState = StateOfPlaying.SelectFirstTerritory;
                                cancelSelectionButton.setVisible(false);
                            }
                        }
                    }
                }
            }
        });

        this.nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == nextRoundButton) {
                    nextRoundButton();
                }
            }
        });

        Thread drawThread = new Thread() {
            public void run() {
                super.run();
                drawTimer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        map.repaint();
                    }
                });

                drawTimer.start();
            }
        };
        drawThread.start();

        Thread pcPlayerThread = new Thread() {
            @Override
            public void run() {
                super.run();
                pcTimer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Boolean winner = Winner();
                        if (winner != null) {
                            Main.setCurrentFrame(new WinnerScreen(winner));
                            pcTimer.stop();
                            drawTimer.stop();
                        } else {
                            if ((isPlayer1Turn && player1.getClass() == Computer.class) || (!isPlayer1Turn && player2.getClass() == Computer.class)) {
                                updateComputer();
                            }
                        }
                    }
                });

                pcTimer.start();
            }
        };
        pcPlayerThread.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                pcTimer.stop();
                drawTimer.stop();
            }
        });

        this.cancelSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelSelectionButton) {
                    player1.lastSelected = null;
                    player2.lastSelected = null;
                    currentState = StateOfPlaying.SelectFirstTerritory;
                    cancelSelectionButton.setVisible(false);
                    consoleLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + "Canceled moves" + "</span></html>");
                }
            }
        });
    }

    private Territorium getTerritoriumFromPosition(Point point) {
        if (this.continents == null) {
            return null;
        }

        for (Kontinent k : this.continents) {
            Territorium testTerr = k.getTerritoriumFromPosition(point, this);
            if (testTerr != null) {
                return testTerr;
            }
        }

        return null;
    }

    private void checkIfAllcontinentsAreSet() {
        if (this.continents != null) {
            for (Kontinent k : this.continents) {
                if (!k.allTerritoriesAreSet()) {
                    return;
                }
            }

            // TODO: Welcher State sollte in dieser Methode aufgerufen werden?
            //this.StatesOfPlaying++;

            // Temporär!
            this.currentState = StateOfPlaying.SelectFirstTerritory;

            this.cancelSelectionButton.setVisible(false);
        }
    }

    public int GetBonus(boolean player1) {
        if (this.continents == null) {
            return 0;
        }

        int toRet = 0;
        for (Kontinent con : this.continents) {
            toRet += (con.ownedBy(player1) ? con.getBonus() : 0) + con.getCountOfOwnedTerritories(player1) / 3;
        }

        return toRet < 3 ? 3 : toRet;
    }

    private Boolean Winner() {
        Boolean owner = this.continents.get(0).getTheOwner();
        if (owner == null) {
            return null;
        }

        for (int i = 1; i < this.continents.size(); i++) {
            if (owner != this.continents.get(i).ownedBy(true) || (!this.continents.get(i).ownedBy(false) && !this.continents.get(i).ownedBy(true))) {
                return null;
            }
        }

        return owner;
    }

    private int SecureCounter = 0;

    private void updateComputer() {
        /*try {
            Thread.sleep(500); // so the viewer sees whats going on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        boolean cantMove = false;
        Territorium nextTarget = null;
        SecureCounter++;
        if (this.currentState == StateOfPlaying.Expansion) {
            nextTarget = getFirstNotOwnedTerretorium();
            if (nextTarget == null) {
                this.currentState = StateOfPlaying.Reinforcing;
            }
        }

        if (NewRoundStarted) {
            player1.movedThisTurn = false;
            player2.movedThisTurn = false;
        }

        if ((this.currentState == StateOfPlaying.SelectFirstTerritory || this.currentState == StateOfPlaying.SelectSecondTerritory) && (this.isPlayer1Turn ? this.player1.verstärkung.count > 0 : this.player2.verstärkung.count > 0)) {
            nextTarget = getHeaviestTerritory();
        } else if (this.currentState == StateOfPlaying.SelectFirstTerritory) {
            nextTarget = getHeaviestTerritory();
        } else if (this.currentState == StateOfPlaying.SelectSecondTerritory) {
            if (isPlayer1Turn && player1.lastSelected != null && player1.lastSelected.neighbours.size() != 0) {
                for (Territorium neighb :
                        player1.lastSelected.neighbours) {
                    if (neighb.occupation.state != true && neighb.occupation.count < player1.lastSelected.occupation.count) {
                        nextTarget = neighb;
                        break;
                    }
                }
            } else if (!isPlayer1Turn && player2.lastSelected != null && player2.lastSelected.neighbours.size() != 0) {
                for (Territorium neighb :
                        player2.lastSelected.neighbours) {
                    if (neighb.occupation.state != false && neighb.occupation.count < player2.lastSelected.occupation.count) {
                        nextTarget = neighb;
                        break;
                    }
                }
            }

            if (nextTarget == null) { // Well be basically don't have any enemies left around us.
                // We now have to move our "big group" to another country (which should have enemies as neighbors)
                // And bet we don't get into an endless loop 0-0

                if (isPlayer1Turn && player1.lastSelected != null && player1.lastSelected.neighbours.size() != 0 && player1.lastSelected.occupation.count > 1) {
                    //nextTarget=player1.lastSelected.neighbours.get(0);
                    nextTarget = player1.lastSelected.getNextStepToEnemie(true, player1.lastSelected.occupation.count);
                } else if (!isPlayer1Turn && player2.lastSelected != null && player2.lastSelected.neighbours.size() != 0 && player2.lastSelected.occupation.count > 1) {
                    //nextTarget=player2.lastSelected.neighbours.get(0);
                    nextTarget = player2.lastSelected.getNextStepToEnemie(false, player2.lastSelected.occupation.count);
                }
            }
        }

        if (nextTarget == null || (isPlayer1Turn ? player1.movedThisTurn : player2.movedThisTurn)) {
            nextRoundButton();
            return;
        }

        if (isPlayer1Turn) {
            consoleLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + player1.update(currentState, nextTarget, rand) + "</span></html>");
        } else {
            consoleLabel.setText("<html><span style=\"color: " + hexColorCode(isPlayer1Turn ? player1.color : player2.color) + ";\">" + player2.update(currentState, nextTarget, rand) + "</span></html>");
        }

        NewRoundStarted = false;

        if (currentState == StateOfPlaying.Reinforcing) {
            currentState = StateOfPlaying.SelectFirstTerritory;
            return;
        }

        if (currentState == StateOfPlaying.Expansion) {
            checkIfAllcontinentsAreSet();
        }

        if (currentState == StateOfPlaying.SelectFirstTerritory) {
            if (nextTarget == null) {
                nextRoundButton();
                return;
            }

            currentState = StateOfPlaying.SelectSecondTerritory;
            return;
        }

        if (currentState == StateOfPlaying.SelectSecondTerritory) {
            currentState = StateOfPlaying.SelectFirstTerritory;
        }

        if (this.currentState == StateOfPlaying.Expansion) {
            nextRoundButton();
        }
    }

    private Territorium getHeaviestTerritory() {
        if (this.continents == null || this.continents.size() == 0) {
            return null;
        }

        Territorium toRet = null;
        for (Kontinent kon : this.continents) {
            Territorium testTerr = kon.getHeaviestTerritory(isPlayer1Turn);
            if (testTerr != null) {
                toRet = (toRet == null || toRet.occupation.count < testTerr.occupation.count) ? testTerr : toRet;
            }
        }

        return toRet;
    }

    private Territorium getFirstNotOwnedTerretorium() {
        for (Kontinent kon :
                this.continents) {
            Territorium toRet = kon.getFirstNotOwnedTerritorium();
            if (toRet != null) {
                return toRet;
            }
        }

        return null;
    }

    private boolean NewRoundStarted;

    private void nextRoundButton() {
        currentState = currentState.ordinal() > 1 ? StateOfPlaying.Reinforcing : StateOfPlaying.Expansion;
        isPlayer1Turn = !isPlayer1Turn;
        NewRoundStarted = true;

        if (currentState == StateOfPlaying.Expansion || ((isPlayer1Turn && player1.getClass() == Computer.class) || (!isPlayer1Turn && player2.getClass() == Computer.class))) {
            nextRoundButton.setVisible(false);
        } else if (currentState != StateOfPlaying.Expansion) {
            nextRoundButton.setVisible(true);
        }

        cancelSelectionButton.setVisible(false);

        player1.lastSelected = null;
        player2.lastSelected = null;

        if (currentState == StateOfPlaying.Reinforcing) {
            if (isPlayer1Turn) {
                player1.addBonus(GetBonus(isPlayer1Turn));
            } else {
                player2.addBonus(GetBonus(isPlayer1Turn));
            }

            currentState = StateOfPlaying.SelectFirstTerritory;
        }
    }
}
