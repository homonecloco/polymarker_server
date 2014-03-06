package ac.uk.tgac.compgen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @OneToMany( cascade={CascadeType.ALL})
    List<SNP> snpList;

    public SNPFile(){
        snpList = new LinkedList<SNP>();
    }
    public static SNPFile parseStream(InputStream inputStream) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        SNPFile sf = new SNPFile();

        while ((line = in.readLine()) != null) {
            //responseData.append(line);
            System.out.println(line);
            SNP.SNPFromLine(line, sf);
            sf.add(SNP.SNPFromLine(line, sf));
        }
        return sf;
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

}
