package webforge.row_manage_api.validator;
import org.springframework.stereotype.Component;
import webforge.row_manage_api.exception.BadRequestException;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class FieldValidator {

    public static void validateRequiredField(Object fieldValue, String fieldName) {
        if (fieldValue == null || (fieldValue instanceof String && isEmpty((String) fieldValue))) {
            throw new BadRequestException(fieldName + " is a required field.");
        }
    }
}
