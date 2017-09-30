package exemplo.twitter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TweetDetailsActivity extends Activity {

  private Tweet tweet;

  private Autor usuarioLogado;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detalhestweet);

    TextView textAutor = (TextView) findViewById(R.id.autor);
    TextView textConteudo = (TextView) findViewById(R.id.texto);
    TextView textData = (TextView) findViewById(R.id.postagem);

    Intent intent = getIntent();

    tweet = (Tweet) intent.getSerializableExtra(Tweet.TWEET_INFO);

    textAutor.setText(tweet.getAutor().getNome());
    textConteudo.setText(tweet.getTexto());
    textData.setText(tweet.getData().toGMTString());

  }

  public void voltar(View v) {

    finish();
  }
  
  

  public void telefonar(View v) {

    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse("tel:" + tweet.getAutor().getTelefone()));

    startActivity(callIntent);
  }

}
