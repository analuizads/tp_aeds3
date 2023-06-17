import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Leitor_csv {


    public static void leitor() throws IOException, ParseException{

        File arquivoCSV = new File("/home/anadiniz/Documentos/Faculdade/AEDs 3/tp_aeds3/FilmesDatabase.csv");
		SimpleDateFormat formato = new SimpleDateFormat("yyyy");

		RandomAccessFile arq;
	
		try {
				
			Scanner leitor = new Scanner (arquivoCSV);
		
			String linhaarquivo =new  String();
			leitor.nextLine();
			int i = 0;
			while(leitor.hasNext())
				{
				//split das linhas do csv
				linhaarquivo=leitor.nextLine();
				String[] valores = linhaarquivo.split(";");
				
				
				float nota = Float.parseFloat(valores[3]);
				String startdate = (valores[2]);
				Date date = formato.parse(startdate);
				int id = Integer.parseInt(valores[0]);
				//Criptografia
                String cript=valores[1];

                String criptografiaNome = CifraVigenere.criptografia(cript);

				System.out.printf("Crip: %s", criptografiaNome);
				
				
				// adicionando os conteudos no objeto e e escrevendo ele em byte
				//contador=id, valores[1]=titulo 
				// nota= float(nota do filme ) // valores[4]=generos do filme
				
				Filmes filme1 = new Filmes(id,criptografiaNome,nota,valores[4], date);
			
				Escrita_db.escrita(filme1);
				//leitura do arquivo em byte no hexa.db
			}
		}  
		catch(FileNotFoundException e) { e.printStackTrace();
		
	}
    }
    
}
