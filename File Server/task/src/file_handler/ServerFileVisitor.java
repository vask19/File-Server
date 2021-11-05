package file_handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ConcurrentHashMap;

public class ServerFileVisitor extends SimpleFileVisitor<Path> {
    private ConcurrentHashMap<String , File> map;
    Integer count = 1;

    public ServerFileVisitor(ConcurrentHashMap map) {
        this.map = map;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        map.put((++count).toString(),file.toFile());
        return FileVisitResult.CONTINUE;
    }
}
