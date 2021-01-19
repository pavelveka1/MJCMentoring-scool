package com.epam.esm.dao;

public enum ModeOfSort {
	ASC("asc"), 
	DESC("desc");

	private String mode;

	private ModeOfSort(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

}
