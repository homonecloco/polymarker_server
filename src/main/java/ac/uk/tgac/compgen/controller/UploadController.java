package ac.uk.tgac.compgen.controller;

/**
 * Created by ramirezr on 04/03/2014.
 */

import java.io.*;
import java.util.Map;

import ac.uk.tgac.compgen.model.SNP;
import ac.uk.tgac.compgen.model.SNPFile;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import ac.uk.tgac.compgen.model.UploadedFile;
import ac.uk.tgac.compgen.validator.FileValidator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping (value = "/status", method = {RequestMethod.POST, RequestMethod.GET})
      public ModelAndView getStatus (HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        org.hibernate.internal.SessionFactoryImpl sessionFactory = (org.hibernate.internal.SessionFactoryImpl) context.getAttribute("sessionFactory");
        Session session = sessionFactory.getCurrentSession();
        String[] ids = parameters.get("id");
        Long id =  Long.parseLong(ids[0]);
        Transaction transaction = session.getTransaction();
        transaction.begin();
        SNPFile sf  =(SNPFile) session.get(SNPFile.class,id);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("primer_status");
        mv.addObject("sf",sf);
        transaction.commit();
        return mv;
    }

    @RequestMapping("/fileUpload")
    public ModelAndView fileUploaded(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {
        InputStream inputStream;


        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        String fileName = file.getOriginalFilename();

        if (result.hasErrors()) {
            return new ModelAndView("uploadForm");
        }

        try {
            inputStream = file.getInputStream();
            org.hibernate.internal.SessionFactoryImpl sessionFactory = (org.hibernate.internal.SessionFactoryImpl) context.getAttribute("sessionFactory");
            Session session = sessionFactory.getCurrentSession();

            Transaction transaction = session.getTransaction();
            transaction.begin();
            SNPFile sf = SNPFile.parseStream(inputStream);
            sf.setFilename(fileName);
            sf.setEmail(uploadedFile.getEmail());
            Long id_saved = (Long) session.save(sf);
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

    @RequestMapping (value = "/get_file", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView renderPromotePage (HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();


        org.hibernate.internal.SessionFactoryImpl sessionFactory = (org.hibernate.internal.SessionFactoryImpl) context.getAttribute("sessionFactory");
        Session session = sessionFactory.getCurrentSession();
        String[] ids = parameters.get("id");
        String[] outs = parameters.get("output");


        Long id =  Long.parseLong(ids[0]);
        String format = outs[0];


        Transaction transaction = session.getTransaction();
                   transaction.begin();
        SNPFile sf  =(SNPFile) session.get(SNPFile.class,id);

        String text;

        if (format.equals("mask") ){
                text = sf.getMask_fasta();
        }else if(format.equals("primers")){
            text = sf.getPolymarker_output();
        }else{
            text = "Invalid file";
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("show_file");
        mv.addObject("text",text);
        transaction.commit();
        return mv;
    }


}