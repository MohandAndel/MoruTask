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
package morutask.GUI.utils;

import morutask.GUI.List.GroupList;
import morutask.GUI.List.items.itemUnit;
import morutask.GUI.Table.TableEditor;
import morutask.GUI.Table.TaskTable;
import morutask.models.Task;

/**
 *
 * @author mohand
 */
public class viewUtils {
    
     private static viewUtils INSTANCE = null;
     
     private TaskTable taskTable;
     private GroupList groupList;
     private String viewtype;

    public static String ViewType_Task = "TaskView";
    public static String ViewType_Calendar = "CalendarView";

    private viewUtils()
    {
      
    }
   
	public static viewUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new viewUtils();
		}
		
		return INSTANCE;
	}

    public TaskTable getTaskTable() {
        return taskTable;
    }
    
    public TableEditor getTaskEditor()
    {
        return (TableEditor) taskTable.getCellEditor();
    }

    public void setTaskTable(TaskTable taskTable) {
        this.taskTable = taskTable;
    }
    
    public Task getSelectedTask()
    {
        return this.taskTable.getSelectedTask();
    }
    
    public void setSelectedTask(Task t)
    {
        this.taskTable.setSelectedTask(t);
    }
    
    public void setSelectedTaskAndStartEdit(Task task)
    {
        this.taskTable.setSelectedTaskAndStartEdit(task);
    }

    public GroupList getGroupList() {
        return groupList;
    }

    public void setGroupList(GroupList groupList) {
        this.groupList = groupList;
    }
        
      public itemUnit getSelecteditem()
      {
         return this.groupList.getSelecteditem();
      }

    public String getCurrentViewType()
    {
         return this.viewtype;
    }

    public void setCurrentViewType(String view)
    {
        this.viewtype = view;
    }
    
}
