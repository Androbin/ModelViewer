package de.androbin.opengl.modelviewer;

import static de.androbin.opengl.modelviewer.Configuration.viewer_.*;
import static org.lwjgl.input.Keyboard.*;
import de.androbin.opengl.cam.*;
import org.lwjgl.util.vector.*;

public final class ModelCamera extends MightyCam3D {
  public ModelCamera() {
    this( new Vector3f(), new Vector3f() );
  }
  
  public ModelCamera( final Vector3f pos, final Vector3f dir ) {
    super( pos, dir, CAMERA_SENSITIVITY, 1f );
  }
  
  @ Override
  public boolean move( final float delta ) {
    boolean update = super.move( delta );
    
    /*  */ if ( keys.check( KEY_ADD ) ) {
      speed *= 1.25f;
      update = true;
    } else if ( keys.check( KEY_SUBTRACT ) ) {
      speed *= 0.8f;
      update = true;
    }
    
    return update;
  }
}