package ac.uk.tgac.compgen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ramirezr on 27/02/15.
 */
@Entity
@Table(name="reference")
public class Reference {
    @Id
    @Column
    private Long ReferenceID;

    @Column
    private String path;



}
