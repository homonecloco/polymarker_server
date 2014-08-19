package ac.uk.tgac.compgen.model;


import ac.uk.tgac.compgen.PolymarkerException;
import com.google.common.base.Splitter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ramirezr on 05/03/2014.
 */
@Entity
@Table(name="snp")
public class SNP {

    @Id
    @Column
    @GeneratedValue
    Long snpId;
    @Column
    String name;
    @Column
    String chromosome;
    @Column(length=1000)
    String sequence;

    @ManyToOne
    @JoinColumn( nullable = false)
    SNPFile snpFile;

    @OneToMany( cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    List<SNPWarning> warnings = new LinkedList<SNPWarning>();

    @Column
    private
    boolean process;


    public SNPFile getSnpFile() {
        return snpFile;
    }

    public void setSnpFile(SNPFile snpFile) {
        this.snpFile = snpFile;
    }

    public SNP()  {

    }

    public SNP(String name, String chromosome, String sequence, SNPFile sf) {
        this.name = name;
        this.chromosome = chromosome;
        this.snpFile = sf;
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getSequence() {
        return sequence;
    }

    public Iterator<String> getSequenceSplitted(){
        Iterable<String> it = Splitter.fixedLength(80).split(sequence);
        //List<String> list = new LinkedList<String>();

        return  it.iterator();
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Long getSnpId() { return snpId; }

    public void setSnpId(Long id) {
        this.snpId = id;
    }
    public static final String valid_bases = "ACGTURYSWKMBDHVN";
    public static final String valid_in_snp = "ACTG";
    public static final String snp_regex = "^([" + valid_bases + "]+)\\[([" + valid_in_snp + "])/(["+ valid_in_snp + "])\\]([" + valid_bases +"]+)$";
    public static final String snp_parse_regex = "^(\\w+)\\[(\\w)/(\\w)\\](\\w+)$";
    public static Pattern snp_valid_string = Pattern.compile(snp_regex);
    public static Pattern snp_parse_string = Pattern.compile(snp_parse_regex);

    public static final String invalid_bases = "[^ACGTRYSWKMBDHVN]";
    public static final String invalid_in_snp = "[^ACTG]";

    public void sanitize_snp() throws PolymarkerException{
        String seq = this.sequence.toUpperCase();
        seq = seq.replace('U', 'T');

        if(!snp_valid_string.matcher(seq).matches())   {

            Matcher m = snp_parse_string.matcher(seq);
            String tokens[] = new String[4];
            if(!m.matches() || m.groupCount() != 4){
                throw  new PolymarkerException("The SNP is not in the expected format (AAAAAAAA[T/G]CCCCC)" + snp_parse_regex);
            }

            tokens[0] = m.group(1).replaceAll(invalid_bases, "N");
            tokens[3] = m.group(4).replaceAll(invalid_bases, "N");
            tokens[1] = m.group(2);
            tokens[2] = m.group(3);

            if(tokens[1].matches(invalid_in_snp) || tokens[2].matches(invalid_in_snp) ){
                throw  new PolymarkerException("The SNP must be A,C,T, or G");
            }

            if(tokens[2].equals(tokens[1])){
                throw  new PolymarkerException("The bases in the SNP are the same");
            }

            String new_seq = tokens[0] + "[" + tokens[1] + "/" + tokens[2] + "]" + tokens[3];
            if(!snp_valid_string.matcher(new_seq).matches())   {
               throw new PolymarkerException(new_seq + " is invalid");
            }
            seq = new_seq;
            this.addWarning("SNP transformed");
        }

        this.setSequence(seq);

    }

    public void sanitize_chr_arm(){
        this.chromosome = chromosome.substring(0,2);



    }

    public static SNP SNPFromLine(String line, SNPFile sf){
       String[] elements = line.split(",");
       SNP snp = null;
       try {
           if(elements.length == 3){
            String sequence = elements[2];
            String error_message = "";
            snp =  new SNP(elements[0], elements[1], sequence, sf) ;
            snp.setProcess(true);
            try {
                snp.sanitize_snp();
                snp.sanitize_chr_arm();
            }catch (PolymarkerException pe){
                snp.addWarning(pe.getMessage());
                snp.setProcess(false);
                System.out.println("[PolymarkerException] " + pe.getMessage());
            }
           }
       }catch (Exception e){
            e.printStackTrace();
        if(snp != null){
            snp.addWarning(e.getMessage());
        }
       }

       return snp;
    }

    private void addWarning(String error_message) {
        SNPWarning snpw = new SNPWarning() ;
        snpw.setMessage(error_message);
        snpw.setSnp(this);
        this.warnings.add(snpw);
    }

    public List<SNPWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<SNPWarning> warnings) {
        this.warnings = warnings;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }
}
