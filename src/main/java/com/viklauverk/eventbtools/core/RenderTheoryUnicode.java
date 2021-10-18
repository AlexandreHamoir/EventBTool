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

    @Override
    public void visit_Import(Theory th, Theory imp)
    {
        cnvs().id(imp.name());
        cnvs().space();
    }

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
        cnvs().set(type_parameter.name()); //TODO Modify cnvs().set => cnvs().typeParam?
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
    public void visit_ArgumentsStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keyword("arguments");
        cnvs().endLine();

        cnvs().startAlignments(Canvas.align_3col);
    }

    @Override
    public void visit_Arguments(Theory th, Arguments arg)
    {
            cnvs().startAlignedLine();

            cnvs().align();
            cnvs().label(arg.name());
            cnvs().align();
/*
            cnvs().startMath();
            arg.getType().writeFormulaStringToCanvas(cnvs());
            cnvs().stopMath();
*/
            stopAlignedLineAndHandlePotentialComment(arg.comment(), cnvs());
    }

    @Override
    public void visit_ArgumentsEnd(Theory th)
    {
        cnvs().stopAlignments();
    }


    @Override
    public void visit_OperatorsStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keyword("operators");
        cnvs().endLine();

        cnvs().startAlignments(Canvas.align_3col);        
    }

    @Override
    public void visit_Operator(Theory th, Operator operator)
    {
        cnvs().startAlignedLine();
        cnvs().label(operator.name());
        cnvs().align();
        if (operator.hasDirectDef())
        {        
            cnvs().startMath();
            operator.getDef().writeFormulaStringToCanvas(cnvs());
            cnvs().stopMath();
            stopAlignedLineAndHandlePotentialComment(operator.comment(), cnvs());
        }

        /* Print arguments */
        if (operator.hasArguments())
        {
            visit_ArgumentsStart(th);
            for (Arguments arg : operator.argumentsOrdering())
            {
                visit_Arguments(th,arg);
            }
            visit_ArgumentsEnd(th);
        }

        /* Print WDCs */
        /*
        if (operator.hasWDCs())
        {
            visit_WDCsStart(th);
            for (WDConditions wdc : operator.wdcsOrdering)
            {
                visit_WDCs(th, wdc);
            }
            visit_WDCsEnd(th);
        }
        */
    }

    @Override
    public void visit_OperatorsEnd(Theory th)
    {
        cnvs().stopAlignments();
    }

    @Override
    public void visit_AxiomaticDefinitionsStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keyword("axiomatic definitions");
        cnvs().endLine();

        cnvs().startAlignments(Canvas.align_3col);        
    }

    @Override
    public void visit_AxiomaticDefinition(Theory th, AxiomaticDefinition axiomatic_definition)
    {
        if (axiomatic_definition.hasTypeDefs())
        {
            // TODO
        }

        if (axiomatic_definition.hasOperators())
        {
            visit_OperatorsStart(th);
            for (Operator op : axiomatic_definition.operatorOrdering())
            {
                visit_Operator(th,op);
            }
            visit_OperatorsEnd(th);
        }

        if (axiomatic_definition.hasAxioms())
        {
            // TODO
        }
    }

    @Override
    public void visit_AxiomaticDefinitionsEnd(Theory th)
    {
        cnvs().stopAlignments();
    }

    @Override
    public void visit_TheoremsStart(Theory th)
    {
        cnvs().startLine();
        cnvs().keyword("theorems");
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

}