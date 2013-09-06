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
package morutask.gui.settings;

import com.leclercb.commons.api.properties.PropertyMap;
import morutask.gui.Main;
import morutask.models.enums.TaskPriority;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mohand Andel <MohandAndel@gmail.com>
 */
public class settings {

    private PropertyMap properties;

    public settings() {
        try {
            Main.main(null);
        } catch (Exception ex) {
            Logger.getLogger(settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        properties = Main.getSettings();
    }

    public void setDefultTitleofTask(String str) {
        properties.setStringProperty("tasktable.tasks.selected.value", str);
    }

    public void setPriorityColors(Color top, Color high, Color medium, Color low, Color negative) {
        TaskPriority t = null;

        properties.setColorProperty("theme.color.Priority." + t.TOP, top);
        properties.setColorProperty("theme.color.Priority." + t.HIGH, high);
        properties.setColorProperty("theme.color.Priority." + t.MEDIUM, medium);
        properties.setColorProperty("theme.color.Priority." + t.LOW, low);
        properties.setColorProperty("theme.color.Priority." + t.NEGATIVE, negative);
    }

    public void setRowHigh(int x) {
        properties.setIntegerProperty("tasktable.row.high", x);
    }

    public void setDateFormat(String dateFormat) {
        properties.setSimpleDateFormatProperty("Date.Date_Format", new SimpleDateFormat(dateFormat));
    }

}
