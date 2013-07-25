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
package morutask.models;

import com.leclercb.commons.api.event.listchange.ListChangeEvent;
import com.leclercb.commons.api.event.listchange.ListChangeListener;
import com.leclercb.commons.api.utils.CheckUtils;
import com.leclercb.commons.api.utils.DateUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import morutask.GUI.Main;
import morutask.beans.ModelBean;
import morutask.beans.TaskBean;
//import morutask.models.TaskGroup.TaskItem;
import morutask.models.enums.TaskPriority;
import morutask.models.enums.TaskRepeatFrom;
import morutask.models.enums.TaskStatus;

public class Task extends AbstractModelParent<Task> implements ModelNote, PropertyChangeListener, ListChangeListener {
	
	public static final String PROP_TAGS = "tags";
	public static final String PROP_FOLDER = "folder";
	public static final String PROP_CONTEXT = "context";
	public static final String PROP_GOAL = "goal";
	public static final String PROP_LOCATION = "location";
	public static final String PROP_PROGRESS = "progress";
	public static final String PROP_COMPLETED = "completed";
	public static final String PROP_COMPLETED_ON = "completedOn";
	public static final String PROP_START_DATE = "startDate";
	public static final String PROP_START_DATE_REMINDER = "startDateReminder";
	public static final String PROP_DUE_DATE = "dueDate";
	public static final String PROP_DUE_DATE_REMINDER = "dueDateReminder";
	public static final String PROP_REPEAT = "repeat";
	public static final String PROP_REPEAT_FROM = "repeatFrom";
	public static final String PROP_STATUS = "status";
	public static final String PROP_LENGTH = "length";
	public static final String PROP_TIMER = "timer";
	public static final String PROP_PRIORITY = "priority";
	public static final String PROP_STAR = "star";
	public static final String PROP_CONTACTS = "contacts";
	public static final String PROP_TASKS = "tasks";
	public static final String PROP_FILES = "files";
	public static final String PROP_REMINDER = "Reminder";
        
        
	private double progress;
	private boolean completed;
        private boolean withReminder;
	private Calendar completedOn;
	private Calendar startDate;
	private int startDateReminder;
	private Calendar dueDate;
	private int dueDateReminder;
	private String repeat;
	private TaskRepeatFrom repeatFrom;
	private TaskStatus status;
	private int length;
	private Timer timer;
	private TaskPriority priority;
	//private boolean star;
	private String note;
	//private TaskGroup tasks;
	
	protected Task(TaskBean bean, boolean loadReferenceIds) {
		this(bean.getModelId(), bean.getTitle());
		this.loadBean(bean, loadReferenceIds);
	}
	
	protected Task(String title) {
		this(new ModelId(), title);
	}
	
	protected Task(ModelId modelId, String title) {
		super(modelId, title);
		
		
		this.setCompleted(false);
		this.setCompletedOn(null);
		this.setStartDate(null);
		this.setDueDate(null);
		this.SetReminder(false);
		this.setRepeat(null);
		this.setRepeatFrom(TaskRepeatFrom.DUE_DATE);
		this.setStatus(TaskStatus.NONE);
		this.setTimer(new Timer());
		this.setPriority(TaskPriority.LOW);
		this.setNote(null);
        this.setLength(0);

		this.getFactory().register(this);
	}
	
	@Override
	public Task clone(ModelId modelId) {
		Task task = this.getFactory().create(modelId, this.getTitle());
		
		task.setParent(this.getParent());
		task.setCompleted(this.isCompleted());
		task.setCompletedOn(this.getCompletedOn());
		task.setStartDate(this.getStartDate());
		task.setDueDate(this.getDueDate());
                task.SetReminder(this.HasReminder());
		task.setRepeat(this.getRepeat());
		task.setRepeatFrom(this.getRepeatFrom());
		task.setStatus(this.getStatus());
		task.setTimer(this.getTimer());
		task.setPriority(this.getPriority());
		task.setNote(this.getNote());
        task.setLength(this.getLength());

		// After all other setXxx methods
		task.setOrder(this.getOrder());
		task.addProperties(this.getProperties());
		task.setModelStatus(this.getModelStatus());
		task.setModelCreationDate(Calendar.getInstance());
		task.setModelUpdateDate(Calendar.getInstance());
		
		return task;
	}
	
	@Override
	public TaskFactory<Task, TaskBean> getFactory() {
		return TaskFactory.getInstance();
	}
	
	@Override
	public ModelType getModelType() {
		return ModelType.TASK;
	}
	
	@Override
	public void loadBean(ModelBean b, boolean loadReferenceIds) {
		CheckUtils.isNotNull(b);
		CheckUtils.isInstanceOf(b, TaskBean.class);
		
		TaskBean bean = (TaskBean) b;
                
		
		this.setStartDate(bean.getStartDate());
		this.setDueDate(bean.getDueDate());
		this.SetReminder(bean.HasReminder());
		this.setRepeat(bean.getRepeat());
		this.setRepeatFrom(bean.getRepeatFrom());
		this.setStatus(bean.getStatus());
		this.setTimer(bean.getTimer());
		this.setPriority(bean.getPriority());
		this.setNote(bean.getNote());
        this.setLength(bean.getLength());

		// Set completed at the end (repeat)
		this.setCompleted(bean.isCompleted());
		
		if (bean.isCompleted() && bean.getCompletedOn() != null)
			this.setCompletedOn(bean.getCompletedOn());
		
		super.loadBean(bean, loadReferenceIds);
	}
	
	@Override
	public TaskBean toBean() {
		TaskBean bean = (TaskBean) super.toBean();
		
		bean.setStartDate(this.getStartDate());
		bean.setDueDate(this.getDueDate());
		bean.setReminder(this.HasReminder());
		bean.setRepeat(this.getRepeat());
		bean.setRepeatFrom(this.getRepeatFrom());
		bean.setStatus(this.getStatus());
		bean.setTimer(this.getTimer());
		bean.setPriority(this.getPriority());
		bean.setNote(this.getNote());
        bean.setLength(this.getLength());

		// Set completed at the end (repeat)
		bean.setCompleted(this.isCompleted());
		bean.setCompletedOn(this.getCompletedOn());
		
		return bean;
	}
	
	
	
	public boolean isCompleted() {
		return this.completed;
	}
	
	public void setCompleted(boolean completed) {
		if (!this.checkBeforeSet(this.isCompleted(), completed))
			return;
		
		boolean oldCompleted = this.completed;
		this.completed = completed;
		
		Calendar oldCompletedOn = this.completedOn;
		if (this.completed)
			this.completedOn = Calendar.getInstance();
		else
			this.completedOn = null;
		
		double oldProgress = this.progress;
		if (this.completed)
			this.progress = 1;
		else
			this.progress = 0;
		
		this.updateProperty(PROP_COMPLETED_ON, oldCompletedOn, this.completedOn);
		this.updateProperty(PROP_PROGRESS, oldProgress, this.progress);
		this.updateProperty(PROP_COMPLETED, oldCompleted, completed);
	}
	
	public Calendar getCompletedOn() {
		return DateUtils.cloneCalendar(this.completedOn);
	}
	
	public void setCompletedOn(Calendar completedOn) {
		if (!this.checkBeforeSet(this.getCompletedOn(), completedOn))
			return;
		
		Calendar oldCompletedOn = this.completedOn;
		this.completedOn = DateUtils.cloneCalendar(completedOn);
		
		boolean oldCompleted = this.completed;
		if (this.completedOn == null)
			this.completed = false;
		else
			this.completed = true;
		
		double oldProgress = this.progress;
		if (this.completedOn == null)
			this.progress = 0;
		else
			this.progress = 1;
		
		this.updateProperty(PROP_COMPLETED, oldCompleted, this.completed);
		this.updateProperty(PROP_PROGRESS, oldProgress, this.progress);
		this.updateProperty(PROP_COMPLETED_ON, oldCompletedOn, completedOn);
	}
	
	public boolean isOverDue(boolean dateOnly) {
		if (this.getDueDate() == null)
			return false;
		
		Calendar today = Calendar.getInstance();
		if (today.compareTo(this.getDueDate()) > 0) {
			if (dateOnly) {
				if (!org.apache.commons.lang3.time.DateUtils.isSameDay(
						today,
						this.getDueDate()))
					return true;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isDueToday(boolean dateOnly) {
		if (this.getDueDate() == null)
			return false;
		
		Calendar today = Calendar.getInstance();
		if (org.apache.commons.lang3.time.DateUtils.isSameDay(
				today,
				this.getDueDate())) {
			if (dateOnly) {
				return true;
			} else {
				if (today.compareTo(this.getDueDate()) <= 0)
					return true;
			}
		}
		
		return false;
	}
	
	public Calendar getStartDate() {
		return DateUtils.cloneCalendar(this.startDate);
	}
	
	public void setStartDate(Calendar startDate) {
		if (!this.checkBeforeSet(this.getStartDate(), startDate))
			return;
		
		Calendar oldStartDate = this.startDate;
		this.startDate = DateUtils.cloneCalendar(startDate);
		this.updateProperty(PROP_START_DATE, oldStartDate, startDate);
	}
	
        @Deprecated
	public int getStartDateReminder() {
		return this.startDateReminder;
	}
	
        @Deprecated
	public void setStartDateReminder(int startDateReminder) {
		CheckUtils.isPositive(startDateReminder);
		
		if (!this.checkBeforeSet(this.getStartDateReminder(), startDateReminder))
			return;
		
		int oldStartDateReminder = this.startDateReminder;
		this.startDateReminder = startDateReminder;
		this.updateProperty(
				PROP_START_DATE_REMINDER,
				oldStartDateReminder,
				startDateReminder);
	}
	
	public Calendar getDueDate() {
		return DateUtils.cloneCalendar(this.dueDate);
	}
	
	public void setDueDate(Calendar dueDate) {
		if (!this.checkBeforeSet(this.getDueDate(), dueDate))
			return;
		
		Calendar oldDueDate = this.dueDate;
		this.dueDate = DateUtils.cloneCalendar(dueDate);
		this.updateProperty(PROP_DUE_DATE, oldDueDate, dueDate);
	}
	
        @Deprecated
	public int getDueDateReminder() {
		return this.dueDateReminder;
	}
	
        @Deprecated
	public void setDueDateReminder(int dueDateReminder) {
		CheckUtils.isPositive(dueDateReminder);
		
		if (!this.checkBeforeSet(this.getDueDateReminder(), dueDateReminder))
			return;
		
		int oldDueDateReminder = this.dueDateReminder;
		this.dueDateReminder = dueDateReminder;
		this.updateProperty(
				PROP_DUE_DATE_REMINDER,
				oldDueDateReminder,
				dueDateReminder);
	}
        
        public void SetReminder(boolean reminder)
        {
            boolean oldReminder = this.withReminder;
            this.withReminder = reminder;
            this.updateProperty(PROP_REMINDER, oldReminder, reminder);
        }
        
        public boolean HasReminder()
        {
            return this.withReminder;
        }
        
	
	public String getRepeat() {
		return this.repeat;
	}
	
	public void setRepeat(String repeat) {
		if (!this.checkBeforeSet(this.getRepeat(), repeat))
			return;
		
		if (repeat != null)
			repeat = repeat.trim();
		
		String oldRepeat = this.repeat;
		this.repeat = repeat;
		this.updateProperty(PROP_REPEAT, oldRepeat, repeat);
	}
	
	public TaskRepeatFrom getRepeatFrom() {
		return this.repeatFrom;
	}
	
	public void setRepeatFrom(TaskRepeatFrom repeatFrom) {
		CheckUtils.isNotNull(repeatFrom);
		
		if (!this.checkBeforeSet(this.getRepeatFrom(), repeatFrom))
			return;
		
		TaskRepeatFrom oldRepeatFrom = this.repeatFrom;
		this.repeatFrom = repeatFrom;
		this.updateProperty(PROP_REPEAT_FROM, oldRepeatFrom, repeatFrom);
	}
	
	public TaskStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(TaskStatus status) {
		CheckUtils.isNotNull(status);
		
		if (!this.checkBeforeSet(this.getStatus(), status))
			return;
		
		TaskStatus oldStatus = this.status;
		this.status = status;
		this.updateProperty(PROP_STATUS, oldStatus, status);
	}
	
	
	public Timer getTimer() {
		return new Timer(this.timer);
	}
	
	public void setTimer(Timer timer) {
		CheckUtils.isNotNull(timer);
		
		if (!this.checkBeforeSet(this.getTimer(), timer))
			return;
		
		Timer oldTimer = this.timer;
		this.timer = new Timer(timer);
		this.updateProperty(PROP_TIMER, oldTimer, timer);
	}
	
	public TaskPriority getPriority() {
		return this.priority;
	}
	
	public void setPriority(TaskPriority priority) {
		CheckUtils.isNotNull(priority);
		
		if (!this.checkBeforeSet(this.getPriority(), priority))
			return;
		
		TaskPriority oldPriority = this.priority;
		this.priority = priority;
		this.updateProperty(PROP_PRIORITY, oldPriority, priority);
	}
	
	
	@Override
	public String getNote() {
		return this.note;
	}
	
	@Override
	public void setNote(String note) {
		if (!this.checkBeforeSet(this.getNote(), note))
			return;
		
		if (note != null)
			note = note.trim();
		
		String oldNote = this.note;
		this.note = note;
		this.updateProperty(PROP_NOTE, oldNote, note);
	}

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        CheckUtils.isPositive(length);

        if (!this.checkBeforeSet(this.getLength(), length))
            return;

        int oldLength = this.length;
        this.length = length;
        this.updateProperty(PROP_LENGTH, oldLength, length);
    }
	
	
	@Override
	public void listChange(ListChangeEvent event) {
		
		//if (event.getValue() instanceof TaskItem) {
			//this.updateProperty(PROP_TASKS, null, this.tasks);
		//}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		//if (event.getSource() instanceof TaskItem) {
			//this.updateProperty(PROP_TASKS, null, this.tasks);
		//}
		
	}
	
	@Override
	public String toDetailedString() {
            
		StringBuffer buffer = new StringBuffer(super.toDetailedString());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		
		if (this.getParent() != null)
			buffer.append("Parent: " + this.getParent() + "\n");
		buffer.append("Completed: " + this.isCompleted() + "\n");
		if (this.getCompletedOn() != null)
			buffer.append("Completed On: "
					+ dateFormat.format(this.getCompletedOn().getTime())
					+ "\n");
		if (this.getStartDate() != null)
			buffer.append("Start Date: "
					+ dateFormat.format(this.getStartDate().getTime())
					+ "\n");
		if (this.getDueDate() != null)
			buffer.append("Due Date: "
					+ dateFormat.format(this.getDueDate().getTime())
					+ "\n");
		buffer.append("With Reminder : " + this.HasReminder() + "\n");
		buffer.append("Repeat: " + this.getRepeat() + "\n");
		if (this.getRepeatFrom() != null)
			buffer.append("Repeat From: " + this.getRepeatFrom() + "\n");
		buffer.append("Status: " + this.getStatus() + "\n");
		buffer.append("Timer: " + this.getTimer() + "\n");
		buffer.append("Priority: " + this.getPriority() + "\n");
		buffer.append("Note: " + this.getNote() + "\n");
		
		return buffer.toString();
	}

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        
        buffer.append(this.getTitle() + "      ");
        buffer.append(this.getPriority() + "      ");
        if (this.getStartDate() !=null)
        {
            //dateFormat.applyPattern(Main.getSettings().getStringProperty("Date.date_Format")  + "    "  + Main.getSettings().getStringProperty("Date.time_Format"));
            dateFormat.applyPattern("dd-MMM-yyy"  + "    "  + Main.getSettings().getStringProperty("Date.time_Format"));
            buffer.append(dateFormat.format(this.getStartDate().getTime()) + "      ");
        }
        return buffer.toString();
    }
        
        

}
