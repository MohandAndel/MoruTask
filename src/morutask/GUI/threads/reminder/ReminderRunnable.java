/*
 * TaskUnifier
 * Copyright (c) 2011, Benjamin Leclerc
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
 *   - Neither the name of TaskUnifier or the names of its
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
package morutask.GUI.threads.reminder;

import morutask.GUI.utils.TaskUtils;
import morutask.GUI.utils.MTSwingUtilities;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import com.leclercb.commons.api.event.listchange.ListChangeEvent;
import com.leclercb.commons.api.event.listchange.ListChangeListener;
import morutask.models.Task;
import morutask.models.TaskFactory;


class ReminderRunnable implements Runnable, PropertyChangeListener, ListChangeListener {
	
	private static final long SLEEP_TIME = 10 * 1000;
	
        private ReminderList notifiedTasks;
	
	public ReminderRunnable() {

              notifiedTasks = ReminderList.getInstance();
              
		TaskFactory.getInstance().addListChangeListener(this);

		TaskFactory.getInstance().addPropertyChangeListener(this);
	}
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(SLEEP_TIME);
				
//				Boolean showRemindersEnabled = Main.getSettings().getBooleanProperty(
//						"general.show_reminders.enabled");
//				if (showRemindersEnabled == null || !showRemindersEnabled)
//					continue;
				
				if (Synchronizing.getInstance().isSynchronizing())
					continue;
				
				MTSwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						boolean reminders = false;
						List<Task> tasks = TaskFactory.getInstance().getList();
						
						synchronized (this) {
							for (final Task task : tasks) {
								if (ReminderRunnable.this.notifiedTasks.getNotifiedTasks().contains(task))
									continue;
								
								if (!task.getModelStatus().isEndUserStatus() || !task.HasReminder())
									continue;
								
								if (TaskUtils.isInStartDateReminderZone(task)
										|| TaskUtils.isInDueDateReminderZone(task)) {
									ReminderRunnable.this.notifiedTasks.getNotifiedTasks().remove(task);
									ReminderRunnable.this.notifiedTasks.getNotifiedTasks().add(task);
									
									
									Toolkit.getDefaultToolkit().beep();
									
									reminders = true;
								}
							}
						}
						
						if (reminders) {
                                                    ReminderDialog.getInstance().viewTask(notifiedTasks.getNotifiedTasks().get(notifiedTasks.getNotifiedTasks().size()-1));
						}
					}
					
				});
			} catch (InterruptedException e) {
				
			}
		}
	}

    public List<Task> getNotifiedTasks() {
        return notifiedTasks.getNotifiedTasks();
    }
        
        
	
	@Override
	public void listChange(ListChangeEvent evt) {
		if (evt.getChangeType() == ListChangeEvent.VALUE_REMOVED) {
			
			this.notifiedTasks.getNotifiedTasks().remove(evt.getValue());
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Task.PROP_DUE_DATE)
				|| evt.getPropertyName().equals(Task.PROP_DUE_DATE_REMINDER)
				|| evt.getPropertyName().equals(Task.PROP_START_DATE)
				|| evt.getPropertyName().equals(Task.PROP_START_DATE_REMINDER)
				|| evt.getPropertyName().equals(Task.PROP_COMPLETED)
				|| !((Task) evt.getSource()).getModelStatus().isEndUserStatus()) {
			
			this.notifiedTasks.getNotifiedTasks().remove(evt.getSource());
		}
	}
	
}
