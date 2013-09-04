package morutask.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 7/21/13
 * Time: 3:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ComponentsBar extends JPanel {

    ArrayList<JToggleButton> list;

    public ComponentsBar() {
        setLayout(new FlowLayout(5, 5, 5));
        setSize(50, 300);
        setBorder(BorderFactory.createLineBorder(Color.gray));
        list = new ArrayList<>();
    }

    public void addComponent(JComponent component, String tipText) {
        Component[] components = component.getComponents();
        for (Component comp : components) {
            if (comp instanceof JComponent && tipText != null) {
                ((JComponent) comp).setToolTipText(tipText);
            }

        }
        component.setToolTipText(tipText);
        add(component);
    }

    public void addToggleButton(Icon icon, String name, String tipText, String commondName, ItemListener itemListener) {
        final JToggleButton button = new JToggleButton(name);
        button.setToolTipText(tipText);
        button.setActionCommand(commondName);
        button.setIcon(icon);
        button.addItemListener(itemListener);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        list.add(button);
        add(button);
    }

    public JToggleButton getToggleButton(String commondName) {
        for (JToggleButton button : list) {
            if (button.getActionCommand().equals(commondName)) {
                return button;
            }
        }

        return null;
    }
}
