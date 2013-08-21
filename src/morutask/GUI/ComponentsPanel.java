package morutask.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 7/21/13
 * Time: 3:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ComponentsPanel extends JPanel {

    ArrayList<JToggleButton> list;

    public ComponentsPanel()
    {
        setLayout(new FlowLayout(5,5,5));
       setSize(50,300);
        setBorder(BorderFactory.createLineBorder(Color.gray));
        list = new ArrayList<>();
    }

    public void addComponent(JComponent component , String name, String tipText)
    {
        Component[] components = component.getComponents();
        for(Component comp : components)
        {
            if(comp instanceof JComponent && tipText != null)
            {
                ((JComponent) comp).setToolTipText(tipText);
            }

        }
        component.setToolTipText(tipText);
        //component.setActionCommand(commondName);
        //component.addItemListener(itemListener);
        add(component);
    }

    public void addToggleButton(Icon icon ,String name , String tipText , String commondName , ItemListener itemListener)
    {
        final JToggleButton button = new JToggleButton(name);
        button.setToolTipText(tipText);
        button.setActionCommand(commondName);
        button.setIcon(icon);
        button.addItemListener(itemListener);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(itemEvent.getStateChange() == ItemEvent.SELECTED)
                {

                    //button.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));

                }
                else {
                    //button.setBorder(null);
                }
            }
        });

        list.add(button);
        add(button);
    }

    public JToggleButton getToggleButton(String commondName)
    {
        for (JToggleButton button : list)
        {
            if(button.getActionCommand().equals(commondName))
            {
                 return button;
            }
        }

        return null;
    }
}
