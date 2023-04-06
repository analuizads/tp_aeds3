import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Formatter;
import java.text.SimpleDateFormat;

public class parteleitura {

	public static void main(String[] args) throws IOException {

		File arquivoCSV = new File("/home/anadiniz/Documentos/Faculdade/AEDs 3/tp_aeds3/vfinal.csv");
		//File arquivoCSV =new File ("C:\\vfinal.csv");

		RandomAccessFile arq;

		byte[] ba;
		boolean lapide=true;
		int contador=0;
		
		try {
				
			Scanner leitor = new Scanner (arquivoCSV);
		
			String linhaarquivo =new  String();
			leitor.nextLine();

			while(leitor.hasNext())
				{	

				//split das linhas do csv
				linhaarquivo=leitor.nextLine();
				String[] valores = linhaarquivo.split(";");
				contador=contador+1;
				
				System.out.println("\t"+valores[0]+"\t" +valores[1] + "\t"+valores[2]+"\t"+valores[3]+"\t"+ valores[4]);
				float nota = Float.parseFloat(valores[3]);
				
				Date date= formatter.parse(valores[2]);
				filme f1=new filme(contador,valores[1],nota,valores[4], valores[2]);
				//escrita
				
				arq = new RandomAccessFile("hexa.db","rw");

				long pos1 = arq.getFilePointer();
				ba = f1.toByteArray();
				arq.writeInt(ba.length);
				arq.write(ba);

				

					//leitura do arquivo em byt
					filme f2 = new filme();
					int tam;
					arq.seek(0);
		
				
					tam = arq.readInt();
					ba = new byte[tam];
					arq.read(ba);
					f2.fromByteArray(ba);
					System.out.println(f2);
			}
		}  
			catch(FileNotFoundException e) { e.printStackTrace();	 
		}
	}
}

