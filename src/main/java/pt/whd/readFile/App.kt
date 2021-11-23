package pt.whd.readFile

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.stream.Collectors


fun main(args: Array<String>) {
    readFile(args[0])
}


fun readFile(fileName: String): String {
    val file = File(fileName)
    val br = BufferedReader(FileReader(file))
    val ids = StringBuffer()

    val pedidos: MutableList<Pedido> = extractFields(br)
    br.close()
    println("Resumo do ficheiro")
    println("\n")
    println("Total de Registos: " + pedidos.size)
    println("\n")

    pedidos.forEach {
        ids.append(it.id).append(",")
    }

    val pedidosPorTipo = pedidos.stream().collect(Collectors.groupingBy(Pedido::estado))

    pedidosPorTipo.forEach { (k: String, v: List<Pedido>) ->
        println("$k:${v.size} soma:${v.stream().collect(Collectors.summingDouble(Pedido::valor))}")

    }

    println("\n")
    println("IDs: " + ids.substring(0, ids.length - 1))
    return ids.substring(0, ids.length - 1)
}

private fun extractFields(br: BufferedReader): MutableList<Pedido> {
    val pedidos: MutableList<Pedido> = ArrayList()
    val text: List<String> = br.readLines()
    for (fileLine in text) {
        val fields = fileLine.split(";").toTypedArray()
        if (isValidLine(fields)) {
            val pedido = Pedido(fields[0].toLong(), fields[1], getDouble(fields[2]))
            pedidos.add(pedido)
        }
    }
    return pedidos
}

private fun getDouble(valor: String?): Double {
    if (valor == null) return 0.0
    return if (valor.isEmpty()) 0.0 else java.lang.Double.valueOf(valor.replace(",", "."))
}

private fun isValidLine(fields: Array<String>): Boolean  = fields.isNotEmpty() && isNumeric(fields[0])


private fun isNumeric(str: String): Boolean =
    str.matches(Regex("-?\\d+(\\.\\d+)?")) //match a number with optional '-' and decimal.
