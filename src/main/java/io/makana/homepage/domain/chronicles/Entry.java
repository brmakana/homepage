package io.makana.homepage.domain.chronicles;

import java.util.ArrayList;
import java.util.List;

public class Entry {
    private String year;
    private List<String> lines = new ArrayList<>();

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
