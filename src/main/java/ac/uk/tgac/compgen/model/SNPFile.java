package ac.uk.tgac.compgen.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    @OneToMany( cascade={CascadeType.ALL})
    List<  SNP> snpList;
    @Column(length = 65535,columnDefinition="Text")
    private String polymarker_output;
    @Column(length = 65535,columnDefinition="Text")
    private String mask_fasta;

    public SNPFile(){
        snpList = new LinkedList<SNP>();
    }

    public static SNPFile parseStream(InputStream inputStream) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        SNPFile sf = new SNPFile();
        sf.setStatus(Status.NEW);

        while ((line = in.readLine()) != null) {
            //responseData.append(line);
            System.out.println(line);
            SNP.SNPFromLine(line, sf);
            sf.add(SNP.SNPFromLine(line, sf));
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

    public void add(SNP snp) {
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

    public enum Status {
       NEW, SUBMITTED, RUNNING, DONE, ERROR
    }

    @OneToMany(mappedBy = "snpFile")
    private Collection<SNP> snp;

    public Collection<SNP> getSnp() {
        return snp;
    }

    public void setSnp(Collection<SNP> snp) {
        this.snp = snp;
    }
}
