package Forms;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Territorium;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Random;

/**
 * Created by Thomas on 05/01/2016.
 */
public class BattleScreen {
    private static boolean Working;

    public static void Fight(Territorium terr1, Territorium terr2, Armee armee1, Armee armee2, Random rand){
        if(terr1==null||armee1==null||terr2==null||armee2==null||rand==null||Working){
            return;
        }

        Working=true;

        JFrame battleFrame=new JFrame(terr1.Name+" VS "+terr2.Name);
        battleFrame.setLayout(null);
        battleFrame.setSize(500,250);
        battleFrame.setResizable(false);

        JLabel vsLabel=new JLabel();
        vsLabel.setText("VS");
        vsLabel.setBounds(battleFrame.getWidth()/2-10,10,20,35);
        battleFrame.add(vsLabel);

        JLabel terr1Label=new JLabel();
        terr1Label.setText(terr1.Name+" ("+armee1.Count+")");
        terr1Label.setBounds(35,10,100,35);
        battleFrame.add(terr1Label);

        JLabel terr2Label=new JLabel();
        terr2Label.setText(terr2.Name+" ("+armee2.Count+")");
        terr2Label.setBounds(battleFrame.getWidth()-70,10,100,35);
        battleFrame.add(terr2Label);

        JLabel terr1CastOfDice=new JLabel();
        terr1CastOfDice.setBounds(35,50,100,300);
        battleFrame.add(terr1CastOfDice);

        JLabel terr2CastOfDice=new JLabel();
        terr2CastOfDice.setBounds(battleFrame.getWidth()-70,50,100,300);
        battleFrame.add(terr2CastOfDice);

        JButton fightButton=new JButton();
        fightButton.setText("FIGHT");
        fightButton.setBounds(battleFrame.getWidth()/2-50,battleFrame.getHeight()/2-20,100,40);
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == fightButton) {
                    int t1 = 0, nextT1 = 0, t2 = 0, nextT2 = 0;
                    for (int i = 0; i < (armee1.Count > 3 ? 3 : armee1.Count); i++) {
                        int temp = rand.nextInt(6) + 1;
                        if (temp > t1) {
                            nextT1 = t1;
                            t1 = temp;
                        }
                        else if(temp==t1){
                            nextT1=t1;
                        }
                    }

                    for (int i = 0; i < (armee2.Count > 1 ? 2 : armee1.Count); i++) {
                        int temp = rand.nextInt(6) + 1;
                        if (temp > t2) {
                            nextT2 = t2;
                            t2 = temp;
                        }
                        else if(temp==t2){
                            nextT2=t2;
                        }
                    }

                    if (t1 > t2) {
                        armee2.Count--;
                    }
                    else if(t2>t1){
                        armee1.Count--;
                    }
                    else{
                        armee1.Count--;
                    }

                    if (!(armee2.Count == 1 || armee1.Count == 1)) {
                        if (nextT1 > nextT2) {
                            armee2.Count--;
                        }
                        else if(nextT1>nextT2){
                            armee1.Count--;
                        }
                        else {
                            armee1.Count--;
                        }
                    }

                    terr1CastOfDice.setText((armee1.Count>2?"Rolled:  "+t1+" | "+nextT1:"Rolled:  "+t1));
                    terr2CastOfDice.setText((armee2.Count>2?"Rolled:  "+t2+" | "+nextT2:"Rolled:  "+t2));
                    terr1Label.setText(terr1.Name+" ("+armee1.Count+")");
                    terr2Label.setText(terr2.Name+" ("+armee2.Count+")");

                    if(armee1.Count==0){
                        Working=false;
                        battleFrame.setVisible(false);
                        battleFrame.dispose();
                    }
                    else if(armee2.Count==0){
                        Working=false;
                        terr2.Occupation=armee1;
                        terr2.Occupation.Count++;
                        battleFrame.setVisible(false);
                        battleFrame.dispose();
                    }
                }
            }
        });

        battleFrame.add(fightButton);

        JButton cancelButton=new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setBounds(battleFrame.getWidth()/2-50,battleFrame.getHeight()-75,100,35);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==cancelButton){
                    Working=false;
                    terr1.Occupation.Count+=armee1.Count;
                    battleFrame.setVisible(false);
                    battleFrame.dispose();
                }
            }
        });

        battleFrame.add(cancelButton);

        battleFrame.setVisible(true);
    }
}
