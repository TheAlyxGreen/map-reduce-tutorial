package com.AlyxGreen;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ReducerExamples {
	/**
	 * This function demonstrates how to iterate over an array with a standard
	 * "for" loop and add every number in it.
	 */
	public static void BasicSummation(){
		System.out.println("BASIC SUMMATION");
		/*
		BASIC SUMMATION
		This function takes an array of integers, iterates over them in a "for"
		loop, and finds the sum of all numbers in the array.
		 */
		
		// begin with an array
		int[] inputs = { 1, 2, 3, 4, 5 };
		
		// grab the first value in the array
		int previousValue = inputs[0];
		
		// iterate over the array
		for (int i = 1; i < inputs.length; i++){
			
			// get the next value in the array
			int nextValue = inputs[i];
			
			// add the next value to the previous value
			previousValue = previousValue + nextValue;
		}
		
		// print results
		System.out.println("Output: " + previousValue);
		
		/*
		This function takes an array of integer, then *reduces* it to a single
		integer. It's a straight-forward solution to the problem, with a slight
		difference: it doesn't start at 0. Instead, it begins with the first
		element in the array. This doesn't change anything here, but it more
		closely mimics what actual reducers do.
		 */
	}
	/**
	 * This function replicates the BasicSummation function, but does so using
	 * a proper reducer function.
	 */
	public static void ReducerSummation(){
		System.out.println("REDUCER SUMMATION");
		/*
		REDUCER SUMMATION
		Java streams feature, among other things, both a .map() and a .reduce()
		function. They are pretty similar, except that, while .map() takes one
		input and gives one output, .reduce() takes two inputs to get one
		output.
		Reducers grab the first two elements in the stream, run the function on
		them, then passes that output into the reducer with the next input.
		This repeats until there is only one left. The process looks like this:
		
		Inputs: [1, 2, 3, 4, 5]
		Reducer Function: f(x,y) = x + y
		
		Inputs: [2, 3, 4, 5] <- inputs at start of this step
		NextValue: 2 <- next element in array
		LastValue: 1 <- last output (or first element)
		f(1, 2) = 3 <- output for this step
		
		Inputs: [3, 4, 5]
		NextValue: 3 <- take the 3 from the array, leaves [4, 5]
		LastValue: 3
		f(3, 3) = 6
		
		Inputs: [4, 5]
		NextValue: 4
		LastValue: 6
		f(6, 4) = 10
		
		Inputs: [5]
		NextValue: 5
		LastValue: 10
		f(10, 5) = 15
		
		Result = 15
		 */
		
		// begin with an array
		int[] inputs = { 1, 2, 3, 4, 5 };
		
		// turn it into a stream
		IntStream inputsAsStream = Arrays.stream(inputs);
		
		// reduce it by adding the last value and previous value
		int result = inputsAsStream
			.reduce((previousValue, nextValue) -> previousValue + nextValue)
			.orElseThrow(); // throw error if the reducer fails
		
		// print result
		System.out.println("Sum: " + result);
		
		/*
		Because reducers interact with multiple items in the stream at once,
		they can be a bit more complicated to write. Unlike with mapping, the
		order can matter, and parallelising the reducer takes a lot more
		consideration.
		Main takeaways:
			- Reducers start with values 0 and 1, then pass the *result* to the
				next step of the reducer
			- Because the previous output affect the next output, reducers can't
			    always be run in parallel.
			- Reducers take an array of variables and returns a single variable,
				so int[] -> int, string[] -> string, etc.
		 */
	}
	/**
	 * This function builds on the ReducerSummation function by first mapping
	 * the array, then reducing it.
	 */
	public static void MapReduceSummation(){
		System.out.println("MAP REDUCE SUMMATION");
		/*
		MAP REDUCE SUMMATION
		This function combines the .map() and .reduce() functions to show how
		they can work together.
		 */
		
		// begin with an array
		int[] inputs = { 1, 2, 3, 4, 5 };
		
		// turn it into a stream
		IntStream inputsAsStream = Arrays.stream(inputs);
		
		// map a doubling function to the stream
		IntStream mappedAsStream = inputsAsStream.map(input -> input * 2);
		
		// reduce the stream by adding them up
		int result = mappedAsStream
			.reduce((previousValue, nextValue) -> previousValue + nextValue)
			.orElseThrow();
		
		// print result
		System.out.println("Sum: " + result);
		
		/*
		Combining mappers and reducers is a powerful way to parallelize data
		processing and use multiple cores, multiple computers, or even
		multiple data centers all to solve a single problem. Main takeaway:
			- Mappers and Reducers can be strung together, with the output of
				each feeding into the next.
		 */
	}
	/**
	 * This function takes the MapReduceSummation function, condenses it,
	 * and runs it concurrently
	 */
	public static void MapReduceConcurrent(){
		System.out.println("MAP REDUCE CONCURRENT");
		/*
		MAP REDUCE CONCURRENT
		This function builds upon MapReduceSummation by making it run in
		parallel. It also shows what stringing mappers and reducers together
		looks like in practice.
		 */
		// begin with an array
		int[] inputs = { 1, 2, 3, 4, 5 };
		
		// turn it into a stream
		int result = Arrays.stream(inputs)
		                   // run in parallel
		                   .parallel()
		                   // double everything in the array
		                   .map(input -> input * 2)
		                   // sum the results
		                   .reduce((previousValue, nextValue) -> previousValue + nextValue)
		                   // make sure the reducer didn't fail
		                   .orElseThrow();
		
		// print result
		System.out.println("Sum: " + result);
		/*
		Chaining method calls together can have a lot of benefits, such as
		reducing the number of lines of code in a function or reducing the
		number of variables in a given scope.
		 */
	}
}
