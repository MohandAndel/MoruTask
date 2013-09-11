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
package morutask.gui.table;

import com.leclercb.commons.api.event.listchange.ListChangeEvent;
import com.leclercb.commons.api.event.listchange.ListChangeListener;
import morutask.gui.utils.ViewUtils;
import morutask.models.Task;
import morutask.models.TaskFactory;

import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author mohand
 */
public class modelTable extends AbstractTableModel implements ListChangeListener, PropertyChangeListener {


    public modelTable() {
        TaskFactory.getInstance().addListChangeListener(this);
        TaskFactory.getInstance().addPropertyChangeListener(this);
    }

    @Override
    public int getRowCount() {

        return TaskFactory.getInstance().size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return (Task) TaskFactory.getInstance().get(rowIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Task.class;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }


    @Override
    public void listChange(ListChangeEvent lce) {

        if (lce.getChangeType() == ListChangeEvent.VALUE_ADDED) {

            fireTableRowsInserted(lce.getIndex(), lce.getIndex());
        }
        if (lce.getChangeType() == ListChangeEvent.VALUE_REMOVED) {
            fireTableRowsDeleted(lce.getIndex(), lce.getIndex());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getSource() instanceof Task) {
          Task task = (Task) evt.getSource();
            if ( !task.getModelStatus().isEndUserStatus())
            {
              int x =  ViewUtils.getInstance().getTaskTable().getSelectedRow();
                fireTableRowsDeleted(0,x);
            }
            fireTableCellUpdated(ViewUtils.getInstance().getTaskTable().getSelectedRow(), 0);
        }

    }

}
