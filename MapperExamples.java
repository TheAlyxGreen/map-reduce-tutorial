package com.AlyxGreen;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.IntStream;

import static com.AlyxGreen.UnimportantFunctions.randomArray;

public class MapperExamples {
	/**
	 * This function demonstrates how to iterate over an array with a standard
	 * "for" loop and double every number in it.
	 */
	public static void BasicDoubler(){
		System.out.println("BASIC DOUBLER");
		/*
		BASIC DOUBLER
		This function takes an array and uses a "for" loop to double every
		number in the array and store the results.
		 */
		
		// begin with an array
		int[] inputs = { 1, 2, 3, 4, 5 };
		
		// create an empty array to store the results
		int[] results = new int[inputs.length];
		
		// iterate over the array
		for (int i = 0; i < inputs.length; i++){
			
			// get the next input to be used
			int currentInput = inputs[i];
			
			// double it
			int currentOutput = currentInput * 2;
			
			// store it in the results array
			results[i] = currentOutput;
		}
		// print the inputs and outputs
		System.out.println("Inputs: " + Arrays.toString(inputs));
		System.out.println("Outputs: " + Arrays.toString(results));
		/*
		 When run, this code outputs:
		 Inputs: [1, 2, 3, 4, 5]
		 Outputs: [2, 4, 6, 8, 10]
		 This is the most obvious way to handle this problem, but that doesn't
		 mean it is the best. Some things to note:
		    - This is single threaded. If you run this on an array that is
		        1,000,000 elements long, it will perform very poorly.
		    - You manually create the output array at the start
		    - You can essentially do whatever you want inside of the loop.
		 */
		
	}
	/**
	 * This function replicates the BasicDoubler function, but uses a mapper
	 * function to do it.
	 */
	public static void MapperDoubler(){
		System.out.println("BASIC MAPPER");
		/*
		BASIC MAPPER
		Mapping in Java can be done by turning data into a "stream" and using
		the .map() function. Mappers take a function, then call it on each item
		in the stream. The results of these function calls is returned at the
		end. This can be visualised as:
		
		Inputs: [1, 2, 3, 4, 5]
		Mapper Function: f(x) = x * 2
		
		Inputs | Mapper | Outputs
		   x  ->  f(x) -> x * 2
		   1  ->  f(1) -> 2
		   2  ->  f(2) -> 4
		   3  ->  f(3) -> 6
		   4  ->  f(4) -> 8
		   5  ->  f(5) -> 10
		   
		   When implemented in real code, it's a bit less visually appealing,
		   but it still works the same:
        */
		
		// begin with an array
		int[] inputs = { 1, 2, 3, 4, 5 };
		
		// turn it into a stream
		IntStream inputsAsStream = Arrays.stream(inputs);
		
		// map a function to the stream that doubles every int
		IntStream resultsAsStream = inputsAsStream.map(currentInput -> currentInput * 2);
		
		// turn it back into an array
		int[] results = resultsAsStream.toArray();
		
		// print the inputs and outputs
		System.out.println("Inputs: " + Arrays.toString(inputs));
		System.out.println("Outputs: " + Arrays.toString(results));
		/*
		 Just like the basic doubler, this code outputs:
		 Inputs: [1, 2, 3, 4, 5]
		 Outputs: [2, 4, 6, 8, 10]
		 This method is a bit more complex, but here's the important things to
		 take note of:
		    - The "currentInput -> currentInput * 2" style function is called a
		        "lambda function," which is different from other, normal
		        functions.
		    - Lambda functions can only modify variables inside the function.
		        For example:
		            (x)-> x = x * 2;
		        This works because x is an argument of the function.
		        Counter example:
		            int y = 2;
		            (x)-> y = x * y;
		        This does not work because y is declared outside of the function
		*/
	}
	/**
	 * This function replicates the MapperDoubler function, except it runs the
	 * mapper concurrently
	 */
	public static void ConcurrentDoubler(){
		System.out.println("CONCURRENT DOUBLER");
		/*
		The power of mappers is their ability to run concurrently and process
		larges sets of data at once. Implementing this in Java is very simple
		and involves simply adding the .parallel() function to the mix. Check
		out the performance difference between the two
		 */
		
		int[] inputs = { 1, 2, 3, 4, 5 };
		
		IntStream inputsAsStream = Arrays.stream(inputs);
		
		IntStream resultsAsStream = inputsAsStream
			// this is the important addition to the process
			// adding this tells java to run the mapper across multiple threads
			.parallel()
			.map(currentInput -> currentInput * 2);
		
		int[] results = resultsAsStream.toArray();
		
		System.out.println("Inputs: " + Arrays.toString(inputs));
		System.out.println("Outputs: " + Arrays.toString(results));
		
		/*
		In an array this size, the difference is insignificant, but, as the data
		increases in size, the difference becomes greater and greater. While
		this program is limited to a single computer, programs like hadoop can
		distribute this workload across dozens of computers to speed the process
		up exponentially.
		 */
	}
	/**
	 * This function replicates both the MapperDoubler and ConcurrentDoubler
	 * functions, timing the duration of each to compare performance.
	 */
	public static void DoublerSpeedTest(){
		System.out.println("DOUBLER SPEED TEST");
		/*
		DOUBLER SPEED TEST
		Test the difference made by running a set of data in parallel versus
		running it sequentially.
		 */
		
		int arrayLength = 1000000;
		int loopCount = 10000;
		
		System.out.println("Generating inputs...");
		
		int[] inputs = randomArray(arrayLength);
		
		System.out.println("Running sequentially...");
		
		// record start time
		long sequentialStartTime = System.nanoTime();
		
		for (int i = 0; i < loopCount; i++){
			Arrays
				.stream(inputs)
				.map(currentInput -> currentInput * 2)
				.toArray();
		}
		
		// record stop time
		long sequentialEndTime = System.nanoTime();
		
		System.out.println("Running concurrently...");
		
		// record start time
		long concurrentStartTime = System.nanoTime();
		
		for (int i = 0; i < loopCount; i++){
			Arrays
				.stream(inputs)
				.parallel()
				.map(currentInput -> currentInput * 2)
				.toArray();
		}
		
		// record stop time
		long concurrentEndTime = System.nanoTime();
		
		Duration sequentialDuration = Duration.ofNanos(sequentialEndTime - sequentialStartTime);
		Duration concurrentDuration = Duration.ofNanos(concurrentEndTime - concurrentStartTime);
		
		System.out.printf("Sequential run took %d ms to complete\n", sequentialDuration.toMillis());
		System.out.printf("Concurrent run took %d ms to complete\n", concurrentDuration.toMillis());
	}
}
