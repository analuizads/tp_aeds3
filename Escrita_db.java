import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Escrita_db {

	static long ponteiro;

	public static void escrita(Filmes filme1) throws IOException
	{	
		//função para escrita do objeto em byte
		RandomAccessFile arq = new RandomAccessFile("arquivo_db/hexa.db", "rw");
	
		byte lapide = ' ';
		 // converte o char em um vetor de byte
		 //criar  em byte o chars
		byte[] ba;
		ba = Filmes.toByteArray(filme1);
		arq.seek(0);
		//Criando cabeçalho
		arq.writeInt(filme1.getId());
		ponteiro = arq.length();
		arq.seek(ponteiro);
		arq.writeByte(lapide);
		arq.writeInt(ba.length);
		arq.write(ba);
		System.out.println(filme1);
	}  

}
