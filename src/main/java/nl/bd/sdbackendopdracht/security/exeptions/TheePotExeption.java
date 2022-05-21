package nl.bd.sdbackendopdracht.security.exeptions;

import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "Any attempt to brew coffee with a teapot should result in this error")
public class TheePotExeption extends RuntimeException{
    public TheePotExeption() {
        super();
    }

}
