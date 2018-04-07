package de.androbin.opengl.modelviewer;

import de.androbin.json.*;

public final class Configuration {
  private static final XObject CONFIG = JSONUtil.readJSON( "config.json" ).get().asObject();
  
  public static final class display_ {
    private static final XObject CONFIG_DISPLAY = CONFIG.get( "display" ).asObject();
    
    public static final float SCALE = CONFIG_DISPLAY.get( "scale" ).asFloat();
    
    public static final int FPS = CONFIG_DISPLAY.get( "fps" ).asInt();
  }
  
  public static final class viewer_ {
    private static final XObject CONFIG_VIEWER = CONFIG.get( "viewer" ).asObject();
    
    public static final float CAMERA_SENSITIVITY = CONFIG_VIEWER.get( "camera_sensitivity" )
        .asFloat();
    
    public static final String TITLE = CONFIG_VIEWER.get( "title" ).asString();
  }
}