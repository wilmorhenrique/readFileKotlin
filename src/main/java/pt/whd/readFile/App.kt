package pt.whd.readFile

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.math.BigDecimal


fun main(args: Array<String>) {
    readFile(args[0])
}


fun readFile(fileName: String): String {
    val br = BufferedReader(FileReader(File(fileName)))


    val pedidos: MutableList<Pedido> = extractFields(br)
    br.close()
    println("Resumo do ficheiro")
    println("\n")
    println("Total de Registos: " + pedidos.size)
    println("\n")

    val ids = getIDs(pedidos)

    val pedidosPorTipo = pedidos.groupBy { it.estado }
    pedidosPorTipo.forEach() { (k: String, v: List<Pedido>) ->
          val soma = v.sumOf { it.valor }
          println("$k:${v.size} soma:${soma}")
    }

    println("\n")
    println("IDs: $ids")
    return ids
}

private fun getIDs(pedidos: MutableList<Pedido>) = pedidos.joinToString(",") { it.id.toString() }


private fun extractFields(br: BufferedReader): MutableList<Pedido> {
    val pedidos: MutableList<Pedido> = ArrayList()
    val text: List<String> = br.readLines()
    for (fileLine in text) {
        val fields = fileLine.split(";").toTypedArray()
        if (isValidLine(fields)) {
            val pedido = Pedido(fields[0].toLong(), fields[1], getValor(fields[2]))
            pedidos.add(pedido)
        }
    }
    return pedidos
}

private fun getValor(valor: String?): BigDecimal {
    if (valor == null) return BigDecimal(0)
    return if (valor.isEmpty())  BigDecimal(0) else  BigDecimal(valor.replace(",", "."))
}

private fun isValidLine(fields: Array<String>): Boolean  = fields.isNotEmpty() && isNumeric(fields[0])


private fun isNumeric(str: String): Boolean =
    str.matches(Regex("-?\\d+(\\.\\d+)?")) //match a number with optional '-' and decimal.
