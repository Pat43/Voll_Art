package iut.vollart;

import iut.vollart.R;
import iut.vollart.graphics.Model3D;
import iut.vollart.models.Model;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Verouillage extends Activity{
	
	public static Model3D model3d = VollARtActivity.model3d;
	public static Model3D model3d2 = VollARtActivity.model3d2;
	public Model model;
	public Model model2;
	
	GLSurfaceView view;
	ImageView img;

	private final int MENU_ALPHA = 0;
	private final int MENU_TRANSFO = 1;
	private final int MENU_TRANSFO_TAILLE = 11;
	private final int MENU_TRANSFO_ROT = 12;
	private final int MENU_TRANSFO_TRANS = 13;
	private final int MENU_TRANSFO_RAZ = 10;
	private final int MENU_COUPES = 3;
	private final int MENU_QUIT = 4;
	private final int MENU_VIEWER = 5;
	private boolean menu_swap = false;
	
	private int mode = 0;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		view = new GLSurfaceView(this);
		Dessin dessin = new Dessin(this);
		view.setRenderer(dessin);
		setContentView(view);
		view.setOnTouchListener(new TouchEventHandler());
		
		model = VollARtActivity.model;
		model2 = VollARtActivity.model2;
		model3d.associerModel(model);
		model3d2.associerModel(model2);
		
		Toast.makeText(this, "Mode Verrouillage", 100).show();     
	}

public boolean onCreateOptionsMenu(Menu menu){

	    menu.add(0, MENU_ALPHA, 0, "Alpha").setIcon(R.drawable.alpha);

	    menu.addSubMenu(0, MENU_TRANSFO, 1, "Transformations").setIcon(R.drawable.trans);   
	    menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_TAILLE, 0, "Taille");
	    menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_ROT, 1, "Rotation");
	    menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_TRANS, 2, "Translation");
	    menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_RAZ, 3, "Defaut");
  
	    menu.add(0, MENU_COUPES, 3, "Coupes").setIcon(R.drawable.coupe);

	    menu.add(0, MENU_QUIT, 4, "Quitter").setIcon(R.drawable.quit);
 
	    return true;
	}
public boolean onPrepareOptionsMenu(Menu menu){
	
	if (menu_swap)
	{
		if (mode == MENU_COUPES)
		{
			menu.clear();
			menu.add(0, MENU_VIEWER, 0, "Viewer").setIcon(R.drawable.viewer);
		}
		if (mode != MENU_COUPES)
		{
			menu.clear();

			menu.add(0, MENU_ALPHA, 0, "Alpha").setIcon(R.drawable.alpha);
			menu.addSubMenu(0, MENU_TRANSFO, 1, "Transformations").setIcon(R.drawable.trans);
				menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_TAILLE, 0, "Taille");
			    menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_ROT, 1, "Rotation");
			    menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_TRANS, 2, "Translation");
			    menu.getItem(MENU_TRANSFO).getSubMenu().add(0, MENU_TRANSFO_RAZ, 3, "Defaut");
			menu.add(0, MENU_COUPES, 3, "Coupes").setIcon(R.drawable.coupe);
			menu.add(0, MENU_QUIT, 4, "Quitter").setIcon(R.drawable.quit);
		}
		menu_swap = false;
	}
	return true;
}

/* Handles item selections */
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
        
        case MENU_COUPES:
        	mode = MENU_COUPES;
        	img = new Coupe(this, "aorte.png");
        	setContentView(img);
        	img.setOnTouchListener(new afficherCoupe());
        	menu_swap = true;
        	return true;
               	       	
        case MENU_VIEWER:
        	mode = MENU_TRANSFO_TAILLE;
        	view = null;
    		view = new GLSurfaceView(Verouillage.this);
    		Dessin dessin = new Dessin(Verouillage.this);
    		view.setRenderer(dessin);
    		setContentView(view);
    		view.setOnTouchListener(new TouchEventHandler());
        	menu_swap = true;
        	return true;
        	
        case MENU_QUIT:
        	System.exit(0);       	
    }
    return false;
}


class TouchEventHandler implements OnTouchListener {
	
	private float lastX=0;
	private float lastY=0;

	/* handles the touch events.
	 * the object will either be scaled, translated or rotated, dependen on the
	 * current user selected mode.
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(model!=null) {
			switch(event.getAction()) {
				//Action started
				default:
				case MotionEvent.ACTION_DOWN:
					lastX = event.getX();
					lastY = event.getY();
					break;
				//Action ongoing
				case MotionEvent.ACTION_MOVE:
					float dX = lastX - event.getX();
					float dY = lastY - event.getY();
					lastX = event.getX();
					lastY = event.getY();
					if(model != null) {
						switch(mode) {
							case MENU_ALPHA:
					            model.setAlpha(dY/500.0f);
					            break;
							case MENU_TRANSFO_TAILLE:
								model.setScale(dY/100.0f);
								model2.setScale(dY/100.0f);
					            break;
					        case MENU_TRANSFO_ROT:
					        	model.setXrot(-1*dX);//dY-> Rotation um die X-Achse
					        	model2.setXrot(-1*dX);//dY-> Rotation um die X-Achse
					        	model.setYrot(-1*dY);//dX-> Rotation um die Y-Achse
					        	model2.setYrot(-1*dY);//dX-> Rotation um die Y-Achse
					            break;
					        case MENU_TRANSFO_TRANS:
					        	model.setXpos(-dY/50f);
					        	model2.setXpos(-dY/50f);
					        	model.setYpos(-dX/50f);
					        	model2.setYpos(-dX/50f);
					        	break;
						}		
					}
					break;
				//Action ended
				case MotionEvent.ACTION_CANCEL:	
				case MotionEvent.ACTION_UP:
					lastX = event.getX();
					lastY = event.getY();
					break;
			}
		}
		return true;
	}
		
}
class afficherCoupe implements OnTouchListener
{
	int taille = getWindowManager().getDefaultDisplay().getWidth();
	int tailleCoupe = taille/9;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getX() < taille
			&& event.getX() > taille - tailleCoupe)
		{	
			img = null;
			img = new Coupe(Verouillage.this, "coupes/coupe9.jpg");
	    	setContentView(img);
	    	img.setOnTouchListener(new enleverCoupe());
		}
		
		else if (event.getX() < taille - tailleCoupe
				&& event.getX() >  taille - 2*tailleCoupe)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe8.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		else if (event.getX() < taille - 2*tailleCoupe
				&& event.getX() >  taille - 3*tailleCoupe)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe7.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		else if (event.getX() < taille - 3*tailleCoupe
				&& event.getX() >  taille - 4*tailleCoupe)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe6.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		else if (event.getX() < taille - 4*tailleCoupe
				&& event.getX() >  taille - 5*tailleCoupe)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe5.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		else if (event.getX() < taille - 5*tailleCoupe
				&& event.getX() >  taille - 6*tailleCoupe)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe4.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		else if (event.getX() < taille - 6*tailleCoupe
				&& event.getX() >  taille - 7*tailleCoupe)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe3.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		else if (event.getX() < taille - 7*tailleCoupe
				&& event.getX() >  taille - 8*tailleCoupe)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe2.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		else if (event.getX() < taille - 8*tailleCoupe
				&& event.getX() > 0)
			{	
				img = null;
				img = new Coupe(Verouillage.this, "coupes/coupe1.jpg");
		    	setContentView(img);
		    	img.setOnTouchListener(new enleverCoupe());
			}
		
		return true;
	}
	
}

class enleverCoupe implements OnTouchListener
{
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		img = null;
		img = new Coupe(Verouillage.this, "aorte.png");
    	setContentView(img);
    	img.setOnTouchListener(new afficherCoupe());
		
		return true;
	}
	
}

}
