package org.sonar.lua.api;

/*
 * SonarQube CLua Plugin
 * Copyright (C) 2016 SonarSource and MC
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */


import org.sonar.squidbridge.measures.CalculatedMetricFormula;
import org.sonar.squidbridge.measures.MetricDef;

public enum LuaMetric implements MetricDef {

  LINES_OF_CODE,
  LINES,
  FILES,
  COMMENT_LINES,
  BLOCKS,
  FUNCTIONS,
  STATEMENTS,
  COMPLEXITY;


  @Override
  public String getName() {
    return name();
  }

  @Override
  public boolean isCalculatedMetric() {
    return false;
  }

  @Override
  public boolean aggregateIfThereIsAlreadyAValue() {
    return true;
  }

  @Override
  public boolean isThereAggregationFormula() {
    return true;
  }

  @Override
  public CalculatedMetricFormula getCalculatedMetricFormula() {
    return null;
  }
}
