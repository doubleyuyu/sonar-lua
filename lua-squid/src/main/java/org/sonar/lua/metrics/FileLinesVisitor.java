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

import com.google.common.collect.Sets;
import com.sonar.sslr.api.AstAndTokenVisitor;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;

import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.lua.api.LuaMetric;
//import org.sonar.python.api.PythonTokenType;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * Visitor that computes {@link CoreMetrics#NCLOC_DATA_KEY} and {@link CoreMetrics#COMMENT_LINES_DATA_KEY} metrics used by the DevCockpit.
 */
public class FileLinesVisitor extends SquidAstVisitor<LexerlessGrammar> implements AstAndTokenVisitor {

  private final FileLinesContextFactory fileLinesContextFactory;

  private Set<Integer> linesOfCode = Sets.newHashSet();
  private Set<Integer> linesOfComments = Sets.newHashSet();
  private final FileSystem fileSystem;
  private final Map<InputFile, Set<Integer>> allLinesOfCode;

  public FileLinesVisitor(FileLinesContextFactory fileLinesContextFactory, FileSystem fileSystem, Map<InputFile, Set<Integer>> linesOfCode) {
    this.fileLinesContextFactory = fileLinesContextFactory;
    this.fileSystem = fileSystem;
    this.allLinesOfCode = linesOfCode;
  }

  @Override
  public void visitToken(Token token) {
    if (token.getType().equals(GenericTokenType.EOF)) {
      return;
    }


    List<Trivia> trivias = token.getTrivia();
    for (Trivia trivia : trivias) {
      if (trivia.isComment()) {
        linesOfComments.add(trivia.getToken().getLine());
      }
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(getContext().getFile()));
    if (inputFile == null){
      throw new IllegalStateException("InputFile is null, but it should not be.");
    }
    FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(inputFile);

    int fileLength = getContext().peekSourceCode().getInt(LuaMetric.LINES);
    for (int line = 1; line <= fileLength; line++) {
      fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, linesOfCode.contains(line) ? 1 : 0);
      fileLinesContext.setIntValue(CoreMetrics.COMMENT_LINES_DATA_KEY, line, linesOfComments.contains(line) ? 1 : 0);
    }
    fileLinesContext.save();

    allLinesOfCode.put(inputFile, linesOfCode);

    linesOfCode = Sets.newHashSet();
    linesOfComments = Sets.newHashSet();
  }

}
