package ac.uk.tgac.compgen.controller;

/**
 * Created by ramirezr on 04/03/2014.
 */

import ac.uk.tgac.compgen.model.SNP;
import ac.uk.tgac.compgen.model.SNPFile;
import ac.uk.tgac.compgen.model.UploadedFile;
import ac.uk.tgac.compgen.validator.FileValidator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController {

    @Autowired
    FileValidator fileValidator;
    @Autowired
    ServletContext context;

    @RequestMapping("/")
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

        if(! session.getTransaction().isActive()  ){
         session.beginTransaction();
        }
        String[] ids = parameters.get("id");
        Long id =  Long.parseLong(ids[0]);


        SNPFile sf  =(SNPFile) session.get(SNPFile.class,id);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("primer_status");
        mv.addObject("sf",sf);
        session.getTransaction().commit();

        return mv;
    }

    @RequestMapping("/fileUpload")
    public ModelAndView fileUploaded(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {
        InputStream inputStream;


        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        String fileName = file.getOriginalFilename().replaceAll(" ", "_");

        if (result.hasErrors()) {
            return new ModelAndView("uploadForm");
        }
        SNPFile sf = null;
        Long id_saved = 0L;
        try {
            inputStream = file.getInputStream();
            sf = SNPFile.parseStream(inputStream);
            sf.setFilename(fileName);
            sf.setEmail(uploadedFile.getEmail());
            List<SNP> snps = sf.getSnpList();
            if(snps.size() == 0){
                result.rejectValue("file", "uploadForm.salectFile", "Unable to parse any SNP in the file");
            }
            if(snps.size() > 200){
                result.rejectValue("file", "uploadForm.salectFile", "The maximum number of SNPs to analyze is 200");
            }
            if(!result.hasErrors()){
                org.hibernate.internal.SessionFactoryImpl sessionFactory = (org.hibernate.internal.SessionFactoryImpl) context.getAttribute("sessionFactory");
                Session session = sessionFactory.getCurrentSession();
                Transaction transaction = session.getTransaction();
                transaction.begin();
                id_saved = (Long) session.save(sf);
                transaction.commit();
                ModelAndView mv = new ModelAndView("showFile", "message", fileName);
                mv.addObject("id", id_saved);
                return mv;
            }
        } catch (Exception e) {
            result.rejectValue("file", "uploadForm.salectFile", e.getMessage());
            e.printStackTrace();
        }

        return new ModelAndView("uploadForm");

    }

    @RequestMapping (value = "/get_file_old", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView renderPromotePage_old (HttpServletRequest request) {
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

    public final static String TEXT_CSV = "text/csv";
    public final static MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");
    public final static MediaType TEXT_FASTA_NA_TYPE = new MediaType("chemical", "seq-na-fasta");

    @RequestMapping(method=RequestMethod.GET, value="/get_file")
    public ResponseEntity<String> renderPromotePage (HttpServletRequest request)  {

        HttpHeaders responseHeaders = new HttpHeaders();
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
            responseHeaders.setContentType(TEXT_FASTA_NA_TYPE);
        }else if(format.equals("primers")){
            responseHeaders.setContentType(TEXT_CSV_TYPE);
            text = sf.getPolymarker_output();

        }else{
            text = "Invalid file";
        }
        transaction.commit();

        return new ResponseEntity<String>(text, responseHeaders, HttpStatus.CREATED);


    }

}