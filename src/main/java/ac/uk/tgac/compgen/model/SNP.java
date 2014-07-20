package ac.uk.tgac.compgen.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

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
            snp =  new SNP(elements[0], elements[1], elements[2], sf) ;
       }catch (Exception e){
          e.printStackTrace();
       }
       return snp;
    }

}
