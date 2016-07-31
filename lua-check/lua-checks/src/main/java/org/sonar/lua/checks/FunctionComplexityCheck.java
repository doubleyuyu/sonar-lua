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
package org.sonar.lua.checks;

import com.sonar.sslr.api.AstNode;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.lua.api.LuaMetric;
import org.sonar.lua.grammar.LuaGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearWithOffsetRemediation;
import org.sonar.squidbridge.api.SourceFunction;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
    key = FunctionComplexityCheck.CHECK_KEY,
    priority = Priority.MAJOR,
    name = "Functions should not be too complex",
    tags = Tags.BRAIN_OVERLOAD
)
@SqaleLinearWithOffsetRemediation(
    coeff = "1min",
    offset = "10min",
    effortToFixDescription = "per complexity point above the threshold")
@ActivatedByDefault
public class FunctionComplexityCheck extends SquidCheck<LexerlessGrammar>{
  public static final String CHECK_KEY = "FunctionComplexity";
  private static final int DEFAULT_MAXIMUM_FUNCTION_COMPLEXITY_THRESHOLD = 10;

  @RuleProperty(
    key = "maximumFunctionComplexityThreshold",
    defaultValue = "" + DEFAULT_MAXIMUM_FUNCTION_COMPLEXITY_THRESHOLD)
  private int maximumFunctionComplexityThreshold = DEFAULT_MAXIMUM_FUNCTION_COMPLEXITY_THRESHOLD;

  @Override
  public void init() {
    subscribeTo(LuaGrammar.FUNCTION);
  }

  @Override
  public void leaveNode(AstNode node) {
    SourceFunction function = (SourceFunction) getContext().peekSourceCode();
    if (function.getInt(LuaMetric.COMPLEXITY) > maximumFunctionComplexityThreshold) {
      getContext().createLineViolation(this,
          "Function has a complexity of {0,number} which is greater than {1,number} authorized.", node,
          function.getInt(LuaMetric.COMPLEXITY), maximumFunctionComplexityThreshold);
    }
  }

  public void setMaximumFunctionComplexityThreshold(int threshold) {
    this.maximumFunctionComplexityThreshold = threshold;
  }

}
