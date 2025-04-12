/*
 * Copyright © 2025 CDAP. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import io.cdap.wrangler.api.parser.Token;
import io.cdap.wrangler.parser.DirectivesParser.ValueContext;

public class DirectiveParserVisitorImpl extends DirectivesBaseVisitor<Token> {

  @Override
  public Token visitValue(ValueContext ctx) {
    String text = ctx.getText();

    // Match BYTE_SIZE like "10KB", "1.5MB", etc.
    if (text.matches("(?i)^\\d+(\\.\\d+)?(B|KB|MB|GB|TB)$")) {
      return new ByteSize(text);
    }

    // Match TIME_DURATION like "10ms", "2s", etc.
    if (text.matches("(?i)^\\d+(\\.\\d+)?(ns|us|ms|s|m|h|d)$")) {
      return new TimeDuration(text);
    }

    // Fallback for other token types (e.g., string, int)
    return super.visitValue(ctx);
  }
}