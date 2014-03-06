package ac.uk.tgac.compgen.controller;

/**
 * Created by ramirezr on 04/03/2014.
 */
import java.io.*;

import ac.uk.tgac.compgen.model.SNP;
import ac.uk.tgac.compgen.model.SNPFile;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ac.uk.tgac.compgen.model.UploadedFile;
import ac.uk.tgac.compgen.validator.FileValidator;

import javax.servlet.ServletContext;

@Controller
public class UploadController {

    @Autowired
    FileValidator fileValidator;
    @Autowired
    ServletContext context;

    @RequestMapping("/fileUploadForm")
    public ModelAndView getUploadForm(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {
        return new ModelAndView("uploadForm");
    }

    @RequestMapping("/fileUpload")
    public ModelAndView fileUploaded(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        String fileName = file.getOriginalFilename();

        if (result.hasErrors()) {
            return new ModelAndView("uploadForm");
        }

        try {
            inputStream = file.getInputStream();
            org.hibernate.internal.SessionFactoryImpl sessionFactory =  (org.hibernate.internal.SessionFactoryImpl)    context.getAttribute("sessionFactory");
            Session session = sessionFactory.getCurrentSession() ;

            Transaction transaction = session.getTransaction();
            transaction.begin();
            SNPFile sf = SNPFile.parseStream(inputStream);
            Long id_saved = (Long)session.save(sf);
            //sf.setId(id_saved);

 //           for (SNP s: sf.getSnpList()){
//                session.save(s);
  //          }
            transaction.commit();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new ModelAndView("showFile", "message", fileName);
    }

}