package org.sonar.lua.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.squidbridge.annotations.NoSqale;
import org.sonar.squidbridge.annotations.RuleTemplate;
import org.sonar.squidbridge.checks.AbstractCommentRegularExpressionCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "CommentRegularExpression",
  name = "Comments matching a regular expression should be handled",
  priority = Priority.MAJOR)
@RuleTemplate
@NoSqale
public class CommentRegularExpressionCheck extends AbstractCommentRegularExpressionCheck<LexerlessGrammar> {

  private static final String DEFAULT_REGULAR_EXPRESSION = "";
  private static final String DEFAULT_MESSAGE = "The regular expression matches this comment.";

  @RuleProperty(
    key = "regularExpression",
    description = "The regular expression",
    defaultValue = "" + DEFAULT_REGULAR_EXPRESSION)
  public String regularExpression = DEFAULT_REGULAR_EXPRESSION;

  @RuleProperty(
    key = "message",
    description = "The issue message",
    defaultValue = "" + DEFAULT_MESSAGE)
  public String message = DEFAULT_MESSAGE;

  @Override
  public String getRegularExpression() {
    return regularExpression;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
