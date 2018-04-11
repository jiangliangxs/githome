package test.pdf;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TestPdfUtil {

	public static String FILE_DIR = "C:/Users/张军/Desktop/新建文件夹/11/createSamplePDF.pdf";

	public static void main(String[] args) throws Exception {
		createPDF();
	}

	/**
	 * @throws Exception
	 * 
	 * @author ChenY201
	 * @time 2015-11-21 下午4:54:39
	 * 
	 */
	public static void createPDF() throws Exception {
		/** pdf文档中中文字体的设置，注意一定要添加iTextAsian.jar包 */
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font titleFont = new Font(bfChinese, 14, Font.BOLD);// 加入document：
		Font chineseFont = new Font(bfChinese, 12, Font.NORMAL);// 加入document：

		// 步骤 1: 创建一个document对象，大小为A4，上下左右边距都为36
		Document document = new Document(PageSize.A4, 35, 35, 20, 20);
		// 步骤 2:
		// 我们为document创建一个监听，并把PDF流写到文件中
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR));
		// PDF版本(默认1.4)
		writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
		// 步骤 3:打开文档
		document.open();

		// ----------------------------- PDF内容开始 ------------------------------
		Paragraph title = new Paragraph("余额报告", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		addBlankRow(document, 2);
		List<String> list = new ArrayList<String>();
		list.add("客户： 东吴人寿保险股份有限公司-万能产品");
		list.add("交易帐号：0007");
		list.add("客户账号：TH0000000077");
		list.add("网点：直销中心网点");
		PdfPTable table1 = new PdfPTable(1);
		addCell(table1, chineseFont, list);
		document.add(table1);

		addBlankRow(document, 2);
		List<String> list2 = new ArrayList<String>();
		list2.add("报告截止时间：2015-11-21");
		list2.add("产品代码： 000003");
		list2.add("产品名称： 得惠2号资产管理产品");
		list2.add("份额余额：31,000,000.00");
		list2.add("单位净值：1.0000");
		list2.add("参考市值：31,000,000.00");
		PdfPTable table2 = new PdfPTable(1);
		addCell(table2, chineseFont, list2);
		document.add(table2);

		addBlankRow(document, 1);
		Paragraph p1 = new Paragraph("网点签章", chineseFont);
		p1.setAlignment(Element.ALIGN_RIGHT);
		document.add(p1);

		Image sign = Image.getInstance("C:/Users/颖/Desktop/新建文件夹/11/sign.png");
		sign.setAlignment(Element.ALIGN_RIGHT);
		sign.scaleToFit(140, 120);// 大小
		document.add(sign);

		// addBlankRow(document, 2);
		Paragraph p2 = new Paragraph("2015 年   11 月   21 日", chineseFont);
		p2.setAlignment(Element.ALIGN_RIGHT);
		document.add(p2);

		addBlankRow(document, 2);
		Paragraph p3 = new Paragraph("备注：", chineseFont);
		document.add(p3);

		Paragraph p4 = new Paragraph("1、账户余额报告中的单位净值为报告截止日的产品单位净值。参考市值为报告截止日产品份额余额的市值，参考市值=单位净值*份额余额", chineseFont);
		document.add(p4);
		Paragraph p5 = new Paragraph("2、产品投资于证券市场，单位净值会随着证券市场行情变化而上下波动，您的产品份额存在下跌的可能。产品投资有风险，请您慎重决策。", chineseFont);
		document.add(p5);

		// ----------------------------- PDF内容结束 ------------------------------
		// 步骤 5:关闭文档
		document.close();
		System.out.println("创建PDF结束");
	}

	public static void addBlankRow(Document document, int num) throws DocumentException {
		for (int i = 0; i < num; i++) {
			document.add(new Paragraph(" "));
		}
	}

	public static void addCell(PdfPTable table, Font chineseFont, List<String> list) {
		int end = list.size() - 1;
		table.setWidthPercentage(100);// table100%
		for (int i = 0; i < list.size(); i++) {
			PdfPCell cell = new PdfPCell();
			cell.addElement(new Paragraph(list.get(i), chineseFont));
			if (i == 0 && i != end) {
				cell.disableBorderSide(Rectangle.BOTTOM);
			} else if (i == end) {
				cell.disableBorderSide(Rectangle.TOP);
				cell.setPaddingBottom(10);
			} else {
				cell.disableBorderSide(Rectangle.TOP);
				cell.disableBorderSide(Rectangle.BOTTOM);
			}
			cell.setPaddingLeft(10);
			cell.setPaddingRight(10);
			table.addCell(cell);
		}
	}
}
