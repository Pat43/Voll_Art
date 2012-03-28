package iut.vollart;

import java.io.BufferedInputStream;
import java.io.IOException;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.widget.ImageView;

public class Coupe extends ImageView{

	public Coupe(Context context, String image) {
		super(context);
		AssetManager am = context.getAssets();

		try {
			
			BufferedInputStream buf = new BufferedInputStream(am.open(image));
			Bitmap bitmap = BitmapFactory.decodeStream(buf);
		    this.setImageBitmap(bitmap);
		    buf.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}


}
