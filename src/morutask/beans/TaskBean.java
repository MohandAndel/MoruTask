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
package morutask.beans;

import java.util.Calendar;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import morutask.converters.CalendarConverter;
import morutask.converters.TimerConverter;
import morutask.models.ModelId;
import morutask.models.ModelType;
import morutask.models.Timer;
import morutask.models.enums.TaskPriority;
import morutask.models.enums.TaskRepeatFrom;
import morutask.models.enums.TaskStatus;

public class TaskBean extends AbstractModelParentBean {
	
//	@XStreamAlias("tags")
//	@XStreamConverter(TagListConverter.class)
//	private TagList tags;
	
	
	
	
	@XStreamAlias("completed")
	private boolean completed;
	
	@XStreamAlias("completedon")
	@XStreamConverter(CalendarConverter.class)
	private Calendar completedOn;
	
	@XStreamAlias("startdate")
	@XStreamConverter(CalendarConverter.class)
	private Calendar startDate;
	
	@XStreamAlias("startdatereminder")
	private int startDateReminder;
	
	@XStreamAlias("duedate")
	@XStreamConverter(CalendarConverter.class)
	private Calendar dueDate;
	
	@XStreamAlias("hasreminder")
	private boolean withReminder;
	
	@XStreamAlias("repeat")
	private String repeat;
	
	@XStreamAlias("repeatfrom")
	private TaskRepeatFrom repeatFrom;
	
	@XStreamAlias("status")
	private TaskStatus status;
	
	@XStreamAlias("length")
	private int length;
	
	@XStreamAlias("timer")
	@XStreamConverter(TimerConverter.class)
	private Timer timer;
	
	@XStreamAlias("priority")
	private TaskPriority priority;
	
	
	@XStreamAlias("note")
	private String note;
	
        
	public TaskBean() {
		this((ModelId) null);
	}
	
	public TaskBean(ModelId modelId) {
		super(modelId);
		

		this.setCompleted(false);
		this.setCompletedOn(null);
		this.setStartDate(null);
		this.setStartDateReminder(0);
		this.setDueDate(null);
		this.setReminder(false);
		this.setRepeat(null);
		this.setRepeatFrom(TaskRepeatFrom.DUE_DATE);
		this.setStatus(TaskStatus.NONE);
		this.setTimer(new Timer());
		this.setPriority(TaskPriority.LOW);
		this.setNote(null);
	}
	
	public TaskBean(TaskBean bean) {
		super(bean);
		
		this.setCompleted(bean.isCompleted());
		this.setCompletedOn(bean.getCompletedOn());
		this.setStartDate(bean.getStartDate());
		this.setStartDateReminder(bean.getStartDateReminder());
		this.setDueDate(bean.getDueDate());
		this.setReminder(bean.HasReminder());
		this.setRepeat(bean.getRepeat());
		this.setRepeatFrom(bean.getRepeatFrom());
		this.setStatus(bean.getStatus());
		this.setTimer(bean.getTimer());
		this.setPriority(bean.getPriority());
		this.setNote(bean.getNote());
	}
	
	@Override
	public ModelType getModelType() {
		return ModelType.TASK;
	}
	
	
	
	
	public boolean isCompleted() {
		return this.completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public Calendar getCompletedOn() {
		return this.completedOn;
	}
	
	public void setCompletedOn(Calendar completedOn) {
		this.completedOn = completedOn;
	}
	
	public Calendar getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
        @Deprecated
	public int getStartDateReminder() {
		return this.startDateReminder;
	}
	
        @Deprecated
	public void setStartDateReminder(int startDateReminder) {
		this.startDateReminder = startDateReminder;
	}
	
	public Calendar getDueDate() {
		return this.dueDate;
	}
	
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	
	public boolean HasReminder() {
		return this.withReminder;
	}
	
	public void setReminder(boolean reminder) {
		this.withReminder = reminder;
	}
	
	public String getRepeat() {
		return this.repeat;
	}
	
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	
	public TaskRepeatFrom getRepeatFrom() {
		return this.repeatFrom;
	}
	
	public void setRepeatFrom(TaskRepeatFrom repeatFrom) {
		this.repeatFrom = repeatFrom;
	}
	
	public TaskStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public TaskPriority getPriority() {
		return this.priority;
	}
	
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}
	
	
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
}
