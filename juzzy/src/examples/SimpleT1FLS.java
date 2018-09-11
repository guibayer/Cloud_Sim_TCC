/*
 * SimpleT1FLS.java
 *
 * Created on May 20th 2012
 *
 * Copyright 2012 Christian Wagner All Rights Reserved.
 */
package examples;

import generic.Input;
import generic.Output;
import generic.Tuple;
import tools.JMathPlotter;
import type1.sets.T1MF_Interface;
import type1.sets.T1MF_Trapezoidal;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

/**
 * A simple example of a type-1 FLS based on the "How much to tip the waiter"
 *  scenario.
 * We have two inputs: food quality and service level and as an output we would
 * like to generate the applicable tip.
 * @author Christian Wagner
 */
public class SimpleT1FLS 
{
    Input cpu;    //the inputs to the FLS
    Input memory;
    Output priority;             //the output of the FLS
    T1_Rulebase rulebase;   //the rulebase captures the entire FLS
    
	public SimpleT1FLS()
    {
        //Define the inputs
        cpu = new Input("Cpu usage", new Tuple(0,10));
        memory = new Input("Memory usage", new Tuple(0,10));
        priority = new Output("Priority", new Tuple(0,10));

       //** INPUT FUNCTIONS **//
        
        //Set up the membership functions (MFs) for each input and output
        double[] parametroslowCpu = {-3.74, -0.476, 0.5, 3}; 
        T1MF_Trapezoidal lowCpuMF = new T1MF_Trapezoidal("MF for low CPU", parametroslowCpu);
        double[] parametrosreasonableCpu =  {2, 4, 6, 8};
        T1MF_Trapezoidal reasonableCpuMF = new T1MF_Trapezoidal("MF for reasonable CPU", parametrosreasonableCpu);
        double[] parametroshighCpu =  {7, 8, 10, 10};
        T1MF_Trapezoidal highCpuMF = new T1MF_Trapezoidal("MF for high CPU", parametroshighCpu);

        //Set up the antecedents and consequents - note how the inputs are associated...
        T1_Antecedent lowCpu = new T1_Antecedent("lowCpu",lowCpuMF, cpu);
        T1_Antecedent reasonableCpu = new T1_Antecedent("ReasonableCpu",reasonableCpuMF, cpu);
        T1_Antecedent highCpu = new T1_Antecedent("HighCpu",highCpuMF, cpu);

        //Set up the membership functions (MFs) for each input and output
        double[] parametroslowMem = {-3.74, -0.476, 0.5, 3}; 
        T1MF_Trapezoidal lowMemMF = new T1MF_Trapezoidal("MF for low Memory", parametroslowMem);
        double[] parametrosreasonableMem =  {2, 4, 6, 8};
        T1MF_Trapezoidal reasonableMemMF = new T1MF_Trapezoidal("MF for reasonable Memory", parametrosreasonableMem);
        double[] parametroshighMem =  {7, 8, 10, 10};
        T1MF_Trapezoidal highMemMF = new T1MF_Trapezoidal("MF for high Memory", parametroshighMem);

        //Set up the antecedents and consequents - note how the inputs are associated...
        T1_Antecedent lowMem = new T1_Antecedent("lowMemory",lowMemMF, memory);
        T1_Antecedent reasonableMem = new T1_Antecedent("ReasonableMemory",reasonableMemMF, memory);
        T1_Antecedent highMem = new T1_Antecedent("HighMemory",highMemMF, memory);
        
        //** OUTPUT FUNCTIONS **//
        
        //Set up the membership functions (MFs) for each input and output
        double[] parametrosLowPriority = {-1.36, -0.0428, 0.4, 4.5}; 
        T1MF_Trapezoidal lowPriorityMF = new T1MF_Trapezoidal("MF for low priority", parametrosLowPriority);
        double[] parametrosNormalPriority =  {1, 4.6, 5.4, 9};
        T1MF_Trapezoidal normalPriorityMF = new T1MF_Trapezoidal("MF for normal priority", parametrosNormalPriority);
        double[] parametrosHighPriority=  {5.5, 9.6, 10, 10};
        T1MF_Trapezoidal highPriorityMF = new T1MF_Trapezoidal("MF for high priority", parametrosHighPriority);
        
        T1_Consequent lowPriorityToReceiveVM = new T1_Consequent("LowPriority", lowPriorityMF, priority);
        T1_Consequent normalPriorityToReceiveVM = new T1_Consequent("NormalPriority", normalPriorityMF, priority);
        T1_Consequent highPriorityToReceiveVM = new T1_Consequent("HighPriority", highPriorityMF, priority);

        //Set up the rulebase and add rules
        rulebase = new T1_Rulebase(9);
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{lowCpu, lowMem}, highPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{lowCpu, reasonableMem}, lowPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{lowCpu, highMem}, lowPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{reasonableCpu, lowMem}, highPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{reasonableCpu, reasonableMem}, normalPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{reasonableCpu, highMem}, lowPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{highCpu, lowMem}, highPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{highCpu, reasonableMem}, normalPriorityToReceiveVM));
        rulebase.addRule(new T1_Rule(new T1_Antecedent[]{highCpu, highMem}, lowPriorityToReceiveVM));
        
        //just an example of setting the discretisation level of an output - the usual level is 100
        priority.setDiscretisationLevel(100);        
        
        //get some outputs
        
        for(int i = 0; i <= 20; i++) {
        	for(int j = 0; j <= 20; j++) {
	        	double cpuInput = i * 0.5;
	        	double memoryInput = j * 0.5;
	        	getPriority(cpuInput, memoryInput);
        	}
        }
        
        //plot some sets, discretizing each input into 100 steps.
        plotMFs("Cpu Membership Functions", new T1MF_Interface[]{lowCpuMF, reasonableCpuMF, highCpuMF},
        		cpu.getDomain(), 100); 
        plotMFs("Priority Membership Functions", new T1MF_Interface[]{lowPriorityMF, normalPriorityMF, highPriorityMF}, 
        		priority.getDomain(), 100);
        //plot control surface
        //do either height defuzzification (false) or centroid d. (true)
        //plotControlSurface(true, 100, 100);
        
        
        //cpu.setInput(cpuUsage);
        //setReturnvalue(rulebase.evaluate(1).get(priority));
        
        //print out the rules
        System.out.println("\n"+rulebase);        
    }
    
    /**
     * Basic method that prints the output for a given set of inputs.
     * @param foodQuality
     * @param serviceLevel 
     */
    public double getPriority(double cpuUsage, double memUsage)
    {
        //first, set the inputs
        cpu.setInput(cpuUsage);
        memory.setInput(memUsage);
        //now execute the FLS and print output
        /*System.out.println("The CPU was: "+cpu.getInput());
        System.out.println("The Memory was: "+memory.getInput());
        System.out.println("Using height defuzzification, the FLS recommends a priority of"
                + "tip of: "+rulebase.evaluate(0).get(priority)); 
        System.out.println("Using centroid defuzzification, the FLS recommends a priority of"
                + "tip of: "+rulebase.evaluate(1).get(priority));   */
    	
        
        return rulebase.evaluate(0).get(priority);
    }
    
    private void plotMFs(String name, T1MF_Interface[] sets, Tuple xAxisRange, int discretizationLevel)
    {
        JMathPlotter plotter = new JMathPlotter(17,17,15);
        for (int i=0;i<sets.length;i++)
        {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, xAxisRange, new Tuple(0.0,1.0), false);
        }
        plotter.show(name);
    }

    private void plotControlSurface(boolean useCentroidDefuzzification, int input1Discs, int input2Discs)
    {
        double output;
        double[] x = new double[input1Discs];
        double[] y = new double[input2Discs];
        double[][] z = new double[y.length][x.length];
        double incrX, incrY = 0;
        incrX = cpu.getDomain().getSize()/(input1Discs-1.0);

        //first, get the values
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
            x[currentX] = currentX * incrX;        
        }
        for(int currentY=0; currentY<input2Discs; currentY++)
        {
            y[currentY] = currentY * incrY;
        }
        
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
            cpu.setInput(x[currentX]);
            for(int currentY=0; currentY<input2Discs; currentY++)
            {
                if(useCentroidDefuzzification)
                    output = rulebase.evaluate(1).get(priority);
                else
                    output = rulebase.evaluate(0).get(priority);
                z[currentY][currentX] = output;
            }    
        }
        
        //now do the plotting
        JMathPlotter plotter = new JMathPlotter(17, 17, 14);
        plotter.plotControlSurface("Control Surface",
                new String[]{cpu.getName(), "Priority"}, x, y, z, new Tuple(0.0,30.0), true);   
       plotter.show("Type-1 Fuzzy Logic System Control Surface for Tipping Example");
    }

	public static void main(String args[]) {
		new SimpleT1FLS();
	}
	
}
