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
package morutask.gui.utils;

import morutask.gui.ComponentsBar;
import morutask.gui.sort.TaskComparable;
import morutask.gui.sort.TaskSortType;
import org.jdesktop.swingx.sort.TableSortController;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author mohand
 */
public class ComponentFactory {


    public static JSplitPane createThinJSplitPane(int orientation) {
        JSplitPane splitPane = new JSplitPane(orientation);

        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(1);
        ((BasicSplitPaneUI) splitPane.getUI()).getDivider().setBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(0xa5a5a5)));
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        return splitPane;
    }

    public static JComboBox<Object> createSortComboBox() {

        final JComboBox sortComboBox = new JComboBox(new DefaultComboBoxModel<>(TaskSortType.values()));
        sortComboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    TableSortController rowsorter = (TableSortController) ViewUtils.getInstance().getTaskTable().getRowSorter();
                    TaskComparable taskComparable = new TaskComparable();
                    taskComparable.setTypeOfComparison((TaskSortType) sortComboBox.getSelectedItem());

                    ViewUtils.getInstance().getTaskTable().resetSortOrder();
                    rowsorter.setComparator(0, taskComparable);
                    ViewUtils.getInstance().getTaskTable().setSortOrder(0, SortOrder.DESCENDING);
                }
            }
        });

        return sortComboBox;

    }

    public static ComponentsBar createComponentsPanel() {
        return new ComponentsBar();
    }

}
