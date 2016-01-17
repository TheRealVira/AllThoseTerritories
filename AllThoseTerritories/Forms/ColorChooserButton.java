package Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Main.Tools.NegateColor;

/**
 * Created by Thomas on 03/01/2016.
 */
public class ColorChooserButton extends JButton {
    public Color MyColor;

    public ColorChooserButton(Color current, String title){
        MyColor=current;
        setForeground(NegateColor(MyColor));
        setBackground(MyColor);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyColor=JColorChooser.showDialog(null,title,MyColor);
                setForeground(NegateColor(MyColor));
                setBackground(MyColor);
            }
        });
    }
}
