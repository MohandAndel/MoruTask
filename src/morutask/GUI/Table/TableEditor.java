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
package morutask.GUI.Table;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import morutask.GUI.Actions.ActionEditTask;
import morutask.GUI.EditTaskPanel;
import morutask.GUI.Main;
import morutask.GUI.utils.viewUtils;
import morutask.models.Task;

/**
 *
 * @author mohand
 */
public class TableEditor extends AbstractCellEditor implements TableCellEditor{

    private Task task;
    private JTable table;
    private int tableRowhigh;
    public TableEditor() {
        tableRowhigh = Main.getSettings().getIntegerProperty("tasktable.row.high");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        task = (Task) value;
       this.table = table;
       
       if(isSelected == false) {
           this.table.setRowHeight(tableRowhigh);
            return null;
        }
       
        this.table.setRowHeight(row, ActionEditTask.EditTask().getHeight());//EditTaskPanel.getinstance().getHeight());

        return ActionEditTask.EditTask();//EditTaskPanel.getinstance();
    }


    @Override
    public Object getCellEditorValue() {
        
        return task;
    }

    
    public void StopEditing()
    { 
        fireEditingStopped();
        modelTable.setEdited(false);
        table.setRowHeight(tableRowhigh);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return super.isCellEditable(e);    //To change body of overridden methods use File | Settings | File Templates.
    }
}

