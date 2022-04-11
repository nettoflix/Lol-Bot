import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.sikuli.basics.Settings;
import org.sikuli.script.*;

import javax.swing.*;
import java.awt.*;
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
    private boolean aiming;
    ArrayList<Match> targetsFound;
    private int targetIndex=0;
    private MyWindow window;
    String[] targets = {"Vladimir.png", "Cassiopeia.png", "MissFortune.png", "Nocturne.png", "Jhin.png", "boneco.png"};
    JLabel label1;
    JLabel infoLabel;
    public Test(MyWindow myWindow)
    {
        this.window = myWindow;
    }
    private void start() {
        try{
           label1 = (JLabel) window.getComponent(window.mainPanel, "label1");
            infoLabel = (JLabel) window.getComponent(window.mainPanel, "infoLabel");
            //Mouse.init();
            s = new Screen();

            targetsFound = new ArrayList<>();
            URL resourceFolderURL = this.getClass().getClassLoader().getResource("Imagens");
            basePath = resourceFolderURL.toURI().getPath() + "/";
            //targets = {"Vladimir.png"};
            Settings.MoveMouseDelay = 0f;
            Settings.MinSimilarity = 0.5;
            Settings.WaitScanRate = 200;
            Pattern pattern = new Pattern();
            running = false;
            while(true)
            {
                long begin = System.currentTimeMillis();
               //System.out.println("Running While =" + running);

                if (running)
                {
                    //System.out.println("Running If =" + running);
                    if(aiming) {
                        Match targetMatch = findMatch(targets[targetIndex]);
                        if (targetMatch != null) {
                            Location location = targetMatch.getCenter();
                            location.y += 120;
                            s.mouseMove(location);
                            long end = System.currentTimeMillis();
                            float elapsedTime = (end - begin) / 1000.0f;
                            System.out.println("Elapsed Time: "+elapsedTime +" seconds");
                        }
                    }

                }
                sleep(1);
            }

        }
        catch(FindFailed | URISyntaxException | InterruptedException e){ //  e
            e.printStackTrace();
        }
    }
private Match findMatch(String path)
{
    try {
        Region region = new Region(0, 0, 800, 600);
        Match match = region.find(basePath+ path);
        if( match.getScore() >= 0.7) {
            infoLabel.setText("IMAGEM ENCONTRADA: "+ path);
        return match;
        }

    }catch(FindFailed e){System.out.println("IMAGEM NAO ENCONTRADA: "+ path+": "+ e.getMessage());}
    System.out.println("IMAGEM NAO ENCONTRADA: "+ path);
    infoLabel.setText("IMAGEM NAO ENCONTRADA: "+ path);
    return null;
}


    private void findAllMatches(String path, ArrayList<Match> found) {
        try
        {
            Region region = new Region(170,0,1180, 685);
            Iterator<Match> matches =   region.findAll(basePath + path);
            while (matches.hasNext()) {
                Match match = matches.next();
                if( match.getScore() >= 0.7) {
                        found.add(match);
                }
            }
        }catch (FindFailed e){e.printStackTrace();}
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
        MyWindow mywindow = new MyWindow();
        mywindow.setVisible(true);
        //
        Test test = new Test(mywindow);
        GlobalScreen.addNativeKeyListener(test);
        test.start();

        //

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
       // System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if(e.getKeyCode() == NativeKeyEvent.VC_PAGE_UP)
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
        if(e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK)
        {
            System.out.println("aiming!");
            aiming = true;
        }
        if(e.getKeyCode() == NativeKeyEvent.VC_BACKQUOTE)
        {
            targetIndex++;
            if(targetIndex>targets.length-1) targetIndex=0;
        }
        label1.setText("Index: " + targets[targetIndex]);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if(e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK)
        {
            aiming = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}

