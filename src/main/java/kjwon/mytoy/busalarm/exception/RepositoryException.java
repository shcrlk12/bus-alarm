package kjwon.mytoy.busalarm.exception;

import lombok.AllArgsConstructor;

public class RepositoryException extends RuntimeException{
    int status;

    public RepositoryException(int status, String message){
        super(message);

        this.status = status;
    }
}
