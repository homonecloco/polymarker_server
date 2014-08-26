package ac.uk.tgac.compgen.model;

/**
 * Created by ramirezr on 20/08/2014.
 */
public class FastaEntry {
    private String id;
    private String full_header;
    private String sequence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_header() {
        return full_header;
    }

    public void setFull_header(String full_header) {
        this.full_header = full_header;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
