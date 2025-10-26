package com.grupoK.web_service_server.rest.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoK.connector.database.entities.Donacion;
import com.grupoK.connector.database.entities.Solicitud;
import com.grupoK.connector.database.entities.SolicitudDonacion;
import com.grupoK.connector.database.entities.enums.TipoCategoria;
import com.grupoK.connector.database.serviceImp.SolicitudService;
import com.grupoK.web_service_server.rest.model.FiltroSolicitudInput;
import com.grupoK.web_service_server.rest.model.InformeSolicitudDetalleDto;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudRestController {
    @Autowired
    private SolicitudService solicitudService;

    @PostMapping(value = "/informes", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> generarInformeExcel(@RequestBody FiltroSolicitudInput filtro) {

        try {
            LocalDateTime fechaDesdeDate = filtro.getFechaDesde() != null
                    ? LocalDate.parse(filtro.getFechaDesde()).atStartOfDay()
                    : null;
            LocalDateTime fechaHastaDate = filtro.getFechaHasta() != null
                    ? LocalDate.parse(filtro.getFechaHasta()).atStartOfDay()
                    : null;

            Boolean eliminado;
            if (filtro.getEliminado() == null || filtro.getEliminado().isEmpty()) {
                eliminado = null;
            } else if (filtro.getEliminado().equalsIgnoreCase("si")) {
                eliminado = true;
            } else {
                eliminado = false;
            }
            List<Solicitud> solicitudes = solicitudService.find(
                    filtro.getCategoria(),
                    fechaDesdeDate,
                    fechaHastaDate,
                    eliminado
            );

            List<InformeSolicitudDetalleDto> informes = new ArrayList<>();
    		for (Solicitud solicitud : solicitudes) {
    			try {
    			List<SolicitudDonacion> donationsAssociated = solicitudService.findAllDonationsAssociatedByRequest(solicitud);
    			List<Donacion> donaciones = donationsAssociated.stream()
    	                .map(SolicitudDonacion::getDonacion)
    	                .filter(don -> filtro.getCategoria() == null || 
    	                        filtro.getCategoria().equals(don.getCategoria().getDescripcion()))
    	                .toList();

                    String eliminadoINF = solicitud.getActiva() ? "NO" : "SI";
                    Boolean recibida = solicitud.getOrganizacionSolicitante().getId() != 1;

                    for (Donacion d : donaciones) {
                    	
                    	TipoCategoria categoria = d.getCategoria().getDescripcion();
        				String tipo = solicitud.getOrganizacionSolicitante().getId()==1? "Enviada" : "Recibida";
                    	InformeSolicitudDetalleDto informeNuevo = 
                    				new InformeSolicitudDetalleDto(categoria.getDescription(), solicitud.getFechaAlta(), d.getDescripcion(), d.getCantidad(),!(solicitud.getActiva()),d.getUsuarioAlta(), d.getUsuarioModificacion(),tipo);
                    	informes.add(informeNuevo);
                        
                    }

                } catch (Exception e) {
                    System.out.println("Solicitud sin donaciones: " + e.getMessage());
                }
            }

            // === Generación del Excel ===
            byte[] excelBytes = generarExcelPorCategoria(informes);

            // === Respuesta HTTP para descarga ===
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=informe_solicitudes.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(excelBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔹 Genera un Excel con una hoja por cada categoría
    private byte[] generarExcelPorCategoria(List<InformeSolicitudDetalleDto> informes) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {

            // Agrupar por categoría
            Map<String, List<InformeSolicitudDetalleDto>> agrupado = informes.stream()
                    .collect(Collectors.groupingBy(InformeSolicitudDetalleDto::getCategoria));

            for (Map.Entry<String, List<InformeSolicitudDetalleDto>> entry : agrupado.entrySet()) {
                String categoria = entry.getKey();
                Sheet sheet = workbook.createSheet(categoria);

                // Encabezados
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Fecha de Alta");
                header.createCell(1).setCellValue("Descripcion");
                header.createCell(2).setCellValue("Cantidad");
                header.createCell(3).setCellValue("Eliminado");
                header.createCell(4).setCellValue("Usuario Alta");
                header.createCell(5).setCellValue("Usuario Modificacion");
                header.createCell(6).setCellValue("Origen");
                int rowNum = 1;
                for (InformeSolicitudDetalleDto dto : entry.getValue()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(dto.getFechaAlta().toString());
                    row.createCell(1).setCellValue(dto.getDescripcion());
                    row.createCell(2).setCellValue(dto.getCantidad());
                    row.createCell(3).setCellValue(dto.getEliminado() ? "Sí" : "No");
                    row.createCell(4).setCellValue(dto.getUsuarioAlta().getUsername());
                    row.createCell(5).setCellValue(dto.getUsuarioModificacion().getUsername());
                    row.createCell(6).setCellValue(dto.getTipo());
                }

                // Ajuste automático de columnas
                for (int i = 0; i < 4; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }
}

