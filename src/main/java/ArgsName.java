import java.util.HashMap;
import java.util.Map;

public class ArgsName {
    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Key '%s' is missing", key));
        }
        return values.get(key);
    }

    private void validate(String arg) {
        if (!arg.contains("=")) {
            throw new IllegalArgumentException(String.format("Error: Argument '%s' does not contain an equal", arg));
        }
        if (!arg.startsWith("-")) {
            throw new IllegalArgumentException(String.format("Error: Argument '%s' must start with a '-' character", arg));
        }
        if (arg.substring(0, arg.indexOf("=")).length() < 2) {
            throw new IllegalArgumentException(String.format("Error: Argument '%s' does not contain a key", arg));
        }
        if (arg.substring(arg.indexOf("=") + 1).isBlank()) {
            throw new IllegalArgumentException(String.format("Error: Argument '%s' does not contain a value", arg));
        }
    }

    private void parse(String[] args) {
        for (String arg : args) {
            validate(arg);
            values.put(
                    arg.substring(1, arg.indexOf("=")),
                    arg.substring(arg.indexOf("=") + 1)
            );
        }
    }


    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to the program");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}