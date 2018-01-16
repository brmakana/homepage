package io.makana.homepage.domain.chronicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileBasedChronicleRepository implements ChronicleRepository {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<Entry> getEntries(String chronicleName) throws ChroniclesException {
        try {
            ChroniclesParser parser = new ChroniclesParser();
            String body = loadChronicle(chronicleName);
            return parser.parse(body);
        }
        catch (IOException ex) {
            throw new ChroniclesException(ex);
        }
    }

    @Override
    public List<String> getChronicleNames() throws ChroniclesException {
        try {
            return getChronicleFilenames();
        }
        catch (IOException ex) {
            throw new ChroniclesException(ex);
        }
    }

    private String loadChronicle(String chronicleName) throws IOException {
        StringBuilder body = new StringBuilder();
        String filename = "classpath:chronicles/" + chronicleName + ".txt";
        Resource resource = resourceLoader.getResource(filename);
        if (resource != null) {
            InputStream is = resource.getInputStream();
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line)
                            .append("\n");
                }
            }
            else {
                throw new FileNotFoundException(filename);
            }
        }
        else {
            throw new FileNotFoundException(chronicleName);
        }
        return body.toString();
    }

    private List<String> getChronicleFilenames() throws IOException {
        Resource[] resources = loadResources("classpath*:chronicles/*.txt");
        return Arrays.stream(resources)
                .map(r -> r.getFilename().substring(0,r.getFilename().indexOf(".txt")))
                .collect(Collectors.toList());
    }

    private Resource[] loadResources(String pattern) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
    }

}
