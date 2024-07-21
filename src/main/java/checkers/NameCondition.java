package checkers;

public class NameCondition implements ConditionChecker {
    String fileName;

    public NameCondition(String fileName) throws IllegalArgumentException {
        this.fileName = fileName;

    }

    @Override
    public boolean checkCondition(String fileName) {
        return this.fileName.equals(fileName);
    }
}
