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
package morutask.gui.sort;

import morutask.models.Task;

import java.util.Comparator;

/**
 * @author mohand
 */
public class TaskComparable implements Comparator<Task> {

    private TaskSortType TypeOfComparison;

    public TaskComparable() {

    }

    public TaskComparable(TaskSortType x) {
        this.TypeOfComparison = x;
    }

    public TaskSortType getTypeOfComparison() {
        return TypeOfComparison;
    }

    public void setTypeOfComparison(TaskSortType TypeOfComparison) {
        this.TypeOfComparison = TypeOfComparison;
    }


    @Override
    public int compare(Task o1, Task o2) {
        int result = 0;
        switch (TypeOfComparison) {

            case By_title:
                result = o1.getTitle().compareToIgnoreCase(o2.getTitle());
            case By_modelID:
                result = o1.getModelId().compareTo(o2.getModelId());
                break;
            case By_Priority:
                result = o1.getPriority().compareTo(o2.getPriority());
                break;
            case By_Date:
                result = o1.getStartDate().compareTo(o2.getStartDate());
                break;
        }

        return result;
    }

}
