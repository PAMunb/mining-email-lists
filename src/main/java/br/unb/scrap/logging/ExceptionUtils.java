package br.unb.scrap.logging;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe utilitária para lidar com exceções, logando e registrando-as.
 */
public class ExceptionUtils {

    private static final Logger logger = LogManager.getLogger(ExceptionUtils.class);
    private static final FileLogger fileLogger;

    static {
        try {
            fileLogger = new FileLogger("scrap_log.txt");
        } catch (IOException e) {
            throw new RuntimeException("Error initializing FileLogger", e);
        }
    }

    /**
     * Método auxiliar para lidar com exceções, logando e registrando-as.
     *
     * @param message a mensagem de erro
     * @param e       a exceção ocorrida
     * @param url     a URL relacionada à exceção
     */
    public static void handleException(String message, Exception e, String url) {
        logger.error(message, e);
        fileLogger.logException(message, url, e);
    }
}
