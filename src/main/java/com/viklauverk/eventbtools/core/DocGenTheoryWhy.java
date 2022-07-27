package com.viklauverk.eventbtools.core;

public class DocGenTheoryWhy extends BaseDocGen {

    public DocGenTheoryWhy(CommonSettings common_settings, DocGenSettings docgen_settings, Sys sys)
    {
        super(common_settings, docgen_settings, sys, "why");
    }

    @Override
    public String suffix() {
        return ".why";
    }

    @Override
    public String generateDocument() throws Exception {
        Canvas cnvs = new Canvas();
        cnvs.setRenderTarget(RenderTarget.WHY);

        for (String th : sys().theoryFullNames())
        {
            cnvs.append("EVBT(show part why \""+th+"\")\n");
        }

        return cnvs.render();
    }
    
}
