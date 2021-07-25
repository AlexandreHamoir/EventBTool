/*
 Copyright (C) 2021 Viklauverk AB
 Author Fredrik Öhrström

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.viklauverk.eventbtools.core;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import com.viklauverk.eventbtools.core.Formula;

public class CommonRenderFunctions
{
    protected AllRenders renders_;
    protected boolean build_template_;

    protected AllRenders renders()
    {
        return renders_;
    }

    protected Canvas cnvs()
    {
        return renders_.currentCanvas();
    }

    public void setRenders(AllRenders ar)
    {
        renders_ = ar;
    }

    protected void pushCanvass(String id)
    {
        renders().pushCanvass(id);
    }

    protected void popStoreCanvasAndAppendd(String id)
    {
        renders().popStoreCanvasAndAppendd(id);
    }

    protected void stopAlignedLineAndHandlePotentialComment(String comment, Canvas cnvs, IsAFormula f)
    {
        boolean has_comment = comment.length() > 0;
        boolean use_next_line = false;

        // If comment contains line breaks, then always place
        // the comment on its own line below the commented material.
        if (Util.hasNewLine(comment))
        {
            use_next_line = true;
        }
        // Also any comment with a length longer than 30 characters
        // will also be placed on its own line below the commented material.
        if (comment.length() > 30)
        {
            use_next_line = true;
        }
        // Check if the commented formula has a raw unicode length longer than 30 characters.
        // If so, then place the comment on its own line.
        if (f != null && f.formula() != null && f.formula().toString().length() > 30)
        {
            use_next_line = true;
        }

        if (has_comment && !use_next_line)
        {
            // Placing the comment on the same line.
            cnvs.align();
            cnvs.comment(comment);
        }

        cnvs.stopAlignedLine();

        if (has_comment && use_next_line)
        {
            // Place the comment on a new line.
            cnvs.startAlignedLine();
            cnvs.commentWithExtraVSpace(comment);
            cnvs.stopAlignedLine();
        }
    }
}
