package printer;

import java.awt.BasicStroke;  
import java.awt.BorderLayout;  
import java.awt.Component;  
import java.awt.Dimension;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.awt.print.PageFormat;  
import java.awt.print.Paper;
import java.awt.print.Printable;  
import java.awt.print.PrinterException;  
import java.awt.print.PrinterJob;  
  
import javax.swing.JButton;  
import javax.swing.JDialog;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
import javax.swing.JScrollPane;  
import javax.swing.WindowConstants;  

import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import SensorBase.LMSLog;
  
/** 
 * 使用了原始的分页方式去渲染JTextArea，提供了打印预览机制。 
 * <p> 
 * 事实上，我们还可以通过其他方式打印JTextArea： 
 * <ol> 
 * <li>{@code Component.print(Graphics g);} or 
 * {@code Component.printAll(Graphics g);}</li> 
 * <li>{@code Component.paint(Graphics g);} or 
 * {@code Component.paintAll(Graphics g);} whose rending may be slightly 
 * different to the previous method (for example, <code>JFrame</code>)</li> 
 * <li>{@code JTable.print();} or {@code JTextComponent.print();} provide 
 * especially powerful and convenient printing mechanism</li> 
 * </ol> 
 *  
 * @author Gaowen 
 */  
public class PrintUIComponent extends JPanel implements ActionListener,  
        Printable {  
	String DEBUG_TAG = "PrintUIComponent";
	
    private static final long serialVersionUID = 4797002827940419724L;  
    private static JFrame frame;  
    private PrinterJob jobPreview;  
    private PrinterJob jobTrue;  
      
    public JButton printPreviewButton;
    public JButton printButton;
    
    private final int PAGE_W = 595;
    private final int PAGE_H = 842;
    
    PrinterFormatCarContour printerFormatCarContour = new PrinterFormatCarContour();
    public PrintUIComponent(ContourDetectionTabPanelMain _printPanel) {  
        super(new BorderLayout());  
        
        printButton = new JButton("打印");  
        printButton.setName("printButton");  
        printButton.addActionListener(this); 
        
        /* Initialize PrinterJob */  
        jobTrue = PrinterJob.getPrinterJob();  
        jobTrue.setJobName("外廓尺寸检测仪");// 出现在系统打印任务列表  
        jobTrue.setPrintable(this); 
    	
        //=========================================================================
        printPreviewButton = new JButton("打印预览");  
        printPreviewButton.setName("printPreviewButton");  
        printPreviewButton.addActionListener(this);  

        /* Initialize PrinterJob */  
        jobPreview = PrinterJob.getPrinterJob();  
        jobPreview.setJobName("外廓尺寸检测仪");// 出现在系统打印任务列表  
        jobPreview.setPrintable(this); 

        //=========================================================================
        printerFormatCarContour.setPanel(_printPanel);        
    }  
  
    @Override  
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)  
            throws PrinterException {  
    	
    	if (pageIndex > 0) {  
            return NO_SUCH_PAGE;  
        }  
    	
        /* 
         * It is safe to use a copy of this graphics as this will not involve 
         * changes to it. 
         */  
        Graphics2D g2 = (Graphics2D) graphics.create();  

        pageFormat.setOrientation(PageFormat.PORTRAIT);  
  
        /* (0,0) is outside the imageable area, translate to avoid clipping */  
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());  
        Paper paper = pageFormat.getPaper(); 
        paper.setSize(PAGE_W, PAGE_H); 
        paper.setImageableArea(0, 0, PAGE_W, PAGE_H);
        pageFormat.setPaper(paper); 
        double pageW= pageFormat.getImageableWidth();
        double pageH = pageFormat.getImageableHeight(); 
        
    	LMSLog.d(DEBUG_TAG,"pageW="+pageW+" pageH="+pageH);

    	printerFormatCarContour.drawPrint(g2,pageW,pageH);
  
        /* dispose of the graphics copy */  
//        g2.dispose();  
  
        return PAGE_EXISTS;  
    }  
  
    @Override  
    public void actionPerformed(ActionEvent e) {  
        Object actionEventSource = e.getSource();  
        if (actionEventSource instanceof JButton) {  
            JButton button = (JButton) actionEventSource;  
            if (button.getName().equals("printButton")) {  

                boolean ok = jobTrue.printDialog();  
                if (ok) {  
                    try {  
                    	jobTrue.print();  
                    } catch (PrinterException ex) {  
                        /* The job did not successfully complete */  
                        ex.printStackTrace();  
                    }  
                }  
            } else if (button.getName().equals("printPreviewButton")) { 

                createAndShowPreviewDialog();  
            }  
        }  
    }  
  
    private void createAndShowPreviewDialog() {  
        JDialog previewDialog = new JDialog(frame, "预览窗口", true);  
        JPanel contentPane = new JPanel(new BorderLayout());  
        PreviewArea previewArea = new PreviewArea();  
        previewArea.addMouseListener(new PreviewAreaMouseAdapter(previewArea));  
        JScrollPane scrollPane = new JScrollPane(previewArea);  
        contentPane.add(scrollPane, BorderLayout.CENTER);  

        previewDialog.setContentPane(contentPane);  
        previewDialog.setSize(PAGE_W, PAGE_H);  
        previewDialog  
                .setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
        previewDialog.setVisible(true);  
    }  
  
    private class PreviewArea extends Component {  
        private PageFormat pageFormat;  
        private int pageIndex;  
        private int w;  
        private int h;  
        private final int marginX = 15;  
        private final int marginY = 20;  
  
        private PreviewArea() {  
            pageFormat = jobPreview.pageDialog(jobPreview.defaultPage());  
            pageIndex = 0;  
            w = (int) pageFormat.getWidth();  
            h = (int) pageFormat.getHeight();  
        }  
  
        private int getPageIndex() {  
            return pageIndex;  
        }  
  
        private void setPageIndex(int pageIndex) {  
            this.pageIndex = pageIndex;  
        }  
  
        @Override  
        public Dimension getPreferredSize() {  
            return new Dimension(w + 2 * marginX, h + 2 * marginY);  
        }  
  
        @Override  
        public void paint(Graphics g) {  
        	LMSLog.d(DEBUG_TAG,"paint___");

            Graphics2D g2 = (Graphics2D) g.create();  
            g2.translate(marginX, marginY);  
            g2.drawRect(0, 0, w, h);  
            int ix = (int) (pageFormat.getImageableX() - 1);  
            int iy = (int) (pageFormat.getImageableY() - 1);  
            int iw = (int) (pageFormat.getImageableWidth() + 1);  
            int ih = (int) (pageFormat.getImageableHeight() + 1);  
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND,  
                    BasicStroke.JOIN_ROUND, 10f, new float[] { 5, 5 }, 0f));  
            g2.drawRect(ix, iy, iw, ih);  
            try {  
                PrintUIComponent.this.print(g2, pageFormat, pageIndex);  
            } catch (PrinterException e) {  
                e.printStackTrace();  
            }  
            g2.dispose();  
        }  
    }  
  
    private class PreviewAreaMouseAdapter extends MouseAdapter {  
        private PreviewArea previewArea;  
  
        private PreviewAreaMouseAdapter(PreviewArea previewArea) {  
            this.previewArea = previewArea;  
        }  
  
        @Override  
        public void mouseClicked(MouseEvent e) {

        }  
    }  
}  