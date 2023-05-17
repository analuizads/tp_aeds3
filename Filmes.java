import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Filmes {
  
  private int idFilme;
  private String titulo;
  private float imdb;
  private String genero;
  private Date startdate;
  private byte lapide;
	
	SimpleDateFormat formato = new SimpleDateFormat("yyyy");
  
  public Filmes (int i, String t, float n,String g, Date startdate) {
    this.idFilme = i;
    this.titulo = t;
    this.imdb = n;
    this.genero = g;
    this.startdate = startdate;
    lapide = ' ';
  }

  public Filmes() {

    lapide = ' ';
    idFilme = -1;
    titulo = "";
    imdb = 0.F;
    genero="";
  }

  @Override

  public String toString() {
    return "\nID: " + idFilme + 
            "\nTítulo: " + titulo + 
            "\nIMDB: " + imdb + 
          "\nGenero: " +genero +
          "\nData: " + formato.format(startdate);  
  }

  public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Float getImdb() {
		return imdb;
	}

	public void setImdb(Float imdb) {
		this.imdb = imdb;
	}

	public String  getGenero() {
		return genero;
	}

	public void setGenero(String  genero) {
		this.genero = genero;
	}

	public int getId() {
		return idFilme;
	}

	public void setId(int id) {
		this.idFilme = id;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
  }

  public static byte[] toByteArray(Filmes filme) throws IOException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    SimpleDateFormat formato = new SimpleDateFormat("yyyy");
    
    dos.writeInt(filme.getId());
    dos.writeUTF(filme.getTitulo());
    dos.writeFloat(filme.getImdb());
    dos.writeUTF(filme.getGenero());
    dos.writeUTF(formato.format(filme.getStartdate()));
    return baos.toByteArray();
  }
    
  public static Filmes fromByteArray(byte[] ba) throws IOException, ParseException {

    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    SimpleDateFormat formato = new SimpleDateFormat("yyyy");

    Filmes filme = new Filmes();

    filme.setId(dis.readInt());
    filme.setTitulo(dis.readUTF());
    filme.setImdb(dis.readFloat());
    filme.setGenero(dis.readUTF());
    filme.setStartdate(formato.parse(dis.readUTF()));

    dis.close();
    return filme;
  }
}


