package checkers;

import java.util.regex.Pattern;

public class RegexCondition implements ConditionChecker {
    Pattern pattern;

    public RegexCondition(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean checkCondition(String fileName) {
        return pattern.matcher(fileName).find();

    }
}
