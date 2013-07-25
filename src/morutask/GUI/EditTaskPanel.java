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
package morutask.GUI;

import com.leclercb.commons.api.utils.DateUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

import morutask.GUI.Timer.TimerField;
import morutask.GUI.utils.FormBuildHelper;
import morutask.GUI.utils.ImageUtils;
import morutask.GUI.utils.MTSwingUtilities;
import morutask.GUI.utils.viewUtils;
import morutask.models.Task;
import morutask.models.enums.TaskPriority;

/**
 *
 * @author mohand
 */
public class EditTaskPanel extends JPanel{
    
     private Task task;
     private JSpinner.DateEditor timeEditor ;
     
     private JTextField titleField;
     private JTextArea NoteArea;
     private JComboBox PriorityComboBox;
    // private JCheckBox completeTaskCheckBox;
     //private JCheckBox reminderCheckBox;
     private DateChooser dateChooser;
     private JSpinner timeSpinner;
     private JButton OkButton;
     private JButton CancelButton;
     private JPanel panel;
     private TimerField timerField;
    private ComponentsPanel componentsPanel;

     private FormBuildHelper formBuildHelper;
     private static EditTaskPanel instance;
     
     private EditTaskPanel()
     {
         componentsPanel = new ComponentsPanel();
        this.initialize();
     }
     
     public static EditTaskPanel getinstance()
     {
         if (instance == null)
              instance = new EditTaskPanel();
         
         //instance.setValues();
         return instance;
     }
     
     private void initialize()
     {
         setSize(300, 300);
         setLayout(new BorderLayout(10,10));
         formBuildHelper = new FormBuildHelper();
         
         titleField = new JTextField();
         titleField.setColumns(20);
         NoteArea = new JTextArea();
         NoteArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         PriorityComboBox = new JComboBox (new DefaultComboBoxModel(TaskPriority.values()));
         //completeTaskCheckBox = new JCheckBox("Set task as complete");
         //reminderCheckBox = new JCheckBox("Set Reminder");
         
         dateChooser = new DateChooser(new Date());
         timeSpinner = new JSpinner(new SpinnerDateModel());
         timeEditor = new JSpinner.DateEditor(timeSpinner,Main.getSettings().getStringProperty("Date.time_Format"));
         timeSpinner.setEditor(timeEditor);
         timeSpinner.setValue(new Date());;

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
                 //task.setCompleted(completeTaskCheckBox.isSelected());
                 //task.SetReminder(reminderCheckBox.isSelected());
                 task.setPriority((TaskPriority) PriorityComboBox.getSelectedItem());
                 task.setTimer(timerField.getTimer());
                 viewUtils.getInstance().getTaskEditor().StopEditing();
             }
         });
         
         
         CancelButton = new JButton("Cancel");
         CancelButton.setActionCommand("CancelButton");
         CancelButton.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
                 viewUtils.getInstance().getTaskEditor().StopEditing();
             }
         });
         JPanel ButtonsPanel = new JPanel(new FlowLayout());
         ButtonsPanel.add(OkButton);
         ButtonsPanel.add(CancelButton);

         //******
         componentsPanel.addComponent(dateChooser,null,"Set Start Date");
         componentsPanel.addComponent(timeSpinner,null,"Set Start Time");
         componentsPanel.addComponent(PriorityComboBox,null,"Set Priority of Task");
         componentsPanel.addComponent(timerField,null,"Set Timer");

         componentsPanel.addToggleButton(ImageUtils.getImage("alarm.png",25,25),null,"set to Enable the Reminder","reminder",new ItemListener() {
             @Override
             public void itemStateChanged(ItemEvent itemEvent) {
                 if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                 {
                     task.SetReminder(true);
                 }
                 else {
                     task.SetReminder(false);
                 }
             }
         });
         componentsPanel.addToggleButton(ImageUtils.getImage("task.png",25,25),null,"set to make this Task as Completed","complete",new ItemListener() {
             @Override
             public void itemStateChanged(ItemEvent itemEvent) {
                 if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                 {
                     task.setCompleted(true);
                 }
                 else {
                     task.setCompleted(false);
                 }
             }
         });


         //******
          panel = new JPanel();
         panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
         panel.setSize(getWidth(), getHeight());
         panel.add(componentsPanel);
         panel.add(Box.createRigidArea(new Dimension(0,6)));
         panel.add(titleField);

         add(panel,BorderLayout.PAGE_START);
         add(NoteArea,BorderLayout.CENTER);
         add(ButtonsPanel,BorderLayout.PAGE_END);
         
     }
     
     private void setValues()
     {
        //task = viewUtils.getInstance().getSelectedTask();
          //if(task==null) return;

        this.titleField.setText(task.getTitle());
        this.NoteArea.setText(task.getNote());
        PriorityComboBox.setSelectedItem(task.getPriority());
        //completeTaskCheckBox.setSelected(task.isCompleted());
        //reminderCheckBox.setSelected(task.HasReminder());
         componentsPanel.getToggleButton("complete").setSelected(task.isCompleted());
         componentsPanel.getToggleButton("reminder").setSelected(task.HasReminder());

         timerField.setTimer(task.getTimer());

        if (task.getStartDate() == null) {
            timeSpinner.setValue(new Date());
            dateChooser.setDate(new Date());
        } else {
            dateChooser.setCalendar(task.getStartDate());
            timeSpinner.setValue(task.getStartDate().getTime());
        }
    }

    public void setTask(Task t)
    {
      this.task = t;
        setValues();
    }

    public JButton getCancelButton() {
        return CancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        CancelButton = cancelButton;
    }

   private class itemhandle implements ItemListener
    {

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {

            //JToggleButton source = (JToggleButton) itemEvent.getSource();

            if(itemEvent.getStateChange()==ItemEvent.SELECTED)
            {
                task.SetReminder(true);
            }
            else {task.SetReminder(false); }
        }
    }
}


