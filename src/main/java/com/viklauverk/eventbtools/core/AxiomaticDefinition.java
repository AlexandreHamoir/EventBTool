/*
 Copyright (C) 2021 Viklauverk AB
 Author Marius Hinge

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AxiomaticDefinition
{
    private String name_;
    private Map<String,TypeDef> tyd_ = new HashMap<>(); //TODO Change to Type or TypeParameter?
    private Map<String,Operator> opd_ = new HashMap<>();
    private Map<String,Axiom> ax_ = new HashMap<>();
    private String comment_;

    public AxiomaticDefinition(String n, String c)
    {
        name_ = n;
        comment_ = c;
    }

    public String name()
    {
      return name_;
    }

    public String comment()
    {
      return comment_;
    }

    public boolean hasComment()
    {
        return comment_.length() > 0;
    }

    public void parse(SymbolTable st)
    {
        //TODO
    }

    public void addOperator(TypeDef tyd)
    {
        tyd_.put(tyd.name(),tyd);
    }

    public void addOperator(Operator opd)
    {
        opd_.put(opd.name(),opd);
    }

    public void addAxiom(Axiom ax)
    {
        ax_.put(ax.name(), ax);
    }

}