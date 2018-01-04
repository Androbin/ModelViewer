package de.androbin.opengl.modelviewer;

import static de.androbin.util.JSONUtil.*;
import org.json.simple.*;

public final class Configuration {
  private static final JSONObject CONFIG = (JSONObject) parseJSON( "config.json" ).get();
  
  public static final class display_ {
    private static final JSONObject CONFIG_DISPLAY = (JSONObject) CONFIG.get( "display" );
    
    public static final float SCALE = ( (Number) CONFIG_DISPLAY.get( "scale" ) ).floatValue();
    
    public static final int FPS = ( (Number) CONFIG_DISPLAY.get( "fps" ) ).intValue();
  }
  
  public static final class viewer_ {
    private static final JSONObject CONFIG_VIEWER = (JSONObject) CONFIG.get( "viewer" );
    
    public static final float CAMERA_SENSITIVITY = ( (Number) CONFIG_VIEWER
        .get( "camera_sensitivity" ) ).floatValue();
    
    public static final String TITLE = (String) CONFIG_VIEWER.get( "title" );
  }
}