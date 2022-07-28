/*
 Copyright (C) 2021 Viklauverk AB
 Author Alexandre Hamoir

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

public class RenderTheoryWhy extends RenderTheoryUnicode {

    @Override
    public void visit_TheoryStart(Theory th)
    {
        cnvs().startLine();
        cnvs().append("theory Th_"+th.dir()+"_"+th.localName());
        cnvs().endLine();
        cnvs().startIndent();
        cnvs().skipLine();
        cnvs().startIndentedLine();
        cnvs().append("use ImportAll");
        cnvs().endLine();
        cnvs().skipLine();
    }

    @Override
    public void visit_ImportsStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_Import(Theory th, Theory imp)
    {
        cnvs().importTheory(imp.dir()+"_"+imp.localName());
    }

    @Override
    public void visit_ImportsEnd(Theory th)
    {
        cnvs().skipLine();
    }

    @Override
    public void visit_HeadingComplete(Theory th)
    {
    }

    @Override
    public void visit_TypeParametersStart(Theory th)
    {
        cnvs().startIndentedLine();
        if (th.hasTypeParameters()) cnvs().append("(* type"); // type parameters are not declared in why3 or else you have to specifically instantiate them
    }

    @Override
    public void visit_TypeParameter(Theory th, TypeParameters tp)
    {
        cnvs().append(" 'tp_"+tp.name()); //TODO: use a why3 prefix constant
    }

    @Override
    public void visit_TypeParametersEnd(Theory th)
    {
        if (th.hasTypeParameters()) cnvs().append(" *)");
        cnvs().endLine();
        cnvs().skipLine();
    }

    @Override
    public void visit_DatatypesStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_Datatype(Theory th, Datatype dt)
    {
        cnvs().startIndentedLine();
        cnvs().append("type dt_"+dt.name()+" = ");
        int i = 0;
        for (Operator cons : dt.constructorsOrdering())
        {
            if (i > 0) cnvs().append(" | ");
            else i++;
            cnvs().append("Cst_"+cons.name());
            for (Arguments dest : cons.argumentsOrdering())
            {
                // TODO: Only for type parameters for now
                cnvs().append(" ");
                dest.getType().writeFormulaStringToCanvas(cnvs());
            }
        }
        cnvs().endLine();
        cnvs().skipLine();
    }

    @Override
    public void visit_DatatypesEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_WDConditionsStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_WDCondition(Theory th, WDConditions wdc)
    {
        cnvs().beginWDC();
        wdc.writeFormulaStringToCanvas(cnvs());
        cnvs().endWDC();

    }

    @Override
    public void visit_WDConditionsEnd(Theory th)
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
        cnvs().startIndentedLine();
        cnvs().comment(operator.comment());
        cnvs().endLine();
        cnvs().startIndentedLine();
        cnvs().operatorDef("op_"+operator.name());
        for (Arguments arg : operator.argumentsOrdering())
        {
            cnvs().append(" ("+arg.name()+":");
            arg.getType().writeFormulaStringToCanvas(cnvs());
            cnvs().append(")");
        }
        if (operator.hasReturnType())
        {
            cnvs().append(" : ");
            operator.getReturnType().writeFormulaStringToCanvas(cnvs());
        }
        cnvs().endLine();
        cnvs().startIndent();
        for (WDConditions wdc : operator.wdcsOrdering())
        {
            visit_WDCondition(th, wdc);
        }
        if (operator.hasDirectDefinition())
        {
            cnvs().beginOpDef();
            operator.getDef().writeFormulaStringToCanvas(cnvs());
            cnvs().endOpDef();
        }
        for (String argName : operator.getRecursiveArgs())
        {
            cnvs().startIndentedLine();
            cnvs().append("match "+argName+" with");
            cnvs().endLine();
            cnvs().startIndent();

            for (IsAFormula[] rec_def : operator.getRecursiveDefs(argName))
            {
                cnvs().startIndentedLine();
                cnvs().append("| ");
                rec_def[0].writeFormulaStringToCanvas(cnvs());
                cnvs().append(" -> ");
                rec_def[1].writeFormulaStringToCanvas(cnvs());
                cnvs().endLine();
            }

            cnvs().endIndent();
            cnvs().startIndentedLine();
            cnvs().append("end");
            cnvs().endLine();
        }
        cnvs().endIndent();
        cnvs().skipLine();
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
        cnvs().startIndentedLine();
        cnvs().comment(axiomatic_definition.comment());
        cnvs().endLine();
        cnvs().skipLine();

        for (TypeDef td : axiomatic_definition.typeDefOrdering())
        {
            cnvs().startIndentedLine();
            cnvs().append("type td_"+td.name());
            cnvs().endLine();
            cnvs().skipLine();
        }

        for (Operator op : axiomatic_definition.operatorOrdering())
        {
            visit_Operator(th, op);
        }

        for (Axiom axm : axiomatic_definition.axiomOrdering())
        {
            cnvs().axiomDef(axm.name(), axm.comment());
            cnvs().startIndent();
            cnvs().startIndentedLine();
            axm.writeFormulaStringToCanvas(cnvs());
            cnvs().endLine();
            cnvs().endIndent();
            cnvs().skipLine();
        }
    }

    @Override
    public void visit_AxiomaticDefinitionsEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_TheoremsStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_Theorem(Theory th, Theorem theorem)
    {
        cnvs().theoremDef(theorem.name(), theorem.comment());
        cnvs().startIndent();
        cnvs().startIndentedLine();
        theorem.writeFormulaStringToCanvas(cnvs());
        cnvs().endLine();
        cnvs().endIndent();
        cnvs().skipLine();
    }

    @Override
    public void visit_TheoremsEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_TheoryEnd(Theory th)
    {
        cnvs().endIndent();
        cnvs().startLine();
        cnvs().keyword("end");
        cnvs().endLine();
        cnvs().skipLine();
    }
}
