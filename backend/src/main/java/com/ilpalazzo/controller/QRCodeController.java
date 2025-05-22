package com.ilpalazzo.controller;

import com.google.zxing.WriterException;
import com.ilpalazzo.service.QRCodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @Value("${app.frontend.url:http://localhost:3000/order}")
    private String frontendBaseUrl;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping(value = "/{tableId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String tableId) throws IOException, WriterException {
        String url = frontendBaseUrl + "?tableId=" + tableId;
        byte[] qrImage = qrCodeService.generateQRCodeImage(url, 250, 250);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
    }
}
