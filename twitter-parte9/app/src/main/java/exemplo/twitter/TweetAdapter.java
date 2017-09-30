package exemplo.twitter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	private final Context context;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();

	public TweetAdapter(Context context, ArrayList<Tweet> tweets) {
		super(context, R.layout.tweetlayout, tweets);
		this.context = context;
		this.tweets = tweets;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		View rowView = inflater.inflate(R.layout.tweetlayout, parent, false);
		
		TextView textAutor = (TextView) rowView.findViewById(R.id.autor);
		TextView textConteudo = (TextView) rowView.findViewById(R.id.texto);
		TextView textData = (TextView) rowView.findViewById(R.id.postagem);
		
		Tweet tweet = tweets.get(position);
		
		textAutor.setText(tweet.getAutor().getNome());
		textConteudo.setText(tweet.getTexto());
		textData.setText(tweet.getData().toGMTString());

		return rowView;
	}

}
