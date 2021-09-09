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

import com.viklauverk.eventbtools.core.Formula;

import java.util.List;
import java.util.LinkedList;

public class RenderTheoryUnicode extends RenderTheory
{
    @Override
    public void visit_TheoryStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keywordLeft("theory");
        cnvs().space();
        cnvs().id(th.name());
        cnvs().endLine();

        cnvs().hrule();

        if (th.hasComment())
        {
            cnvs().acomment(th.comment());
            cnvs().nl();
        }
    }

    @Override
    public void visit_ImportsStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keywordLeft("imports");
        cnvs().space();
    }

/*  //TODO same weird compilation error
    @Override
    public void visit_Import(Theory th, Theory import)
    {
        cnvs().id(import.name());
        cnvs().space();
    }
*/

    @Override
    public void visit_ImportsEnd(Theory th)
    {
        cnvs().endLine();
    }

    @Override
    public void visit_HeadingComplete(Theory th)
    {
    }

    @Override
    public void visit_TypeParametersStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keyword("type parameters");
        cnvs().endLine();

        cnvs().startAlignments(Canvas.align_2col);

    }

    @Override
    public void visit_TypeParameter(Theory th, TypeParameters type_parameter)
    {
        cnvs().startAlignedLine();
        cnvs().set(type_parameter.name()); //TODO Modify?
        cnvs().align();
        cnvs().comment(type_parameter.comment());
        cnvs().stopAlignedLine();
    }

    @Override
    public void visit_TypeParametersEnd(Theory th)
    {
        cnvs().stopAlignments();
    }

    @Override
    public void visit_DatatypesStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_Datatype(Theory th, Datatype datatype)
    {
        //TODO
    }

    @Override
    public void visit_DatatypesEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_OperatorsStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_Operator(Theory th, Operator operator)
    {
        //TODO
    }

    @Override
    public void visit_OperatorsEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_AxiomaticDefinitionsStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_AxiomaticDefinition(Theory th, AxiomaticDefinition axiomatic_definition)
    {
        //TODO
    }

    @Override
    public void visit_AxiomaticDefinitionsEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_TheoremsStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keyword("theorem");
        cnvs().endLine();

        cnvs().startAlignments(Canvas.align_3col);        
    }

    @Override
    public void visit_Theorem(Theory th, Theorem theorem)
    {
        cnvs().startAlignedLine();
        cnvs().label(theorem.name());
        cnvs().align();
        cnvs().startMath();
        theorem.writeFormulaStringToCanvas(cnvs());
        cnvs().stopMath();
        stopAlignedLineAndHandlePotentialComment(theorem.comment(), cnvs());
    }

    @Override
    public void visit_TheoremsEnd(Theory th)
    {
        cnvs().stopAlignments();
    }

    @Override
    public void visit_TheoryEnd(Theory th)
    {
        cnvs().startLine();
        cnvs().keyword("end");
        cnvs().endLine();
    }

    //TODO
}