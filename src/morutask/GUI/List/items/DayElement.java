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

import com.leclercb.commons.api.utils.DateUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import morutask.GUI.utils.TimeDifference;
import morutask.GUI.utils.viewUtils;
import morutask.models.Task;
import morutask.models.TaskFactory;

/**
 *
 * @author mohand
 */
public class DayElement extends AbstractElement<Days> implements PropertyChangeListener{

    public DayElement()
    {
        TaskFactory.getInstance().addPropertyChangeListener(Task.PROP_START_DATE,this);
        TaskFactory.getInstance().addPropertyChangeListener(Task.PROP_MODEL_STATUS,this);
        
        Scan();
        setUnitItems(Days.values());
    }
    
    public void Scan()
    {
        for(Task tt : TaskFactory.getInstance().getList())
        {
            TimeDifference.GetTaskday(tt);
        }
    }

    @Override
    public boolean include(Task t) {

        Days aday = (Days) viewUtils.getInstance().getSelecteditem().getDataUnitItem();

        if (aday == Days.ALL) {
            return true;
        }

        int daysBetween;

        daysBetween = (int) DateUtils.getDiffInDays(Calendar.getInstance(), t.getStartDate(), false);

        if ((t.getStartDate() == null) || (daysBetween == -1))
        {
            return false;
        }

        if (aday == Days.TODAY && daysBetween == 0) {
            return true;
        }

        if (aday == Days.TOMORROW && daysBetween == 1) {
            return true;
        }

        if (aday == Days.THIS_WEEK && daysBetween > 1 && daysBetween <= 6) {
            return true;
        }

        if (aday == Days.LATER && daysBetween >= 6) {
            return true;
        }

        return false;
    }

    @Override
    public int getSize() {
        return Days.values().length;
    }

    @Override
    public String toDisplayinList(String str) {
        
        String ss = str.concat("(" + String.valueOf(TimeDifference.GetValue(str) + ")"));
        return ss;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        if ( evt.getSource() instanceof Task)
        {
            if ( (evt.getOldValue() instanceof Calendar )&& (DateUtils.getDiffInDays((Calendar)evt.getOldValue(),(Calendar)evt.getNewValue(), false) ==0))
                 return;
            
            Task tt = (Task) evt.getSource();
            TimeDifference.GetTaskday(tt);
            viewUtils.getInstance().getGroupList().fireContentsChanged();
        }
        
    }
    
}
