package de.androbin.opengl.modelviewer;

import static de.androbin.gfx.SystemGraphics.*;
import static de.androbin.io.util.FileChooserUtil.*;
import static de.androbin.lwjgl.util.BufferUtil.*;
import static de.androbin.opengl.model.util.ModelUtil.*;
import static de.androbin.opengl.modelviewer.Configuration.display_.*;
import static de.androbin.opengl.modelviewer.Configuration.viewer_.*;
import static de.androbin.opengl.util.DisplayUtil.*;
import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.opengl.GL11.*;
import de.androbin.opengl.cam.*;
import de.androbin.opengl.model.*;
import de.androbin.shell.*;
import de.androbin.shell.env.*;
import de.androbin.shell.gfx.*;
import de.androbin.shell.input.*;
import java.io.*;
import org.lwjgl.util.vector.*;

public final class ModelViewer extends BasicShell implements KeyInput, GLGraphics {
  private final MightyCam3D camera;
  
  private Vector3f lightPosition;
  private boolean bindLightToCamera = true;
  
  private final int displayList;
  
  public ModelViewer( final File file ) {
    this( loadModel( file ) );
  }
  
  public ModelViewer( final Model model ) {
    this.camera = new ModelCamera();
    this.lightPosition = ( (Cam3D) camera ).getTranslation();
    
    if ( model.normals == null ) {
      model.calcNormals();
    }
    
    displayList = model.createDisplayList();
    
    addKeyInput( this );
    addKeyInput( camera.keys );
    addMouseMotionInput( camera.mouseTrack );
  }
  
  @ Override
  public void initGL() {
    glFrontFace( GL_CCW );
    
    /* LIGHTING */
    {
      glEnable( GL_LIGHTING );
      
      glEnable( GL_LIGHT0 );
      glLight( GL_LIGHT0, GL_DIFFUSE, wrapFloatBuffer( 1f, 1f, 1f, 1f ) );
    }
  }
  
  @ Override
  public void keyPressed( final int keycode ) {
    switch ( keycode ) {
      case KEY_ESCAPE:
        System.exit( 0 );
        
      case KEY_F11:
        toggleFullscreen( () -> createDisplayMode( SCALE ) );
        break;
      
      case KEY_L:
        repositionLight();
        break;
    }
  }
  
  public static void main( final String[] args ) {
    setSystemLookAndFeel();
    
    final File file = openFile( null, null );
    
    if ( file == null ) {
      return;
    }
    
    GLEnv.initDisplay( TITLE, createDisplayMode( SCALE ), false );
    GLEnv.initGeneral();
    GLEnv.init3D();
    
    new GLEnv( new ModelViewer( file ), FPS ).run();
  }
  
  @ Override
  protected void onResized( final int width, final int height ) {
  }
  
  @ Override
  public void render() {
    glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
    glLoadIdentity();
    camera.applyCamera();
    
    glLight( GL_LIGHT0, GL_POSITION, wrapFloatVector( lightPosition ) );
    
    glCallList( displayList );
  }
  
  public void repositionLight() {
    bindLightToCamera ^= true;
    
    if ( bindLightToCamera ) {
      lightPosition = ( (Cam3D) camera ).getTranslation();
    } else {
      lightPosition = new Vector3f( lightPosition );
    }
  }
  
  @ Override
  public void update( final float delta ) {
  }
  
  @ Override
  public void updateUI( final float delta ) {
    camera.move( delta );
    camera.rotate();
  }
}