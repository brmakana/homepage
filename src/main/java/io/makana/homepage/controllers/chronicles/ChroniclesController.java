package io.makana.homepage.controllers.chronicles;

import io.makana.homepage.domain.chronicles.ChronicleRepository;
import io.makana.homepage.domain.chronicles.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ChroniclesController {

    @Autowired
    private ChronicleRepository chronicleRepository;


    @RequestMapping(value="/chronicles", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getChronicleNames() throws Exception {
        return chronicleRepository.getChronicleNames();
    }

    @RequestMapping(value="/chronicles/{chronicleName}")
    public ModelAndView getChronicle(@PathVariable String chronicleName) throws Exception {
        List<Entry> entries = chronicleRepository.getEntries(chronicleName);
        ModelAndView mav = new ModelAndView("chronicle");
        mav.getModel().put("entries", entries);
        String chronicleParsedName = chronicleName.substring(0, chronicleName.indexOf("_"));
        mav.getModel().put("chronicleName", chronicleParsedName);
        String titleString = "This is the Chronicle of House %s, in which is contained the record of its yearly fortunes, glories and difficulties.";
        mav.getModel().put("chronicleTitle", String.format(titleString, chronicleParsedName));
        return mav;
    }
}
