package iut.vollart.graphics;

import iut.vollart.models.Group;
import iut.vollart.models.Material;
import iut.vollart.models.Model;

import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.util.Log;

import edu.dhbw.andar.ARObject;



public class Model3D extends ARObject implements Serializable{
	
	private Model model;
	private Group[] texturedGroups;
	private Group[] nonTexturedGroups;
	private HashMap<Material, Integer> textureIDs = new HashMap<Material, Integer>();
	
	public Model3D(Model model) {
		super("model", "marqueur.patt", 80.0, new double[]{0,0});
		this.model = model;
		model.finalize();

		Vector<Group> groups = model.getGroups();
		Vector<Group> texturedGroups = new Vector<Group>();
		Vector<Group> nonTexturedGroups = new Vector<Group>();
		for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext();) {
			Group currGroup = iterator.next();
			if(currGroup.isTextured()) {
				texturedGroups.add(currGroup);
			} else {
				nonTexturedGroups.add(currGroup);
			}			
		}
		this.texturedGroups = texturedGroups.toArray(new Group[texturedGroups.size()]);
		this.nonTexturedGroups = nonTexturedGroups.toArray(new Group[nonTexturedGroups.size()]);	
	}
	
	public void associerModel(Model model)
	{
		this.model = model;
	}
	
	@Override
	public void init(GL10 gl){
		int[]  tmpTextureID = new int[1];

		Iterator<Material> materialI = model.getMaterials().values().iterator();
		while (materialI.hasNext()) {
			Material material = (Material) materialI.next();
			if(material.hasTexture()) {
	
				gl.glGenTextures(1, tmpTextureID, 0);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, tmpTextureID[0]);
				textureIDs.put(material, tmpTextureID[0]);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, material.getTexture(),0);
				material.getTexture().recycle();
				gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
				gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR); 
			}
		}

	}
		
	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
		

		gl.glScalef(model.scale, model.scale, model.scale);
		gl.glTranslatef(model.xpos, model.ypos, model.zpos);
		gl.glRotatef(model.xrot, 1, 0, 0);
		gl.glRotatef(model.yrot, 0, 1, 0);
		gl.glRotatef(model.zrot, 0, 0, 1);
		
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	
	
		if (model.alpha < 1f)
		{
			gl.glColor4f(155f/255f,16f/255f,35f/255f, model.alpha);
			gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
			
			gl.glDisable(GL10.GL_LIGHTING);
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

			gl.glDisable(GL10.GL_DEPTH_TEST);
		}
		else
		{
			gl.glEnable(GL10.GL_LIGHTING);
			gl.glDisable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_DEPTH_TEST);
		}


		gl.glDisable(GL10.GL_TEXTURE_2D);
		int cnt = nonTexturedGroups.length;
		for (int i = 0; i < cnt; i++) {
			Group group = nonTexturedGroups[i];
			Material mat = group.getMaterial();
			if(mat != null) {
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat.specularlight);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat.ambientlight);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat.diffuselight);
				gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat.shininess);
			}
			gl.glVertexPointer(3,GL10.GL_FLOAT, 0, group.vertices);
	        gl.glNormalPointer(GL10.GL_FLOAT,0, group.normals);	        
	        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, group.vertexCount);
		}
		
		gl.glEnable(GL10.GL_LIGHTING);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		cnt = texturedGroups.length;
		for (int i = 0; i < cnt; i++) {
			Group group = texturedGroups[i];
			Material mat = group.getMaterial();
			if(mat != null) {
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat.specularlight);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat.ambientlight);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat.diffuselight);
				gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat.shininess);
				if(mat.hasTexture()) {
					gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, group.texcoords);
					gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs.get(mat).intValue());
				}
			}
			gl.glVertexPointer(3,GL10.GL_FLOAT, 0, group.vertices);
	        gl.glNormalPointer(GL10.GL_FLOAT,0, group.normals);	        
	        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, group.vertexCount);
		}
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	}

	class LogWriter extends Writer {

	    @Override public void close() {
	        flushBuilder();
	    }

	    @Override public void flush() {
	        flushBuilder();
	    }

	    @Override public void write(char[] buf, int offset, int count) {
	        for(int i = 0; i < count; i++) {
	            char c = buf[offset + i];
	            if ( c == '\n') {
	                flushBuilder();
	            }
	            else {
	                mBuilder.append(c);
	            }
	        }
	    }

	    private void flushBuilder() {
	        if (mBuilder.length() > 0) {
	            Log.e("OpenGLCam", mBuilder.toString());
	            mBuilder.delete(0, mBuilder.length());
	        }
	    }

	    private StringBuilder mBuilder = new StringBuilder();
	    
	    
	}
}
