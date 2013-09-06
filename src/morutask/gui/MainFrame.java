/*
 * MoruTask
 * Copyright (c) 2013, Mohand Andel
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of MoruTask or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package morutask.gui;

import com.leclercb.commons.api.coder.exc.FactoryCoderException;
import morutask.gui.actions.ActionAddTask;
import morutask.gui.actions.ActionDeleteTask;
import morutask.gui.actions.ActionReportBug;
import morutask.gui.list.CategoryList;
import morutask.gui.list.categoriesList.Category;
import morutask.gui.table.Filter.TableFilter;
import morutask.gui.table.TaskTable;
import morutask.gui.trayIcons.TrayIconManager;
import morutask.gui.utils.ComponentFactory;
import morutask.gui.utils.FormBuildHelper;
import morutask.gui.utils.ImageUtils;
import morutask.gui.utils.ViewUtils;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.sort.TableSortController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mohand
 */
public class MainFrame extends JXFrame {

    private JXSearchField searchField;
    private JComboBox sortComboBox;
    private JButton addButton;
    private JButton deleteButton;
    private JScrollPane scrollPane;
    private CategoryList categoryList;
    private TaskTable taskTable;
    private JPanel panel;
    private TableSortController rowsorter;
    private FormBuildHelper formBuildHelper;
    private JSplitPane splitPane;


    public MainFrame() {
        setIconImage(ImageUtils.getImage("Morulogo.png"));
        this.initialize();


        getContentPane().add(splitPane, BorderLayout.CENTER);

    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setSize(950, 550);

        this.initMenuBar();

        setTitle("MoruTask 0.4 Alpha");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {

                    int choose = JOptionPane.showOptionDialog(MainFrame.this, "would you like to keep the program running in background ?", null
                            , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ok", "Exit"}, "Ok");


                    Main.saveModels();

                    if (choose == 0) {
                        TrayIconManager.getInstance().fireShowTrayIcon("Main", null);
                        dispose();
                    } else {
                        System.exit(0);
                    }

                } catch (FileNotFoundException | FactoryCoderException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AWTException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });

        formBuildHelper = new FormBuildHelper();

        scrollPane = new JScrollPane();

        categoryList = new CategoryList();
        categoryList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                Category selectedValue = (Category) categoryList.getSelectedValue();
                TableFilter.setFilter(selectedValue.getFilter());
                rowsorter.sort();
            }
        });
        taskTable = new TaskTable();

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(taskTable);

        rowsorter = (TableSortController) ViewUtils.getInstance().getTaskTable().getRowSorter();

        splitPane = ComponentFactory.createThinJSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setRightComponent(scrollPane);
        splitPane.setLeftComponent(categoryList);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);

        searchField = new JXSearchField("Search");
        searchField.setColumns(20);
        searchField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (searchField.getText().length() == 0)
                    rowsorter.setRowFilter(new TableFilter());
                else
                    rowsorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
            }

        });
        searchField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField) e.getSource();
                if (source.getText().equalsIgnoreCase("Search")) {
                    source.setText(null);
                }
            }

        });

        sortComboBox = ComponentFactory.createSortComboBox();


        addButton = new JButton("Add");
        addButton.addActionListener(new ActionAddTask());

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionDeleteTask());


        panel = new JPanel(new FlowLayout(5, 5, 5));
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(formBuildHelper.appendLabeltoComponent("Sort", sortComboBox, true));
        panel.add(searchField);

        add(panel, BorderLayout.PAGE_START);
        add(splitPane, BorderLayout.CENTER);

    }

    private void initMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItemRB = new JMenuItem("Report Bug");
        menuItemRB.addActionListener(new ActionReportBug("https://sourceforge.net/p/morutask/bugs/?source=navbar"));

        JMenuItem menuItemFR = new JMenuItem("Report Feature Requests");
        menuItemFR.addActionListener(new ActionReportBug("https://sourceforge.net/p/morutask/feature-requests/?source=navbar"));

        JMenuItem menuItemLC = new JMenuItem("Leave a comment ");
        menuItemLC.addActionListener(new ActionReportBug("https://sourceforge.net/projects/morutask/reviews/new"));


        JMenu menu = new JMenu("About");

        menu.add(menuItemRB);
        menu.add(menuItemFR);
        menu.add(menuItemLC);
        menuBar.add(menu);

        setJMenuBar(menuBar);

    }

}
