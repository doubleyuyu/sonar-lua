
/*
 * SonarQube LuaPlugin
 * Copyright (C) 2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.sonar.lua.checks;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.lua.grammar.LuaGrammar;
import org.sonar.lua.checks.Tags;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "S108",
  name = "Nested blocks of code should not be left empty",
  priority = Priority.MAJOR,
  tags = Tags.SUSPICIOUS)
@ActivatedByDefault
@SqaleConstantRemediation("5min")
public class EmptyNestedBlockCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(LuaGrammar.BLOCK);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (!astNode.getFirstChild(LuaGrammar.CHUNK).hasChildren() && isNested(astNode) && !hasComment(astNode)) {
      getContext().createLineViolation(this, "Either remove or fill this block of code.", astNode);
    }
  }

  private static boolean isNested(AstNode blockNode) {
    return !blockNode.getParent().is(
      
      LuaGrammar.STATEMENT,
      LuaGrammar.LASTSTAT,
      LuaGrammar.FUNCTION);
    
  }
  private static boolean hasComment(AstNode blockNode) {
	    for (Token token : blockNode.getTokens()) {
	      for (Trivia trivia : token.getTrivia()) {
	        if (trivia.isComment()) {
	          return true;
	        }
	      }
	    }
	    return false;
	  }

 

}
