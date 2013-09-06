package morutask.gui.trayIcons;

import com.leclercb.commons.api.coder.exc.FactoryCoderException;
import com.leclercb.commons.api.properties.PropertyMap;
import morutask.gui.Main;
import morutask.gui.table.TaskTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: mohand
 * Date: 9/1/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class MenuPanel extends JPanel implements MenuElement {

    private JScrollPane scrollPane;
    private TaskTable taskTable;

    public MenuPanel() {
        setPreferredSize(new Dimension(525, 400));
        setSize(400, 700);


        setLayout(new BorderLayout());
        try {
            PropertyMap setting = new PropertyMap();
            Main.loadSettings(setting);
            Main.setSettings(setting);
            Main.loadModels();
        } catch (FactoryCoderException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        init();
        initButtoms();
        setVisible(true);
    }

    public void init() {
        //taskTable = ViewUtils.getInstance().getTaskTable();
        taskTable = new TaskTable();//new JTable(new modelTable());
        //taskTable.setSize(200,200);

        scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void initButtoms() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        add(exitButton, BorderLayout.PAGE_END);
    }

    @Override
    public void processMouseEvent(MouseEvent mouseEvent, MenuElement[] menuElements, MenuSelectionManager menuSelectionManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void processKeyEvent(KeyEvent keyEvent, MenuElement[] menuElements, MenuSelectionManager menuSelectionManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void menuSelectionChanged(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MenuElement[] getSubElements() {
        return new MenuElement[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Component getComponent() {
        return this;
    }
}
