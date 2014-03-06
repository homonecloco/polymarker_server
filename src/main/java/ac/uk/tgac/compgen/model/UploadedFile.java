package ac.uk.tgac.compgen.model;

/**
 * Created by ramirezr on 04/03/2014.
 */
import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

 private MultipartFile file;

 public MultipartFile getFile() {
  return file;
 }

 public void setFile(MultipartFile file) {
  this.file = file;
 }
}
