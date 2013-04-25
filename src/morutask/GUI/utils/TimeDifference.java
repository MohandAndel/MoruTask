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

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import morutask.models.Task;

/**
 *
 * @author mohand
 */
public class TimeDifference {
    
    public final static byte ALL = 0;
    public final static byte TODAY = 1;
    public final static byte TOMORROW = 2;
    public final static byte THIS_WEEK = 3;
    public final static byte LATER = 4 ;
    
    private static HashMap<Byte, Byte> days = new HashMap<>();
    private static Field[] fields = TimeDifference.class.getFields();

    public static String GetTaskday(Task t)
    {
        int x = (int) DateUtils.getDiffInDays(Calendar.getInstance(), t.getStartDate(), false);
        byte i = 1 ;
        initMaps();
        
        if (!t.getModelStatus().isEndUserStatus())
        {
            i = -1;
        }
        days.put(ALL,(byte) (days.get(ALL) + i ));

        if (x >= 7) {
            days.put(LATER, (byte) (days.get(LATER) + i));
            return getFieldName(LATER);
        }

        if (x > 1 ){
            days.put(THIS_WEEK, (byte) (days.get(THIS_WEEK) + i));
            return getFieldName(THIS_WEEK);
        }

        if (x == 1) {
            days.put(TOMORROW, (byte) (days.get(TOMORROW) + i));
            return getFieldName(TOMORROW);
        }
        if (x == 0) {
            days.put(TODAY, (byte) (days.get(TODAY) + i));
            return getFieldName(TODAY);
        }

        return null;

    }
    
    public static void initMaps()
    {
        if (days.isEmpty())
        {
            byte x = 0;
        days.put(ALL,x);
        days.put(TODAY,x);
        days.put(TOMORROW,x);
        days.put(THIS_WEEK,x);
        days.put(LATER,x);
        }
    }
    
    public static int GetValue(String key)
    {
        Field f;

        try
        {
            f = TimeDifference.class.getField(key);

        //initMaps();

        if (days.containsKey(f.getByte(null)))
        {
        return days.get(f.getByte(null));
        }

        } catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getFieldName(int i)
    {
         //Field[] fields = TimeDifference.class.getFields();
        return fields[i].getName();
    }
    
}
