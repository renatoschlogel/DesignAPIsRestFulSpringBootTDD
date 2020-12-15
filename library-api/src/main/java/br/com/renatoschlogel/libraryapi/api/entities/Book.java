package br.com.renatoschlogel.libraryapi.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	private Long id;
	private String title;
	private String author;
	private String isbn;
		
}
