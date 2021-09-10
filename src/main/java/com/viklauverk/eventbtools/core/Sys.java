/*
 Copyright (C) 2021 Viklauverk AB
 Author Fredrik Öhrström & Marius Hinge

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

import java.net.URL;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;

public class Sys
{
    private static LogModule log = LogModule.lookup("sys");

    private String project_info_; // Loaded from project.info and displayed above the table of contents.

    private SymbolTable root_symbol_table_; // Contains TRUE FALSE BOOL
    private Map<String,SymbolTable> all_symbol_tables_;
    private Typing typing_;

    private Map<String,Theory> theories_;
    private List<Theory> theorie_ordering_;
    private List<String> theorie_names_;

    private Map<String,Context> contexts_;
    private List<Context> context_ordering_;
    private List<String> context_names_;

    private Map<String,Machine> machines_;
    private List<Machine> machine_ordering_;
    private List<String> machine_names_;

    private Console console_;
    private EDK edk_;

    public Sys()
    {
        project_info_ = "";

        theories_ = new HashMap<>();
        theorie_ordering_ = new ArrayList<>();
        theorie_names_ = new ArrayList<>();
        contexts_ = new HashMap<>();
        context_ordering_ = new ArrayList<>();
        context_names_ = new ArrayList<>();
        machines_ = new HashMap<>();
        machine_ordering_ = new ArrayList<>();
        machine_names_ = new ArrayList<>();

        root_symbol_table_ = new SymbolTable("root");
        all_symbol_tables_ = new HashMap<>();
        all_symbol_tables_.put("root", root_symbol_table_);

        typing_ = new Typing();

        CarrierSet BOOL = new CarrierSet("BOOL");
        Constant TRUE = new Constant("TRUE");
        Constant FALSE = new Constant("FALSE");
        BOOL.addMember(TRUE);
        BOOL.addMember(FALSE);
        TRUE.setType(typing_.lookup("BOOL"));
        FALSE.setType(typing_.lookup("BOOL"));
        root_symbol_table_.addSet(BOOL);
        root_symbol_table_.addConstant(TRUE);
        root_symbol_table_.addConstant(FALSE);

        console_ = new Console(this, new Canvas());
        edk_ = new EDK(this);
    }

    public String projectInfo()
    {
        return project_info_;
    }

    public Console console()
    {
        return console_;
    }

    public EDK edk()
    {
        return edk_;
    }

    public SymbolTable rootSymbolTable()
    {
        return root_symbol_table_;
    }

    public SymbolTable newSymbolTable(String name, SymbolTable parent)
    {
        if (parent == null) parent = root_symbol_table_;
        SymbolTable st = new SymbolTable(name);
        st.addParent(parent);
        all_symbol_tables_.put(name, st);
        return st;
    }

    public Map<String,SymbolTable> allSymbolTables()
    {
        return all_symbol_tables_;
    }

    public SymbolTable getSymbolTable(String name)
    {
        return all_symbol_tables_.get(name);
    }

    public Typing typing()
    {
        return typing_;
    }

    public void addTheory(Theory t)
    {
        theories_.put(t.name(), t);
        theorie_ordering_.add(t);
        theorie_names_ = theories_.keySet().stream().sorted().collect(Collectors.toList());
    }

    public Theory getTheory(String name)
    {
        return theories_.get(name);
    }

    public List<Theory> theoryOrdering()
    {
        return theorie_ordering_;
    }

    public List<String> theoryNames()
    {
        return theorie_names_;
    }

    public void addContext(Context c)
    {
        contexts_.put(c.name(), c);
        context_ordering_.add(c);
        context_names_ = contexts_.keySet().stream().sorted().collect(Collectors.toList());
    }

    public Context getContext(String name)
    {
        return contexts_.get(name);
    }

    public List<Context> contextOrdering()
    {
        return context_ordering_;
    }

    public List<String> contextNames()
    {
        return context_names_;
    }

    public void addMachine(Machine m)
    {
        machines_.put(m.name(), m);
        machine_ordering_.add(m);
        machine_names_ = machines_.keySet().stream().sorted().collect(Collectors.toList());
    }

    public Machine getMachine(String name)
    {
        return machines_.get(name);
    }

    public List<Machine> machineOrdering()
    {
        return machine_ordering_;
    }

    public List<String> machineNames()
    {
        return machine_names_;
    }

    //TODO Bad name :c
    public String loadMachinesAndContexts(String path) throws Exception
    {
        if (path == null || path.equals("")) return "";

        File dir = new File(path);

        if (!dir.exists() || !dir.isDirectory())
        {
            LogModule.usageErrorStatic("Not a valid directory \"%s\"", path);
        }

        // First create empty object instances for each context and machine.
        populateTheories(dir);
        populateContexts(dir);
        populateMachines(dir);

        // Now load the content, it is now possible to refer to other
        // contexts and machines since we have prepopulated those maps.
        // These will also add the known symbols for sets,constants and variables
        // to the SymbolTables that are needed for parsing the formulas.
        loadTheories();
        loadContexts();
        loadMachines();

        // Now we can actually parse the formulas and figure out the types.
        parseTheoryFormulas();
        parseContextFormulas();
        parseMachineFormulas();


        if (contextNames().size() == 0 && machineNames().size() == 0 && theoryNames().size() == 0)
        {
            log.info("No contexts, machines or theories found in %s\n",  path);
        }

        // Load the projct.info file, if it exists.
        loadProjectInfo(dir);

        /*return "read "+contextNames().size()+" contexts, "+machineNames().size()+" machines, "+theoryNames().size()+" theories";*/ // This command will fail the automatic tests.
        return "read "+contextNames().size()+" contexts and "+machineNames().size()+" machines"; //TODO Correct the test to make them pass.
    }

    private List<Pair<String,File>> eachFileEndingIn(File dir, String suffix)
    {
        List<File> files = Stream.of(dir.listFiles()).sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
        List<Pair<String,File>> result = new ArrayList<>();

        for (File f : files)
        {
            String name = f.getName();
            int pp = name.lastIndexOf('/');
            if (pp != -1) name = name.substring(pp+1);
            if (name.endsWith(suffix))
            {
                name = name.substring(0, name.length()-suffix.length());
                result.add(new Pair<String,File>(name, f));
            }
        }
        return result;
    }

    private void populateTheories(File dir) throws Exception
    {
        List<Pair<String,File>> files = eachFileEndingIn(dir, ".tuf");

        for (Pair<String,File> p : files)
        {
            String name = p.left;
            File file = p.right;
            Theory t = new Theory(name, this, file);
            addTheory(t);
            log.debug("found theory "+name);
        }
    }

    private void populateContexts(File dir) throws Exception
    {
        List<Pair<String,File>> files = eachFileEndingIn(dir, ".buc");

        for (Pair<String,File> p : files)
        {
            String name = p.left;
            File file = p.right;
            Context c = new Context(name, this, file);
            addContext(c);
            log.debug("found context "+name);
        }
    }

    private void populateMachines(File dir) throws Exception
    {
        List<Pair<String,File>> files = eachFileEndingIn(dir, ".bum");

        for (Pair<String,File> p : files)
        {
            String name = p.left;
            File file = p.right;
            Machine m = new Machine(name, this, file);
            addMachine(m);
            log.debug("found machine "+m.name());
        }
    }

    private void loadTheories() throws Exception
    {
        for (String name : theoryNames())
        {
            Theory t = getTheory(name);
            t.load();
        }
    }

    private void loadContexts() throws Exception
    {
        for (String name : contextNames())
        {
            Context c = getContext(name);
            c.load();
        }
    }

    private void loadMachines() throws Exception
    {
        for (String name : machineNames())
        {
            Machine m = getMachine(name);
            m.load();
        }
    }

    private void loadProjectInfo(File dir) throws Exception
    {
        List<Pair<String,File>> files = eachFileEndingIn(dir, ".info");

        for (Pair<String,File> p : files)
        {
            if (p.left.equals("project"))
            {
                Stream<String> lines = Files.lines(p.right.toPath());
                project_info_ = lines.collect(Collectors.joining("\n"));
                lines.close();
                log.debug("found project.info");
            }
        }
    }

    private void parseTheoryFormulas()
    {
        for (String name : theoryNames())
        {
            Theory t = getTheory(name);
            t.parse();
        }
    }

    private void parseContextFormulas()
    {
        for (String name : contextNames())
        {
            Context c = getContext(name);
            c.parse();
        }
    }

    private void parseMachineFormulas()
    {
        for (String name : machineNames())
        {
            Machine m = getMachine(name);
            m.parse(rootSymbolTable());
        }
    }

    public void walkSystem(AllRenders ar, String pattern)
    {
        for (Theory t : theoryOrdering())
        {
            ar.walkTheory(t, pattern);
        }
   
        for (Context c : contextOrdering())
        {
            ar.walkContext(c, pattern);
        }

        for (Machine m : machineOrdering())
        {
            ar.walkMachine(m, pattern);
        }
    }

    public List<String> listParts()
    {
        AllRenders ar = lookupSearchWalker();

        walkSystem(ar, "");

        return ar.search().foundParts();
    }

    public String show(ShowSettings ss, Canvas canvas, String pattern)
    {
        AllRenders ar = lookupRenders(RenderTarget.TERMINAL,
                                      canvas);

        //TODO Add theories
        if (ss.showingTheories())
        {
            for (Theory th : theoryOrdering())
            {
                ar.walkTheory(th, pattern);
            }
        }

        if (ss.showingContexts())
        {
            for (Context c : contextOrdering())
            {
                ar.walkContext(c, pattern);
            }
        }

        if (ss.showingMachines())
        {
            for (Machine m : machineOrdering())
            {
                ar.walkMachine(m, pattern);
            }
        }

        return canvas.render();
    }

    AllRenders lookupSearchWalker()
    {
        return new AllRenders(new RenderContextSearch(),
                              new RenderMachineSearch(),
                              new RenderEventSearch(),
                              new RenderFormulaSearch(null),
                              new RenderTheorySearch(),
                              null);
    }

    AllRenders lookupRenders(RenderTarget format, Canvas canvas)
    {
        switch (format)
        {
        case PLAIN:
            return new AllRenders(new RenderContextUnicode(),
                                  new RenderMachineUnicode(),
                                  new RenderEventUnicode(),
                                  new RenderFormulaUnicode(canvas),
                                  new RenderTheoryUnicode(),
                                  canvas);
        case TERMINAL:
            return new AllRenders(new RenderContextUnicode(),
                                     new RenderMachineUnicode(),
                                     new RenderEventUnicode(),
                                     new RenderFormulaUnicode(canvas),
                                     new RenderTheoryUnicode(),
                                     canvas);
        case TEX:
           return new AllRenders(new RenderContextTeX(),
                                    new RenderMachineTeX(),
                                    new RenderEventTeX(),
                                    new RenderFormulaTeX(canvas),
                                    new RenderTheoryTeX(),
                                    canvas);
        case HTMQ:
           return new AllRenders(new RenderContextHtmq(),
                                    new RenderMachineHtmq(),
                                    new RenderEventHtmq(),
                                    new RenderFormulaHtmq(canvas),
                                    new RenderTheoryHtqm(),
                                    canvas);
        }
        assert (false) : "No case for format: "+format;
        return null;
    }

}
