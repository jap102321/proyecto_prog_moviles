package com.example.proyecto_poli

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient

class WebView: AppCompatActivity() {

    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)

        webView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Configuración adicional para Android 12
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE)
        webSettings.setDomStorageEnabled(true)

        webView.loadUrl("https://luisdavidmedinagaviria9903.github.io/images-storage/")
        //webView.loadData("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"><link href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\" rel=\"stylesheet\"><title>Bootstrap Example</title><script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"></script></head><body class=\"p-3 m-0 border-0 bd-example m-0 border-0\"><div id=\"carouselExample\" class=\"carousel slide\" style=\"width:100%\"><div class=\"carousel-inner\"><div class=\"carousel-item active\" style=\"width:100%\"><img src=\"https://luisdavidmedinagaviria9903.github.io/images-storage/robot_banner.jpeg\" alt=\"FIRTS SLIDE\" style=\"width:100%;height:100%\"></div><div class=\"carousel-item\"><img src=\"https://luisdavidmedinagaviria9903.github.io/images-storage/play2.jpeg\" alt=\"FIRTS SLIDE\" style=\"width:100%;height:100%\"></div><div class=\"carousel-item\"><img src=\"https://luisdavidmedinagaviria9903.github.io/images-storage/reader.jpeg\" alt=\"FIRTS SLIDE\" style=\"width:100%;height:100%\"></div></div><button class=\"carousel-control-prev\" type=\"button\" data-bs-target=\"#carouselExample\" data-bs-slide=\"prev\"><span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span><span class=\"visually-hidden\">Previous</span></button><button class=\"carousel-control-next\" type=\"button\" data-bs-target=\"#carouselExample\" data-bs-slide=\"next\"><span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span><span class=\"visually-hidden\">Next</span></button></div><div><h1 style=\"color:#4a535c;font-size:2rem\">1X Ha desarrollado un humanoide.</h1><p style=\"font-size:1rem;color:#999fa8\">1X ha desarrollado un robot tan humanoide que ha comenzado a probarlo en el mundo real: Asi es neo Beta</p></div><div><img src=\"https://luisdavidmedinagaviria9903.github.io/images-storage/icon_avatar.jpeg\" style=\"border-radius:50%\"><span style=\"font-size:1rem;color:#4a535c;margin:0 auto\"><b>Luis Medina</b></span></div><div><div style=\"width:100%;border:solid 1px #4a535c;display:flex;align-items:center;justify-content:center;margin:1rem auto\"><div style=\"width:60%\"><h1 style=\"color:#4a535c;font-size:2rem\">Videojuegos</h1><p style=\"font-size:1rem;color:#999fa8\">La PS2 ha vuelto a hacer historia: despues de 24 años llego al registro del patrimonio Tecnologico del futuro de japon<br>Se trata de la primera consola de videojuegos del registro.<a href=\"#\">Leer mas >></a></p></div><div style=\"width:40%;height:90%\"><img src=\"https://luisdavidmedinagaviria9903.github.io/images-storage/play2.jpeg\" style=\"width:100%;height:100%\"></div></div></div></body></html>", "text/html", "UTF-8");
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}