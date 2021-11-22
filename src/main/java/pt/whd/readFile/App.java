package pt.whd.readFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws IOException {
		// get the downloaded and unziped file
		File file = new File(args[0]); 

		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		StringBuffer ids = new StringBuffer();

		List<Pedido> pedidos = new ArrayList<Pedido>();
		String fileLine; 
		while ((fileLine = br.readLine()) != null) {
			
			String[] fields = fileLine.split(";");
			if ( isValidLine(fields)) {
				ids.append(fields[0]).append(",");
				Pedido pedido = new Pedido(Long.valueOf(fields[0]), fields[1], getDouble(fields[2]));
				pedidos.add(pedido);
			}
		}	
		br.close();
		
		System.out.println("Resumo do ficheiro");
		System.out.println("\n");
		System.out.println("Total de Registos: " + pedidos.size());
		System.out.println("\n");
		
		Map<String, List<Pedido>> pedidosPorTipo = pedidos.stream().
				  collect(Collectors.groupingBy(Pedido::getEstado));
		
		
		pedidosPorTipo.forEach((k, v) -> System.out.println(("\n" + k + ":" + v.size() + " soma:" + v.stream().collect(Collectors.summingDouble(Pedido::getValor))) ));
		System.out.println("\n");
		
		System.out.println("IDs: " + ids.substring(0, ids.length()-1));
	}

	private static Double getDouble(String valor) {
		if (valor == null) return 0d;
		if (valor.isBlank()) return 0d;
		return Double.valueOf(valor.replace(",", "."));
	}

	private static boolean isValidLine(String[] fields) {
		return fields.length > 0 && isNumeric(fields[0]);
	}
	
	
	public static boolean isNumeric(String str) {
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}





//ID_PEDIDO;ESTADO;MONTANTE;QTD_DIAS;MOTIVO
//1301;DEFERIDO;585,12;16;;
