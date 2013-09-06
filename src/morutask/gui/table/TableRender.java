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

import morutask.gui.Main;
import morutask.gui.utils.ImageUtils;
import morutask.models.Task;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * @author mohand
 */
public class TableRender extends DefaultTableRenderer {

    private JTable table;
    private Task task;
    private SimpleDateFormat dateFormat;
    private boolean isSelected;

    public TableRender() {

        dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(Main.getSettings().getStringProperty("Date.Date_Format") + "    " + Main.getSettings().getStringProperty("Date.time_Format"));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        this.table = table;
        this.isSelected = isSelected;
        this.task = (Task) value;

        return new CellTable();

    }

    public class CellTable extends JPanel {

        private JLabel title;
        private JLabel date;
        private JLabel priority;

        public CellTable() {

            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setSize(table.getWidth(), table.getRowHeight());
            setBackground(Color.WHITE);

            if (isSelected) {
                setBackground(Color.ORANGE);
            }

            initTitle();
            initDate();
            initPriority();

            int x = table.getWidth() / 6;

            add(Box.createRigidArea(new Dimension(5, 0)));
            add(title);
            add(Box.createRigidArea(new Dimension(x, 0)));
            add(date);
            add(Box.createRigidArea(new Dimension(x, 0)));
            add(priority);
        }

        public void initTitle() {

            title = new JLabel(task.getTitle(), JLabel.LEFT);
            title.setMaximumSize(new Dimension(table.getWidth() / 3, table.getRowHeight()));
            title.setPreferredSize(new Dimension(table.getWidth() / 3, table.getRowHeight()));

        }

        public void initDate() {

            date = new JLabel(dateFormat.format(task.getStartDate().getTime()));

            if (task.hasReminder()) {
                date.setIcon(ImageUtils.getImage("alarm.png", 25, 25));
            }

        }

        public void initPriority() {

            priority = new JLabel(task.getPriority().toString());
            priority.setForeground(Main.getSettings().getColorProperty("theme.color.Priority." + task.getPriority().toString()));

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (task.isCompleted()) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
                title.setForeground(Color.LIGHT_GRAY);
                date.setForeground(Color.LIGHT_GRAY);
                priority.setForeground(Color.LIGHT_GRAY);
            }

        }
    }
}
