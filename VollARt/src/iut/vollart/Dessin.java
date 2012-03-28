package iut.vollart;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.dhbw.andar.util.GraphicsUtil;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.view.View;

public class Dessin extends View implements Renderer {
		
	private float[] ambientlight0 = {.3f, .3f, .3f, 1f};
	private float[] diffuselight0 = {155f/255f, 16f/255f, 35f/255f, 1f};
	private float[] specularlight0 = {0.6f, 0.6f, 0.6f, 1f};
	private float[] lightposition0 = {-1.0f,2.0f,-2.0f,0.0f};
	
	private FloatBuffer lightPositionBuffer0 =  GraphicsUtil.makeFloatBuffer(lightposition0);
	private FloatBuffer specularLightBuffer0 = GraphicsUtil.makeFloatBuffer(specularlight0);
	private FloatBuffer diffuseLightBuffer0 = GraphicsUtil.makeFloatBuffer(diffuselight0);
	private FloatBuffer ambientLightBuffer0 = GraphicsUtil.makeFloatBuffer(ambientlight0);
	
	
	private float[] ambientlight1 = {.3f, .3f, .3f, 1f};
	private float[] diffuselight1 = {155f/255f, 16f/255f, 35f/255f, 1f};
	private float[] specularlight1 = {0.6f, 0.6f, 0.6f, 1f};
	private float[] lightposition1 = {2.0f,-4.0f,1.0f,1f};
	
	private FloatBuffer lightPositionBuffer1 =  GraphicsUtil.makeFloatBuffer(lightposition1);
	private FloatBuffer specularLightBuffer1 = GraphicsUtil.makeFloatBuffer(specularlight1);
	private FloatBuffer diffuseLightBuffer1 = GraphicsUtil.makeFloatBuffer(diffuselight1);
	private FloatBuffer ambientLightBuffer1 = GraphicsUtil.makeFloatBuffer(ambientlight1);
	
	private float[] ambientlight2 = {.4f, .4f, .4f, 1f};
	private float[] diffuselight2 = {155f/255f, 16f/255f, 35f/255f, 1f};
	private float[] specularlight2 = {0.6f, 0.6f, 0.6f, 1f};
	private float[] lightposition2 = {5f,-3f,-20f,1.0f};
	
	private FloatBuffer lightPositionBuffer2 =  GraphicsUtil.makeFloatBuffer(lightposition2);
	private FloatBuffer specularLightBuffer2 = GraphicsUtil.makeFloatBuffer(specularlight2);
	private FloatBuffer diffuseLightBuffer2 = GraphicsUtil.makeFloatBuffer(diffuselight2);
	private FloatBuffer ambientLightBuffer2 = GraphicsUtil.makeFloatBuffer(ambientlight2);
	
	private float[] ambientlight3 = {.4f, .4f, .4f, 1f};
	private float[] diffuselight3 = {155f/255f, 16f/255f, 35f/255f, 1f};
	private float[] specularlight3 = {0.6f, 0.6f, 0.6f, 1f};
	private float[] lightposition3 = {0,0f,5f,0.0f};
	
	private FloatBuffer lightPositionBuffer3 =  GraphicsUtil.makeFloatBuffer(lightposition3);
	private FloatBuffer specularLightBuffer3 = GraphicsUtil.makeFloatBuffer(specularlight3);
	private FloatBuffer diffuseLightBuffer3 = GraphicsUtil.makeFloatBuffer(diffuselight3);
	private FloatBuffer ambientLightBuffer3 = GraphicsUtil.makeFloatBuffer(ambientlight3);
	
	
	public Dessin (Context context) {
		super(context);

				
	}
	

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
                GL10.GL_DEPTH_BUFFER_BIT);
		

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientLightBuffer0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseLightBuffer0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularLightBuffer0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer0);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientLightBuffer1);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseLightBuffer1);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularLightBuffer1);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPositionBuffer1);
		gl.glEnable(GL10.GL_LIGHT1);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_AMBIENT, ambientLightBuffer2);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_DIFFUSE, diffuseLightBuffer2);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_SPECULAR, specularLightBuffer2);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_POSITION, lightPositionBuffer2);
		gl.glEnable(GL10.GL_LIGHT2);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_AMBIENT, ambientLightBuffer3);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_DIFFUSE, diffuseLightBuffer3);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_SPECULAR, specularLightBuffer3);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_POSITION, lightPositionBuffer3);
		gl.glEnable(GL10.GL_LIGHT3);
		
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_NORMALIZE);
		gl.glEnable(GL10.GL_RESCALE_NORMAL);
		
		
		
		
		

		GLU.gluLookAt(gl, 0,0,2, 0,0,0, 0,1,0);
		gl.glColor4f(1,0,0,0);
		
		Verouillage.model3d.draw(gl);
		Verouillage.model3d2.draw(gl);
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {}

	
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // OpenGL docs.
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);// OpenGL docs.
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                          GL10.GL_NICEST);

		
	}
	
}
