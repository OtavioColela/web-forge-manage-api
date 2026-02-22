package webforge.row_manage_api.validator;


import org.springframework.stereotype.Component;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.exception.BadRequestException;
import webforge.row_manage_api.model.UserEntity;

import static io.micrometer.common.util.StringUtils.isEmpty;
import static java.util.regex.Pattern.matches;
import static webforge.row_manage_api.validator.FieldValidator.validateRequiredField;

@Component
public class UserValidator {


    //TODO: adding regex on the one Utils


    public static void validateName(String name) {
        if (isEmpty(name)) {
            validateRequiredField(name, "Nome");
        }

        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new BadRequestException("O nome deve conter apenas letras e espaços.");
        }
    }

    public static void validateEmail(String email) {
        if (isEmpty(email)) {
            validateRequiredField(email, "E-mail");
        }

        var emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!matches(emailRegex, email)) {
            throw new BadRequestException("Formato de e-mail inválido.");
        }

        if (!email.endsWith("@gmail.com")) {
            throw new BadRequestException("Apenas contas do Gmail são permitidas.");
        }
    }

    public static void validatePassword(String password) {
        if (isEmpty(password)) {
            validateRequiredField(password, "Senha");
        }

        var passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        if (!matches(passwordRegex, password)) {
            throw new BadRequestException(
                    "A senha deve ter no mínimo 8 caracteres e conter pelo menos uma letra, um número e um caractere especial."
            );
        }
    }


    public static void validateFieldsUserRequest(UserRequest userRequest) {
        validateName(userRequest.getName());
        validateEmail(userRequest.getEmail());
        validatePassword(userRequest.getPassword());
    }

    public static void validateFields(UserEntity userEntity) {
        validateName(userEntity.getName());
        validateEmail(userEntity.getEmail());
        validatePassword(userEntity.getPassword());
    }


}

