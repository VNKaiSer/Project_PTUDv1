package bus.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dto.DTO_PhieuLuongCaNhan;
import dto.DTO_PhieuLuongCongNhan;
import dto.DTO_PhieuLuongNhanVien;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfFileExporter {
    private static final DecimalFormat df = new DecimalFormat("###,###VNĐ");

    public static void exportPDF(DTO_PhieuLuongCaNhan pl) {

        // Tạo đối tượng tài liệu
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        Font FontTieuDe = null;
        Font FontNomal = null;
        Font fontBold = null;
        Font fontPDFSmall = null;
        Font fontPDFSmallItalic = null;

        // top setSpacingBefore
        // bottom setSpacingAfter
        // left setIndentationLeft
        // right setIndentationRight

        FontTieuDe = FontFactory.getFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16, Font.BOLD, BaseColor.RED);
        Font FontTenCT = FontFactory.getFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 20, Font.BOLD);
        FontNomal = FontFactory.getFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
        fontPDFSmallItalic = FontFactory.getFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.ITALIC);
        fontBold = FontFactory.getFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13, Font.BOLD);

        try {

            // Tạo đối tượng PdfWriter
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("PDF Files", "*.pdf");
            fc.getExtensionFilters().add(ef);
            fc.setInitialFileName(pl.getMaPhieuLuong());
            File file = fc.showSaveDialog(null);
            if (file != null) {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                // Mở file để thực hiện ghi
                document.open();
                Paragraph titleCT = new Paragraph("CÔNG TY MAY MẶC THIÊN NAM", FontTenCT);
                titleCT.setAlignment(Element.ALIGN_CENTER);
                Date d = new Date();
                int y = d.getYear() + 1900;
                int m = d.getMonth() + 1;
                Paragraph title = new Paragraph("PHIẾU LƯƠNG THÁNG " + m + "/" + y, FontTieuDe);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(titleCT);
                document.add(title);
                Paragraph underLine = new Paragraph("---------------------------------------------------------------------------------------\n\n",
                        fontPDFSmallItalic);
                underLine.setAlignment(Element.ALIGN_CENTER);
                document.add(underLine);

                // bảng lương

                PdfPTable t = new PdfPTable(6);

                t.setWidthPercentage(110);
                t.setPaddingTop(10);
                t.setSpacingAfter(25);
                t.setSpacingBefore(25);

                PdfPCell cell;
                if (pl instanceof DTO_PhieuLuongNhanVien nv) {
                    cell = new PdfPCell(new Phrase("Họ tên: " + nv.getNhanVien().getTenNhanVien(), FontNomal));
                    cell.setColspan(4);
                    cell.setFixedHeight(20);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Mã nhân viên: " + nv.getNhanVien().getMaNhanVien(), FontNomal));
                    cell.setColspan(2);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Chức danh: Nhân Viên", FontNomal));
                    cell.setColspan(2);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Ngày vào làm: " + new SimpleDateFormat("dd/MM/yyyy").format(nv.getNhanVien().getNgayVaoLam()), FontNomal));
                    cell.setColspan(2);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Ca: Sáng - May", FontNomal));
                    cell.setColspan(2);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Tổng công", fontBold));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(nv.getSoNgayCong() + "", fontBold));
                    t.addCell(cell);

                    cell = new PdfPCell(new Phrase("Tiền thưởng", fontBold));
                    cell.setColspan(2);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Khấu trừ", fontBold));
                    cell.setColspan(2);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Đi làm: ", FontNomal));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(pl.getCoMat() + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Thưởng chuyên cần: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTienThuong()) + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tiền phạt: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTienPhat()) + "", FontNomal));
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Vắng: ", FontNomal));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(pl.getVang() + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tiền phụ cấp: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(pl.getTienPhuCap()) + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tạm ứng: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTamUng()) + "", FontNomal));
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Phép: ", FontNomal));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(pl.getPhep() + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tiền trách nhiệm: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(pl.getTienTrachNhiem()) + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Thuế: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getThue()) + "", FontNomal));
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Tổng lương: ", FontNomal));
                    cell.setColspan(2);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTongLuong()) + "", FontNomal));
                    cell.setColspan(4);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Thực nhận: ", FontNomal));
                    cell.setColspan(2);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getThucNhan()) + "", FontNomal));
                    cell.setColspan(4);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Ghi chú: ", FontNomal));
                    cell.setColspan(6);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                } else {
                    DTO_PhieuLuongCongNhan nv = (DTO_PhieuLuongCongNhan) pl;
                    cell = new PdfPCell(new Phrase("Họ tên: " + nv.getCongNhan().getMaCongNhan(), FontNomal));
                    cell.setColspan(4);
                    cell.setFixedHeight(20);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Mã công nhân: " + nv.getCongNhan().getMaCongNhan(), FontNomal));
                    cell.setColspan(2);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Chức danh: Công Nhân", FontNomal));
                    cell.setColspan(2);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Ngày vào làm: " + new SimpleDateFormat("dd/MM/yyyy").format(nv.getCongNhan().getNgayVaoLam()), FontNomal));
                    cell.setColspan(2);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Ca: Sáng - May", FontNomal));
                    cell.setColspan(2);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Tổng công", fontBold));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(nv.getSoNgayCong() + "", fontBold));
                    t.addCell(cell);

                    cell = new PdfPCell(new Phrase("Tiền thưởng", fontBold));
                    cell.setColspan(2);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Khấu trừ", fontBold));
                    cell.setColspan(2);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Đi làm: ", FontNomal));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(pl.getCoMat() + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Thưởng chuyên cần: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTienThuong()) + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tiền phạt: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTienPhat()) + "", FontNomal));
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Vắng: ", FontNomal));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(pl.getVang() + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tiền phụ cấp: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTienPhuCap()) + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tạm ứng: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTamUng()) + "", FontNomal));
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Phép: ", FontNomal));
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(pl.getPhep() + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Tiền trách nhiệm: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(pl.getTienTrachNhiem()) + "", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase("Thuế: ", FontNomal));
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getThue() * nv.getTongLuong()) + "", FontNomal));
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Tổng lương: ", FontNomal));
                    cell.setColspan(2);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getTongLuong()) + "", FontNomal));
                    cell.setColspan(4);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Thực nhận: ", FontNomal));
                    cell.setColspan(2);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                    cell = new PdfPCell(new Phrase(df.format(nv.getThucNhan()) + "", FontNomal));
                    cell.setColspan(4);
                    t.addCell(cell);

                    //
                    cell = new PdfPCell(new Phrase("Ghi chú: ", FontNomal));
                    cell.setColspan(6);
                    cell.setFixedHeight(20);
                    t.addCell(cell);
                }


                document.add(t);

                // Đóng File
                document.close();
                System.out.println("Write file succes!");
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


}