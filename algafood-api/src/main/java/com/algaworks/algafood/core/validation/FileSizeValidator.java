package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile>{
	
	// DataSize é uma classe do spring, representa um tamanho de dados
	private DataSize maxSize;
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		// transformando uma string em um DataSize, fica mais facil de validar tamanhos de dados
	 this.maxSize = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile arquivo, ConstraintValidatorContext context) {
		
		return arquivo == null || arquivo.getSize() <= this.maxSize.toBytes();
	}

}
