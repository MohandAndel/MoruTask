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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import morutask.GUI.utils.FormBuildHelper;
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
     private JCheckBox completeTaskCheckBox;
     private JCheckBox reminderCheckBox;
     private DateChooser dateChooser;
     private JSpinner timeSpinner;
     private JButton OkButton;
     private JButton CancelButton;
     private JPanel panel;
     
     private FormBuildHelper formBuildHelper;
     private static EditTaskPanel instance;
     
     private EditTaskPanel()
     {
        this.initialize();
     }
     
     public static EditTaskPanel getinstance()
     {
         if (instance == null)
                instance = new EditTaskPanel();
         
         instance.setValues();
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
         PriorityComboBox = new JComboBox (new DefaultComboBoxModel(TaskPriority.values()));
         completeTaskCheckBox = new JCheckBox("Set task as complete");
         reminderCheckBox = new JCheckBox("Set Reminder");
         
         dateChooser = new DateChooser(new Date());
         timeSpinner = new JSpinner(new SpinnerDateModel());
         timeEditor = new JSpinner.DateEditor(timeSpinner,Main.getSettings().getStringProperty("Date.time_Format"));
         timeSpinner.setEditor(timeEditor);
         timeSpinner.setValue(new Date());
         
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
                 task.setCompleted(completeTaskCheckBox.isSelected());
                 task.SetReminder(reminderCheckBox.isSelected());
                 task.setPriority((TaskPriority) PriorityComboBox.getSelectedItem());
             }
         });
         
         
         CancelButton = new JButton("Cancel");
         CancelButton.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
                 viewUtils.getInstance().getTaskEditor().StopEditing();
             }
         });
         JPanel ButtonsPanel = new JPanel(new FlowLayout());
         ButtonsPanel.add(OkButton);
         ButtonsPanel.add(CancelButton);
         
         panel = new JPanel(new GridBagLayout());
         GridBagConstraints c = new GridBagConstraints();
         c.fill = GridBagConstraints.HORIZONTAL;
         c.gridx=0;
         c.gridy=0;
         c.gridwidth = GridBagConstraints.RELATIVE;
         panel.add(titleField,c);
        
         
         c = new GridBagConstraints();
         c.gridx=6;
         c.gridy=0;
         panel.add(formBuildHelper.appendLabeltoComponent("Priority", PriorityComboBox, true),c);
         
         c = new GridBagConstraints();
         //c.gridx=0;
         c.gridy=1;
         panel.add(formBuildHelper.appendLabeltoComponent("Start Date", dateChooser, true),c);

         //c = new GridBagConstraints();
         //c.gridx=1;
         c.gridy=1;
         panel.add(timeSpinner,c);
         
         //c = new GridBagConstraints();
         c.gridy=1;
         panel.add(reminderCheckBox,c);
         
         //c = new GridBagConstraints();
         c.gridy=1;
         panel.add(completeTaskCheckBox,c);
         
         add(panel,BorderLayout.PAGE_START);
         add(NoteArea,BorderLayout.CENTER);
         add(ButtonsPanel,BorderLayout.PAGE_END);
         
     }
     
     private void setValues()
     {
        task = viewUtils.getInstance().getSelectedTask();

        this.titleField.setText(task.getTitle());
        this.NoteArea.setText(task.getNote());
        PriorityComboBox.setSelectedItem(task.getPriority());
        completeTaskCheckBox.setSelected(task.isCompleted());
        reminderCheckBox.setSelected(task.HasReminder());

        if (task.getStartDate() == null) {
            timeSpinner.setValue(new Date());
            dateChooser.setDate(new Date());
        } else {
            dateChooser.setCalendar(task.getStartDate());
            timeSpinner.setValue(task.getStartDate().getTime());
        }
    }
    
}
