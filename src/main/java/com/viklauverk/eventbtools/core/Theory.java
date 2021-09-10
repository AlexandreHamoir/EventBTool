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

import com.viklauverk.eventbtools.core.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Attribute;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Theory
{
    private static LogModule log = LogModule.lookup("theory");
    private static LogModule log_codegen = LogModule.lookup("codegen");

    private SymbolTable symbol_table_;

    private String name_;

    private Map<String,Theory> imports_ = new HashMap<>();
    private List<Theory> imports_ordering_ = new ArrayList<>();
    private List<String> imports_names_ = new ArrayList<>();

    private Map<String,TypeParameters> type_parameters_ = new HashMap<>();
    private List<TypeParameters> type_parameters_ordering_ = new ArrayList<>();
    private List<String> type_parameters_names_ = new ArrayList<>();

    private Map<String,Datatype> datatype_ = new HashMap<>(); // TODO Finish Datatype class
    private List<Datatype> datatype_ordering_ = new ArrayList<>();
    private List<String> datatype_names_ = new ArrayList<>();

    private Map<String,Operator> operator_ = new HashMap<>(); // TODO Create Operator class
    private List<Operator> operator_ordering_ = new ArrayList<>();
    private List<String> operator_names_ = new ArrayList<>();

    private Map<String,AxiomaticDefinition> axiom_def_ = new HashMap<>(); // TODO Create AxiomaticDefinition class
    private List<AxiomaticDefinition> axiom_def_ordering_ = new ArrayList<>();
    private List<String> axiom_def_names_ = new ArrayList<>();

    private Map<String,Theorem> theorems_ = new HashMap<>();
    private List<Theorem> theorem_ordering_ = new ArrayList<>();
    private List<String> theorem_names_ = new ArrayList<>();

    // private Map<String,ProofRules> proof_rules_ = new HashMap<>(); // TODO Create ProofRules class
    // private List<ProofRules> proof_rules_ordering_ = new ArrayList<>();
    // private List<String> proof_rules_names_ = new ArrayList<>();

    String comment_;

    File source_;
    Sys sys_;

    public Theory(String n, Sys s, File f) // TODO Finish
    {
        name_ = n;

        // types_ = new HashMap<>();
        // type_names_ = new ArrayList<>();
        sys_ = s;
        source_ = f;
    }

    public SymbolTable symbolTable()
    {
        return symbol_table_;
    }

    public Sys sys()
    {
        return sys_;
    }

    public String info()
    {
        String s = "thy: "+Util.padRightToLen(name_, ' ', 30);
        return s;
    }

    public String name()
    {
        return name_;
    }

    @Override
    public String toString()
    {
        return name_;
    }

    public String comment()
    {
        return comment_;
    }

    public boolean hasComment()
    {
        return !comment_.equals("");
    }

// -----------------------------------------------------------------------------
//    IMPORTS
// -----------------------------------------------------------------------------
    public Theory getImports(String name)
    {
        return imports_.get(name);
    }

    public List<Theory> importsOrdering()
    {
        return imports_ordering_;
    }

    public boolean hasImports()
    {
        return imports_.size() > 0;
    }

    public List<String> importsNames()
    {
        return imports_names_;
    }

    public void addImport(Theory imp)
    {
        imports_.put(imp.name(), imp);
        imports_ordering_.add(imp);
        imports_names_ = imports_.keySet().stream().sorted().collect(Collectors.toList());
    }

// -----------------------------------------------------------------------------
//    TYPE PARAMETERS
// -----------------------------------------------------------------------------
    public TypeParameters getTypeParameters(String name)
    {
        return type_parameters_.get(name);
    }

    public List<TypeParameters> typeParametersOrdering()
    {
        return type_parameters_ordering_;
    }

    public boolean hasTypeParameters()
    {
        return type_parameters_.size() > 0;
    }

    public List<String> typeParametersNames()
    {
        return type_parameters_names_;
    }

    public void addTypeParameters(TypeParameters type_param)
    {
        type_parameters_.put(type_param.name(), type_param);
        type_parameters_ordering_.add(type_param);
        type_parameters_names_ = type_parameters_.keySet().stream().sorted().collect(Collectors.toList());
    }

// -----------------------------------------------------------------------------
//    DATATYPES
// -----------------------------------------------------------------------------
    public Datatype getDatatype(String name)
    {
        return datatype_.get(name);
    }

    public List<Datatype> datatypesOrdering()
    {
        return datatype_ordering_;
    }

    public boolean hasDatatype()
    {
        return datatype_.size() > 0;
    }

    public List<String> datatypeNames()
    {
        return datatype_names_;
    }

    public void addDatatype(Datatype datatype)
    {
        datatype_.put(datatype.name(), datatype);
        datatype_ordering_.add(datatype);
        datatype_names_ = datatype_.keySet().stream().sorted().collect(Collectors.toList());
    }

// -----------------------------------------------------------------------------
//    OPERATOR
// -----------------------------------------------------------------------------
    public Operator getOperator(String name)
    {
        return operator_.get(name);
    }

    public List<Operator> operatorOrdering()
    {
        return operator_ordering_;
    }

    public boolean hasOperator()
    {
        return operator_.size() > 0;
    }

    public List<String> operatorNames()
    {
        return operator_names_;
    }

    public void addOperator(Operator operator)
    {
        operator_.put(operator.name(), operator);
        operator_ordering_.add(operator);
        operator_names_ = operator_.keySet().stream().sorted().collect(Collectors.toList());
    }

// -----------------------------------------------------------------------------
//    AXIOMATIC DEFINITION
// -----------------------------------------------------------------------------
    public AxiomaticDefinition getAxiomaticDefinition(String name)
    {
        return axiom_def_.get(name);
    }

    public List<AxiomaticDefinition> axiomaticDefinitionOrdering()
    {
        return axiom_def_ordering_;
    }

    public boolean hasAxiomaticDefinition()
    {
        return axiom_def_.size() > 0;
    }

    public List<String> axiomaticDefinitionNames()
    {
        return axiom_def_names_;
    }

    public void addAxiomaticDefinition(AxiomaticDefinition axiom_def)
    {
        axiom_def_.put(axiom_def.name(), axiom_def);
        axiom_def_ordering_.add(axiom_def);
        axiom_def_names_ = axiom_def_.keySet().stream().sorted().collect(Collectors.toList());
    }

// -----------------------------------------------------------------------------
//    THEOREMS
// -----------------------------------------------------------------------------

    public Theorem getTheorem(String name)
    {
        return theorems_.get(name);
    }

    public List<Theorem> theoremOrdering()
    {
        return theorem_ordering_;
    }

    public boolean hasTheorems()
    {
        return theorem_ordering_.size() > 0;
    }
    
    public List<String> theoremNames()
    {
        return theorem_names_;
    }

    public void addTheorem(Theorem t)
    {
        theorems_.put(t.name(), t);
        theorem_ordering_.add(t);
        theorem_names_ = theorems_.keySet().stream().sorted().collect(Collectors.toList());
    }

// -----------------------------------------------------------------------------
//    LOAD
// -----------------------------------------------------------------------------
    public void load() throws Exception
    {
        SAXReader reader = new SAXReader();
        Document document = reader.read(source_);
        log.debug("loading theory "+source_);

        List<Node> list = document.selectNodes("//org.eventb.theory.core.theoryRoot");

        for (Node m : list)
        {
            comment_ = m.valueOf("@org.eventb.core.comment");
        }

        list = document.selectNodes("//org.eventb.theory.core.importTheory");
        for (Node i : list)
        {
            String t = i.valueOf("@org.eventb.theory.core.importTheory");
            /*Theory import = sys_.getTheory(t); // TODO add getTheory in the system
            if (import == null) log.error("Error while loading theory %s, could not find imported theory %s", name(), m);
            addImport(import);*/
        }

        list = document.selectNodes("//org.eventb.theory.core.typeParameter");
        for (Node tp : list)
        {
            String name = tp.valueOf("@org.eventb.core.identifier");
            String comment = tp.valueOf("@org.eventb.core.comment");
            addTypeParameters(new TypeParameters(name, comment));
        }

        list = document.selectNodes("//org.eventb.theory.core.datatypeDefinition");
        for (Node dt : list)
        {
            String name = dt.valueOf("@org.eventb.core.identifier");
            String comment = dt.valueOf("@org.eventb.core.comment");
            //TODO Finish
            addDatatype(new Datatype(name,comment));
        }

        list = document.selectNodes("//org.eventb.theory.core.newOperatorDefinition");
        for (Node op : list)
        {
            String name = op.valueOf("@org.eventb.core.label");
            String comment = op.valueOf("@org.eventb.core.comment");
            boolean associative = op.valueOf("@org.eventb.theory.core.associative").equals("true");
            boolean commutative = op.valueOf("@org.eventb.theory.core.commutative").equals("true");

            Operator operator = new Operator(name,associative,commutative,comment);

            List<Node> arguments = op.selectNodes("org.eventb.theory.core.operatorArgument");
            for (Node arg : arguments)
            {
                String i = arg.valueOf("@org.eventb.core.identifier");
                String e = arg.valueOf("@org.eventb.core.expression");
                String c = arg.valueOf("@org.eventb.core.comment");
                //TODO Finish the Argument class.
                //operator.addArgument(new Argument(i, e, c)); 
            }

            List<Node> well_def_cond = op.selectNodes("org.eventb.theory.core.operatorWDcondition");
            for (Node wdc : well_def_cond)
            {
                String well_def = wdc.valueOf("@org.eventb.core.predicate");
                //String c = wdc.valueOf("@org.eventb.core.comment");
                //operator.addWDC(well_def); //TODO
            }

            List<Node> direct_def = op.selectNodes("org.eventb.theory.core.directOperatorDefinition");
            for (Node dd : direct_def)
            {
                String d = dd.valueOf("@org.eventb.theory.core.formula");
                //String c = dd.valueOf("@org.eventb.core.comment");
                //operator.addDirectDef(d); //TODO
            }

            addOperator(operator);
        }

        list = document.selectNodes("//org.eventb.theory.core.axiomaticDefinitionsBlock");
        for (Node axd : list)
        {
            String name = axd.valueOf("@org.eventb.core.label");
            String comment = axd.valueOf("@org.eventb.core.comment");
            AxiomaticDefinition axiomatic_definition = new AxiomaticDefinition(name,comment);

            List<Node> axiomatic_type_def = axd.selectNodes("org.eventb.theory.core.axiomaticTypeDefinition");
            for (Node ax_td : axiomatic_type_def)
            {
                //TODO Finish
            }

            List<Node> axiomatic_op_def = axd.selectNodes("org.eventb.theory.core.axiomaticOperatorDefinition");
            for (Node ax_op : axiomatic_op_def)
            {
                //TODO Finish
            }

            List<Node> axiom = axd.selectNodes("org.eventb.theory.core.axiomaticDefinitionAxiom");
            for (Node ax : axiom)
            {
                String l = ax.valueOf("@org.eventb.core.label");
                String p = ax.valueOf("@org.eventb.core.predicate");
                String c = ax.valueOf("@org.eventb.core.comment");
                axiomatic_definition.addAxiom(new Axiom(l,p,c));
            }

            addAxiomaticDefinition(axiomatic_definition);
        }

        list = document.selectNodes("//org.eventb.theory.core.theorem");
        for (Node n : list)
        {
            String name = n.valueOf("@org.eventb.core.label");
            String pred = n.valueOf("@org.eventb.core.predicate");
            String comment = n.valueOf("@org.eventb.core.comment");

            Theorem t = new Theorem(name, pred, comment);
            addTheorem(t);
        }
    }

// -----------------------------------------------------------------------------
//    buildSymbolTable
// -----------------------------------------------------------------------------
    private void buildSymbolTable(SymbolTable parent)
    {
        // if (symbol_table_ != null) return;

        // symbol_table_ = sys_.newSymbolTable(name_, parent);

        // if (refines_ != null)
        // {
        //     refines_.buildSymbolTable(parent);
        //     symbol_table_.addParent(refines_.symbolTable());
        // }

        // for (Context c : contextOrdering())
        // {
        //     symbol_table_.addParent(c.symbolTable());
        // }

        // for (Variable v : variableOrdering())
        // {
        //     Variable vv  = symbol_table_.getVariable(v.name());
        //     if (vv != null)
        //     {
        //         SymbolTable st = symbol_table_.whichSymbolTableForVariable(v.name());
        //         log.debug("variable %s refines %s in %s", v, v, st.name());
        //         v.setRefines(vv);
        //     }
        //     symbol_table_.addVariable(v);
        // }
    }

// -----------------------------------------------------------------------------
//    PARSE
// -----------------------------------------------------------------------------
    public void parse()
    {
        // buildSymbolTable(st);

        // log.debug("parsing %s", name());

        // for (String name : invariantNames())
        // {
        //     Invariant i = getInvariant(name);
        //     i.parse(symbol_table_);
        //     sys().typing().extractInfoFromInvariant(i.formula(), symbol_table_);
        // }

        // for (String name : variantNames())
        // {
        //     Variant i = getVariant(name);
        //     i.parse(symbol_table_);
        //     //sys().typing().extractInfoFromInvariant(i.formula(), symbol_table_);
        // }

        // for (String name : eventNames())
        // {
        //     Event e = getEvent(name);
        //     e.parse();
        // }
    }

// -----------------------------------------------------------------------------
//    ???
// -----------------------------------------------------------------------------
    public void showw(ShowSettings ss, Canvas canvas)
    {
        // StringBuilder o = new StringBuilder();
        // o.append(name_);
        // if (refines_ != null)
        // {
        //     o.append(" ⊏ ");
        //     o.append(refines_.name());
        // }
        // o.append("\n");
        // o.append("-\n");
        // for (Context c : contextOrdering())
        // {
        //     o.append(c.name());
        //     o.append("\n");
        // }
        // o.append("-\n");
        // for (Variable v : variableOrdering())
        // {
        //     if (v.comment().length() > 0)
        //     {
        //         String cc = "";
        //         if (ss.showingComments())
        //         {
        //             cc = "    "+v.comment();
        //         }
        //         o.append(v.name()+cc);
        //         o.append("\n");
        //     }
        // }
        // if (ss.showingInvariants())
        // {
        //     o.append("-\n");
        //     for (Invariant inv : invariantOrdering())
        //     {
        //         o.append(inv.writeFormulaStringToCanvas(canvas));
        //         o.append("\n");
        //     }
        // }
        // o.append("-\n");
        // for (Event e : eventOrdering())
        // {
        //     if (!e.isEmpty())
        //     {
        //         o.append(e.name());
        //         o.append("\n");
        //     }
        // }
        // String f = canvas.frame("", o.toString(), Canvas.dline);
        // String comment = "";
        // if (ss.showingComments())
        // {
        //     comment = Canvas.flow(canvas.layoutWidth(), comment_);
        // }
        // canvas.flush();
        // canvas.appendBox("\n\n"+comment);
        // canvas.flush();
        // canvas.appendBox(f);
        // canvas.flush();
        // canvas.appendBox("\n\n");
        // canvas.flush();

        // if (ss.showingEvents())
        // {
        //     for (Event e : eventOrdering())
        //     {
        //         if (!e.isEmpty())
        //         {
        //             e.show(ss, canvas);
        //         }
        //     }
        // }
    }

    // public Event getConcreteEvent(String name)
    // {
    //     return concrete_events_.get(name);
    // }

    // public List<Event> concreteEventOrdering()
    // {
    //     return concrete_event_ordering_;
    // }

    // public List<String> concreteEventNames()
    // {
    //     return concrete_event_names_;
    // }

    public void buildImplementation() throws Exception
    {
        // log_codegen.debug("Building implementation for %s", this);
        // for (Event e : eventOrdering())
        // {
        //     buildConcreteEvent(e);
        // }
        // log_codegen.debug("Done building implementation for %s", this);
        // concrete_event_names_ = concrete_events_.keySet().stream().sorted().collect(Collectors.toList());
    }

    private void buildConcreteEvent(Event eventt) throws Exception
    {
    //     Event concrete_event = eventt.deepCopy();
    //     log_codegen.debug("Building concrete event %s:%s", eventt.machine(), concrete_event);
    //     concrete_events_.put(concrete_event.name(), concrete_event);
    //     concrete_event_ordering_.add(concrete_event);
    //     Event i = concrete_event;

    //     // Check if the event extends another event.
    //     while (i.extended())
    //     {
    //         Event extended = i.refinesEvents().get(0);
    //         String extended_event_name = extended.name();
    //         log_codegen.debug("Spec says event %s extends %s::%s", i.name(), i.machine().refines(), extended_event_name);

    //         Machine mch = i.machine().refines();
    //         Event extended_event = mch.getEvent(extended_event_name);
    //         if (extended_event == null)
    //         {
    //             log_codegen.error("Could not find extended event %s in machine %s!",
    //                               extended_event_name, mch.name());
    //         }
    //         else
    //         {
    //             concrete_event.extendFrom(extended_event);
    //             log_codegen.debug("Extended from %s in %s", extended_event, mch);
    //             i = extended_event;
    //             // Loop around to check if this one also extends from another event.
    //         }
    //     }
    //     log_codegen.debug("Done building concrete event %s", concrete_event);
    }

}
