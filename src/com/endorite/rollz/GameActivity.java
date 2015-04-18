package com.endorite.rollz;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.endorite.rollz.scene.SceneManager;

public class GameActivity extends BaseGameActivity{

	private BoundCamera camera;
	//private ResourcesManager resourcesManager;
	public static int CAMERA_WIDTH;
	public static int CAMERA_HEIGHT;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        CAMERA_WIDTH = displayMetrics.widthPixels;
        CAMERA_HEIGHT = displayMetrics.heightPixels;
		//this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		camera = new BoundCamera(0, 0, 400, 240);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
	    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);	    
	    return engineOptions;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
	    //resourcesManager = ResourcesManager.getInstance();
	    pOnCreateResourcesCallback.onCreateResourcesFinished();		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            SceneManager.getInstance().createMenuScene();
	        }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}
	
	//@Override
	//protected void onDestroy()
	//{
	//	super.onDestroy();
	//        System.exit(0);	
	//}
}
