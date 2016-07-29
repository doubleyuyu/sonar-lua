package org.sonar.lua;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;

import org.junit.Ignore;
import org.junit.Test;
import org.sonar.flex.FlexAstScanner;
import org.sonar.flex.FlexConfiguration;
import org.sonar.flex.api.FlexMetric;
import org.sonar.lua.api.LuaMetric;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.api.SourceProject;
import org.sonar.squidbridge.indexer.QueryByType;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.io.File;
import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;
public class LuaAstScannerTest {
	 

	 @Test
	  public void files() {
	    AstScanner<LexerlessGrammar> scanner = LuaAstScanner.create(new LuaConfiguration(Charsets.UTF_8));
	    scanner.scanFiles(ImmutableList.of(new File("src/test/resources/metrics/comments.lua"),
	      new File("src/test/resources/metrics/lines.lua")));
	    SourceProject project = (SourceProject) scanner.getIndex().search(new QueryByType(SourceProject.class)).iterator().next();
	    assertThat(project.getInt(LuaMetric.FILES)).isEqualTo(2);
	  }
	 @Test
	  public void lines() {
	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/metrics/comments.lua"));
	    assertThat(file.getInt(LuaMetric.LINES)).isEqualTo(6);
	  }
	 @Test
	  public void lines_of_code() {
	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/metrics/line_of_code.lua"));
	    assertThat(file.getInt(LuaMetric.LINES_OF_CODE)).isEqualTo(2);
	  } 
	 @Test
	  public void functions() {
	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/metrics/functions.lua"));
	    assertThat(file.getInt(LuaMetric.FUNCTIONS)).isEqualTo(1);
	  }
	 @Test
	  public void block_statment() {
	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/metrics/blocks.lua"));
	    assertThat(file.getInt(LuaMetric.BLOCKS)).isEqualTo(1);
	  } 
	 @Test
	  public void statments() {
	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/metrics/statement.lua"));
	    assertThat(file.getInt(LuaMetric.STATEMENTS)).isEqualTo(2);
	  } 
	 @Test
	  public void complexity() {
	    SourceFile file = LuaAstScanner.scanSingleFile(new File("src/test/resources/metrics/complexity.lua"));
	    assertThat(file.getInt(FlexMetric.COMPLEXITY)).isEqualTo(13);
	  }
}