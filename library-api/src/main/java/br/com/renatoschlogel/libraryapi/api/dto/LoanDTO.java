package br.com.renatoschlogel.libraryapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

	private Long id;
	
	private String isbn;
	
	private String custumer;
	
	private BookDTO book;
	
}
