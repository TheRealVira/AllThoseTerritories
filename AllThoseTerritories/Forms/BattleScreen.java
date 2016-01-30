package Forms;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Territorium;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

/**
 * Created by Thomas on 05/01/2016.
 */
public class BattleScreen {
    private static boolean Working;

    public static void fight(Territorium terr1, Territorium terr2, Armee armee1, Armee armee2, Random rand) {
        if (terr1 == null || armee1 == null || terr2 == null || armee2 == null || rand == null || Working) {
            return;
        }

        Working = true;

        // JDialog instead of JFrame, since the battleFrame is essentially a sub-frame of the calling Frame and should always be on top of it.
        JDialog battleFrame = new JDialog(Main.Main.CurrentFrame, terr1.name + " VS " + terr2.name);
        battleFrame.setLayout(null);
        battleFrame.setSize(500, 250);
        battleFrame.setResizable(false);
        battleFrame.setLocationRelativeTo(Main.Main.CurrentFrame);

        battleFrame.setContentPane(new JLabel(new ImageIcon(BattleScreen.class.getClassLoader().getResource("resources/Sprites/FightingBackground.png"))));

        //region Set Labels
        JLabel vsLabel = new JLabel();
        vsLabel.setText("VS");
        vsLabel.setBounds(battleFrame.getWidth() / 2 - 10, 10, 20, 35);
        battleFrame.add(vsLabel);

        JLabel terr1Label = new JLabel();
        terr1Label.setText(terr1.name + " (" + armee1.count + ")");
        terr1Label.setBounds(35, 10, 350, 35);
        battleFrame.add(terr1Label);

        JLabel terr2Label = new JLabel();
        terr2Label.setText(terr2.name + " (" + armee2.count + ")");
        terr2Label.setBounds(battleFrame.getWidth() - 200, 10, 350, 35);
        battleFrame.add(terr2Label);

        JLabel terr1CastOfDice = new JLabel();
        terr1CastOfDice.setBounds(35, 50, 100, 300);
        battleFrame.add(terr1CastOfDice);

        JLabel terr2CastOfDice = new JLabel();
        terr2CastOfDice.setBounds(battleFrame.getWidth() - 70, 50, 100, 300);
        battleFrame.add(terr2CastOfDice);
        //endregion

        //region Init Fight Button.
        JButton fightButton = new JButton();
        fightButton.setText("FIGHT");
        fightButton.setBounds(battleFrame.getWidth() / 2 - 50, battleFrame.getHeight() / 2 - 20, 100, 40);
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == fightButton) {
                    battleLogic(armee1, armee2, rand, terr1CastOfDice, terr2CastOfDice);

                    terr1Label.setText(terr1.name + " (" + armee1.count + ")");
                    terr2Label.setText(terr2.name + " (" + armee2.count + ")");

                    if (armee1.count < 1) {
                        Working = false;
                        battleFrame.setVisible(false);
                        battleFrame.dispose();
                    } else if (armee2.count < 1) {
                        Working = false;
                        terr2.occupation = armee1;
                        battleFrame.setVisible(false);
                        battleFrame.dispose();
                    }
                }
            }
        });

        battleFrame.add(fightButton);
        //endregion

        //region Init CancelButton
        JButton cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setBounds(battleFrame.getWidth() / 2 - 50, battleFrame.getHeight() - 75, 100, 35);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelButton) {
                    Working = false;
                    terr1.occupation.count += armee1.count;
                    battleFrame.setVisible(false);
                    battleFrame.dispose();
                }
            }
        });

        battleFrame.add(cancelButton);
        //endregion

        //region Add Window Listener
        battleFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Working = false;
                terr1.occupation.count += armee1.count;
                battleFrame.setVisible(false);
                battleFrame.dispose();
            }
        });
        //endregion

        battleFrame.setVisible(true);
    }

    public static void fastBattle(Armee armee1, Armee armee2, Random rand) {
        while (armee1.count > 0 && armee2.count > 0) {
            battleLogic(armee1, armee2, rand, null, null);
        }
    }

    private static void battleLogic(Armee armee1, Armee armee2, Random rand, JLabel rolled1, JLabel rolled2) {
        int t1 = 0, nextT1 = 0, t2 = 0, nextT2 = 0;
        for (int i = 0; i < (armee1.count > 3 ? 3 : armee1.count); i++) {
            int temp = rand.nextInt(6) + 1;
            if (temp > t1) {
                nextT1 = t1;
                t1 = temp;
            } else if (temp == t1) {
                nextT1 = t1;
            }
        }

        for (int i = 0; i < (armee2.count > 1 ? 2 : armee2.count); i++) {
            int temp = rand.nextInt(6) + 1;
            if (temp > t2) {
                nextT2 = t2;
                t2 = temp;
            } else if (temp == t2) {
                nextT2 = t2;
            }
        }

        if (armee2.count > 1 && armee1.count > 1) {
            if (nextT1 > nextT2) {
                armee2.count--;
            } else if (nextT1 > nextT2) {
                armee1.count--;
            } else {
                armee1.count--;
            }
        }

        if (t1 > t2) {
            armee2.count--;
        } else if (t2 > t1) {
            armee1.count--;
        } else {
            armee1.count--;
        }

        if (rolled1 != null) {
            rolled1.setText((armee1.count > 2 ? "Rolled:  " + t1 + " | " + nextT1 : "Rolled:  " + t1));
        }
        if (rolled2 != null) {
            rolled2.setText((armee2.count > 2 ? "Rolled:  " + t2 + " | " + nextT2 : "Rolled:  " + t2));
        }
    }
}
