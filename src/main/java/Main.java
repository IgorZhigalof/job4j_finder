import checkers.ConditionChecker;
import checkers.MaskCondition;
import checkers.NameCondition;
import checkers.RegexCondition;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        ArgsName argsName = ArgsName.of(args);
        validate(argsName);
        ConditionChecker checker = getChecker(argsName);
        List<String> result = new DirectoryScanner(checker, Path.of(argsName.get("d"))).getPathsAsList()
                .stream()
                .map(x -> x.toAbsolutePath().toString())
                .toList();
        BufferedWriter output = new BufferedWriter(new FileWriter("result\\" + argsName.get("o")));
        for (String file : result) {
            output.write(file + System.lineSeparator());
        }
        output.flush();
        output.close();
    }

    private static ConditionChecker getChecker(ArgsName argsName) {
        ConditionChecker checker = null;
        if ("name".equals(argsName.get("t"))) {
            checker = new NameCondition(argsName.get("n"));
        } else if ("mask".equals(argsName.get("t"))) {
            checker = new MaskCondition(argsName.get("n"));
        } else if ("regex".equals(argsName.get("t"))) {
            new RegexCondition(argsName.get("n"));
        }
        return checker;
    }

    private static void validate(ArgsName argsName) throws IllegalArgumentException {
        if (!Files.isDirectory(Path.of(argsName.get("d")))) {
            throw new IllegalArgumentException(argsName.get("d") + " is not a directory");
        }
        if (!(List.of("mask", "name", "regex").contains(argsName.get("t")))) {
            throw new IllegalArgumentException(String.format("There's no type: %s, choose one of the following: mask, name, regex", argsName.get("t")));
        }
        if ("".equals(argsName.get("n"))) {
            throw new IllegalArgumentException("Name param must not be empty");
        }
    }
}
