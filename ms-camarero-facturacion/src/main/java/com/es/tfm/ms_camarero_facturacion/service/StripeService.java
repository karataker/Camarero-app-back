package com.es.tfm.ms_camarero_facturacion.service;

import com.es.tfm.ms_camarero_facturacion.model.Factura;
import com.es.tfm.ms_camarero_facturacion.model.LineaFactura;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StripeService {

    private final String successUrl;
    private final String cancelUrl;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public StripeService(
            @Value("${stripe.success-url}") String successUrl,
            @Value("${stripe.cancel-url}") String cancelUrl
    ) {
        this.successUrl = successUrl;
        this.cancelUrl = cancelUrl;
    }

    public String crearSesionPago(Factura factura) throws StripeException {
        if (factura.getLineas() == null || factura.getLineas().isEmpty()) {
            throw new IllegalArgumentException("La factura no contiene líneas de productos");
        }

        // ⚠️ Importante: Asignar la clave justo antes de crear la sesión
        com.stripe.Stripe.apiKey = stripeSecretKey;

        List<SessionCreateParams.LineItem> lineItems = factura.getLineas().stream().map(linea ->
                SessionCreateParams.LineItem.builder()
                        .setQuantity((long) linea.getCantidad())
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("eur")
                                        .setUnitAmount((long) (linea.getPrecio() * 100))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(linea.getNombre())
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
        ).toList();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);
        return session.getId();
    }
}