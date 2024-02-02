package br.unb.scrap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UrlUtilsTest {

	@Test
	public void testBoostArchivesBaseUrl() {
		assertEquals("https://lists.boost.org/Archives/boost/", UrlUtils.BOOST_ARCHIVES_BASE_URL);
	}

	@Test
	public void testOpenJdkMailingListBaseUrl() {
		assertEquals("https://mail.openjdk.org/pipermail/hotspot-dev/", UrlUtils.OPENJDK_MAILING_LIST_BASE_URL);
	}

	@Test
	public void testPythonListMailingListBaseUrl() {
		assertEquals("https://mail.python.org/pipermail/python-list/", UrlUtils.PYTHON_LIST_MAILING_LIST_BASE_URL);
	}

	@Test
	public void testJavaMailArchiveBaseUrl() {
		assertEquals("https://download.oracle.com/javaee-archive/javamail.java.net/dev/",
				UrlUtils.JAVA_MAIL_ARCHIVE_BASE_URL);
	}
}
