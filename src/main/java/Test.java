import org.sikuli.basics.Settings;
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

            URL resourceFolderURL = this.getClass().getClassLoader().getResource("Imagens");
            basePath = resourceFolderURL.toURI().getPath() + "/";
            String platiniumImage = basePath + "platinium.png";
            String iconeMorgana_Image = basePath + "iconeMorgana.png";
            String lifeBar1_Image = basePath + "lifeBar1.png";
            String[] lifeBars = {"lifeBar1.png", "lifeBar2.png", "lifeBar3.png","lifeBar4.png",
                    "lifeBar5.png", "lifeBar6.png","lifeBar7.png","lifeBar8.png","lifeBar9.png",
                    "lifeBar10.png","lifeBar11.png","lifeBar12.png","lifeBar13.png", "lifeBar14.png",
                    "lifeBar15.png","lifeBar16.png"};
            Region region1 = new Region(416, 231,850,210);
            Settings.MoveMouseDelay = 0f;
            Settings.MinSimilarity = 0.5;
            Pattern pattern = new Pattern();


            while(true)
            {
                for(int i=0; i<lifeBars.length; i++)
                {
                    try
                    {
                        String lifeBarPath = basePath + lifeBars[i];
                        Match lifeBarFound = s.find(lifeBarPath);
                        if (lifeBarFound != null) {
                            System.out.println("Found image with score: " + lifeBarFound.getScore());
                            //s.find(iconeMorgana_Image).highlight();
                            if (lifeBarFound.getScore() >= 0.7) {
                                s.mouseMove(lifeBarPath);
                                s.mouseMove(-60,60);
                                break;
                                //s.wait(iconeMorgana_Image);
                                //s.mouseMove(iconeMorgana_Image);
                            }
                        }
                    }
                    catch(FindFailed e){}
                }
                //if(clickOnImage(platiniumImage))
                //{
                  //  System.out.println("clicked");
                //}
               /* try {
                    //System.out.println("oi");
                    System.out.println("Find: " + s.find(iconeMorgana_Image).toString());

                    if (s.find(lifeBar1_Image) != null) {
                        System.out.println("Found image with score: " + s.find(lifeBar1_Image).getScore());
                        //s.find(iconeMorgana_Image).highlight();
                        if (s.exists(lifeBar1_Image).getScore() >= 0.7) {
                            s.mouseMove(lifeBar1_Image);
                            //s.wait(iconeMorgana_Image);
                            //s.mouseMove(iconeMorgana_Image);
                        }
                    }
                }
                catch(FindFailed e) {
                   // System.out.println(e.getMessage());
                } */
            }



        }
        catch(/*FindFailed |*/ URISyntaxException /*| InterruptedException*/ e){ //
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
    private boolean clickOnImage(String path) throws FindFailed, InterruptedException {
        boolean succes=false;
            if(s.exists(path)!=null)
            {
                if(s.exists(path).getScore() >=0.8)
                {
                    s.wait(path);
                    s.mouseMove(path);
                    s.mouseDown(Button.LEFT);
                    sleep(500);
                    s.mouseUp(Button.LEFT);
                    succes = true;
                }
            }
        return succes;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.start();

    }
}

