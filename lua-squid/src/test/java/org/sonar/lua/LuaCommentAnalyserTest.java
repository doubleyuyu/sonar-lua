package org.sonar.lua;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class LuaCommentAnalyserTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private LuaCommentAnalyser analyser = new LuaCommentAnalyser();

  @Test
  public void content() {
  
  assertThat(analyser.getContents("--[[comment1 \n comment2--]]")).isEqualTo("comment1 \n comment2");
  assertThat(analyser.getContents("--comment")).isEqualTo("comment");
  }

  @Test
  public void blank() {
    assertThat(analyser.isBlank(" ")).isTrue();
    assertThat(analyser.isBlank("comment")).isFalse();
  }

  @Test
  public void unknown_type_of_comment() {
    thrown.expect(IllegalArgumentException.class);
    analyser.getContents("");
  }

}
