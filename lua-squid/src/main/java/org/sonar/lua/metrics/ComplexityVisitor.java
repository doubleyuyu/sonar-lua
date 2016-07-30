/*
 * SonarQube Lua Plugin
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

package org.sonar.lua.metrics;
import com.sonar.sslr.api.AstNode;
import org.sonar.lua.api.LuaMetric;
import org.sonar.lua.grammar.LuaGrammar;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.sslr.parser.LexerlessGrammar;

public class ComplexityVisitor extends SquidAstVisitor<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(
      // Entry points
      LuaGrammar.FUNCTION,
     // LuaGrammar.TABLECONSTRUCTOR,
      // Branching nodes

      LuaGrammar.WHILE_STATEMENT,
      LuaGrammar.FOR_STATEMENT,
      LuaGrammar.IF_STATEMENT,
      LuaGrammar.DO_STATEMENT,
      LuaGrammar.REPEAT_STATEMENT,
      LuaGrammar.LASTSTAT);
     
    
      
  }
  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(LuaGrammar.Keyword.RETURN) && isLastReturnStatement(astNode)) {
      return;
    }
    getContext().peekSourceCode().add(LuaMetric.COMPLEXITY, 1);
  }

  private boolean isLastReturnStatement(AstNode astNode) {
    AstNode parent = astNode.getParent().getParent();
    return parent.is(LuaGrammar.LASTSTAT);
  }

}



 

