package com.playlist.model.common;
public enum ErrorCategory {

	APPLICATION("Application/Service specific error."), SYSTEM(
			"System/Container specific error."), REQUEST(
			"Request/Transaction specific error."), DATA("Data specific error.");

	private final String description;


	private ErrorCategory(String description) {
		this.description = description;
	}

}
