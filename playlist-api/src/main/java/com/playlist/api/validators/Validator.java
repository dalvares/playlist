package com.playlist.api.validators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import com.playlist.model.common.ErrorCategory;
import com.playlist.model.common.ErrorCode;
import com.playlist.model.exception.PlaylistInvalidEntityException;

public class Validator {
	private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	public static final javax.validation.Validator validator = factory.getValidator();

	public static <T> T validate(final T object) {
		final Set<ConstraintViolation<T>> violations = validator.<T>validate(object);
		if (!violations.isEmpty()) {
			final PlaylistInvalidEntityException.Builder exceptionBuilder = PlaylistInvalidEntityException.newBuilder();

			for (ConstraintViolation<T> v : violations) {
				exceptionBuilder.addError(new com.playlist.model.common.Error(ErrorCode.MISSING_ATTRIBUTE.name(),
						v.getPropertyPath().toString() + " " + v.getMessage(), ErrorCategory.REQUEST));
			}

			PlaylistInvalidEntityException ex = exceptionBuilder.build();
			throw ex;
		}
		return object;
	}
}
