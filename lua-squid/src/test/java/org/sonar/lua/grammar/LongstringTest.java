/*
 * Sonar Lua
 * Copyright (C) 2013 SonarSource
 * dev@sonar.codehaus.org
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.lua.grammar;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class LongstringTest {

  private LexerlessGrammar g = LuaGrammar.createGrammar();

  @Test
  public void test() {
    assertThat(g.rule(LuaGrammar.LONGSTRING))
        .as("level 0")
        .matches("[[ foo \n bar ]]")
        .as("level 1")
        .matches("[=[ foo \n bar ]=]")
        .as("level 2")
        .matches("[==[ foo \n bar ]==]");
  }

}
