package com.viklauverk.eventbtools.core;

public class RenderTheoryWhy extends RenderTheoryUnicode {

    @Override
    public void visit_TheoryStart(Theory th)
    {
        cnvs().startLine();
        cnvs().append("theory Th_"+th.name());
        cnvs().endLine();
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
        //TODO
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
        cnvs().startLine();
        if (th.hasTypeParameters()) cnvs().append("type");
    }

    @Override
    public void visit_TypeParameter(Theory th, TypeParameters tp)
    {
        cnvs().append(" 'tp_"+tp.name());
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
        //TODO
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
        cnvs().startLine();
        cnvs().keyword("end");
        cnvs().endLine();
    }
}
