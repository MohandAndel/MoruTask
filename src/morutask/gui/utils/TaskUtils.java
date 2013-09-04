/*
 * TaskUnifier
 * Copyright (c) 2011, Benjamin Leclerc
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
 *   - Neither the name of TaskUnifier or the names of its
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

import com.leclercb.commons.api.utils.CheckUtils;
import com.leclercb.commons.api.utils.DateUtils;
import com.leclercb.commons.api.utils.EqualsUtils;
import morutask.gui.Main;
import morutask.gui.threads.reminder.Synchronizing;
import morutask.models.Task;
import morutask.models.TaskFactory;

import java.util.Calendar;
import java.util.List;


public final class TaskUtils {

    private TaskUtils() {

    }


    public static void updateOrder(
            int index,
            Task[] tasksToOrder,
            Task[] displayedTasks) {
        Synchronizing.getInstance().setSynchronizing(true);

        try {
            int newOrder = 0;

            if (index > 0 && index <= displayedTasks.length)
                newOrder = displayedTasks[index - 1].getOrder() + 1;

            List<Task> tasks = TaskFactory.getInstance().getList();
            main:
            for (Task task : tasks) {
                if (!task.getModelStatus().isEndUserStatus())
                    continue;

                for (Task t : tasksToOrder)
                    if (EqualsUtils.equals(task, t))
                        continue main;

                for (Task t : displayedTasks)
                    if (EqualsUtils.equals(task, t))
                        continue main;

                if (task.getOrder() >= newOrder)
                    task.setOrder(task.getOrder() + 1 + tasksToOrder.length);
            }

            for (int i = 0; i < displayedTasks.length; i++) {
                Task task = displayedTasks[i];

                if (task == null)
                    continue;

                if (i >= index)
                    task.setOrder(task.getOrder() + 1 + tasksToOrder.length);
            }

            for (int i = 0; i < tasksToOrder.length; i++) {
                Task task = tasksToOrder[i];

                if (task == null)
                    continue;

                task.setOrder(newOrder + i);
            }
        } finally {
            Synchronizing.getInstance().setSynchronizing(false);
        }
    }

    public static boolean isInStartDateReminderZone(Task task) {
        if (task.getStartDate() == null)
            return false;

        if (task.isCompleted())
            return false;

//		if (task.getStartDateReminder() == 0
//				&& !Main.getSettings().getBooleanProperty(
//						"reminder.always_show_reminder"))
//			return false;

        Calendar startDate = task.getStartDate();

        if (!Main.getSettings().getBooleanProperty("date.use_start_time")) {
            startDate.set(
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DAY_OF_MONTH),
                    0,
                    0,
                    0);
        }

        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        Calendar startDateReminder = DateUtils.cloneCalendar(startDate);
        //startDateReminder.add(Calendar.MINUTE,-1); //-task.getStartDateReminder());
        if (now.compareTo(startDateReminder) == 0
                && now.compareTo(startDate) <= 0)
            return true;


        return false;
    }

    public static boolean isInDueDateReminderZone(Task task) {
        if (task.getDueDate() == null)
            return false;

        if (task.isCompleted())
            return false;

        if (task.getDueDateReminder() == 0
                && !Main.getSettings().getBooleanProperty(
                "reminder.always_show_reminder")
                && !Main.getSettings().getBooleanProperty(
                "reminder.show_overdue_tasks"))
            return false;

        Calendar dueDate = task.getDueDate();

        if (!Main.getSettings().getBooleanProperty("date.use_due_time")) {
            dueDate.set(
                    dueDate.get(Calendar.YEAR),
                    dueDate.get(Calendar.MONTH),
                    dueDate.get(Calendar.DAY_OF_MONTH),
                    0,
                    0,
                    0);
        }

        Calendar now = Calendar.getInstance();
        Calendar dueDateReminder = DateUtils.cloneCalendar(dueDate);
        dueDateReminder.add(Calendar.MINUTE, -task.getDueDateReminder());

        if (now.compareTo(dueDateReminder) >= 0)
            if (now.compareTo(dueDate) <= 0
                    || Main.getSettings().getBooleanProperty(
                    "reminder.show_overdue_tasks"))
                return true;

//		Calendar exitDate = Main.getSettings().getCalendarProperty(
//				"general.last_exit_date");
//		
//		if (exitDate != null)
//			if (now.compareTo(dueDateReminder) >= 0
//					&& exitDate.compareTo(dueDateReminder) <= 0)
//				return true;

        return false;
    }

    public static int getOverdueTaskCount() {
        int count = 0;

        List<Task> tasks = TaskFactory.getInstance().getList();
        for (Task task : tasks) {
            if (!task.getModelStatus().isEndUserStatus())
                continue;

            if (task.isCompleted())
                continue;

            if (!task.isOverDue(false))
                continue;

            count++;
        }

        return count;
    }

    public static int getImportance(Task task) {
        CheckUtils.isNotNull(task);

        int importance = 2;

        switch (task.getPriority()) {
            case NEGATIVE:
                importance += -1;
                break;
            case LOW:
                importance += 0;
                break;
            case MEDIUM:
                importance += 1;
                break;
            case HIGH:
                importance += 2;
                break;
            case TOP:
                importance += 3;
                break;
        }

        //importance += (task.isStar() ? 1 : 0);

        if (task.getDueDate() != null) {
            boolean useTime = Main.getSettings().getBooleanProperty(
                    "date.use_due_time");

            double diffDays = DateUtils.getDiffInDays(
                    Calendar.getInstance(),
                    task.getDueDate(),
                    useTime);

            if (diffDays > 14)
                importance += 0;
            else if (diffDays >= 7)
                importance += 1;
            else if (diffDays >= 2)
                importance += 2;
            else if (diffDays >= 1)
                importance += 3;
            else if (diffDays >= 0)
                importance += 5;
            else
                importance += 6;
        }

        return importance;
    }
}
