package pt.whd.readFile;

public class Pedido {

	private Long id;
	private String estado;
	private Double valor;
	
	

	public Pedido(Long id, String estado, Double valor) {
		super();
		this.id = id;
		this.estado = estado;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public String getEstado() {
		return estado;
	}

	public Double getValor() {
		return valor;
	}

}
