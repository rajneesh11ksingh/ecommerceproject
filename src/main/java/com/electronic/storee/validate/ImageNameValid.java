package com.electronic.storee.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=ImageNameValidator.class)
public @interface ImageNameValid {
         
    //error message
	String message() default "{javax.validation.constraints.NotBlank.message}";
    
	//represent group of constraints
	Class<?>[] groups() default { };
    
	//additional information about annotation
	Class<? extends Payload>[] payload() default { };
	
}
