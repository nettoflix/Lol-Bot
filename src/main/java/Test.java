import org.sikuli.script.*;

import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.Thread.sleep;
import static org.sikuli.script.Constants.FOREVER;

public class Test {
    private Screen s;
    private String basePath;

    private void start() {
        try{
            s = new Screen();
            //String imagePath = "perfil.png";
            URL resourceFolderURL = this.getClass().getClassLoader().getResource("Imagens");
            basePath = resourceFolderURL.toURI().getPath() + "/";
            String finishedBanditImage = basePath + "finished_tentBanditLeader.png";
            String sendOnQuestImage = basePath + "sendOnQuest.png";
            String confirmImage = basePath + "confirm.png";
            String finishedElvishForestImage = basePath + "finished_elvishForest";
            Region region1 = new Region(416, 231,850,210);
            while(true)
            {
                if(clickOnImage(region1, finishedBanditImage))
                {
                    System.out.println();
                    clickOnImage(null, sendOnQuestImage);
                    sleep(7000);
                    clickOnImage(null, confirmImage);

                }
                sleep(7000);
                if(clickOnImage(region1,finishedElvishForestImage))
                {
                    clickOnImage(null,sendOnQuestImage);
                    sleep(7000);
                    clickOnImage(null,confirmImage);
                }
            }



            //String pathYourSystem = System.getProperty("user.dir") + "\\";
            //s.wait(pathYourSystem+ "perfil.png");
            //s.click(pathYourSystem+ "perfil.png");
            //s.wait("imgs/spotlight-input.png");


        }
        catch(FindFailed | URISyntaxException | InterruptedException e){
            e.printStackTrace();
        }
    }
   private boolean clickOnImage(Region region, String path) throws FindFailed {
        boolean succes=false;
        if(region!=null)
        {
            if(region.exists(path)!=null)
            {
                if(region.exists(path).getScore() >=0.8)
                {
                    region.wait(path);
                    region.click(path);
                    succes = true;
                }


            }
        }
        else
        {
            if(s.exists(path)!=null)
            {
                if(s.exists(path).getScore() >=0.8)
                {
                    s.wait(path);
                    s.click(path);
                    succes = true;
                }


            }
        }
        return succes;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.start();

    }
}

