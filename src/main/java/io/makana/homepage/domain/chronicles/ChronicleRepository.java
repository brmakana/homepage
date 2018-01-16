package io.makana.homepage.domain.chronicles;

import java.util.List;

public interface ChronicleRepository {

    /**
     * Get all entries for a given chronicle
     * @param chronicleName
     * @return
     */
    List<Entry> getEntries(String chronicleName) throws ChroniclesException;

    /**
     * Get all chronicle names
     * @return
     */
    List<String> getChronicleNames() throws ChroniclesException;
}
