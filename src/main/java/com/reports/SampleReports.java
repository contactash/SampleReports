package com.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = { "/SampleReports" })
public class SampleReports extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public SampleReports() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = request.getServletContext();
		String reportTemplateFilePath = ctx.getRealPath("school.jrxml");
        System.out.println("***** DEBUG POINT ******" +reportTemplateFilePath);
        ByteArrayOutputStream reportStream = getReportOutputStream(reportTemplateFilePath);
		response.setContentLength(reportStream.size());
		response.setContentType("application/pdf");
		ServletOutputStream servletOutputStream =  response.getOutputStream();
		reportStream.writeTo(servletOutputStream);
		servletOutputStream.flush();
	}

	private ByteArrayOutputStream getReportOutputStream(String reportTemplateFilePath) {
		JasperReport jasperReport;
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		JasperPrint jasperPrint;
		try {
			jasperReport = JasperCompileManager.compileReport(reportTemplateFilePath);
			Map<String, Object> reportParameters = new HashMap<String, Object>();
			jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, getConnection());
			JRExporter exporter = new JRPdfExporter();
//			JRExporter exporter = new JRDocxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputstream);
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return outputstream;
	}
	
	

	private static Connection getConnection()  {
		Connection conn = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/college?user=ash&password=password");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
