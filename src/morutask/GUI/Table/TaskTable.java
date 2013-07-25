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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import morutask.GUI.Actions.ActionEditTask;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.sort.TableSortController;
import morutask.GUI.EditTaskPanel;
import morutask.GUI.Main;
import morutask.GUI.Table.Filter.TaskFilter;
import morutask.GUI.TaskView.TaskTableView;
import morutask.GUI.utils.viewUtils;
import morutask.models.Task;
import morutask.models.TaskFactory;

/**
 *
 * @author mohand
 */
public class TaskTable extends JXTable implements TaskTableView{
    
    private modelTable modeltable = new modelTable();
    private TableSortController tableSorter;
    private TaskFilter taskFilter;
    private int tableRowhigh;

    public TaskTable()
    {
        tableRowhigh = Main.getSettings().getIntegerProperty("tasktable.row.high");
        setSize(200, 200);
        setRowHeight(tableRowhigh);
        setTableHeader(null);
        setModel(modeltable);
        taskFilter = new TaskFilter();

        tableSorter = new TableSortController(modeltable);
        setRowSorter(tableSorter);
        tableSorter.setRowFilter(taskFilter);
        setDefaultRenderer(Task.class, new TableRender());
        setDefaultEditor(Task.class, new TableEditor());
        viewUtils.getInstance().setTaskTable(this);
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                System.out.println("Changed !!");
                setRowHeight(tableRowhigh);
                modeltable.setEdited(false);
            }
        });

        //****
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2)
                {
                    System.out.println("Double Click");
                    TaskTable source = (TaskTable) e.getSource();

                     setSelectedTaskAndStartEdit(source.getSelectedTask());

                }
            }
        });

        //***
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return super.getCellRenderer(row, column);
    }

    @Override
    public int getTaskCount() {
        return this.getRowCount();
    }

    @Override
    public void setSelectedTaskAndStartEdit(Task task) {
        
        this.setSelectedTask(task);
        modeltable.setEdited(true);
		
                int i = TaskFactory.getInstance().getIndexOf(task);

				int row = this.getRowSorter().convertRowIndexToView(i);

				if (row != -1) {
					if (this.editCellAt(row, 0)) {
						Component editor =this.getEditorComponent();

						editor.requestFocusInWindow();

					}
				}

        //modeltable.setEdited(false);

    }

    @Override
    public void refreshTasks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Task getSelectedTask() {

       int x =  this.convertRowIndexToModel(this.getSelectedRow());
      return (Task)this.getModel().getValueAt(x, 0);
    }

    @Override
    public void setSelectedTask(Task tasks) {
        
        this.getSelectionModel().setValueIsAdjusting(true);
        this.getSelectionModel().clearSelection();
        //System.out.println("selectedTask");
        
        int index = this.getRowSorter().convertRowIndexToView(TaskFactory.getInstance().getIndexOf(tasks));
        
        if (index != -1) {
            
            this.getSelectionModel().addSelectionInterval(index, index);
            this.getSelectionModel().setValueIsAdjusting(false);
            
            this.setRowHeight(index, ActionEditTask.EditTask().getHeight());//EditTaskPanel.getinstance().getHeight());
            this.scrollRowToVisible(index);
            
        }
    
    
    }
    
    public Task getTask(int row) {
		return TaskFactory.getInstance().get(row);
	}

    
    
    
}
