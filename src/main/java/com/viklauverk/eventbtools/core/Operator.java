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

public class Operator
{
    private String name_;
    private boolean isAssociative_ = false;
    private boolean isCommutative_ = false;

    private Map<String,Arguments> args_ = new HashMap<>();
    private List<Arguments> args_ordering_ = new ArrayList<>();
    private List<String> args_names_ = new ArrayList<>();

    // private Map<String,WDConditions> wdconditions = new HashMap<>() ;

    private IsAFormula directDefinition_;
    private String comment_;

    public Operator(String n, String c)
    {
        name_ = n;
        comment_ = c;
    }

    public Operator(String n, boolean as, boolean com, String c)
    {
        name_ = n;
        comment_ = c;
        isAssociative_ = as;
        isCommutative_ = com;
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
      st.pushFrame(args_names_);
      directDefinition_.parse(st);
      st.popFrame();
    }

// -----------------------------------------------------------------------------
//    ARGUMENTS
// -----------------------------------------------------------------------------
    public Arguments getArgument(String name)
    {
        return args_.get(name);
    }

    public List<Arguments> argumentsOrdering()
    {
        return args_ordering_;
    }

    public List<String> argumentsNames()
    {
        return args_names_;
    }

    public void addArgument(Arguments arg)
    {
        args_.put(arg.name(), arg);
        args_ordering_.add(arg);
        args_names_.add(arg.name());
    }

    public void addArguments(List<Arguments> args)
    {
        args.addAll(args);
    }

// -----------------------------------------------------------------------------
//    DIRECT DEFINITION
// -----------------------------------------------------------------------------

    public void setDirectDef(IsAFormula f)
    {
      directDefinition_ = f;
    }

    public IsAFormula getDef()
    {
        return directDefinition_;
    }

}
