package org.sonar.lua.checks;
import org.junit.Test;

import java.io.File;

import org.sonar.lua.LuaAstScanner;

import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class FunctionComplexityCheckTest {
	
	  @Test
	  public void test() {
		  FunctionComplexityCheck check = new FunctionComplexityCheck();
	    check.setMaximumFunctionComplexityThreshold(2);

	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/checks/functionComplexity.lua"), check);
	    CheckMessagesVerifier.verify(file.getCheckMessages())
	        .next().atLine(1).withMessage("Function has a complexitygreater than 1 authorized.").withCost(2.)
	       .noMore();

	  }
	
	 

	  
	
}
