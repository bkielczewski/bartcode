package uk.co.bartcode.service.image

import org.springframework.stereotype.Service
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO

@Service
class ImagePreviewService {

    fun writePreview(width: Int, height: Int, formatName: String, inputStream: InputStream, outputStream: OutputStream) {
        val original = ImageIO.read(inputStream)
        val scaled = scaleTo(width, height, original)
        val preview = getPreviewImage(width, height, scaled)
        ImageIO.write(preview, formatName, outputStream)
    }

    private fun scaleTo(width: Int, height: Int, original: BufferedImage): BufferedImage {
        val dimension = getScaledDimension(width, height, original)
        val scaled = BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB)
        scaled.createGraphics().drawImage(
                original.getScaledInstance(dimension.width, dimension.height, BufferedImage.SCALE_SMOOTH),
                0, 0, null)
        return scaled
    }

    private fun getScaledDimension(width: Int, height: Int, image: BufferedImage): Dimension {
        val dimension = Dimension(width, height)
        val ratio = image.width.toDouble() / image.height
        if (ratio > 1 && image.width > width) {
            dimension.setSize(Math.round(width * ratio).toDouble(), height.toDouble())
        } else if (ratio < 1 && image.height > height) {
            dimension.setSize(width.toDouble(), Math.round(height / ratio).toDouble())
        }
        return dimension
    }

    private fun getPreviewImage(width: Int, height: Int, original: BufferedImage): BufferedImage {
        val preview = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        preview.createGraphics().drawImage(
                original.getSubimage(
                        getOffset(original.width, width), getOffset(original.height, height),
                        Math.min(original.width, width), Math.min(original.height, height)),
                0, 0, null)
        return preview
    }

    private fun getOffset(d: Int, t: Int): Int = if (d > t) Math.floorDiv(d - t, 2) else 0

}