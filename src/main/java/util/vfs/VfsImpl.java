package util.vfs;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class VfsImpl implements Vfs {
    private String directory;

    public VfsImpl(String directory) {
        this.directory = directory;
    }

    @Override
    public Iterator<String> getIterator() {
        return new FileIterator(directory);
    }

    private static final class FileIterator implements Iterator<String> {
        private Queue<File> files = new LinkedList<>();

        private FileIterator(String directory) {
            Collections.addAll(files, (new File(directory)).listFiles((dir, name) -> name.toLowerCase().endsWith(".properties")));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file = files.peek();

            if (file.isDirectory()) {
                Collections.addAll(files, file.listFiles());
            }

            return files.poll().getPath();
        }
    }
}
