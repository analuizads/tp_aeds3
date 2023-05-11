import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Scanner;

public class Lzw {
   /*  Responsavel por criar um File  será usado na Descompressao e descompactação
    *  e chamar seus metodos ,passando como parametro o file rstream que representa os arquivos
    * a serem comprimidos
    * @param argsCOMP
    * @throws IOException 
    */
    public static void algoritmo(String opcao) {
      
            boolean repeat = true;
            int w = 0;
    
            while (repeat) {
                Scanner input = new Scanner(System.in);
                String option = opcao;
                String compr = "Compressao";
                w++;
    
                try {
                    if (option.equals("COMPRIMIR")) {
                        System.out.println("Coloque o local onde seu arquivo se encontra");
                        String path = input.nextLine();
                        File toCompress = new File(path);
                        FileInputStream reader = new FileInputStream(toCompress);
    
                        System.out.println("Qual nome do seu arquivo?");
                        String compressedFileName = input.nextLine();
                        compressedFileName = compressedFileName.concat(compr).concat(String.valueOf(w));
                        File compressedFile = new File(compressedFileName);
    
                        System.out.println("Comprimindo arquivo, aguarde ..");
                        compress(reader, compressedFileName);
                        double ratio = getCompressionRatio(toCompress, compressedFile);
    
                        System.out.println("O arquivo foi comprimido, a quantidade de reducao foi: " + NumberFormat.getPercentInstance().format(ratio));
                    } else if (option.equals("DESCOMPRIMIR")) {
                        System.out.println("Coloque o local onde seu arquivo a ser descomprimido se encontra");
                        String path = input.nextLine();
                        FileInputStream in = new FileInputStream(path);
                        System.out.println("Digite o nome e, se quiser, o caminho do arquivo a ser descomprimido");
                        String filename = input.nextLine();
                        decompress(in, filename);
                    } else {
                        System.out.println("Comando inválido, tente novamente");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
                  System.out.println("Deseja tentar de novo ?Digite  SIM ou NAO, em seguida digite o local que deseja compactar ou descompactar");
                 String ans = input.nextLine().toUpperCase();
                 if (ans.equals("NAO")) {
                  System.out.println("Saindo do programa...");
                     repeat = false;
}               else if (!ans.equals("SIM")) {
                System.out.println("Entrada inválida, saindo do programa");
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
    public static void compress(FileInputStream reader, String name) throws IOException {
        /*Mapa has para armazenar sequencia de caracteres que virarao codigos  */
        HashMap<String, Integer> stringList = new HashMap<>();
        /* contador de string */

        int i;
	/* Inicializando a lista de string com os caracteres que esperamos  */
        for (i = 0; i < 256; i++) { 	/* ASC II  */
            stringList.put(Character.toString((char) i), i);
        }
                                                   /* para escrever bytes  representados no código  */
        FileOutputStream out = new FileOutputStream(new File(name));

                                                        /* usado para cada vez que um novo caracteres e lido*/
        StringBuilder prefix = new StringBuilder();

        int c;
        while ((c = reader.read()) != -1) {/*enquanto tem coisa pra ler*/
		
            String temp = prefix.toString() + (char) c;
		                                              /*constroi string atual + caractere lido */
            if (stringList.containsKey(temp)) {  /* se string temp estiver na lista */
                prefix.append((char) c); /* Junta o prefixo ao proximo primeiro caractere */
            } else {
                int code = stringList.get(prefix.toString());
                out.write(code);
                stringList.put(temp, i); /* add  o temp na stringList */
                i++; /* increment o index da String lista */

                prefix = new StringBuilder(Character.toString((char) c)); /* update  no prefix */
            }

            if (i >= 65536) {
                throw new IOException("Muitas strings únicas encontradas no texto, mapa não é grande o suficiente");
            }
        }

        if (!prefix.toString().equals("")) {
            int code = stringList.get(prefix.toString());
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

    public static void decompress(FileInputStream in, String filename) throws IOException {
        /* HashMap para armazenar o codigo e sua sequencia  */
        HashMap<Integer, String> codeList = new HashMap<>();
        int i; /* contador para A LISTA DE CODIGO */
/* iniciando a lista de codigos com codigos para caracteres que sabemos que estara no texto */
        for (i = 0; i < 256; i++) { 
            codeList.put(i, Character.toString((char) i));
        }

        StringBuilder output = new StringBuilder(); 
        int c = in.read();
      /* decomprimir   texto original */

        if (codeList.containsKey(c)) {
            output.append(codeList.get(c)); /*adicionando os caracteres correspondend a saida  */
        }

        int old = c;

        while ((c = in.read()) != -1) { /* enquanto tiver numeros, vai ler */
            String temp;  
            if (codeList.containsKey(c)) { /* se C esta na lsita  */
                temp = codeList.get(c);   /* add c  na saida  */
            } else if (c == i)  { /* SE NAO, deriva os caracteres correspondente  */
                temp = codeList.get(old) + codeList.get(old).charAt(0); /* obtem os caracteres do codigo antigo*/
            } else {
                throw new IOException("Erro na descompressão: código inválido");
            }

            output.append(temp); /* adicionando string de codigo antiga + primeiro caractere do novo codigo
            pois ja sabemos que essa string foi adiconada 
             */
            codeList.put(i, codeList.get(old) + temp.charAt(0)); 
            i++;
            //add temp + o primeiro caractere  de temp na nsaida*/
            old = c;

            if (i >= 65536) {
                throw new IOException("Muitas strings encontradas, mapa não é grande o suficiente");
            }
        }

        in.close();

        FileWriter write = new FileWriter(new File(filename));
        write.write(output.toString());
        write.flush();
        write.close();
        System.out.println("Arquivo descomprimido com sucesso!");
    }	 /*  Metodo para calcular quanto do arquivo foi comprimido.
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

        return (double) diff / initSize;
    }
}
