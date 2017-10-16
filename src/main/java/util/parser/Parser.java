package util.parser;

@FunctionalInterface
public interface Parser {
    Object parse(String filename);
}
