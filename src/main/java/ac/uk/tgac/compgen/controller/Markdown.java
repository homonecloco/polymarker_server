package ac.uk.tgac.compgen.controller;

import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;

/**
 * Created by ramirezr on 09/12/14.
 */
@Controller
public class Markdown {


    @Autowired
    ServletContext context;

    @RequestMapping (value = "/Markdown", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getStatus (HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();


        String rendered_md = null;
        try {

            String filename = "/WEB-INF/markdown/" + parameters.get("md")[0] + ".md";

            InputStream is;
            is = context.getResourceAsStream(filename);
            StringBuilder fileContent = new StringBuilder();
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);

                String text;
                while ((text = reader.readLine()) != null) {
                    fileContent.append(text);
                    fileContent.append("\n");

                }
            }

            rendered_md = new Markdown4jProcessor().addHtmlAttribute("style", "width:960px", "img").process(fileContent.toString());
        } catch (IOException e) {
            rendered_md = "Unable to render markdown: " + e.getMessage();
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("markdown");
        mv.addObject("rendered_md", rendered_md);

        return mv;
    }

}
