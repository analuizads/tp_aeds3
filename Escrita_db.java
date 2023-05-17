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
		// coloca ponteiro no 0 e escreve id
		arq.seek(0);
		//Criando cabeçalho
		arq.writeInt(filme1.getId());
		// escreve vetor de bytes
		// coloca variavel com o valor do ultimo byte do arquivo
		ponteiro = arq.length();
		// preenche o indice
		Index indice = new Index(filme1.getId(), ponteiro);
		Index.escritorIndex(indice);
		// manda o ponteiro para o final do arquivo e escrevendo proximo registro
		arq.seek(ponteiro);
		arq.writeByte(lapide);
		// escreve tamanho do registro
		arq.writeInt(ba.length);
		//ecreve vetor de bytes no arquivo hexa.db
		arq.write(ba);
		System.out.println(filme1);
		arq.close();
	}  

}
