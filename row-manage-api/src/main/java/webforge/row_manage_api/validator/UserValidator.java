package webforge.row_manage_api.validator;


import org.springframework.stereotype.Component;
import webforge.row_manage_api.dto.user.UserRequest;
import webforge.row_manage_api.exception.BadRequestException;

@Component
public class UserValidator {
    public static void validateUser(UserRequest userRequest){

        if(userRequest.getCpf().isBlank()){
            throw new BadRequestException("O número de CPF não pode estar vazio");
        }
        if(userRequest.getName().isBlank()){
            throw new BadRequestException("O nome não pode estar vazio");
        } else if (!userRequest.getName().matches("^[a-zA-Z\\s]+$")){
            throw new BadRequestException("O nome só deve conter letras e espaços.");
        }
        if(userRequest.getSchool().isBlank()){
            throw new BadRequestException("Selecione uma escola!");
        }
        if(userRequest.getPassword().isBlank()){
            throw new BadRequestException("Digite uma senha");
        }
        if(userRequest.getEmail().isBlank()){
            throw new BadRequestException("Digite um email");
        }
        if(!(userRequest.getEmail().endsWith("@gmail.com")
                || userRequest.getEmail().endsWith("@yahoo.com")
                || userRequest.getEmail().endsWith("@outlook.com")
                || userRequest.getEmail().endsWith("@hotmail.com"))){

            throw new BadRequestException("Digite um email válido");
        }
    }

}
