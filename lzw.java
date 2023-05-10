import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// codigo de compressao-descompressao
 // @author mingescharlie
 //
public class Main {

	/**  Responsavel por criar um File Readerque será usado na Descompressao e descompactação
	 *  e chamar seus metodos ,passando como parametro o file reader e fileinput stream que representa os arquivos
	 * a serem comprimidos
	 * @param argsCOMP
	 * @throws IOException 
	 */
	public static void main ( String[] args ) {
		Boolean repeat = true;
		while (repeat) {
			Scanner input = new Scanner(System.in);

			System.out.println("COMPRIMIR or DESCOMPRIMIR?");
			String option = input.nextLine().toUpperCase();

			try {
				if (option.equals("COMPRIMIR")) {
					System.out.println("Coloque o local onde seu arquivo se encontra");
					String path = input.nextLine();
					File toCompress = new File(path);
					FileReader reader = new FileReader(toCompress);

					System.out.println("Qual nome do seu arquivo?");
					String compressedFileName = input.nextLine();
					File compressedFile = new File(compressedFileName);

					System.out.println("Comprimindo arquivo, aguarde ..");
					compress(reader, compressedFileName);
					double ratio = getCompressionRatio(toCompress, compressedFile);

					System.out.println("O arquivo foi comprimido, a quantidade de reducao foi : " + NumberFormat.getPercentInstance().format(ratio));
				} else if (option.equals("DECOMPRIMIR")) {
					System.out.println("Coloque o local onde seu arquivo a ser descomprimido se encontra");
					String path = input.nextLine();
					FileInputStream in = new FileInputStream(path);
					System.out.println(" Digite o nome e se quiser o caminho do arquivo a ser descomprimido");
					String filename = input.nextLine();
					decompress(in, filename);
				} else {
					System.out.println("Comando inválido, tente novamente");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			System.out.println("Deseja comprimir outro arquivo? SIM or NaO");
			String ans = input.nextLine().toUpperCase();
			if (ans.equals("NaO")) {
				repeat = false;
				System.out.println("saindo do programa ...");
				input.close();
			} else if (!ans.equals("SIM")) {
				System.out.println("Entrada invalida, saindo do programa");
				input.close();
			}
		}

	}

	/**
	 * comprimir, ele pega o arquivo a ser lido
	 * construirá uma lista de caracteres  e olha se estao na lista de de strings. Se estiverem adiciona a sequencia ao codigo, senao
	 * cria mais uma sequencia ao final e começa novamente a sequencia
	 * 
	 * @param reader

	 * @throws Exception 
	 * @throws MapOverflowException 
	 */
	public static void compress(FileReader reader, String name) throws Exception {

		/*Mapa has para armazenar sequencia de caracteres que virarao codigos  */
		HashMap<String, Integer> stringList = new HashMap<>();

		int i; 															/* contador de string */

		/* Inicializando a lista de string com os caracteres que esperamos  */
		for (i = 1; i < 96; i++) { 						/* ASC II  */
			char t = (char) (i+31); 
			stringList.put(Character.toString(t),i);
		}

		/* para escrever bytes  representados no código  */
		FileOutputStream out = new FileOutputStream(new File(name));

		/* usado para cada vez que um novo caracteres e lido*/
		String prefix = "";

		int c;
		while ((c = reader.read()) != -1) { 		/*enquanto tem coisa pra ler*/

			/*constroi string atual + caractere lido */
			String temp = prefix + (char)c;

			if (stringList.containsKey(temp)) { /* se string temp estiver na lista */
				prefix = temp; 									/* prefixo=temp*/
			} else { 													
				int code = stringList.get(prefix);
				out.write(code);

				stringList.put(temp,i); 					/* add  o temp na stringList */
				i++; 														/* increment o index da String lista */
				prefix = "" + (char)c; 						/* update  no prefix */
			}

			if (i >= 255) {
				throw new Exception("Muitas strings únicas encontradas no texto, mapa não é grande o suficiente");
			}
		}

		if (stringList.containsKey(prefix)) {
			int code = stringList.get(prefix);
			out.write(code);
		} else {
			stringList.put(prefix,i);
			int code = stringList.get(prefix);
			out.write(code);
		}

		reader.close();

		out.flush();
		out.close();

		System.out.println("Texto comprimido com sucesso");
	}

	/**
	 * Descomprimir -> recebe um arquivo comprimido, contendo o codigo comprimido
	 * traduz os caracteres pelos codigos, se o codigo nao estiver na lista
	 * ele cria códigos derivados 
	 * @param in
	 * 	
	 * @throws Exception 
	 * @throws MapOverflowException 
	 */
	public static void decompress(FileInputStream in, String filename) throws Exception {

		/* HashMap para armazenar o codigo e sua sequencia  */
		HashMap<Integer, String> codeList = new HashMap<>();

		int i; 						/* contador para A LSITA DE CODIGO */

		/* iniciando a lista de codigos com codigos para caracteres que sabemos que estara no texto
 */
		for (i = 1; i < 96; i++) {
			char t = (char) (i+31);
			codeList.put(i, Character.toString(t));
		}

		String output = ""; 							/* decomprimir   texto original */

		int c = in.read();
		if (codeList.containsKey(c)) {
			output += codeList.get(c); 			/*adicionando os caracteres correspondend a saida  */
		}
		int old = c; 									

		while ((c = in.read()) != -1) { 	/* enquanto tiver numeros, vai ler */
			if (codeList.containsKey(c)) { 	/* se C esta na lsita  */
				String temp = codeList.get(c);
				output = output + temp; 			/* add c  na saida  */

				/* adicionando string de codigo antiga + primeiro caractere do novo codigo
				pois ja sabemos que essa string foi adiconada 
				 */
				codeList.put(i, codeList.get(old) + temp.charAt(0));
				i++;
				old = c;
			} else { /* SE NAO, deriva os caracteres correspondente  */
				String temp = codeList.get(old); /* obtem os caracteres do codigo antigo*/

				
				codeList.put(i, temp + temp.charAt(0));
				i++;
				//add temp + o primeiro caractere  de temp na nsaida*/
				output = output + temp + temp.charAt(0);
			}

			if (i >= 255) {
				throw new Exception("Muitass strings encontradas, mapa n e tao grande");
			}
		}

		in.close();

		FileWriter write = new FileWriter(new File(filename));
		write.write(output);
		write.flush();
		write.close();
		System.out.println("Text file successfully decompressed!");
	}

	/**
	 *  Metodo para calcular quanto do arquivo foi comprimido.
	 * 
	 * @param initial
	 * 		original file
	 * @param compressed
	 * 		compressed file
	 * @return
	
	 */
	public static double getCompressionRatio(File initial, File compressed) {
		long initSize = initial.length();
		long compSize = compressed.length();

		long diff = initSize - compSize;

		double ratio = ((double)diff/initSize);
		return ratio;
	}

}