package com.adanext.NoPainNoMain.service.query;

import com.adanext.NoPainNoMain.domain.Booking;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


@Service
public class BookingExcelExport {

    private static final String[] HEADERS = {
        "ID Reserva", "Estudiante", "Documento", "Máquina", "Fecha", "Franja Horaria", "Estado"
    };

    private static final int COL_ID = 0;
    private static final int COL_STUDENT = 1;
    private static final int COL_DOCUMENT = 2;
    private static final int COL_MACHINE = 3;
    private static final int COL_DATE = 4;
    private static final int COL_TIME_SLOT = 5;
    private static final int COL_STATUS = 6;

    public ByteArrayInputStream export(List<Booking> bookings) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Reservas");
            createHeaderRow(workbook, sheet);
            fillDataRows(workbook, sheet, bookings);
            autoSizeColumns(sheet);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Error al generar el archivo Excel de reservas", e);
        }
    }

    private void createHeaderRow(Workbook workbook, Sheet sheet) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setFontHeightInPoints((short) 12);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void fillDataRows(Workbook workbook, Sheet sheet, List<Booking> bookings) {
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.getCreationHelper()
                .createDataFormat().getFormat("dd/mm/yyyy"));

        int rowNum = 1;
        for (Booking booking : bookings) {
            Row row = sheet.createRow(rowNum++);

            setCellValue(row, COL_ID, booking.getId());
            setCellValue(row, COL_STUDENT, formatStudentName(booking));
            setCellValue(row, COL_DOCUMENT, extractDocumentNumber(booking));
            setCellValue(row, COL_MACHINE, extractMachineName(booking));
            setCellDateValue(row, COL_DATE, booking.getDate(), dateStyle);
            setCellValue(row, COL_TIME_SLOT, extractTimeSlotName(booking));
            setCellValue(row, COL_STATUS, extractBookingStatusName(booking));
        }
    }

    private void setCellValue(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value : "");
    }

    private void setCellDateValue(Row row, int column, java.time.LocalDate date,
                                   CellStyle dateStyle) {
        Cell cell = row.createCell(column);
        if (date != null) {
            cell.setCellValue(date);
            cell.setCellStyle(dateStyle);
        } else {
            cell.setCellValue("");
        }
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String formatStudentName(Booking booking) {
        if (booking.getStudent() == null) {
            return "";
        }
        String firstName = booking.getStudent().getFirstName();
        String lastName = booking.getStudent().getLastName();
        String secondLastName = booking.getStudent().getSecondLastName();

        StringBuilder sb = new StringBuilder();
        if (firstName != null) sb.append(firstName).append(" ");
        if (lastName != null) sb.append(lastName).append(" ");
        if (secondLastName != null) sb.append(secondLastName);
        return sb.toString().trim();
    }

    private String extractDocumentNumber(Booking booking) {
        if (booking.getStudent() == null) {
            return "";
        }
        return booking.getStudent().getDocumentNumber();
    }

    private String extractMachineName(Booking booking) {
        if (booking.getMachine() == null) {
            return "";
        }
        return booking.getMachine().getName();
    }

    private String extractTimeSlotName(Booking booking) {
        if (booking.getTimeSlot() == null) {
            return "";
        }
        return booking.getTimeSlot().getName();
    }

    private String extractBookingStatusName(Booking booking) {
        if (booking.getBookingStatus() == null) {
            return "";
        }
        return booking.getBookingStatus().getName();
    }
}