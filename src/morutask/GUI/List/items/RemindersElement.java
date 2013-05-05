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
package morutask.GUI.List.items;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import morutask.GUI.threads.reminder.ReminderList;
import morutask.GUI.utils.viewUtils;
import morutask.models.Task;
import morutask.models.TaskFactory;

/**
 *
 * @author Mohand Andel <MohandAndel@gmail.com>
 */
public class RemindersElement extends AbstractElement<String> implements PropertyChangeListener{
    
    ReminderList instance;
    
    public RemindersElement()
    {
        TaskFactory.getInstance().addPropertyChangeListener(Task.PROP_REMINDER,this);
        instance = ReminderList.getInstance();
       setItemsToDisplay(new String[]{"ReminderList"});
    }

    @Override
    public int getSize() {
        return instance.getNotifiedTasks().size();
    }

    @Override
    public String toDisplayinList(String str) {
        String concat = str.concat("(" + instance.getNotifiedTasks().size() + ")");
        return concat;
    }

    @Override
    public boolean include(Task t) {
        
        if(t.HasReminder())
            return true;
        
        return false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        viewUtils.getInstance().getGroupList().fireContentsChanged();
    }
    
}
