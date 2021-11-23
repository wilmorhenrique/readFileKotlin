package pt.whd.readFile

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.stream.Collectors

/**
 * Hello world!
 *
 */

fun main(args: Array<String>) {
    readFile(args[0])
}

@Throws(IOException::class)
fun readFile(fileName: String?): String {
    val file = File(fileName)
    val br = BufferedReader(FileReader(file))
    val ids = StringBuffer()
    val pedidos: MutableList<Pedido> = ArrayList()
    val text:List<String> = br.readLines()
    for(fileLine in text){
        val fields = fileLine.split(";").toTypedArray()
        if (isValidLine(fields)) {
            ids.append(fields[0]).append(",")
            val pedido = Pedido(java.lang.Long.valueOf(fields[0]), fields[1], getDouble(fields[2]))
            pedidos.add(pedido)
        }
    }
    br.close()
    println("Resumo do ficheiro")
    println("\n")
    println("Total de Registos: " + pedidos.size)
    println("\n")
    val pedidosPorTipo = pedidos.stream().collect(Collectors.groupingBy(Pedido::estado))
    pedidosPorTipo.forEach { (k: String, v: List<Pedido>) ->
        println("$k:${v.size} soma:${v.stream().collect(Collectors.summingDouble(Pedido::valor))}")
    }
    println("\n")
    println("IDs: " + ids.substring(0, ids.length - 1))
    return ids.substring(0, ids.length - 1)
}

private fun getDouble(valor: String?): Double {
    if (valor == null) return 0.0
    return if (valor.isEmpty()) 0.0 else java.lang.Double.valueOf(valor.replace(",", "."))
}

private fun isValidLine(fields: Array<String>): Boolean  = fields.isNotEmpty() && isNumeric(fields[0])


private fun isNumeric(str: String): Boolean =
    str.matches(Regex("-?\\d+(\\.\\d+)?")) //match a number with optional '-' and decimal.
