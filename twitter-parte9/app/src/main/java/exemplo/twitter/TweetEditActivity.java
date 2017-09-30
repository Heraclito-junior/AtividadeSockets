package exemplo.twitter;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class TweetEditActivity extends Activity {
	
	private Tweet tweet;
	private TextView textAutor;
	private EditText textConteudo;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.edittweet);
		
		textAutor = (TextView) findViewById(R.id.autorET);
		textConteudo = (EditText) findViewById(R.id.contentET);
		
		
		Intent intent = getIntent();

		
		tweet = (Tweet)intent.getSerializableExtra(Tweet.TWEET_INFO);

				
		textAutor.setText(tweet.getAutor().getNome());
		textConteudo.setText(tweet.getTexto());

	}
	
	public void voltar(View v){
		
		finish();
	}
	
	public void editarTweet(View v){
		
		tweet.setTexto(textConteudo.getText().toString());
		
		Intent result = new Intent();
		
		result.putExtra(Tweet.TWEET_INFO, tweet);
		
		
		setResult(RESULT_OK, result);
		finish();
		
	}

}

