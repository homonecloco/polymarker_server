package ac.uk.tgac.compgen.model;



import javax.persistence.*;
import java.util.List;
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

    @OneToMany( cascade={CascadeType.ALL})
    List<SNPWarning> warnings;


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

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Long getSnpId() { return snpId; }

    public void setSnpId(Long id) {
        this.snpId = id;
    }
    public static final String valid_bases = "ACGTURYSWKMBDHVN";
    public static final String valid_in_snp = "ACTG";
    public static final String snp_regex = "^[" + valid_bases + "]+\\[[" + valid_in_snp + "]/["+ valid_in_snp + "]\\][" + valid_bases +"]+$\"";
    public static Pattern iuapc_valid_bases = Pattern.compile(snp_regex);
    public static SNP SNPFromLine(String line, SNPFile sf){
       String[] elements = line.split(",");
       SNP snp = null;
       try {
           System.out.println("number of elements: " + elements.length);
           System.out.println("Pattern for regex: " + iuapc_valid_bases.pattern());
           if(elements.length == 3){
            System.out.println(elements[0]);
            //   System.out.println(elements[1]);
            //   System.out.println(elements[2]);
            String sequence = elements[2];
            String error_message = "";
            if(!iuapc_valid_bases.matcher(sequence).matches()){
                error_message = "Sequence is not a valid SNP";
                System.out.println("Doesnt match!");
            }

            snp =  new SNP(elements[0], elements[1], sequence, sf) ;
            snp.addWarning(error_message);
           }
       }catch (Exception e){
          e.printStackTrace();
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
}
