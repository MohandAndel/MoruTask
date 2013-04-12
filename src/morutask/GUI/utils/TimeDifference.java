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

import com.leclercb.commons.api.utils.DateUtils;
import java.util.Calendar;
import java.util.HashMap;
import morutask.models.Task;

/**
 *
 * @author mohand
 */
public class TimeDifference {
    
    public final static String ALL = "ALL";
    public final static String TODAY = "TODAY";
    public final static String TOMORROW = "TOMORROW";
    public final static String THIS_WEEK = "THIS_WEEK";
    public final static String LATER = "LATER" ;
    
    private static HashMap<String,Integer> days = new HashMap<>();
    
    public static String GetTaskday(Task t)
    {
        int x = (int) DateUtils.getDiffInDays(Calendar.getInstance(), t.getStartDate(), false);
        int i = 1 ;
        initMaps();
        
        if (!t.getModelStatus().isEndUserStatus())
        {
            i = -1;
        }
        days.put(ALL, days.get(ALL) + i);

        if (x >= 7) {
            days.put(LATER, days.get(LATER) + i);
            return LATER;
        }

        if (x > 1 ){
            days.put(THIS_WEEK, days.get(THIS_WEEK) + i);
            return THIS_WEEK;
        }

        if (x == 1) {
            days.put(TOMORROW, days.get(TOMORROW) + i);
            return TOMORROW;
        }
        if (x == 0) {
            days.put(TODAY, days.get(TODAY) + i);
            return TODAY;
        }

        return null;

    }
    
    public static void initMaps()
    {
        if (days.isEmpty())
        {
        days.put(ALL,0);
        days.put(TODAY, 0);
        days.put(TOMORROW, 0);
        days.put(THIS_WEEK, 0);
        days.put(LATER,0);
        }
    }
    
    public static int GetValue(String key)
    {
        //initMaps();
        if (days.containsKey(key))
        {
        return days.get(key);
        }
        
        return 0;
    }
    
}
