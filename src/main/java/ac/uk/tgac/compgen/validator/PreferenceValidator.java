package ac.uk.tgac.compgen.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class to validate the preference form.
 * An extra validation should happen in the submission node.
 *
 * Created by ramirezr on 03/03/15.
 */
public class PreferenceValidator implements Validator{

    //We need to make this dinamic at some point, if the number of references keeps increasing
    private static final LinkedHashMap<String, String> validReferences;

    static {
        validReferences = new LinkedHashMap<String, String>();

        validReferences.put("Triticum_aestivum.IWGSP1.21.dna_sm.genome.fa", "IWGSC1" );
        validReferences.put("Triticum_aestivum.IWGSC2.25.dna_sm.toplevel.fa", "IWGSC2");
        validReferences.put("IWGSC_CSS_all_scaff_v1.fa", "IWGSC all");
    }

    public static HashMap<String, String> getValidReferences(){
        return validReferences;
    }


    @Override
     public boolean supports(java.lang.Class<?> aClass) {
        Class[] interfaces =  aClass.getInterfaces();
        for(Class c: interfaces){
            if (c.getCanonicalName().equals(Map.Entry.class.getCanonicalName())){
                return true;
            }
        }
        return false;
     }

     @Override
     public void validate(Object pair, Errors errors) {
         try {
             Map.Entry entry = (Map.Entry) pair;
             String key = entry.getKey().toString();
             String value = entry.getValue().toString();

             if (key.equals("reference")) {
                isValidReference(value, errors);
             }else{
                errors.rejectValue("general", "uploadForm.preferences", "Invalid preference: " + key );
             }
         }  catch (ClassCastException cce){
             errors.rejectValue("general","uploadForm.preferences", "Not a Map.Entry!" );
         }


     }


    private static void isValidReference(String ref, Errors errors){
       if(!validReferences.keySet().contains(ref)){
           errors.rejectValue("reference", "uploadForm", "Invalid reference: " + ref);
       }
    }
}
