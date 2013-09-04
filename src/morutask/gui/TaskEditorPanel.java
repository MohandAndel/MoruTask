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

import com.leclercb.commons.api.utils.DateUtils;
import morutask.gui.timer.TimerField;
import morutask.gui.utils.ComponentFactory;
import morutask.gui.utils.FormBuildHelper;
import morutask.gui.utils.ImageUtils;
import morutask.gui.utils.ViewUtils;
import morutask.models.Task;
import morutask.models.enums.TaskPriority;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mohand
 */
public class TaskEditorPanel extends JPanel {

    private Task task;
    private JSpinner.DateEditor timeEditor;

    private JTextField titleField;
    private JTextArea NoteArea;
    private JComboBox PriorityComboBox;
    private DateChooser dateChooser;
    private JSpinner timeSpinner;
    private JButton OkButton;
    private JButton CancelButton;
    private JPanel panel;
    private TimerField timerField;
    private ComponentsBar componentsBar;

    private FormBuildHelper formBuildHelper;
    private static TaskEditorPanel instance;

    private TaskEditorPanel() {
        componentsBar = ComponentFactory.createComponentsPanel();
        this.initialize();
    }

    public static TaskEditorPanel getinstance() {
        if (instance == null)
            instance = new TaskEditorPanel();

        return instance;
    }

    private void initialize() {
        setSize(300, 300);
        setLayout(new BorderLayout(10, 10));
        formBuildHelper = new FormBuildHelper();

        titleField = new JTextField();
        titleField.setColumns(20);
        NoteArea = new JTextArea();
        NoteArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        PriorityComboBox = new JComboBox(new DefaultComboBoxModel(TaskPriority.values()));

        dateChooser = new DateChooser(new Date());
        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeEditor = new JSpinner.DateEditor(timeSpinner, Main.getSettings().getStringProperty("Date.time_Format"));
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        ;

        timerField = new TimerField(false);

        OkButton = new JButton("OK");
        OkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Calendar instance = dateChooser.getCalendar();
                DateUtils.removeTime(instance);
                Date date = timeEditor.getModel().getDate();
                instance.setTime(date);

                instance.set(dateChooser.getCalendar().get(Calendar.YEAR), dateChooser.getCalendar().get(Calendar.MONTH), dateChooser.getCalendar().get(Calendar.DAY_OF_MONTH));

                System.out.println(instance.getTime());

                task.setTitle(titleField.getText());
                task.setNote(NoteArea.getText());
                task.setStartDate(instance);
                task.setPriority((TaskPriority) PriorityComboBox.getSelectedItem());
                task.setTimer(timerField.getTimer());
                ViewUtils.getInstance().getTaskEditor().stopEditing();
            }
        });


        CancelButton = new JButton("Cancel");
        CancelButton.setActionCommand("CancelButton");
        CancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ViewUtils.getInstance().getTaskEditor().stopEditing();
            }
        });
        JPanel ButtonsPanel = new JPanel(new FlowLayout());
        ButtonsPanel.add(OkButton);
        ButtonsPanel.add(CancelButton);

        //******
        componentsBar.addComponent(dateChooser, "Set Start Date");
        componentsBar.addComponent(timeSpinner, "Set Start Time");
        componentsBar.addComponent(PriorityComboBox, "Set Priority of Task");
        componentsBar.addComponent(timerField, "Set Timer");

        componentsBar.addToggleButton(ImageUtils.getImage("alarm.png", 25, 25), null, "set to Enable the Reminder", "reminder", new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    task.SetReminder(true);
                } else {
                    task.SetReminder(false);
                }
            }
        });
        componentsBar.addToggleButton(ImageUtils.getImage("task.png", 25, 25), null, "set to make this Task as Completed", "complete", new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    task.setCompleted(true);
                } else {
                    task.setCompleted(false);
                }
            }
        });


        //******
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setSize(getWidth(), getHeight());
        panel.add(componentsBar);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(titleField);

        add(panel, BorderLayout.PAGE_START);
        add(NoteArea, BorderLayout.CENTER);
        add(ButtonsPanel, BorderLayout.PAGE_END);

    }

    private void setValues() {

        this.titleField.setText(task.getTitle());
        this.NoteArea.setText(task.getNote());
        PriorityComboBox.setSelectedItem(task.getPriority());
        componentsBar.getToggleButton("complete").setSelected(task.isCompleted());
        componentsBar.getToggleButton("reminder").setSelected(task.HasReminder());

        timerField.setTimer(task.getTimer());

        if (task.getStartDate() == null) {
            timeSpinner.setValue(new Date());
            dateChooser.setDate(new Date());
        } else {
            dateChooser.setCalendar(task.getStartDate());
            timeSpinner.setValue(task.getStartDate().getTime());
        }
    }

    public void setTask(Task t) {
        this.task = t;
        setValues();
    }

    public void setCurrentTaskToEditor()
    {
        Task selectedTask = ViewUtils.getInstance().getSelectedTask();
        this.task = selectedTask;
        setValues();
    }

}


