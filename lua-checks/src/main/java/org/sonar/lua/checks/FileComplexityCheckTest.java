package org.sonar.lua.checks;

import static org.junit.Assert.*;




	import java.io.File;
	import org.junit.Test;
	import org.sonar.lua.LuaAstScanner;
	import org.sonar.squidbridge.api.SourceFile;
	import org.sonar.squidbridge.checks.CheckMessagesVerifier;

	public class FileComplexityCheckTest {

	  private FileComplexityCheck check = new FileComplexityCheck();

	  @Test
	  public void test() {
	    check.setMaximumFileComplexityThreshold(2);

	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/checks/fileComplexity.lua"), check);
	    CheckMessagesVerifier.verify(file.getCheckMessages())
	        .next().withMessage("File has a complexity of 4 which is greater than 2 authorized.")
	        .noMore();
	  }

	  @Test
	  public void defaults() {
	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/checks/fileComplexity.lua"), check);
	    CheckMessagesVerifier.verify(file.getCheckMessages())
	        .noMore();
	  }}
