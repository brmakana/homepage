package io.makana.homepage.domain.chronicles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
public class FileBasedChronicleRepositoryTest {

    @Autowired
    private ChronicleRepository cut;

    private String expectedBody =
            "King Sigurdr divorced Alfhildr av Alfheim.\n" +
            "King Sigurdr of Sviþjod went to war against King Hjörvardr of Austergautland.\n" +
            "King Sigurdr of Sviþjod successfully led his armies, taking Austergautland Tribe from the enemy.\n" +
            "King Sigurdr of Sviþjod successfully led his armies, taking Nerike Tribe from the enemy.";
    private List<String> expectedNames = Arrays.asList(new String[] {"af Munso_877", "Test_877", "Isauros_984"});

    @Test
    public void getEntries() throws Exception {
        List<Entry> entries = cut.getEntries("Test_877");
        assertNotNull(entries);
        assertEquals(1, entries.size());
        Entry entry = entries.get(0);
        assertEquals("769", entry.getYear());
        assertEquals(4, entry.getLines().size());

        String[] expectedLines = expectedBody.split("\\r?\\n");
        assertEquals(entry.getLines().size(), expectedLines.length);
        for (int x = 0; x < expectedLines.length; x++) {
            assertEquals(expectedLines[x], entry.getLines().get(x));
        }
    }

    @Test
    public void getChronicleNames() throws Exception {
        List<String> chronicleNames = cut.getChronicleNames();
        Collections.sort(expectedNames);
        Collections.sort(chronicleNames);

        assertEquals(expectedNames, chronicleNames);
    }

    @Configuration
    public static class TestConfig {

        @Bean
        public ChronicleRepository chronicleRepository() {
            return new FileBasedChronicleRepository();
        }
    }
}