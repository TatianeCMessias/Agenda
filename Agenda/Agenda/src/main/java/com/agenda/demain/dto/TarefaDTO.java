package com.agenda.demain.dto;

public class TarefaDTO {
	
	private Long id;
	private String titulo;
    private String descricao;
    private Boolean concluida;

	public TarefaDTO() {
    }

    public TarefaDTO(String titulo, String descricao, Boolean concluida) {
    	this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }
}