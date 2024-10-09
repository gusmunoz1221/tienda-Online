package com.example.E_Commerce.infraestructura.Uils;

import com.example.E_Commerce.domain.entities.SecuenciaPedidoEntity;
import com.example.E_Commerce.domain.repositories.SecuenciaPedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializacionDeDatos implements CommandLineRunner {

    private final SecuenciaPedidoRepository secuenciaPedidoRepository;

    public InicializacionDeDatos(SecuenciaPedidoRepository secuenciaPedidoRepository) {
        this.secuenciaPedidoRepository = secuenciaPedidoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        inicializarSecuenciaPedido();
    }

    private void inicializarSecuenciaPedido() {
        if (!secuenciaPedidoRepository.existsById(1L)) {
            SecuenciaPedidoEntity secuencia = new SecuenciaPedidoEntity();
            secuencia.setUltimoNumero(1000); // Comienza desde cero o el número que prefieras
            secuenciaPedidoRepository.save(secuencia);
        }
    }
}
