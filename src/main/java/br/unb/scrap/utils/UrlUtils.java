package br.unb.scrap.utils;

/**
 * Classe utilitária para fornecer URLs relacionadas a listas de discussão.
 */
public final class UrlUtils {

	/** URL base para os arquivos de lista de discussão do Boost. */
	public static final String BOOST_ARCHIVES_BASE_URL = "https://lists.boost.org/Archives/boost/";

	/** URL base para a lista de discussão hotspot-dev do OpenJDK. */
	public static final String OPENJDK_MAILING_LIST_BASE_URL = "https://mail.openjdk.org/pipermail/hotspot-dev/";

	/** URL base para a lista de discussão python-list do Python. */
	public static final String PYTHON_LIST_MAILING_LIST_BASE_URL = "https://mail.python.org/pipermail/python-list/";

	/** URL base para o arquivo de lista de discussão javamail-dev do JavaMail. */
	public static final String JAVA_MAIL_ARCHIVE_BASE_URL = "https://download.oracle.com/javaee-archive/javamail.java.net/dev/";

	// Impede que a classe seja instancializada
	private UrlUtils() {
		throw new AssertionError("Esta classe não deve ser instanciada");
	}
}
