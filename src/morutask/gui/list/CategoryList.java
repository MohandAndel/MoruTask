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
package morutask.gui.list;

import morutask.gui.list.categoriesList.Category;
import morutask.gui.list.categoriesList.CompletionCategory;
import morutask.gui.list.categoriesList.DayCategory;
import morutask.gui.list.categoriesList.ReminderCategory;
import morutask.gui.utils.ViewUtils;
import org.jdesktop.swingx.JXList;

import java.awt.*;

/**
 * @author mohand
 */
public class CategoryList extends JXList implements ListCategoryView {

    public CategoryList() {

        //setSize(100, 100);
        setPreferredSize(new Dimension(145,100));
        initCategories();

        setModel(new ModelListCategory());
        ViewUtils.getInstance().setCategoryList(this);
    }

    public void initCategories() {

        DayCategory dayCategory = new DayCategory();
        CompletionCategory completionCategory = new CompletionCategory();
        ReminderCategory reminderCategory = new ReminderCategory();
    }

    @Override
    public Category getSelectedCategory() {
        return (Category) getSelectedValue();
    }

    public void fireContentsChanged() {
        ModelListCategory model = (ModelListCategory) getModel();
        model.fireContentsChanged();

    }
}
