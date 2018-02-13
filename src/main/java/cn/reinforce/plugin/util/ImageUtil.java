package cn.reinforce.plugin.util;

import com.mortennobel.imagescaling.ResampleOp;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * 图片处理工具类：
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * <p>
 * 依赖：
 * <dependency>
 * <groupId>com.mortennobel</groupId>
 * <artifactId>java-image-scaling</artifactId>
 * <version>0.8.6</version>
 * </dependency>
 *
 * @author 幻幻Fate
 * @create 2016-09-06
 * @since 1.0.0
 */
public class ImageUtil {

    /**
     * 几种常见的图片格式
     */
    public static final String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static final String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static final String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static final String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static final String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static final String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class.getName());

    private ImageUtil() {
        super();
    }

    /**
     * 按文件名判断是否是图片
     *
     * @param type
     * @return
     */
    public static boolean isImage(String type) {
        return IMAGE_TYPE_BMP.equals(type) || IMAGE_TYPE_JPG.equals(type) || IMAGE_TYPE_JPEG.equals(type) || IMAGE_TYPE_GIF.equals(type) || IMAGE_TYPE_PNG.equals(type);
    }

    /**
     * 重新设置图片大小
     *
     * @param originalFile
     * @param resizedFile
     * @param newWidth
     * @param newHeight
     * @param quality
     */
    public static void resize(File originalFile, File resizedFile, int newWidth, int newHeight, float quality) {
        BufferedImage inputBufImage = null;
        try {
            inputBufImage = ImageIO.read(originalFile);
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        ResampleOp resampleOp = new ResampleOp(newWidth, newHeight);// 转换
        BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
        try {
            ImageIO.write(rescaledTomato, IMAGE_TYPE_PNG, resizedFile);
        } catch (IOException e) {
            LOG.error("图片写入失败", e);
        }
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     *
     * @param srcImageFile 源图像地址
     * @param result       切片后的图像地址
     * @param x            目标切片起点坐标X
     * @param y            目标切片起点坐标Y
     * @param width        目标切片宽度
     * @param height       目标切片高度
     * @throws IOException
     */
    public static void cut(File srcImageFile, File result, int x, int y, int width, int height) {
        ImageInputStream iis = null;
        try {
            iis = ImageIO.createImageInputStream(srcImageFile);
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
        ImageReader reader = iterator.next();
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rectangle = new Rectangle(x, y, width, height);
        param.setSourceRegion(rectangle);
        BufferedImage bi = null;
        try {
            bi = reader.read(0, param);
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        try {
            ImageIO.write(bi, IMAGE_TYPE_PNG, result);
        } catch (IOException e) {
            LOG.error("图片写入失败", e);
        } finally {
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    LOG.error("文件关闭失败", e);
                }
            }
        }
    }

    /**
     * 图像切割（指定切片的宽度和高度）
     *
     * @param srcImageFile 源图像地址
     * @param descDir      切片目标文件夹
     * @param destWidth    目标切片宽度。默认200
     * @param destHeight   目标切片高度。默认150
     */
    public static void cut(String srcImageFile, String descDir, int destWidth, int destHeight) {

        //设置默认宽高
        destWidth = destWidth <= 0 ? 200 : destWidth; // 切片宽度

        destHeight = destHeight <= 0 ? 150 : destHeight; // 切片高度

        // 读取源图像
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(srcImageFile));
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        int srcWidth = bi.getHeight(); // 源图宽度
        int srcHeight = bi.getWidth(); // 源图高度
        if (srcWidth > destWidth && srcHeight > destHeight) {
            Image img;
            ImageFilter cropFilter;
            Image image = bi.getScaledInstance(srcWidth, srcHeight,
                    Image.SCALE_DEFAULT);
            int cols; // 切片横向数量
            int rows; // 切片纵向数量
            // 计算切片的横向和纵向数量
            if (srcWidth % destWidth == 0) {
                cols = srcWidth / destWidth;
            } else {
                cols = srcWidth / destWidth + 1;
            }
            if (srcHeight % destHeight == 0) {
                rows = srcHeight / destHeight;
            } else {
                rows = srcHeight / destHeight + 1;
            }
            // 循环建立切片，改进的想法:是否可用多线程加快切割速度
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    // 四个参数分别为图像起点坐标和宽高，即: CropImageFilter(int x,int y,int width,int height)
                    cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
                    img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                    BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics g = tag.getGraphics();
                    g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                    g.dispose();
                    // 输出为文件
                    try {
                        ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
                    } catch (IOException e) {
                        LOG.error("图片写入失败", e);
                    }
                }
            }
        }
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param srcImageFile  源图像地址
     * @param formatName    包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public static void convert(String srcImageFile, String formatName,
                               String destImageFile) {
        File f = new File(srcImageFile);
        f.canRead();
        f.canWrite();
        BufferedImage src = null;
        try {
            src = ImageIO.read(f);
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        try {
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (IOException e) {
            LOG.error("图片写入失败", e);
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     */
    public static void gray(String srcImageFile, String destImageFile) {
        BufferedImage src = null;
        try {
            src = ImageIO.read(new File(srcImageFile));
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        src = op.filter(src, null);
        try {
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
            LOG.error("图片写入失败", e);
        }
    }

    /**
     * 添加文字水印
     *
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName  字体名称， 如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize  字体大小，单位为像素
     * @param color     字体颜色
     * @param x         水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y         水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */

    public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x,
                                 int y, float alpha) {
        File file = new File(targetImg);

        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.setColor(color);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));

        int width2 = fontSize * getLength(pressText);
        int height2 = fontSize;
        int widthDiff = width - width2;
        int heightDiff = height - height2;
        if (x < 0) {
            x = widthDiff / 2;
        } else if (x > widthDiff) {
            x = widthDiff;
        }
        if (y < 0) {
            y = heightDiff / 2;
        } else if (y > heightDiff) {
            y = heightDiff;
        }

        g.drawString(pressText, x, y + height2);
        g.dispose();
        try {
            ImageIO.write(bufferedImage, IMAGE_TYPE_JPG, file);
        } catch (IOException e) {
            LOG.error("图片写入失败", e);
        }
    }

    /**
     * 添加图片水印
     *
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param waterImg  水印图片路径，如：C://myPictrue//logo.png
     * @param x         水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y         水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static void pressImage(String targetImg, String waterImg,
                                  int x, int y, float alpha) {
        File file = new File(targetImg);
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);

        Image waterImage = null; // 水印文件
        try {
            waterImage = ImageIO.read(new File(waterImg));
        } catch (IOException e) {
            LOG.error("图片读取失败", e);
        }
        int width2 = waterImage.getWidth(null);
        int height2 = waterImage.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));

        int widthDiff = width - width2;
        int heightDiff = height - height2;
        if (x < 0) {
            x = widthDiff / 2;
        } else if (x > widthDiff) {
            x = widthDiff;
        }
        if (y < 0) {
            y = heightDiff / 2;
        } else if (y > heightDiff) {
            y = heightDiff;
        }
        g.drawImage(waterImage, x, y, width2, height2, null); // 水印文件结束
        g.dispose();
        try {
            ImageIO.write(bufferedImage, IMAGE_TYPE_JPG, file);
        } catch (IOException e) {
            LOG.error("图片写入失败", e);
        }
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     *
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }
}
