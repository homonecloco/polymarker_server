package ac.uk.tgac.compgen.model;



import javax.persistence.*;
import java.util.List;

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

    public static SNP SNPFromLine(String line, SNPFile sf){
       String[] elements = line.split(",");
       SNP snp = null;
       try {
           System.out.println("number of elements: " + elements.length);
           if(elements.length == 3){
            System.out.println(elements[0]);
            //   System.out.println(elements[1]);
            //   System.out.println(elements[2]);
            String sequence = elements[2];
            snp =  new SNP(elements[0], elements[1], sequence, sf) ;
           }
       }catch (Exception e){
          e.printStackTrace();
       }
       return snp;
    }

    public List<SNPWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<SNPWarning> warnings) {
        this.warnings = warnings;
    }
}
