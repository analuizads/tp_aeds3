import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Crud {

    public static void create(filme filme) throws IOException, ParseException {

        RandomAccessFile arq = new RandomAccessFile("/home/anadiniz/Documentos/Faculdade/AEDs 3/tp_aeds3/arquivo_db/hexa.db", "rw");

        arq.seek(0);
		int ultimoId = arq.readInt();
		ultimoId++;
		filme.setId(ultimoId);
		arq.seek(arq.length());
		parteleitura.escritor(filme);
		read(ultimoId++);

    }

    public static void read(int id) throws IOException, Exception {

        RandomAccessFile arq = new RandomAccessFile("/home/anadiniz/Documentos/Faculdade/AEDs 3/tp_aeds3/arquivo_db/hexa.db", "rw");

        arq.seek(0);
        

    }
}
