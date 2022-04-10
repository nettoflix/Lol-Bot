import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.sikuli.basics.Settings;
import org.sikuli.script.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static org.sikuli.script.Constants.FOREVER;

public class Test implements NativeKeyListener {
    private Screen s;
    private String basePath;
    private boolean running;
    ArrayList<Match> imagesFound;
    private void start() {
        try{

            s = new Screen();
            imagesFound = new ArrayList<>();
            URL resourceFolderURL = this.getClass().getClassLoader().getResource("Imagens");
            basePath = resourceFolderURL.toURI().getPath() + "/";
            String[] lifeBars = {"lifeBar1.png", "lifeBar2.png", "lifeBar3.png","lifeBar4.png",
                    "lifeBar5.png", "lifeBar6.png","lifeBar7.png","lifeBar8.png","lifeBar9.png",
                    "lifeBar10.png","lifeBar11.png","lifeBar12.png","lifeBar13.png", "lifeBar14.png",
                    "lifeBar15.png","lifeBar16.png"};
            Region region1 = new Region(416, 231,850,210);
            Settings.MoveMouseDelay = 0f;
            Settings.MinSimilarity = 0.5;
            Pattern pattern = new Pattern();
            running = false;
            while(true)
            {
                long begin = System.currentTimeMillis();
              //  System.out.println("Running While =" + running);

                if (running)
                {
                      for (int i = 0; i < lifeBars.length; i++)
                      {
                        //System.out.println("Running");
                        if(findAllMatches(lifeBars[i], imagesFound))
                        {
                            break;
                        }
                      }
                      System.out.println("imagesFound size: " +imagesFound.size());
                      for(int k=0; k<imagesFound.size(); k++)
                      {
                          s.mouseMove(imagesFound.get(k));
                          s.mouseMove(-60, 60);
                          sleep(200);
                      }
                      imagesFound.clear();
                      long end = System.currentTimeMillis();
                      float elapsedTime = (end - begin) / 1000.0f;
                    System.out.println("Elapsed Time: "+elapsedTime +" seconds");
                }
                sleep(1);
            }

        }
        catch(FindFailed | URISyntaxException | InterruptedException e){ //
            e.printStackTrace();
        }
    }

    private boolean findAllMatches(String path, ArrayList<Match> found) {
        try {

         Iterator<Match> matches =   s.findAll(basePath + path);

            while (matches.hasNext()) {

                Match match = matches.next();
                //System.out.println("Math score: " +match.getScore());
                if( match.getScore() >= 0.7) {
                  //  System.out.println("Math with high score: " + match.getScore() + " --> " + match.toString());
                if(!matchesContains(found, match))
                {
                    //    System.out.println("oneAdded");
                        found.add(match);
                        if(found.size()>=5) return true;

                }
                }
            }
        }catch (FindFailed e){e.printStackTrace();}
        return false;
    }

    private boolean matchesContains(ArrayList<Match> matches, Match otherMatch)
    {
        for(int i=0; i<matches.size(); i++)
        {
            if(matches.get(i)!=null)
            {
                if(matches.get(i).getCenter().equals(otherMatch.getCenter()))
                {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean clickOnImage(String path, double score)
    {
        try {
            String lifeBarPath = basePath + path;
            Match lifeBarFound = s.find(lifeBarPath);
            if (lifeBarFound != null)
            {
                System.out.println("Found image with score: " + lifeBarFound.getScore());
                if (lifeBarFound.getScore() >= score) {
                    s.mouseMove(lifeBarPath);
                    s.mouseMove(-60, 60);
                    return true;
                    //break;
                }
            }
        } catch (FindFailed e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        //Just put this into your main:
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        Test test = new Test();
        GlobalScreen.addNativeKeyListener(test);
        test.start();

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
       // System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if(e.getKeyCode() == 42)
        {
            if(!running)
            {
                System.out.println("running = true");
                running = true;
            }
            else
            {
                System.out.println("running = false");
                running = false;
            }


        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}

