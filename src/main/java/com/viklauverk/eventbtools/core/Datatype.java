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

public class Datatype extends Operator
{
    // NB: The current representation is that a datatype is an operator
    // that takes types as arguments, thus "types" is the same as "arguments"
    private Map<String,CarrierSet> types_ = new HashMap<>();
    private List<CarrierSet> types_ordering_ = new ArrayList<>();

    // NB: For now constructors and destructors are parsed as operators inside formulas 
    // and don't have a class of their own, destructors are the constructor's arguments
    // One may define new symbols in the symbole table to parse them differently,
    // then defining a new way to represent them inside formulas
    // Defining classes may also help for clarity of code
    private Map<String,Operator> cons_ = new HashMap<>();
    private List<Operator> cons_ordering_ = new ArrayList<>();

    public Datatype(String n, String c)
    {
        super(n,c);
    }

    public void addConstructor(Operator cons)
    {
      cons_.put(cons.name(), cons);
      cons_ordering_.add(cons);
    }

    public Operator getConstructor(String name)
    {
      return cons_.get(name);
    }

    public List<Operator> constructorsOrdering()
    {
      return cons_ordering_;
    }

    public void addTypeArgument(CarrierSet type)
    {
      types_.put(type.name(), type);
      types_ordering_.add(type);

      // TODO remove wether datatypes remain as operators
      this.addArgument(new Arguments(type.name(), type.comment()));
    }

    public CarrierSet getTypeArgument(String name)
    {
      return types_.get(name);
    }

    public List<CarrierSet> typeArgumentsOrdering()
    {
      return types_ordering_;
    }
}
