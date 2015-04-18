package com.endorite.rollz.scene.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.endorite.rollz.scene.BaseScene;
import com.endorite.rollz.scene.SceneManager;
import com.endorite.rollz.scene.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

	@Override
	public void createScene() {
		createBackground();		
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
	    System.exit(0);		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground(){
	    attachChild(new Sprite(camera.getWidth()/2, camera.getHeight()/2, resourcesManager.menu_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
	}
	
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_LOAD = 1;
	private final int MENU_OPTIONS = 2;
	private final int MENU_QUIT = 3;
	
	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
	    final IMenuItem loadMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_LOAD, resourcesManager.load_region, vbom), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);   
	    final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT, resourcesManager.quit_region, vbom), 1.2f, 1);
	    
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(loadMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    menuChildScene.addMenuItem(quitMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY());
	    loadMenuItem.setPosition(loadMenuItem.getX(), loadMenuItem.getY());
	    optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY());
	    quitMenuItem.setPosition(quitMenuItem.getX(), quitMenuItem.getY());
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY){
	    switch(pMenuItem.getID())
	    {
	        case MENU_PLAY:
	            SceneManager.getInstance().loadGameScene(engine);
	            return true;
	        case MENU_LOAD:
	            return true;
	        case MENU_OPTIONS:
	            return true;
	        case MENU_QUIT:
	    	    System.exit(0);	
	            return true;
	        default:
	            return false;
	    }
	}

}
