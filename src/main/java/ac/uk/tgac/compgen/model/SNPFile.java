package ac.uk.tgac.compgen.model;

import ac.uk.tgac.compgen.PolymarkerException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by ramirezr on 05/03/2014.
 */
@Entity
@Table(name="snp_file")
public class SNPFile {

    @Id
    @Column(name = "snp_file_id")
    @GeneratedValue
    Long id;

    @Column
    @Enumerated(EnumType.STRING)
    Status status;
    @Column
    String filename;
    @Column
    String email;

    @OneToMany( cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    List<  SNP> snpList;

    @Column(length = 65535,columnDefinition="Text")
    private String polymarker_output;
    @Column(columnDefinition="MEDIUMTEXT")
    private String mask_fasta;


    @Column(columnDefinition = "TIMESTAMP")
    private Date submitted;
    @Column(columnDefinition = "TIMESTAMP on update CURRENT_TIMESTAMP")
    private Date lastChange;

    @Column
    private String error;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Column(nullable = true)
    private String hash;

    public SNPFile(){
        snpList = new LinkedList<SNP>();
    }

    public static SNPFile parseStream(InputStream inputStream) throws IOException, PolymarkerException {
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        SNPFile sf = new SNPFile();
        sf.setStatus(Status.NEW);
        sf.setSubmitted(new Date());

        while ((line = in.readLine()) != null) {
            //responseData.append(line);
            System.out.println(line);
            SNP snp = SNP.SNPFromLine(line, sf);
            if(snp != null){
                sf.add(snp);

            }

        }
        return sf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SNP> getSnpList() {
        return snpList;
    }

    public void setSnpList(List<SNP> snpList) {
        this.snpList = snpList;
    }

    public void add(SNP snp) throws PolymarkerException {
        for (SNP s : snpList){
            if(s.getName().equals(snp.getName())){
                throw new PolymarkerException(snp.getName() + " appears more than once as an SNP id");
            }
        }
        snpList.add(snp);
    }

    public String getMask_fasta() {
        return mask_fasta;
    }

    public void setMask_fasta(String mask_fasta) {
        this.mask_fasta = mask_fasta;
    }

    public String getPolymarker_output() {
        return polymarker_output;
    }

    public void setPolymarker_output(String polymarker_output) {
        this.polymarker_output = polymarker_output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public enum Status {
       NEW, SUBMITTED, RUNNING, DONE, ERROR, NOTIFIED
    }

    public Date getSubmitted() {
           return submitted;
       }

    public void setSubmitted(Date submitted) {
           this.submitted = submitted;
       }

    public Date getLastChange() {
           return lastChange;
       }

    public void setLastChange(Date lastChange) {
           this.lastChange = lastChange;
       }
}
