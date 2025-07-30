package org.sejda.core.pro.support.prefix.processor;

import org.sejda.core.support.prefix.model.NameGenerationRequest;

public class TextPrefixProcessor extends BaseTextPrefixProcessor {
   public TextPrefixProcessor() {
      super("TEXT");
   }

   protected String getReplacement(NameGenerationRequest request) {
      return request.getText();
   }
}
