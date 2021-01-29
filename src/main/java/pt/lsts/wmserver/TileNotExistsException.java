package pt.lsts.wmserver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "tile not found")
public class TileNotExistsException extends RuntimeException {
    private static final long serialVersionUID = -5822775108794064527L;    
}