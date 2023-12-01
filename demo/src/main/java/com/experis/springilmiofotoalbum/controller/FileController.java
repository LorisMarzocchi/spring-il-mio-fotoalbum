package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.exception.PhotoNotFoundException;
import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/*senza un attributo @CrossOrigin specificato nel controller è possibile a causa di come funziona la policy di Same-Origin e Cross-Origin Resource Sharing (CORS) nei browser.

Same-Origin Policy: La Same-Origin Policy è una misura di sicurezza implementata nei browser web. Impedisce a uno script eseguito in una pagina web di fare richieste verso un dominio, protocollo o porta diversi da quelli della pagina originale. Tuttavia, questa policy non si applica alle richieste di "get" di risorse come immagini, CSS o script, che sono considerate "safe" poiché non possono alterare lo stato del server e sono quindi esenti da tale restrizione.

CORS (Cross-Origin Resource Sharing): CORS è un meccanismo che permette a molte risorse su una pagina web (ad esempio, font, richieste AJAX, ecc.) di essere richieste da un dominio diverso da quello da cui è stata servita la risorsa originale. È una misura di sicurezza che gestisce come le richieste verso risorse su un dominio diverso dovrebbero essere trattate.

Nel tuo caso, quando il browser carica un'immagine usando un tag <img src="...">, non è soggetto alle restrizioni di CORS perché si tratta di una richiesta "safe" che non può modificare lo stato del server. Perciò, anche se il controller Spring Boot non ha l'annotazione @CrossOrigin, il browser può ancora caricare l'immagine.
*/

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    private PhotoService photoService;

    @GetMapping("cover/{photoId}")
    public ResponseEntity<byte[]> serveCover(@PathVariable Integer photoId) {

        try {
            Photo photo = photoService.getPhotoById(photoId);
            byte[] coverBytes = photo.getCover();
            if (coverBytes != null && coverBytes.length > 0) {

                MediaType mediaType = getMediaTypeForByteArray(coverBytes);

                return ResponseEntity.ok().contentType(mediaType).body(coverBytes);
            } else {
                return ResponseEntity.notFound().build();

            }
        } catch (PhotoNotFoundException e) {
            return ResponseEntity.notFound().build();

        }
    }

    private MediaType getMediaTypeForByteArray(byte[] array) {
        // determinare il tipo di file in base ai primi byte
        String fileHeader = bytesToHex(array, 8);
        if (fileHeader.startsWith("89504E47")) {
            return MediaType.IMAGE_PNG;
        } else if (fileHeader.startsWith("47494638")) {
            return MediaType.IMAGE_GIF;
        } else if (fileHeader.startsWith("FFD8FF")) {
            return MediaType.IMAGE_JPEG;
        }
        return MediaType.APPLICATION_OCTET_STREAM; // tipo di media generico se non riconosciuto
    }

    private String bytesToHex(byte[] bytes, int bLength) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bLength && i < bytes.length; i++) {
            builder.append(String.format("%02X", bytes[i]));

        }
        return builder.toString();
    }
}



