package org.sejda.core.pro.support.prefix.processor;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.core.support.prefix.model.PrefixTransformationContext;
import org.sejda.core.support.prefix.processor.PrefixProcessor;
import org.sejda.model.util.IOUtils;

public abstract class BaseTextPrefixProcessor implements PrefixProcessor {
   private final Pattern pattern;

   public BaseTextPrefixProcessor(String placeholder) {
      RequireUtils.requireNotBlank(placeholder, "Placeholder cannot be blank");
      this.pattern = Pattern.compile("\\[" + placeholder + "((\\s?\\|\\s?(nospaces))?(\\s?\\|\\s?(upper|lower|capitalize))?(\\s?\\|\\s?replace\\('([^']+)'\\s?,\\s?'([^']+)'\\))?)?]");
   }

   public void accept(PrefixTransformationContext context) {
      Matcher matcher = this.pattern.matcher(context.currentPrefix());
      if (matcher.find()) {
         Optional var10000 = Optional.ofNullable(context.request()).map(this::getReplacement).filter(StringUtils::isNotBlank).map((o) -> {
            return (String)Optional.ofNullable(matcher.group(2)).map(String::toLowerCase).map((c) -> {
               return c.contains("nospaces") ? StringUtils.deleteWhitespace(o) : o;
            }).orElse(o);
         }).map((o) -> {
            return (String)Optional.ofNullable(matcher.group(4)).map(String::toLowerCase).map((c) -> {
               if (c.contains("upper")) {
                  return o.toUpperCase();
               } else if (c.contains("lower")) {
                  return o.toLowerCase();
               } else {
                  return c.contains("capitalize") ? StringUtils.capitalize(o) : o;
               }
            }).orElse(o);
         }).map((text) -> {
            return (String)Optional.ofNullable(matcher.group(7)).map((t) -> {
               return text.replace(t, StringUtils.defaultString(matcher.group(8)));
            }).orElse(text);
         }).map(IOUtils::toSafeFilename).filter(StringUtils::isNotBlank);
         Objects.requireNonNull(matcher);
         var10000.map(matcher::replaceFirst).ifPresent((t) -> {
            context.currentPrefix(t);
            context.uniqueNames(true);
         });
      }

   }

   protected abstract String getReplacement(NameGenerationRequest var1);
}
