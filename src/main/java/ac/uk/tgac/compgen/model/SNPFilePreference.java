package ac.uk.tgac.compgen.model;

import javax.persistence.*;

/**
 * Created by ramirezr on 22/12/14.
 */
@Entity
@Table(name="preferences")
public class SNPFilePreference {
    @Id
    @Column
    @GeneratedValue
    private
    Long preferenceID;
    @Column
    private
    String name;
    @Column
    private
    String value;
    @Column
    private
    String level;


    public Long getPreferenceID() {
        return preferenceID;
    }

    public void setPreferenceID(Long prefId) {
        this.preferenceID = prefId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
