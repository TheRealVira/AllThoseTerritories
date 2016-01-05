package Forms;

import AllThoseTerritories.Armee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Thomas on 05/01/2016.
 */
public class ArmeeTransferScreen extends JFrame {
    private JComboBox ArmeeCount=new JComboBox();
    private JButton Accept=new JButton(),Cancel=new JButton();
    private JLabel Description=new JLabel();
    private Armee Armee1,Armee2;
    private int MaxCount;
    private JFrame This=this;
    public boolean Finished;

    public ArmeeTransferScreen(String header, String description, Armee armee1, Armee armee2,boolean armee2CanBe0){
        super(header);

        setSize(500,250);
        setResizable(false);
        setLayout(null);
        MaxCount=armee1.Count+armee2.Count+(armee2CanBe0?1:0);
        for (int i=1;i<MaxCount;i++){
            ArmeeCount.addItem(i);
        }

        ArmeeCount.setBounds(35,this.getHeight()/2-35,this.getWidth()-70,35);
        Accept.setBounds(35,this.getHeight()-70,75,35);
        Accept.setText("Accept");
        Cancel.setBounds(this.getWidth()-110,this.getHeight()-70,75,35);
        Cancel.setText("Cancel");
        Description.setText(description);
        Description.setBounds( 35, 35, this.getWidth()-70,35);

        add(this.ArmeeCount);
        add(this.Accept);
        add(this.Cancel);
        add(this.Description);
        this.setVisible(true);

        this.Armee1=armee1;
        this.Armee2=armee2;

        this.Accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==Accept){
                    Armee1.Count=(int)ArmeeCount.getSelectedItem();
                    Armee2.Count=MaxCount-Armee1.Count-(armee2CanBe0?1:0);
                    This.dispatchEvent(new WindowEvent(This,WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        this.Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==Cancel){
                    This.dispatchEvent(new WindowEvent(This,WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Finished=true;
            }
        });
    }
}
