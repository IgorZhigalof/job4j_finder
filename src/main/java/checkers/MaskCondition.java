package checkers;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class MaskCondition implements ConditionChecker {
    private final Predicate<String> matcher;

    public MaskCondition(String mask) {
        mask = mask.replaceAll("\\.", "\\.");
        mask = mask.replaceAll("\\*", ".*");
        matcher = Pattern.compile(mask).asMatchPredicate();
    }

    @Override
    public boolean checkCondition(String fileName) {
        return matcher.test(fileName);
    }
}
