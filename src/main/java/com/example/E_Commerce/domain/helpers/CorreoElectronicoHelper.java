package com.example.E_Commerce.domain.helpers;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CorreoElectronicoHelper {
    public boolean esValidoCorreoElectornico(String correo){

        String exprecionRegularCorreo = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern patron = Pattern.compile(exprecionRegularCorreo );
        return patron.matcher(correo).matches();
    }
}
