import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;

public class Index {

    private long ponteiro;
	private int id;

	public Index() {
	}

	public Index(int id, long ponteiro) {

		this.ponteiro = ponteiro;
		this.id = id;
	}

	public long getPonteiro() {
		return ponteiro;
	}

	public void setPonteiro(long ponteiro) {
		this.ponteiro = ponteiro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Index [ponteiro=" + ponteiro + ", id=" + id + "]";
	}
	
	public void imprimir(Index indice) {
		System.out.println(indice.getId());
		System.out.println(indice.getPonteiro());

	}

	//converte um objeto do tipo Index em um array de bytes
	//cria um ByteArrayOutputStream e um DataOutputStream para escrever os valores dos atributos id e ponteiro no fluxo de saída. Em seguida, o método retorna o array de bytes obtido.
	public static byte[] ToByteArrayIndex(Index indice) throws IOException {
		DataOutputStream dos;

		ByteArrayOutputStream baos;

		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
		// escreve vetor de bytes em memoria
		dos.writeInt(indice.getId());
		dos.writeLong(indice.getPonteiro());

		baos.close();
		dos.close();
		return baos.toByteArray();

	}

	//escreve um objeto do tipo Index em um arquivo chamado "Index.db". Cria um RandomAccessFile no modo de escrita e procura o fim do arquivo. Em seguida, ele escreve os valores dos atributos id e ponteiro no arquivo e fecha o arquivo.
	public static void escritorIndex(Index indice) throws IOException {
		// cria vetor de bytes

		RandomAccessFile arquivo = new RandomAccessFile("arquivo_db/Index.db", "rw");
		arquivo.seek(arquivo.length());
		arquivo.writeInt(indice.getId());
		arquivo.writeLong(indice.getPonteiro());
		arquivo.close();
	}

	//converte um array de bytes em um objeto do tipo Index. Cria um ByteArrayInputStream e um DataInputStream para ler os valores dos atributos id e ponteiro do fluxo de entrada. Em seguida, ele cria um novo objeto Index, define os valores lidos nos atributos correspondentes e retorna o objeto Index criado.
	public static Index FromByteArrayIndex(byte[] ba) throws IOException, ParseException {

		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DataInputStream dis = new DataInputStream(bais);
		Index indice = new Index();

		int tam;

		indice.setId(dis.readInt());
		indice.setPonteiro(dis.readLong());

		dis.close();
		return indice;
	}

	// lê um arquivo binário chamado "Index.db" e procura pelo valor do ponteiro correspondente a um determinado identificador
	public static long retornarPonteiro(int id) throws IOException {

		byte[] ba = {};
		long indiceArq = 0;
		long indiceArqAux = 0;
		int idArq = 0;
		long pos = 0;
		RandomAccessFile arquivo = new RandomAccessFile("arquivo_db/Index.db", "r");
		arquivo.seek(0);

		//percorre o arquivo, lendo os valores dos atributos id e ponteiro e verificando se o id lido é igual ao id passado como argumento
		//quando encontra uma correspondência, ele armazena o valor do ponteiro e continua a percorrer o arquivo. No final, o método retorna o valor do ponteiro correspondente ao id buscado.
		while (true) {
			idArq = arquivo.readInt();
			indiceArqAux = arquivo.readLong();
			if (arquivo.getFilePointer() != arquivo.length()) {

				Index indice = new Index(idArq, indiceArqAux);
				ba = ToByteArrayIndex(indice);
				if (id == idArq) {
					indiceArq = indiceArqAux;
				}
				pos += ba.length;
				arquivo.seek(pos);

			} else {
				break;
			}

		}
		arquivo.close();
		return indiceArq;
	}
}
