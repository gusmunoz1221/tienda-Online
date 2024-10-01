package com.example.E_Commerce.domain.helpers;

import com.example.E_Commerce.domain.entities.PedidoEntity;
import com.example.E_Commerce.domain.entities.ProductoEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CorreoElectronicoHelper {

    private final JavaMailSender correoRemitente;

    public  void enviarCorreoCancelacionDeCuenta(String destinatario, String nombre){

        MimeMessage mensaje = correoRemitente.createMimeMessage();
        String contenidoHtml = leerHtmlCancelacionCuenta(nombre);

        try {
            mensaje.setFrom(new InternetAddress("gustavito12217@gmail.com"));//correo desde dominio
            mensaje.setRecipients(MimeMessage.RecipientType.TO,destinatario); //destino
            mensaje.setSubject("Cancelacion de la cuenta");
            mensaje.setContent(contenidoHtml,"text/html; charset=utf-8");//seteo html-> cuerpo;
            correoRemitente.send(mensaje);
        }catch (MessagingException e){
            throw new RuntimeException("error al procesar el correo");
        }
    }

    public  void enviarCorreoCompraDeProducto(String destinatario,
                                              String nombre,
                                              List<ProductoEntity> productos,
                                              PedidoEntity pedido,
                                              String numeroDePedido){

        MimeMessage mensaje = correoRemitente.createMimeMessage();
        String contenidoHtml = leerHtmlProductosComprado(nombre,productos,pedido,numeroDePedido);

        try {
            mensaje.setFrom(new InternetAddress("gustavito12217@gmail.com"));//correo desde dominio
            mensaje.setRecipients(MimeMessage.RecipientType.TO,destinatario); //destino
            mensaje.setSubject("Cancelacion de la cuenta");
            mensaje.setContent(contenidoHtml,"text/html; charset=utf-8");//seteo html-> cuerpo;
            correoRemitente.send(mensaje);
        }catch (MessagingException e){
            throw new RuntimeException("error al procesar el correo");
        }
    }

    private String leerHtmlCancelacionCuenta(String nombre){
        try(var lineas = Files.lines(RUTA_CORREO_CANCELACION)){
            String html = lineas.collect(Collectors.joining());//lee linea por linea y concatena como strinng
            return html.replace("{nombre}",nombre);
        }catch (IOException e){
            throw new RuntimeException();
        }
    }


    private String leerHtmlProductosComprado (String nombre,
                                              List<ProductoEntity> productos,
                                              PedidoEntity pedido,
                                              String numeroDePedido){

        String productosEnHtml = reemplazarProductosEnHtml(productos); //remplazo los productos en html

        try(var lineas = Files.lines(RUTA_CONFIRMACION_COMPRA_PRODUCOS)){

            String html = lineas.collect(Collectors.joining());//lee linea por linea y concatena como string

            return html.replace("{nombre}",nombre)
                    .replace("{numeroPedido}",numeroDePedido)
                    .replace("{fecha}",pedido.getFecha().toString())
                    .replace("{productos}",productosEnHtml);
        }catch (IOException e){
            throw new RuntimeException();
        }
    }


    private String reemplazarProductosEnHtml(List<ProductoEntity> productos) {
        StringBuilder listaProductos = new StringBuilder();

        for (ProductoEntity producto : productos) {
            listaProductos.append("<li>");
            listaProductos.append("<strong>").append(producto.getNombre()).append("</strong>: ");
            listaProductos.append("Precio: ").append(producto.getPrecio()).append(" - ");
            listaProductos.append(producto.getDescripcion());
            listaProductos.append("</li>");
        }

        return listaProductos.toString();
    }


    public boolean esValidoCorreoElectornico(String correo){

        String exprecionRegularCorreo = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern patron = Pattern.compile(exprecionRegularCorreo );
        return patron.matcher(correo).matches();
    }

    private final Path RUTA_CORREO_CANCELACION= Paths.get("src/main/resources/correo/correo_cancelacion_cuenta.html");
    private final Path RUTA_CONFIRMACION_COMPRA_PRODUCOS= Paths.get("rc/main/resources/correo/correo_compra_productos.html");
}
