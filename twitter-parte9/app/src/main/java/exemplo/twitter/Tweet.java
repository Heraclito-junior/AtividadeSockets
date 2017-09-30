package exemplo.twitter;

import java.io.Serializable;
import java.util.Date;

public class Tweet implements Serializable{

	public static final String TWEET_INFO = "TWEET_INFO";
	private static int idCount=0;
	
	private String texto;
	private Autor autor;
	private Date data;
	private int id;
	

	public Tweet(String texto,Autor autor) {
		super();
		this.texto = texto;
		this.autor = autor;
		this.data = new Date();
		id = ++idCount;
	}

	public Tweet(String texto, Autor autor, Date data) {
		super();
		this.texto = texto;
		this.autor = autor;
		this.data = data;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		String content = autor + "\n" + texto + "\n" + data.toGMTString();
		return content;
	}

	public String getTelefoneAutor() {
		// TODO Auto-generated method stub
		return autor.getTelefone();
	}
	
	@Override
	public boolean equals(Object o) {
		
		
		return ((Tweet)o).id == id;
	}

}
