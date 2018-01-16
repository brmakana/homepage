package io.makana.homepage.domain.chronicles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ChroniclesParser {

    private static final Logger logger = LoggerFactory.getLogger(ChroniclesParser.class);

    public List<Entry> parse(String body) throws ChroniclesException {
        if (body == null) {
            throw new IllegalArgumentException("Null body not allowed");
        }
        List<Entry> entries = new ArrayList<>();
        if (body.isEmpty()) {
            logger.debug("Body was empty, returning empty list");
            return entries;
        }

        String[] lines = body.split("[\\r\\n]+");
        Stack<Entry> stack = new Stack<>();
        logger.debug("Body is " + lines.length + " lines long");
        for (int x = 0; x < lines.length; x++) {
            String line = lines[x];
            if (line.startsWith("-")) {
                logger.debug("Found line for new year: " + line);
                Entry entry = new Entry();
                entry.setYear(line.replaceAll("\\D+",""));
                logger.debug("Created new entry, set year to " + entry.getYear());
                stack.push(entry);
            }
            else {
                logger.debug("Found content line");
                if (stack.isEmpty()) {
                    throw new ChroniclesException("Body content improperly formatted");
                }
                /** The first line for the first entry is a repeat of the title string, so remove it **/
                if (!stack.isEmpty() && x != 1) {
                    Entry entry = stack.pop();
                    entry.getLines().add(line);
                    stack.push(entry);
                }
                else {
                    logger.debug("First line for first entry, skipping");
                }
            }
        }
        while (!stack.isEmpty()) {
            entries.add(stack.pop());
        }
        Collections.reverse(entries);
        return entries;
    }
}
