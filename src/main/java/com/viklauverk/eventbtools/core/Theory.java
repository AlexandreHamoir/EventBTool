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
    private static LogModule log = LogModule.lookup("theory"); //Add to LogModule
    private static LogModule log_codegen = LogModule.lookup("codegen");

    private SymbolTable symbol_table_;

    private String name_;

    private Map<String,Theory> imports_ = new HashMap<>();
    private List<Theory> imports_ordering_ = new ArrayList<>();
    private List<String> imports_names_ = new ArrayList<>();

    private Map<String,TypeParameters> type_parameters_ = new HashMap<>(); // TODO Create TypeParameters class
    private List<TypeParameters> type_parameters_ordering_ = new ArrayList<>();
    private List<String> type_parameters_names_ = new ArrayList<>();

    private Map<String,Datatype> datatype_ = new HashMap<>(); // TODO Create Datatype class
    private List<Datatype> datatype_ordering_ = new ArrayList<>();
    private List<String> datatype_names_ = new ArrayList<>();

    private Map<String,Operator> operator_ = new HashMap<>(); // TODO Create Operator class
    private List<Operator> operator_ordering_ = new ArrayList<>();
    private List<String> operator_names_ = new ArrayList<>();

    private Map<String,AxiomaticDefinition> axiom_def_ = new HashMap<>(); // TODO Create AxiomaticDefinition class
    private List<AxiomaticDefinition> axiom_def_ordering_ = new ArrayList<>();
    private List<String> axiom_def_names_ = new ArrayList<>();

    // private Map<String,ProofRules> proof_rules_ = new HashMap<>(); // TODO Create ProofRules class
    // private List<ProofRules> proof_rules_ordering_ = new ArrayList<>();
    // private List<String> proof_rules_names_ = new ArrayList<>();

    // These are the calculated types that variables can be of.
    // private Map<String,Type> types_;
    // private List<String> type_names_;

    // The concrete events (merged with extended events) in
    // more abstract machines.
    // private Map<String,Event> concrete_events_ = new HashMap<>();
    // private List<Event> concrete_event_ordering_ = new ArrayList<>();
    // private List<String> concrete_event_names_ = new ArrayList<>();

    String comment_; // Usually the copyright notice.

    File source_;
    Sys sys_;

    public Theory(String n, Sys s, File f) // TODO make
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

    // public Machine refines()
    // {
    //     return refines_;
    // }

    // public boolean hasRefines()
    // {
    //     return refines_ != null;
    // }

    // public String machineOrRefinement()
    // {
    //     return (refines_ == null) ? "machine" : "refinement";
    // }

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

    public void addTheory(Theory imp)
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
    public Datatypes getDatatypes(String name)
    {
        return datatype_.get(name);
    }

    public List<Datatypes> datatypesOrdering()
    {
        return datatype_ordering_;
    }

    public boolean hasDatatypes()
    {
        return datatype_.size() > 0;
    }

    public List<String> datatypeNames()
    {
        return datatype_names_;
    }

    public void addDatatypes(Datatypes datatype)
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
//    VARIANT
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
//    LOAD TODO
// -----------------------------------------------------------------------------
    public void load() throws Exception
    {
        // SAXReader reader = new SAXReader();
        // Document document = reader.read(source_);
        // log.debug("loading machine "+source_);

        // List<Node> machine_comment = document.selectNodes("//org.eventb.core.machineFile");

        // for (Node m : machine_comment)
        // {
        //     comment_ = m.valueOf("@org.eventb.core.comment");
        // }

        // List<Node> refines_machine = document.selectNodes("//org.eventb.core.refinesMachine");
        // for (Node r : refines_machine)
        // {
        //     String m = r.valueOf("@org.eventb.core.target");
        //     Machine mch = sys_.getMachine(m);
        //     if (mch == null) log.error("Error while loading machine %s, could not find refined machine %s", name(), m);
        //     if (refines_ != null) log.error("Error while loading machine %s, cannot refine more than one mache.");
        //     refines_ = mch;
        // }

        // List<Node> contexts = document.selectNodes("//org.eventb.core.seesContext");
        // for (Node c : contexts)
        // {
        //     String t = c.valueOf("@org.eventb.core.target");
        //     Context context = sys_.getContext(t);
        //     if (context == null) log.error("Error while loading machine %s, could not find context %s", name(), t);
        //     addContext(context);
        // }

        // List<Node> variables = document.selectNodes("//org.eventb.core.variable");
        // for (Node v : variables)
        // {
        //     String n = v.valueOf("@org.eventb.core.identifier");
        //     String c = v.valueOf("@org.eventb.core.comment");
        //     Variable var = new Variable(n, c);
        //     addVariable(var);
        // }

        // List<Node> invariants = document.selectNodes("//org.eventb.core.invariant");
        // for (Node i : invariants)
        // {
        //     String name = i.valueOf("@org.eventb.core.label").trim(); // Clean any spurious newlines at end of label.
        //     String fs = i.valueOf("@org.eventb.core.predicate");
        //     String comment = i.valueOf("@org.eventb.core.comment");
        //     String theorem = i.valueOf("@org.eventb.core.theorem");
        //     boolean is_theorem = theorem.equals("true");
        //     Invariant invar = new Invariant(name, fs, comment, is_theorem);
        //     addInvariant(invar);
        // }

        // List<Node> variants = document.selectNodes("//org.eventb.core.variant");
        // for (Node i : variants)
        // {
        //     String name = i.valueOf("@org.eventb.core.label");
        //     String expression = i.valueOf("@org.eventb.core.expression");
        //     String comment = Util.trimLines(i.valueOf("@org.eventb.core.comment"));
        //     Variant variant = new Variant(name, expression, comment);
        //     addVariant(variant);
        // }

        // List<Node> events = document.selectNodes("//org.eventb.core.event");
        // for (Node e : events)
        // {
        //     String name = e.valueOf("@org.eventb.core.label");
        //     String comment = e.valueOf("@org.eventb.core.comment");
        //     boolean ext = e.valueOf("@org.eventb.core.extended").equals("true");
        //     Convergence convergence = Convergence.from(e.valueOf("@org.eventb.core.convergence"));
        //     Event event = new Event(name, ext, comment, this, convergence);
        //     addEvent(event);

        //     List<Node> refines = e.selectNodes("org.eventb.core.refinesEvent");
        //     for (Node r : refines)
        //     {
        //         String identifier = r.valueOf("@org.eventb.core.target");
        //         event.addRefinesEventName(identifier);
        //     }
        //     if (event.name().equals("INITIALISATION") && event.extended())
        //     {
        //         event.addRefinesEventName("INITIALISATION");
        //     }
        //     List<Node> parameters = e.selectNodes("org.eventb.core.parameter");
        //     for (Node p : parameters)
        //     {
        //         String i = p.valueOf("@org.eventb.core.identifier");
        //         String c = p.valueOf("@org.eventb.core.comment");
        //         event.addParameter(new Variable(i, c));
        //     }

        //     List<Node> guards = e.selectNodes("org.eventb.core.guard");
        //     for (Node g : guards)
        //     {
        //         String l = g.valueOf("@org.eventb.core.label");
        //         String p = g.valueOf("@org.eventb.core.predicate");
        //         String c = g.valueOf("@org.eventb.core.comment");
        //         event.addGuard(new Guard(l, p, c));
        //     }

        //     List<Node> witnesses = e.selectNodes("org.eventb.core.witness");
        //     for (Node w : witnesses)
        //     {
        //         String l = w.valueOf("@org.eventb.core.label");
        //         String p = w.valueOf("@org.eventb.core.predicate");
        //         String c = w.valueOf("@org.eventb.core.comment");
        //         event.addWitness(new Witness(l, p, c));
        //     }

        //     List<Node> actions = e.selectNodes("org.eventb.core.action");
        //     for (Node a : actions)
        //     {
        //         String l = a.valueOf("@org.eventb.core.label");
        //         String f = a.valueOf("@org.eventb.core.assignment");
        //         String c = a.valueOf("@org.eventb.core.comment");
        //         event.addAction(new Action(l, f, c));
        //     }
        // }
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
    public void parse(SymbolTable st)
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
