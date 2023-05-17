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

	public static void escritorIndex(Index indice) throws IOException {
		// cria vetor de bytes

		RandomAccessFile arquivo = new RandomAccessFile("arquivo_db/Index.db", "rw");
		arquivo.seek(arquivo.length());
		arquivo.writeInt(indice.getId());
		arquivo.writeLong(indice.getPonteiro());
		arquivo.close();
	}

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

	public static long retornarPonteiro(int id) throws IOException {

		byte[] ba = {};
		long indiceArq = 0;
		long indiceArqAux = 0;
		int idArq = 0;
		long pos = 0;
		RandomAccessFile arquivo = new RandomAccessFile("arquivo_db/Index.db", "r");
		arquivo.seek(0);

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
