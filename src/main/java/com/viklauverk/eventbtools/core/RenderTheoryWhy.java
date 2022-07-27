package com.viklauverk.eventbtools.core;

public class RenderTheoryWhy extends RenderTheoryUnicode {

    @Override
    public void visit_TheoryStart(Theory th)
    {
        cnvs().startLine();
        cnvs().append("theory Th_"+th.name());
        cnvs().endLine();
        cnvs().startIndent();
        //TODO: import the file with all definitions
    }

    @Override
    public void visit_ImportsStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_Import(Theory th, Theory imp)
    {
        cnvs().importTheory(imp.name());
    }

    @Override
    public void visit_ImportsEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_HeadingComplete(Theory th)
    {
    }

    @Override
    public void visit_TypeParametersStart(Theory th)
    {
        cnvs().startIndentedLine();
        if (th.hasTypeParameters()) cnvs().append("type");
    }

    @Override
    public void visit_TypeParameter(Theory th, TypeParameters tp)
    {
        cnvs().append(" 'tp_"+tp.name()); //TODO: use a why3 prefix constant
    }

    @Override
    public void visit_TypeParametersEnd(Theory th)
    {
        cnvs().endLine();
    }

    @Override
    public void visit_DatatypesStart(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_Datatype(Theory th, Datatype dt)
    {
        cnvs().startLine();
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
                cnvs().append(" 'tp_"+dest.getType().name());
            }
        }
        cnvs().endLine();
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
        cnvs().operatorDef(operator.name());
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

            cnvs().stopIndent();
            cnvs().startIndentedLine();
            cnvs().append("end");
            cnvs().endLine();
        }
        cnvs().stopIndent();
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
        //TODO
    }

    @Override
    public void visit_Theorem(Theory th, Theorem theorem)
    {
        //TODO
    }

    @Override
    public void visit_TheoremsEnd(Theory th)
    {
        //TODO
    }

    @Override
    public void visit_TheoryEnd(Theory th)
    {
        cnvs().stopIndent();
        cnvs().startLine();
        cnvs().keyword("end");
        cnvs().endLine();
    }
}
