package ac.uk.tgac.compgen.model;

import javax.persistence.*;

/**
 * Created by ramirezr on 14/08/2014.
 */
@Entity
@Table(name="snp_warning")
public class SNPWarning {
    @GeneratedValue
    @Id
    Long snpWarnId;

    @ManyToOne
    @JoinColumn( nullable = false)
    private SNP snp;

    private String message;

    public SNP getSnp() {
        return snp;
    }

    public void setSnp(SNP snp) {
        this.snp = snp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
