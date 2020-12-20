package io.makana.homepage.domain.chronicles;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ChroniclesParserTest {

    String testBody = "- 769 -\n" +
            "This is the Chronicle of House Isauros, in which is contained the record of its yearly fortunes, glories and difficulties.\n" +
            "Basileus Konstantinos V of the Byzantine Empire went to war against High Chief Vojislav of Rashka.\n" +
            "the Byzantine Empire was attacked by the Greek realm of Armeniacon, ruled by Strategos Olvianos.\n" +
            "Basileus Konstantinos V of the Byzantine Empire tried but failed to imprison Strategos Olvianos of Armeniacon, causing him to come out in open rebellion.\n" +
            "the Byzantine Empire was attacked by the Greek realm of Dorylaionian Peasant Revolt, ruled by Porphyrios.\n";

    @Test
    public void withNullBody() throws ChroniclesException {
        List<Entry> entries = new ArrayList<>();
        ChroniclesParser cut = new ChroniclesParser();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cut.parse(null);
        });
    }

    @Test
    public void withEmptyBody() throws ChroniclesException {
        List<Entry> entries = new ArrayList<>();
        ChroniclesParser cut = new ChroniclesParser();
        entries = cut.parse("");
        assertTrue(entries.isEmpty());
    }

    @Test
    public void withProperBody() throws ChroniclesException {
        List<Entry> entries = new ArrayList<>();
        ChroniclesParser cut = new ChroniclesParser();
        entries = cut.parse(testBody);
        assertEquals(1, entries.size());
        Entry entry = entries.get(0);
        assertEquals("769", entry.getYear());
        assertEquals(4, entry.getLines().size());
    }
}