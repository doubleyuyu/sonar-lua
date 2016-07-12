

/*
 * SonarQube Lua Plugin
 * Copyright (C) 2016 SonarSource and MC
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

package org.sonar.lua.parser;
import com.sonar.sslr.impl.Parser;
import org.sonar.lua.LuaConfiguration;
import org.sonar.lua.grammar.LuaGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;


public final class LuaParser {

  private LuaParser() {
  }
  public static Parser<LexerlessGrammar> create(LuaConfiguration conf) {
	  return new ParserAdapter<LexerlessGrammar>(conf.getCharset(), LuaGrammar.Grammar());
	  }
  }

