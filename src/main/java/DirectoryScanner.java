import checkers.ConditionChecker;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DirectoryScanner {
    private List<Path> result = new ArrayList<>();
    private final ConditionChecker checker;
    private final Path directory;

    public DirectoryScanner(ConditionChecker checker, Path directory) {
        this.checker = checker;
        this.directory = directory;
    }

    private void scan() throws IOException {
        Queue<Path> directories = new LinkedList<>();
        directories.add(directory);
        while (!directories.isEmpty()) {
            DirectoryStream<Path> currentDirectories;
            try {
                currentDirectories = Files.newDirectoryStream(directories.poll());
            } catch (AccessDeniedException e) {
                e.printStackTrace();
                continue;
            }
            for (Path file : currentDirectories) {
                if (Files.isDirectory(file)) {
                    directories.add(file);
                } else if (checker.checkCondition(file.getFileName().toString())) {
                    result.add(file);
                }
            }
            currentDirectories.close();
        }
    }

    public List<Path> getPathsAsList() throws IOException{
        if (result.isEmpty()) {
            scan();
        }
        return result;
    }
}
