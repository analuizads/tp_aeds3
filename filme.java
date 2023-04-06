import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class filme {
  
  protected int idFilme;
  protected String titulo;
  protected float imdb;
  protected String genero;
  private Date startdate;
	
	SimpleDateFormat formato = new SimpleDateFormat("Y");
  
  public filme(int i, String t, float n,String g, Date startdate) {
    this.idFilme = i;
    this.titulo = t;
    this.imdb = n;
    this.genero = g;
    this.startdate = startdate;
  }

  public filme() {
    idFilme = -1;
    titulo = "";
    imdb = 0.F;
    genero="";
  }

  @Override

  public String toString() {
    return "\nID: " + idFilme + 
            "\nTítulo: " + titulo + 
            "\nimdb:  " + imdb + 
          "\ngenero:  " +genero +
          "\nstartdate" + formato.format(startdate);  
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

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(idFilme);
    dos.writeUTF(titulo);
    dos.writeFloat(imdb);
    dos.writeUTF(genero);
    return baos.toByteArray();
  }
    
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    idFilme = dis.readInt();
    titulo = dis.readUTF();
    imdb = dis.readFloat();
    genero = dis.readUTF();
  }
}


