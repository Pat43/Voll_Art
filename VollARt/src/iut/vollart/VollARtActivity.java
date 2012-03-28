package iut.vollart;

import iut.vollart.R;
import iut.vollart.graphics.LightingRenderer;
import iut.vollart.graphics.Model3D;
import iut.vollart.models.Model;
import iut.vollart.parser.ObjParser;
import iut.vollart.parser.ParseException;
import iut.vollart.util.AssetsFileUtil;
import iut.vollart.util.BaseFileUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;


public class VollARtActivity extends AndARActivity implements SurfaceHolder.Callback {
	
	

	private final int MENU_ALPHA = 0;
	private final int MENU_TRANSFO = 1;
	private final int MENU_TRANSFO_TAILLE = 11;
	private final int MENU_TRANSFO_ROT = 12;
	private final int MENU_TRANSFO_TRANS = 13;
	private final int MENU_TRANSFO_RAZ = 10;
	private final int MENU_PHOTO = 2;
	private final int MENU_LOCK = 3;
	private final int MENU_QUIT = 4;
	
	private int mode = 0;
	

	static public Model model;
	static public Model3D model3d;
	static public Model model2;
	static public Model3D model3d2;
	
	private ProgressDialog waitDialog;
	
	ARToolkit artoolkit;
	
	public VollARtActivity() {
		super(false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setNonARRenderer(new LightingRenderer());//or might be omited
		artoolkit = getArtoolkit();		
		getSurfaceView().setOnTouchListener(new TransformationsTouch());
		getSurfaceView().getHolder().addCallback(this);
	}
	
	

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		System.out.println("");
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
  
        menu.add(0, MENU_ALPHA, 0, "Alpha").setIcon(R.drawable.alpha);
    
        menu.addSubMenu(0, MENU_TRANSFO, 0, "Transformations").setIcon(R.drawable.trans);   
	        menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_TAILLE, 0, "Taille");
	        menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_ROT, 0, "Rotation");
	        menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_TRANS, 0, "Translation");
	        menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_RAZ, 0, "Defaut");
        	   
        menu.add(0, MENU_PHOTO, 0, "Photo").setIcon(R.drawable.photo);
    	 
        menu.add(0, MENU_LOCK, 0, "Verrouiller").setIcon(R.drawable.verrou);
		  
        menu.add(0, MENU_QUIT, 0, "Quitter").setIcon(R.drawable.quit);
		 
        return true;
    }
   

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        
	        case MENU_ALPHA:
	            mode = MENU_ALPHA;
	            return true;
	            
	        case MENU_TRANSFO_TAILLE:
	            mode = MENU_TRANSFO_TAILLE;
	            return true;
	            
	        case MENU_TRANSFO_ROT:
	        	mode = MENU_TRANSFO_ROT;
	            return true;
	            
	        case MENU_TRANSFO_TRANS:
	        	mode = MENU_TRANSFO_TRANS;
	            return true;
	            
	        case MENU_TRANSFO_RAZ:
	        	mode = MENU_TRANSFO_TAILLE;
	        	model.raz();
	        	model2.raz();
	            return true;
	            
	        case MENU_PHOTO:
	        	new TakeAsyncScreenshot().execute();
	        	return true;
	        	
	        case MENU_LOCK:
	        	startActivity(new Intent(this, Verouillage.class));
	        	return true;
	        	
	        case MENU_QUIT:
	        	System.exit(0);
	        	return true;
        }
        return false;
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	super.surfaceCreated(holder);
    
		waitDialog = ProgressDialog.show(this, "", 
                getResources().getText(R.string.loading), true);
		waitDialog.show();
		new ModelLoader().execute();

    }
    

    class TransformationsTouch implements OnTouchListener {
    	
    	private float posX=0;
    	private float posY=0;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(model!=null) {
				switch(event.getAction()) {
					
					case MotionEvent.ACTION_DOWN:
						posX = event.getX();
						posY = event.getY();
						break;
					
					case MotionEvent.ACTION_MOVE:
						float dX = posX - event.getX();
						float dY = posY - event.getY();
						posX = event.getX();
						posY = event.getY();
						if(model != null && model2 != null) {
							switch(mode) {
								
								case MENU_ALPHA:
						            model.setAlpha(dY/500.0f);
						            break;
					            
								case MENU_TRANSFO_TAILLE:
									model.setScale(dY/100.0f);
									model2.setScale(dY/100.0f);
						            break;
						        case MENU_TRANSFO_ROT:
						        	model.setXrot(-1*dX);
						        	model2.setXrot(-1*dX);
						        	model.setYrot(-1*dY);
						        	model2.setYrot(-1*dY);
						            break;
						        case MENU_TRANSFO_TRANS:
						        	model.setXpos(-dY/100f);
						        	model2.setXpos(-dY/100f);
						        	model.setYpos(-dX/100f);
						        	model2.setYpos(-dX/100f);
						        	break;
							}		
							
						}
						break;

					case MotionEvent.ACTION_CANCEL:	
					case MotionEvent.ACTION_UP:
						posX = event.getX();
						posY = event.getY();
						break;
				}
			}
			return true;
		}
    	
    }
    
	private class ModelLoader extends AsyncTask<Void, Void, Void> {
		
		
    	@Override
    	protected Void doInBackground(Void... params) {
    		
    		if (model3d == null)
			{
	    		Intent intent = getIntent();
				Bundle data = intent.getExtras();	
				String modelFileName = data.getString("name");
				String modelFileName2 = data.getString("name2");
				BaseFileUtil fileUtil= null;
				
			
			
				fileUtil = new AssetsFileUtil(getResources().getAssets());
				fileUtil.setBaseFolder("models/");
				
									
				if(modelFileName.endsWith(".obj")) {
					ObjParser parser = new ObjParser(fileUtil);
					try {
						
						if(fileUtil != null) {
							BufferedReader fileReader = fileUtil.getReaderFromName(modelFileName);
							if(fileReader != null) {
								model = parser.parse("Model1", fileReader);
								model3d = new Model3D(model);
							}
							
							fileReader = fileUtil.getReaderFromName(modelFileName2);
							if(fileReader != null) {
								model2 = parser.parse("Model2", fileReader);
								model3d2 = new Model3D(model2);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
    		}
    		
    		return null;
    	}
    	@Override
    	protected void onPostExecute(Void result) {
    		super.onPostExecute(result);
    		waitDialog.dismiss();
	    		try {
	    			if(model3d!=null)
	    				artoolkit.registerARObject(model3d);
	    			if(model3d2 != null)
	    				artoolkit.registerARObject(model3d2);
				} catch (AndARException e) {
					e.printStackTrace();
				}
    		
			startPreview();
    	}
    }
	
	class TakeAsyncScreenshot extends AsyncTask<Void, Void, Void> {
		
		private String errorMsg = null;

		@Override
		protected Void doInBackground(Void... params) {
			Bitmap bm = takeScreenshot();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("/sdcard/VollARt"+new Date().getTime()+".png");
				bm.compress(CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();					
			} catch (FileNotFoundException e) {
				errorMsg = e.getMessage();
				e.printStackTrace();
			} catch (IOException e) {
				errorMsg = e.getMessage();
				e.printStackTrace();
			}	
			return null;
		}
		
		protected void onPostExecute(Void result) {
			if(errorMsg == null)
				Toast.makeText(VollARtActivity.this, getResources().getText(R.string.screenshotsaved), Toast.LENGTH_SHORT ).show();
			else
				return;
		};
		
	}
	
}