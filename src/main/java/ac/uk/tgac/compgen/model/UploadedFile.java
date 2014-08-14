package ac.uk.tgac.compgen.model;

/**
 * Created by ramirezr on 04/03/2014.
 */

//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


public class UploadedFile {

    private MultipartFile file;

//    @NotEmpty
//    @Email
    private String email;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
