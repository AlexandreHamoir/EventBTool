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

public class VisitFormula
{
    public static void walkk(WalkFormula v, Formula f)
    {
        v.startVisiting(f);
    }

    public static String walk(RenderFormula v, Formula f)
    {
        v.cnvs().setMark();
        v.startVisiting(f);
        return v.cnvs().getSinceMark();
    }

    // AH
    public static void walkkTyped(WalkFormula v, Formula f)
    {
        v.startVisitingTyped(f);
    }

    public static String walkTyped(RenderFormula v, Formula f)
    {
        v.cnvs().setMark();
        v.startVisitingTyped(f);
        return v.cnvs().getSinceMark();
    }

}
