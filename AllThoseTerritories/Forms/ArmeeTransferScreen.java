package Forms;

import AllThoseTerritories.Armee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


// Extends JDialog instead of JFrame, since the TransferScreen is essentially a sub-frame of the calling Frame and should always be on top of it.
public class ArmeeTransferScreen extends JDialog {
    private JComboBox armyCountBox = new JComboBox();
    private JButton acceptButton = new JButton();
    private JButton cancelButton = new JButton();
    private JLabel descriptionLabel = new JLabel();
    private Armee army1, army2;
    private int maxCount;
    private JDialog This = this;
    public boolean isFinished;

    public ArmeeTransferScreen(String header, String description, Armee army1, Armee army2, boolean army1AsBorder) {
        super(Main.Main.CurrentFrame, header);

        this.army1 = army1;
        this.army2 = army2;

        initFrame();
        initComponents(description, army1AsBorder);
        initListeners(army1AsBorder);
    }

    public void initFrame() {
        setSize(500, 275);
        setResizable(false);
        setLayout(null);
        setContentPane(new JLabel(new ImageIcon(BattleScreen.class.getClassLoader().getResource("resources/Sprites/TransferBg.png"))));
        setLocationRelativeTo(Main.Main.CurrentFrame);
    }

    public void initComponents(String description, boolean army1AsBorder) {
        maxCount = army1.count + army2.count + (army1AsBorder ? 1 : 0);
        for (int i = army1AsBorder ? army1.count : 1; i < maxCount; i++) {
            armyCountBox.addItem(i);
        }

        armyCountBox.setBounds(35, this.getHeight() / 2 - 35, this.getWidth() - 70, 35);
        acceptButton.setBounds(35, this.getHeight() - 70, 75, 35);
        acceptButton.setText("Accept");
        cancelButton.setBounds(this.getWidth() - 110, this.getHeight() - 70, 75, 35);
        cancelButton.setText("Cancel");
        descriptionLabel.setText(description);
        descriptionLabel.setBounds(40, 40, this.getWidth() - 70, 35);

        add(this.armyCountBox);
        add(this.acceptButton);
        add(this.cancelButton);
        add(this.descriptionLabel);
        this.setVisible(true);


    }

    public void initListeners(boolean army1AsBorder) {
        this.acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == acceptButton) {
                    army1.count = (int) armyCountBox.getSelectedItem();
                    army2.count = maxCount - army1.count - (army1AsBorder ? 1 : 0);
                    This.dispatchEvent(new WindowEvent(This, WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelButton) {
                    This.dispatchEvent(new WindowEvent(This, WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isFinished = true;
            }
        });
    }
}
