package hr.hsnopek.softwaresaunacodechallenge;

import test.JunitTestSuite;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SoftwareSaunaCodeChallenge {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Software Sauna Code Challenge");
		System.out.println("------------------------------");
		System.out.println(
				"Path following algorithm in ASCII Map. Find the position of character @. "
				+ "Follow the path, stop when character x is reached. \r\n"
				+ "Write a piece of code that takes ASCII map as an input and outputs the collected letters and "
				+ "the list of characters of the travelled path.\r\n"
				);

		
		Result result = JUnitCore.runClasses(JunitTestSuite.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println();

		if (result.wasSuccessful())
			System.out.println(String.format("All %d tests completed succesfully!", result.getRunCount()));

	}
}
