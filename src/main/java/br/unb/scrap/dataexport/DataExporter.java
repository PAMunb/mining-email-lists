package br.unb.scrap.dataexport;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

import br.unb.scrap.logging.ExceptionUtils;

/**
 * Classe para exportar dados de um banco de dados para um arquivo CSV.
 */
@Component
public class DataExporter {

    @Autowired
    private DataSource dataSource;

    private static final Logger logger = LogManager.getLogger(DataExporter.class);
    private static final String CSV_FILE_PATH = "/home/alanapaula/desenvolvimento/projetos/rejuvenescimentoDeSoftware/workspace/sql/CSV/dadosCsv.csv";

    /**
     * Exporta os dados do banco de dados para um arquivo CSV.
     */
    public void exportData() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                logger.info("Conexão estabelecida com sucesso.");

                String query = "SELECT * FROM Post";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    exportToCSV(resultSet);
                }
            }
        } catch (SQLException e) {
            String message = "Erro ao exportar dados para CSV";
            String url = "Não especificado";
            ExceptionUtils.handleException(message, e, url);
            logger.error(message, e);
        }
    }

    /**
     * Exporta os dados do ResultSet para um arquivo CSV.
     *
     * @param resultSet o ResultSet contendo os dados a serem exportados
     */
    private void exportToCSV(ResultSet resultSet) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
            writer.writeAll(resultSet, true);
            logger.info("Dados exportados para o arquivo CSV com sucesso.");
        } catch (IOException | SQLException e) {
            String message = "Erro ao exportar para CSV";
            String url = "Não especificado";
            ExceptionUtils.handleException(message, e, url);
            logger.error(message, e);
        }
    }

    /**
     * Gera a documentação da classe DataExporter.
     *
     * @return a documentação da classe
     */
    public static String generateDocumentation() {
        return "Classe para exportar dados de um banco de dados para um arquivo CSV.";
    }
}
