/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1635598
 */
public class GameTime {
    private long syncTime;
    private static final int FPS_TARGET = 25;
    private static GameTime instance;
    
    private static int currentFps;
    private static int fpsCount;
    private static long fpsTimeDelta;
    private static long gameStartTime;
    
    public static GameTime getInstance() {
        if(instance == null) {
            instance = new GameTime();
        }
        return instance;
    } 
    
    private GameTime() {
        updateSyncronizationTime();
        gameStartTime = System.currentTimeMillis();
        fpsTimeDelta = 0;
        currentFps = 0;
    }
    
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
    
    public static int getCurrentFps() {
        return (currentFps > 0) ? currentFps : fpsCount;
    }
    
    public static long getElapsedTime() {
        return System.currentTimeMillis() - gameStartTime;
    }
 
    public static String getElapsedFormattedTime() {
        long time = getElapsedTime();
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(time);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        time -= TimeUnit.SECONDS.toMillis(time);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    private void updateSyncronizationTime() {
        syncTime = System.currentTimeMillis();
    }

    public void synchronize() {
        update();
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException ex) {
        }
        updateSyncronizationTime();
    }
    
    private void update() {
        fpsCount++;
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(getElapsedTime());
        if(fpsTimeDelta != currentSecond) {
            currentFps = fpsCount;
            fpsCount = 0;
        }
        fpsTimeDelta = currentSecond;
    }
    
    private long getSleepTime() {
        long targetTime = 1000L - FPS_TARGET;
        long sleep = FPS_TARGET - (System.currentTimeMillis() - syncTime);
        if (sleep < 0) {
            sleep = 4;
        }
        return sleep;
    }
}
