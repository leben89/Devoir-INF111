package utilitaires;
import java.io.File;

public class Photos {

    private static final String DOSSIER_IMAGE = "photos/";

    private static final String PREFIXE_IMAGE = "image";

    private static final String IMG_TYPE = ".JPG";

    public Photos() {
    }

    public String[] getRecu(){
        File imgRecuDossier = new File(DOSSIER_IMAGE);
        if(!imgRecuDossier.exists()){

            imgRecuDossier.mkdir();
            return new String[0];
        }
        File[] imgRecu = imgRecuDossier.listFiles();
        String[]imgRecuNom = new String[imgRecu.length];

        for(int i=0; i<imgRecu.length; i++){
            if(imgRecu[i].isFile()) {
              imgRecuNom[i]=imgRecu[i].getName();
            }
        }
        return imgRecuNom;
    }

    public File getImg(String nom){
        File img=null;

        try{
            img = new File (DOSSIER_IMAGE+nom+IMG_TYPE);
        }catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }

    public static File getDossierImage(int id){
        File img=null;
        try{
            img= new File(DOSSIER_IMAGE+PREFIXE_IMAGE+String.valueOf(id)+IMG_TYPE);
        }catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }


}
