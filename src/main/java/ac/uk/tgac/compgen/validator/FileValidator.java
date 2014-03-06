package ac.uk.tgac.compgen.validator;

/**
 * Created by ramirezr on 04/03/2014.
 */
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ac.uk.tgac.compgen.model.UploadedFile;

public class FileValidator implements Validator {

 @Override
 public boolean supports(java.lang.Class<?> aClass) {
  // TODO Auto-generated method stub
  return false;
 }

 @Override
 public void validate(Object uploadedFile, Errors errors) {

  UploadedFile file = (UploadedFile) uploadedFile;

  if (file.getFile().getSize() == 0) {
   errors.rejectValue("file", "uploadForm.salectFile",
     "Please select a file!");
  }

 }

}
