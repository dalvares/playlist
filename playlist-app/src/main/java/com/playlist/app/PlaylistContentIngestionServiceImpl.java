package com.playlist.app;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.playlist.api.PlaylistContentIngestionService;
import com.playlist.api.validators.Validator;
import com.playlist.manager.PlaylistManager;
import com.playlist.model.ContentMetaData;
import com.playlist.model.PlaylistResponse;
import com.playlist.model.common.ErrorCategory;
import com.playlist.model.common.ErrorCode;
import com.playlist.model.common.ErrorUtil;
import com.playlist.model.exception.PlaylistCustomException;
import com.playlist.model.exception.PlaylistInvalidEntityException;
import static com.playlist.app.ErrorConstant.CONTENT_NAME_CANNOT_BE_NULL;
import static com.playlist.app.ErrorConstant.COUNTRY_CODE_CANNOT_BE_NULL;

@RestController
public class PlaylistContentIngestionServiceImpl implements PlaylistContentIngestionService {

	@Resource
	PlaylistManager manager;

	public ResponseEntity<Object> uploadContentMetaData(@RequestBody ContentMetaData metaData) {
		// Validate Request
		try {
			Validator.validate(metaData);
		} catch (PlaylistInvalidEntityException e) {
			return ResponseEntity.badRequest().body(PlaylistResponse.newBuilder().addError(e.getErrors()).build());
		}
		if (metaData == null) {
			return ResponseEntity.badRequest().build();
		}
		// Call Save function
		try {
			manager.save(metaData);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(PlaylistResponse.<ContentMetaData>newBuilder().addError(ErrorUtil.createError(e)).build());
		}
		// return response
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<PlaylistResponse<ContentMetaData>> retrieveAllContentMetaData() {
		ContentMetaData result = null;
		try {
			result = manager.retrieveAllContentMetaData();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(PlaylistResponse.<ContentMetaData>newBuilder().addError(ErrorUtil.createError(e)).build());
		}
		// assuming OK status for all other scenarios , even for no records found in DB
		return ResponseEntity.ok(PlaylistResponse.<ContentMetaData>newBuilder().result(result).build());
	}

	public ResponseEntity<PlaylistResponse<List<List<String>>>> getPlayList(@Valid @NotNull String contentName,
			@Valid @NotNull String countryCode) {
		if (StringUtils.isEmpty(contentName)) {
			return ResponseEntity.badRequest()
					.body(PlaylistResponse.<List<List<String>>>newBuilder()
							.addError(new com.playlist.model.common.Error(ErrorCode.BAD_REQUEST.name(),
									CONTENT_NAME_CANNOT_BE_NULL, ErrorCategory.DATA))
							.build());
		}
		if (StringUtils.isEmpty(countryCode)) {
			return ResponseEntity.badRequest()
					.body(PlaylistResponse.<List<List<String>>>newBuilder()
							.addError(new com.playlist.model.common.Error(ErrorCode.BAD_REQUEST.name(),
									COUNTRY_CODE_CANNOT_BE_NULL, ErrorCategory.DATA))
							.build());
		}

		PlaylistResponse.Builder<List<List<String>>> resultBuilder = PlaylistResponse.newBuilder();

		try {
			resultBuilder.result(manager.getPlayList(contentName, countryCode));

		} catch (PlaylistCustomException pce) {
			return ResponseEntity.status(pce.getStatus())
					.body(resultBuilder.addError(ErrorUtil.createError(pce)).build());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(PlaylistResponse.<List<List<String>>>newBuilder()
							.addError(new com.playlist.model.common.Error(ErrorCode.UNKNOW_ERROR.name(), e.getMessage(),
									ErrorCategory.APPLICATION))
							.build());
		}
		return ResponseEntity.ok(resultBuilder.build());
	}

}
