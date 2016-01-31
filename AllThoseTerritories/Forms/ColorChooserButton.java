package Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Main.Tools.negateColor;


public class ColorChooserButton extends JButton {
    public Color MyColor;

    public ColorChooserButton(Color current, String title) {
        MyColor = current;
        setForeground(negateColor(MyColor));
        setBackground(MyColor);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyColor = JColorChooser.showDialog(null, title, MyColor);
                setForeground(negateColor(MyColor));
                setBackground(MyColor);
            }
        });
    }
}
