package com.AlyxGreen;

public class Main {
    public static void main(String[] args){
		/*
		Before starting with the median motif search, it's important to
		understand how mapping and reducing work. MapReduce is a specific
		software, but the principles can be applied to any data set in many
		different languages and software environments.
		
		This project aims to clarify how these functions work, then expand upon
		that into the actual project.
		*/
        
        // MAPPERS:
        // Each of these functions
        if (false){
            MapperExamples.BasicDoubler();
            MapperExamples.MapperDoubler();
            MapperExamples.ConcurrentDoubler();
        }
        
        // REDUCERS:
        // This class demonstrates some of the uses of reducers
        if (false){
            ReducerExamples.BasicSummation();
            ReducerExamples.ReducerSummation();
            ReducerExamples.MapReduceSummation();
            ReducerExamples.MapReduceConcurrent();
        }
    }
}
