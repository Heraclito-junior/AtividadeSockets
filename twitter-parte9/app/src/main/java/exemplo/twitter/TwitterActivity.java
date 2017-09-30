package exemplo.twitter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class TwitterActivity extends Activity {

	protected static final int EDITAR = 0;
	protected static final int FOTO = 1;
	private static final String IMAGEM ="imagem" ;
	private TweetAdapter adaptadorLista;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();

	private Bitmap bitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if(savedInstanceState != null){

			bitmap = savedInstanceState.getParcelable(IMAGEM);

			if(bitmap != null){
				ImageView imgView = (ImageView) findViewById(R.id.minha_imagem);

				imgView.setImageBitmap(bitmap);

			}
		}

		ListView lista = (ListView) findViewById(R.id.listaTweets);

		adaptadorLista = new TweetAdapter(this, tweets);

		lista.setAdapter(adaptadorLista);

		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				Tweet tweetSelecionado = tweets.get(position);

				Intent acao = new Intent(TwitterActivity.this,
						TweetDetailsActivity.class);

				acao.putExtra(Tweet.TWEET_INFO, tweetSelecionado);

				startActivity(acao);

			}
		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Tweet tweetSelecionado = tweets.get(position);

				Intent acao = new Intent(TwitterActivity.this,
						TweetEditActivity.class);

				acao.putExtra(Tweet.TWEET_INFO, tweetSelecionado);

				startActivityForResult(acao, EDITAR);
				return true;
			}
		});
	}

	public void tweettar(View v) {

		EditText tweetText = (EditText) findViewById(R.id.textoTweet);

		String tweetContent = tweetText.getText().toString();

		Autor autor = new Autor("@Gibeon", "gibeon@dimap.ufrn.br", "8182818181000");
		Tweet tweet = new Tweet(tweetContent, autor);

		tweets.add(tweet);

		adaptadorLista.notifyDataSetChanged();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case EDITAR:
			if (resultCode == RESULT_OK) {
				Tweet tweet = (Tweet) data
						.getSerializableExtra(Tweet.TWEET_INFO);

				tweets.remove(tweet);
				tweets.add(tweet);

				adaptadorLista.notifyDataSetChanged();
			}

			break;

		case FOTO:
			if (resultCode == RESULT_OK) {
				alterarFoto(data);
			}

			break;

		default:
			break;
		}
	}

	private void alterarFoto(Intent data) {
		ImageView imgView = (ImageView) findViewById(R.id.minha_imagem);

		bitmap = (Bitmap) data.getExtras().get("data");
		imgView.setImageBitmap(bitmap);
	}



	public void tirarFoto(View v) {
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		startActivityForResult(camera, FOTO);
	}

	public void chat(View v) {


		Intent chat = new Intent(TwitterActivity.this,
				chatActivity.class);

		int socket = 1;
		chat.putExtra("numeroSocket", socket);
		startActivity(chat);

	}
	public void chat2(View v) {


		Intent chat = new Intent(TwitterActivity.this,
				chatActivity.class);

		int socket = 2;
		chat.putExtra("numeroSocket", socket);
		startActivity(chat);

	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {

		outState.putParcelable(IMAGEM,bitmap);
		super.onSaveInstanceState(outState);
	}
}