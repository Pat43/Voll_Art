package iut.vollart;

import iut.vollart.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class Interface extends Activity implements View.OnClickListener {
	 
	private ImageButton ButtonAppli;
	private ImageButton ButtonQuitter;
	private ImageButton ButtonCredits;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        ButtonAppli = (ImageButton)findViewById(R.id.buttonappli);
        ButtonAppli.setOnClickListener(this);
        ButtonCredits = (ImageButton)findViewById(R.id.buttoncredit);
        ButtonCredits.setOnClickListener(this);
        ButtonQuitter= (ImageButton)findViewById(R.id.buttonquitter);
        ButtonQuitter.setOnClickListener(this);
    }
 
	@Override
	public void onClick(View v)
	{
		if (v == ButtonAppli)
		{
			Intent intent = new Intent(Interface.this, VollARtActivity.class);
		    intent.putExtra("name", "artere.obj");
		    intent.putExtra("name2", "insde.obj");
		    intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
		}
		
		if (v == ButtonCredits)
		{
			Intent intent2 = new Intent(Interface.this, CreditActivity.class);
			startActivity(intent2);
		}
		
		if (v == ButtonQuitter)
		{
			this.finish();
		}
	}
}