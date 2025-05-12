package database;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

import static database.ExportFormat.*;

enum ExportFormat {
    JSON, XML, CSV
}

enum EntityType {
    CUSTOMER, READING
}

@CommandLine.Command(name = "data-cli", mixinStandardHelpOptions = true, version = "1.0",
        description = "CLI für Import/Export")
public class DataCli implements Callable<Integer> {

    @CommandLine.Command(name = "export", description = "Export files in selected format")
    public Integer export(@CommandLine.Option(names = {"-f", "--format"}, description = "Use the formats json, xml, csv",
            required = true) ExportFormat format,
                          @CommandLine.Option(names = {"-o", "--output"}, description = "Outputfile",
                                  required = true) File outputFile,
                          @CommandLine.Option(names = {"-F", "--filter"}, description = "Filter",
                                  required = false) String filter,
                          @CommandLine.Option(names = {"-t", "--type"}, description = "Specify Reading or Customer",
                                  required = true) EntityType type) {
        return switch (format) {
            case JSON -> exportType(type, JSON, outputFile);
            case XML -> exportType(type, XML, outputFile);
            case CSV -> exportType(type, CSV, outputFile);
            default -> {
                System.out.println("Invalid format");
                yield 1;
            }
        };
    }

    private Integer exportType(EntityType type, ExportFormat format, File outputfile) {
        return switch (type) {
            case CUSTOMER -> exportCustomer(format, outputfile);
            case READING -> exportReading(format, outputfile);
            default -> {
                System.out.println("Invalid type");
                yield 1;
            }
        };
    }


    private Integer exportCustomer(ExportFormat format, File outputfile) {
        return switch (format) {
            case JSON -> {
                ImportExportParser.exportJSONCustomer(outputfile);
                yield 0;
            }
            case XML -> {
                ImportExportParser.exportXMLCustomer();
                yield 0;
            }
            case CSV -> {
                ImportExportParser.exportCSVCustomer();
                yield 0;
            }
            default -> {
                System.out.println("Invalid format for customer export");
                yield 1;
            }
        };
    }

    private Integer exportReading(ExportFormat format, File outputfile) {
        return switch (format) {
            case JSON -> {
                ImportExportParser.exportJSONReading();
                yield 0;
            }
            case XML -> {
                ImportExportParser.exportXMLReading();
                yield 0;
            }
            case CSV -> {
                ImportExportParser.exportCSVReading();
                yield 0;
            }
            default -> {
                System.out.println("Invalid format for reading export");
                yield 1;
            }
        };
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("invalid command: Enter Import or Export as command");
        return 1;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new DataCli()).execute(args);
        System.exit(exitCode);
    }
}
